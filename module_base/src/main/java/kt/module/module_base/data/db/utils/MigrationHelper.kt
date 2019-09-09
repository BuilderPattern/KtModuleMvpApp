package kt.module.module_base.data.db.dao.utils

import android.database.Cursor
import android.text.TextUtils
import android.util.Log
import kt.module.module_base.data.db.dao.DaoMaster
import org.greenrobot.greendao.AbstractDao
import org.greenrobot.greendao.database.Database
import org.greenrobot.greendao.internal.DaoConfig
import java.util.*

class MigrationHelper {

    fun migrate(db: Database, vararg daoClasses: Class<out AbstractDao<*, *>>) {
        generateTempTables(db, *daoClasses)
        DaoMaster.dropAllTables(db, true)
        DaoMaster.createAllTables(db, false)
        restoreData(db, *daoClasses)
    }

    private fun generateTempTables(db: Database, vararg daoClasses: Class<out AbstractDao<*, *>>) {
        for (i in daoClasses.indices) {
            val daoConfig = DaoConfig(db, daoClasses[i])

            var divider = ""
            val tableName = daoConfig.tablename
            val tempTableName = daoConfig.tablename + "_TEMP"
            val properties = ArrayList<String>()

            val createTableStringBuilder = StringBuilder()

            createTableStringBuilder.append("CREATE TABLE ").append(tempTableName).append(" (")

            for (j in daoConfig.properties.indices) {
                val columnName = daoConfig.properties[j].columnName

                if (getColumns(db, tableName).contains(columnName)) {
                    properties.add(columnName)

                    var type: String? = null

                    try {
                        type = getTypeByClass(daoConfig.properties[j].type)
                    } catch (exception: Exception) {
                    }

                    createTableStringBuilder.append(divider).append(columnName).append(" ").append(type)

                    if (daoConfig.properties[j].primaryKey) {
                        createTableStringBuilder.append(" PRIMARY KEY")
                    }

                    divider = ","
                }
            }
            createTableStringBuilder.append(");")

            db.execSQL(createTableStringBuilder.toString())

            val insertTableStringBuilder = StringBuilder()

            insertTableStringBuilder.append("INSERT INTO ").append(tempTableName).append(" (")
            insertTableStringBuilder.append(TextUtils.join(",", properties))
            insertTableStringBuilder.append(") SELECT ")
            insertTableStringBuilder.append(TextUtils.join(",", properties))
            insertTableStringBuilder.append(" FROM ").append(tableName).append(";")

            db.execSQL(insertTableStringBuilder.toString())
        }
    }

    fun restoreData(db: Database, vararg daoClasses: Class<out AbstractDao<*, *>>) {
        for (i in daoClasses.indices) {
            val daoConfig = DaoConfig(db, daoClasses[i])
            val tableName = daoConfig.tablename
            val tempTableName = daoConfig.tablename + "_TEMP"
            val properties = daoConfig.properties.indices
                .map { daoConfig.properties[it].columnName }
                .filter { getColumns(db, tempTableName).contains(it) }

            val insertTableStringBuilder = StringBuilder()

            insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (")
            insertTableStringBuilder.append(TextUtils.join(",", properties))
            insertTableStringBuilder.append(") SELECT ")
            insertTableStringBuilder.append(TextUtils.join(",", properties))
            insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";")

            val dropTableStringBuilder = StringBuilder()

            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName)

            db.execSQL(insertTableStringBuilder.toString())
            db.execSQL(dropTableStringBuilder.toString())
        }
    }


    @Throws(Exception::class)
    private fun getTypeByClass(type: Class<*>): String {
        if (type == String::class.java) {
            return "TEXT"
        }
        if (type == Long::class.java || type == Int::class.java || type == Long::class.javaPrimitiveType) {
            return "INTEGER"
        }
        if (type == Boolean::class.java) {
            return "BOOLEAN"
        }

        val exception = Exception(CONVERSION_CLASS_NOT_FOUND_EXCEPTION + " - Class: " + type.toString())
        throw exception
    }

    private fun getColumns(db: Database, tableName: String): List<String> {
        var columns: List<String> = ArrayList()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM $tableName limit 1", null)
            if (cursor != null) {
                columns = ArrayList(Arrays.asList(*cursor.columnNames))
            }
        } catch (e: Exception) {
            Log.v(tableName, e.message, e)
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return columns
    }

    companion object {
        private val CONVERSION_CLASS_NOT_FOUND_EXCEPTION =
            "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS"

        fun getInstance(): MigrationHelper {
            return Inner.instance
        }
    }

    private object Inner {
        val instance = MigrationHelper()
    }
}
