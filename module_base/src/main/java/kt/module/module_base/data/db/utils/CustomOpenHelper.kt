package kt.module.module_base.data.db.dao.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import kt.module.module_base.data.db.dao.ChildEntityDao
import kt.module.module_base.data.db.dao.DaoMaster
import kt.module.module_base.data.db.dao.ObjectEntityDao
import org.greenrobot.greendao.database.Database

class CustomOpenHelper : DaoMaster.DevOpenHelper {
    constructor(context: Context, name: String) : super(context, name) {}

    constructor(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?) : super(context, name, factory) {}

    override fun onUpgrade(db: Database?, oldVersion: Int, newVersion: Int) {
        Log.e("Kt_DB：", "Upgrading schema from version $oldVersion to $newVersion by migrating all tables data")
        //第二个及后面的参数为要升级的Dao文件.
        MigrationHelper.getInstance().migrate(db ?: return,
            ObjectEntityDao::class.java
            , ChildEntityDao::class.java)
    }
}
