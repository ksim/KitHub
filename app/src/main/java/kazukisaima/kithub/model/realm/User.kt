package kazukisaima.kithub.model.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Kazuki Saima on 15/12/27.
 * All rights reserved by Vapes Inc.
 */

public open class User(
    @PrimaryKey public open var id: Int = 0,
    public open var name: String = "",
    public open var avatarURLString: String = "",
    public open var urlString: String = ""
): RealmObject() {

}
