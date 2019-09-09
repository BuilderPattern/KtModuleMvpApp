package kt.module.module_base.data.db.utils;

import android.database.sqlite.SQLiteDatabaseLockedException;
import android.util.Log;
import kt.module.BaseApp;
import kt.module.module_base.data.db.dao.DaoSession;
import kt.module.module_base.data.db.table.ChildEntity;
import kt.module.module_base.data.db.table.ObjectEntity;
import org.greenrobot.greendao.AbstractDao;

import java.util.List;

public class DbUtils {

    private static final String TAG = DbUtils.class.getSimpleName();
    private static DbUtils instance;
    private DaoSession mDaoSession;

    private DbUtils() {
    }

    public static DbUtils getInstance() {
        if (instance == null) {
            instance = new DbUtils();
            instance.mDaoSession = BaseApp.Companion.getDaoSession();
        }
        return instance;
    }


    public <T> AbstractDao getAnyDaoByTable(Class<T> c) {
        if (c == ObjectEntity.class) {
            return instance.mDaoSession.getObjectEntityDao();
        } else if (c == ChildEntity.class) {
            return instance.mDaoSession.getChildEntityDao();
        }
        return null;
    }

    /**
     * 根据id查询
     * 一条数据
     *
     * @param id
     * @param c
     * @param <T>
     * @param <K>
     * @return
     */
    public <T, K> T queryById(K id, Class<T> c) {
        AbstractDao abstractDao = getAnyDaoByTable(c);
        return abstractDao != null ? (T) abstractDao.load(id) : null;
    }

    /**
     * 查询该表下的所有数据
     *
     * @param c
     * @param <T>
     * @return
     */
    public <T> List<T> queryAllData(Class<T> c) {
        AbstractDao abstractDao = getAnyDaoByTable(c);
        return abstractDao != null ? (List<T>) abstractDao.loadAll() : null;
    }

    /**
     * 条件查询数据
     * 返回List数据
     *
     * @param c
     * @param where
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> queryByParams(Class<T> c, String where, String... params) {
        AbstractDao abstractDao = getAnyDaoByTable(c);
        return abstractDao != null ? (List<T>) abstractDao.queryRaw(where, params) : null;
    }


    /**
     * 新增一条数据
     *
     * @param note
     * @param c
     * @param <T>
     * @return
     */
    public <T> long insert(T note, Class<T> c) {
        AbstractDao abstractDao = getAnyDaoByTable(c);
        return abstractDao != null ? abstractDao.insert(note) : -1;
    }

    /**
     * 新增数据集
     *
     * @param list
     * @param c
     * @param <T>
     */
    public <T> void insertList(List<T> list, Class<T> c) {
        if (list == null || list.isEmpty()) {
            return;
        }

        AbstractDao abstractDao = getAnyDaoByTable(c);
        if (abstractDao != null) {
            abstractDao.insertInTx(list);
        }
    }

    /**
     * 新增/更新一条数据
     *
     * @param note
     * @param c
     * @param <T>
     * @return
     */
    public <T> long insertOrReplace(T note, Class<T> c) {
        AbstractDao abstractDao = getAnyDaoByTable(c);
        return abstractDao != null ? abstractDao.insertOrReplace(note) : -1;
    }


    /**
     * 新增/更新数据集
     *
     * @param list
     * @param c
     * @param <T>
     */
    public <T> void insertOrReplaceList(List<T> list, Class<T> c) {
        if (list == null || list.isEmpty()) {
            return;
        }

        AbstractDao abstractDao = getAnyDaoByTable(c);
        if (abstractDao != null) {
            abstractDao.insertOrReplaceInTx(list);
        }
    }

    /**
     * 删除该条数据
     *
     * @param note
     * @param c
     * @param <T>
     */
    public <T> void delete(T note, Class<T> c) {
        AbstractDao abstractDao = getAnyDaoByTable(c);
        if (abstractDao != null) {
            abstractDao.deleteInTx(note);
        }
    }

    /**
     * 根据id删除
     *
     * @param id
     * @param c
     * @param <T>
     */
    public <T> void deleteById(long id, Class<T> c) {
        AbstractDao abstractDao = getAnyDaoByTable(c);
        if (abstractDao != null) {
            abstractDao.deleteByKey(id);
        }
    }

    /**
     * 根据id集合删除
     *
     * @param ids
     * @param c
     * @param <T>
     */
    public <T> void deleteByIdList(List<Long> ids, Class<T> c) {
        AbstractDao abstractDao = getAnyDaoByTable(c);
        if (abstractDao != null) {
            abstractDao.deleteByKeyInTx(ids);
        }
    }

    /**
     * 删除该表的全部数据
     *
     * @param c
     * @param <T>
     */
    public <T> void deleteAll(Class<T> c) {
        AbstractDao abstractDao = getAnyDaoByTable(c);
        if (abstractDao != null) {
            try {
                abstractDao.deleteAll();
            } catch (SQLiteDatabaseLockedException e) {
                e.printStackTrace();
            }
        }
    }
}