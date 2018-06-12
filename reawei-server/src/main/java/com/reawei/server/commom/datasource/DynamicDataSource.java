package com.reawei.server.commom.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created in 2018/6/11 16:35
 *
 * @author qigong
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        DbType dbType = DbContextHolder.getDataSource();
        logger.info("*************** { " + dbType);
        if (dbType == null || dbType == DbType.WRITE) {
            return DbType.WRITE.name();
        }
        return DbType.READ.name();
    }

    @Autowired
    @Qualifier("masterDataSource")
    private Object writeDataSource;

    @Autowired
    @Qualifier("clusterDataSource")
    private Object readDataSource;

    @Override
    public void afterPropertiesSet() {
        if (this.writeDataSource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        setDefaultTargetDataSource(writeDataSource);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DbType.WRITE.name(), writeDataSource);
        if (readDataSource != null) {
            targetDataSources.put(DbType.READ.name(), readDataSource);
        }
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    public void setWriteDataSource(Object writeDataSource) {
        this.writeDataSource = writeDataSource;
    }

    public Object getWriteDataSource() {
        return writeDataSource;
    }

    public Object getReadDataSource() {
        return readDataSource;
    }

    public void setReadDataSource(Object readDataSource) {
        this.readDataSource = readDataSource;
    }
}
