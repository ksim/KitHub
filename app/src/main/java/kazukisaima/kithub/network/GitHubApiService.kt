package kazukisaima.kithub.network

import kazukisaima.kithub.model.moshi.SearchRepositoryResponse
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

/**
 * Created by Kazuki Saima on 15/12/27.
 * All rights reserved by Vapes Inc.
 */

interface GitHubApiService {

    @GET("/search/repositories?sort=stars&order=desc")
    fun searchRepositories(@Query("q") query: String): Observable<SearchRepositoryResponse>
}

