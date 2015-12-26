package kazukisaima.kithub.presentation.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import kazukisaima.kithub.R
import kazukisaima.kithub.model.moshi.SearchRepositoryResponse
import kazukisaima.kithub.network.ApiHelper
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    override fun onStart() {
        super.onStart()

        ApiHelper().searchRepository("jquery")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object: Subscriber<SearchRepositoryResponse>(){
                override fun onNext(r: SearchRepositoryResponse?) {
                    Timber.d(r.toString())
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e.toString())
                }

                override fun onCompleted() {
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId ?: return true
        if (id == R.id.menu_search) {

        }

        return super.onOptionsItemSelected(item)
    }
}
