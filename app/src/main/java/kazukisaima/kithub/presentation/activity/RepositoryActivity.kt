package kazukisaima.kithub.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import butterknife.bindView
import io.realm.Realm

import kazukisaima.kithub.R
import kazukisaima.kithub.model.realm.Repository
import kotlin.properties.Delegates

class RepositoryActivity : AppCompatActivity() {

    val textView: TextView by bindView(R.id.textView)

    private val realm: Realm by lazy {
        Realm.getInstance(this)
    }

    private var repository: Repository by Delegates.notNull()
    private enum class IntentExtraKeys {
        REPOSITORY_ID
    }

    companion object {
        fun start(context: Context, repositoryID: Int) {
            val intent = Intent(context, RepositoryActivity::class.java).apply {
                putExtra(IntentExtraKeys.REPOSITORY_ID.name, repositoryID)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_activity)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        intent.getIntExtra(IntentExtraKeys.REPOSITORY_ID.name, 0).let {
            repository = realm.where(Repository::class.java).equalTo("id", it).findFirst()
        }

        textView.text = repository.fullName
    }

}
