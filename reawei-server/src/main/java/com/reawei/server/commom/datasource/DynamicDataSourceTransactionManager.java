package com.reawei.server.commom.datasource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import javax.sql.DataSource;

/**
 * Created in 2018/6/11 21:07
 *
 * @author qigong
 */
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {

    /**
     * 只读事务到读库，读写事务到写库
     * @param transaction
     * @param definition
     */
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {

        //设置数据源
        boolean readOnly = definition.isReadOnly();
        if(readOnly) {
            DbContextHolder.putDataSource(DbType.READ);
        } else {
            DbContextHolder.putDataSource(DbType.WRITE);
        }
        super.doBegin(transaction, definition);
    }

    /**
     * 清理本地线程的数据源
     * @param transaction
     */
    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        DbContextHolder.clearDataSource();
    }

//    public DynamicDataSourceTransactionManager(DataSource dataSource) {
//        super(dataSource);
//    }
}
