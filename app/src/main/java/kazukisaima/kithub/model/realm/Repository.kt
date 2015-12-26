package kazukisaima.kithub.model.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by Kazuki Saima on 15/12/27.
 * All rights reserved by Vapes Inc.
 */

public open class Repository(
    @PrimaryKey public open var id: Int = 0,
    public open var name: String = "",
    public open var fullName: String = "",
    public open var owner: User? = null,
    public open var private: Boolean = false,
    public open var description: String = "",
    public open var urlString: String = "",
    public open var createDate: Date = Date(),
    public open var updateDate: Date = Date(),
    public open var stargazersCount: Int = 0,
    public open var watchersCount: Int = 0,
    public open var score: Double = 0.0
): RealmObject() {

}

