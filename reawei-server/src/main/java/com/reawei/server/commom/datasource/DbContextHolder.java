package com.reawei.server.commom.datasource;

/**
 * Created in 2018/6/11 16:36
 *
 * @author qigong
 */
public class DbContextHolder {


    private static final ThreadLocal<DbType> HOLDER = new ThreadLocal<>();

    private DbContextHolder() {
    }

    public static void putDataSource(DbType dataSource) {
        HOLDER.set(dataSource);
    }

    public static DbType getDataSource() {
        return HOLDER.get();
    }

    public static void clearDataSource() {
        HOLDER.remove();
    }

}
