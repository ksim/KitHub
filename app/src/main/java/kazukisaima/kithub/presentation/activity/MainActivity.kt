package kazukisaima.kithub.presentation.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ListViewCompat
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Menu
import butterknife.bindView
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView
import io.realm.*
import kazukisaima.kithub.R
import kazukisaima.kithub.model.realm.Repository
import kazukisaima.kithub.model.realm.User
import kazukisaima.kithub.network.ApiHelper
import kazukisaima.kithub.presentation.adapter.RepositoryAdapter
import org.jetbrains.anko.onItemClick
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val listView: ListViewCompat by bindView(R.id.listView)
    var searchView: SearchView by Delegates.notNull()

    private var adapter: RepositoryAdapter by Delegates.notNull()

    private val realm: Realm by lazy {
        Realm.getInstance(this)
    }
    private var realmChangeListener: RealmChangeListener by Delegates.notNull()
    private var data: RealmResults<Repository> by Delegates.notNull()

    private val api = ApiHelper()

    private var subscription: Subscription by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        realmChangeListener = RealmChangeListener {
            adapter.data = realm.where(Repository::class.java).contains("name", searchView.query.toString(), Case.INSENSITIVE).findAllSorted("stargazersCount", Sort.DESCENDING)
            adapter.notifyDataSetChanged()
        }
        realm.addChangeListener(realmChangeListener)
        data = realm.allObjectsSorted(Repository::class.java, "stargazersCount", Sort.DESCENDING)
        adapter = RepositoryAdapter(this, data)
        listView.adapter = adapter

        listView.onItemClick { adapterView, view, i, l ->
            if (adapterView != null) {
                val adapter = adapterView.adapter as RepositoryAdapter
                val item = adapter.getItem(i)
                RepositoryActivity.start(this, item.id)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription.unsubscribe()
        realm.removeChangeListener(realmChangeListener)
        realm.close()
   }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        if (menu != null) {
            val item = menu.findItem(R.id.menu_search)
            searchView = item.actionView as SearchView
            subscription = RxSearchView.queryTextChanges(searchView)
                .filter { !TextUtils.isEmpty(it) }
                .throttleLast(1000, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .concatMap { api.searchRepository(it.toString()) }
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { Observable.from(it.items) }
                .subscribe {
                    realm.beginTransaction()
                    val user = it.owner.run {
                        User(id, name, avatarURLString, urlString)
                    }
                    realm.copyToRealmOrUpdate(user)

                    val repo = it.run {
                        Repository(
                            id, name, fullName, user, private, description, urlString,
                            createDate, updateDate, stargazersCount, watchersCount, score
                        )
                    }
                    realm.copyToRealmOrUpdate(repo)
                    realm.commitTransaction()
                }
        }

        return true
    }
}

