package kazukisaima.kithub.network

import com.squareup.moshi.Moshi
import com.squareup.okhttp.OkHttpClient
import kazukisaima.kithub.model.moshi.DateAdapter
import retrofit.MoshiConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory

/**
 * Created by Kazuki Saima on 15/12/27.
 * All rights reserved by Vapes Inc.
 */

public class ApiClient {

    companion object {
        private val BASE_URL = "https://api.github.com"
    }

    public fun getGitHubApiService(): GitHubApiService {
        val moshi = Moshi.Builder()
                .add(DateAdapter.FACTORY)
                .build()
        return  Retrofit.Builder()
            .client(OkHttpClient())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create(GitHubApiService::class.java)
    }
}
