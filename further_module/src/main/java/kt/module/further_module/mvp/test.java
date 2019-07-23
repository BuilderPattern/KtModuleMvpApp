package kt.module.further_module.mvp;

import kt.module.base_module.data.db.dao.ChildEntityDao;
import kt.module.base_module.data.db.table.ChildEntity;
import kt.module.base_module.data.db.utils.DbUtils;
import org.greenrobot.greendao.query.QueryBuilder;

public class test {
    public void tet() {
        ChildEntityDao childEntityDao = (ChildEntityDao) DbUtils.getInstance().getAnyDaoByTable(ChildEntity.class);
        QueryBuilder<ChildEntity> queryBuilder = childEntityDao.queryBuilder();
        queryBuilder.where(ChildEntityDao.Properties.ObjectId.eq(1), queryBuilder.or(ChildEntityDao.Properties.Id.gt(50), queryBuilder.and(ChildEntityDao.Properties.Id.eq(30), ChildEntityDao.Properties.Id.ge(25))));
    }

}
