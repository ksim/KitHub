package kazukisaima.kithub.network

import com.squareup.okhttp.OkHttpClient
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
        return  Retrofit.Builder()
            .client(OkHttpClient())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(GitHubApiService::class.java)
    }
}
