package kazukisaima.kithub

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by Kazuki Saima on 15/12/27.
 * All rights reserved by Vapes Inc.
 */

class KitHub: Application() {

    override fun onCreate() {
        super.onCreate()

        LeakCanary.install(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}