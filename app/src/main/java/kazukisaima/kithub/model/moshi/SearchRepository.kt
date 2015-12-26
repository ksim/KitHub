package kazukisaima.kithub.model.moshi

import com.squareup.moshi.Json
import java.util.*

/**
 * Created by Kazuki Saima on 15/12/27.
 * All rights reserved by Vapes Inc.
 */

data class SearchRepositoryResponse(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "incomplete_results") val incompleteResults: Boolean,
    val items: List<RepositoryData>
)

data class RepositoryData(
    val id: Int,
    val name: String,
    @Json(name = "full_name") val fullName: String,
    val owner: UserData,
    val private: Boolean,
    @Json(name = "html_url") val htmlURLString: String,
    val description: String,
    @Json(name = "url") val urlString: String,
    @Json(name = "created_at") val createDate: Date,
    @Json(name = "updated_at") val updateDate: Date,
    @Json(name = "stargazers_count") val stargazersCount: Int,
    @Json(name = "watchers_count") val watchersCount: Int,
    val score: Double
)

data class UserData(
    @Json(name = "login") val userName: String,
    val id: Int,
    @Json(name = "avatar_url") val avatarURLString: String,
    @Json(name = "url") val urlString: String
)
