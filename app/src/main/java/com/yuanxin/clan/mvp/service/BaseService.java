package com.yuanxin.clan.mvp.service;

import com.yuanxin.clan.mvp.entity.Entity;
import com.yuanxin.clan.mvp.manager.DBManager;
import com.yuanxin.clan.mvp.utils.NumberUtils;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lch
 *         date 2016/7/24.
 */
public abstract class BaseService<T extends Entity> implements ConvertService<T> {
    public int pageSize = 10;
    private int[] paginationInfo;
    private int synDataType;
    private long syncPeriod;

    @Override
    public void doSync(String api, List<? extends Entity> syncData) {
        if (syncData == null) {
            return;
        }
        DBManager.getInstance().saveOrUpdateAll(syncData);
    }

    @Override
    public long getSyncPeriod() {
        return syncPeriod;
    }

    public <T extends Entity> List<T> loadData(WhereCondition condition, Map<String, ?> params, Class<? extends Entity> clazz) {
        return (List<T>) loadDataDesc(condition, null, params, clazz);
    }

    public <T extends Entity> List<T> loadDataDesc(WhereCondition condition, Property orderProperty, Map<String, ?> params, Class<? extends Entity> clazz) {
        AbstractDao myDao = DBManager.getInstance().getTargetDao(clazz);
        int[] pageInfo = parsePaginationInfo(params);
        QueryBuilder<? extends Entity> queryBuilder = myDao.queryBuilder();
        if (condition != null) {
            queryBuilder.where(condition);
        } else if (orderProperty != null) {
            queryBuilder.orderDesc(orderProperty);
        }

        return (List<T>) queryBuilder.offset(pageInfo[0]).limit(pageInfo[1]).list();
    }

    public <T extends Entity> List<T> loadByKey(String api, Map<String, ?> params, Class<? extends Entity> clazz) {
        Serializable id = (Serializable) params.get("id");
        List<Entity> entities;
        if (id != null) {
            String[] keys = null;
            if (id instanceof String) {
                String actualId = (String) id;
                keys = actualId.split(",");
            }

            if (keys != null && keys.length > 1) {
                entities = DBManager.getInstance().loadByKeys(clazz, keys);
            } else {
                entities = new ArrayList<>(1);
                Entity entity = DBManager.getInstance().load(clazz, id);
                entities.add(entity);
            }
        } else {
            entities = new ArrayList<>(0);
        }
        return (List<T>) entities;
    }


    /**
     * 将请求参数的json数据转为对应的Entity类
     *
     * @param param
     * @return
     */
    public <T extends Entity> List<T> parseEntity(Map<String, ?> param) {
        Object data = param.get("data");
        if (data == null) {
            throw new IllegalArgumentException("请求参数data不能为空！");
        }

        List<Entity> entities;
        if (data instanceof List) {
            entities = (List<Entity>) data;
        } else {
            entities = new ArrayList<>(1);
            Entity entity = (Entity) data;
            entities.add(entity);
        }

        return (List<T>) entities;
    }

    public void saveData(Map<String, ?> param) {
        List<Entity> entities = parseEntity(param);
        if (entities != null && !entities.isEmpty()) {
            DBManager.getInstance().saveAll(entities);
        }
    }

    public void updateData(Map<String, ?> param) {
        List<Entity> entities = parseEntity(param);
        if (entities != null && !entities.isEmpty()) {
            DBManager.getInstance().saveOrUpdateAll(entities);
        }
    }

    public void deleteData(Map<String, ?> param, Class<? extends Entity> clazz) {
        Serializable id = (Serializable) param.get("id");
        if (id != null) {
            DBManager.getInstance().deleteByKey(clazz, id);
        } else {
            List<Entity> entities = parseEntity(param);
            DBManager.getInstance().deleteAll(entities);
        }
    }

    public int[] parsePaginationInfo(Map<String, ?> param) {
        int[] pageInfo = new int[2];
        Object pageParam = param.get("page");
        int page = NumberUtils.parseInt(pageParam, 0);
        page = page > 0 ? --page : 0;
        int offset = page * pageSize;
        pageInfo[0] = offset;
        Object sizeParam = param.get("size");
        int size = NumberUtils.parseInt(sizeParam, 0);
        if (size == 0) {
            size = pageSize;
        }
        pageInfo[1] = size;
        return pageInfo;
    }

    public int getSynDataType() {
        return synDataType;
    }

    public void setSynDataType(int synDataType) {
        this.synDataType = synDataType;
    }

    public void setSyncPeriod(long syncPeriod) {
        this.syncPeriod = syncPeriod;
    }
}
