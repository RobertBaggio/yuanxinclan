package com.yuanxin.clan.mvp.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yuanxin.clan.mvp.entity.Entity;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;

//import com.yuanxin.clan.mvp.entity.Entity;

/**
 * @author lch
 *         date 2015/10/13.
 */
public class BaseVo implements Serializable {
    public static final int TYPE_DB = 0x1; //本地数据数据
    public static final int TYPE_SERVER = 0x2; //服务器加载数据
    private List<? extends Entity> queryData;
    private int dataType;

    public BaseVo() {

    }

    /**
     * 请求成功的回调方法，父类默认实现，使用反射方式获取每个field并赋值,field的名称必须和json数据中的名称一致才能有值
     *
     * @param jsonString
     */
    public static <T extends BaseVo> T parseDataVo(String jsonString, Class<T> dataClass) throws JSONException {
        if (TextUtils.isEmpty(jsonString) || dataClass == null) {
            return null;
        }

        T baseVo;
        try {
            if (jsonString.startsWith("[")) {
                baseVo = dataClass.newInstance();
                List<?> dataSet = JSON.parseArray(jsonString, baseVo.elementType());
                baseVo.setDataList(dataSet);
            } else {
                baseVo = JSON.parseObject(jsonString, dataClass);
            }
        } catch (Exception ex) {
            throw new JSONException("数据解析失败! Exception: " + ex.getMessage());
        }
        return baseVo;
    }

    /**
     * 如果返回数据是集合，数据解析方法会调用次方法获取元素的类型
     *
     * @return
     */
    public Class<?> elementType() {
        return null;
    }


    /**
     * 数据解析成功后会调用此方法设置数据
     *
     * @param dataSet
     */
    public void setDataList(List<?> dataSet) {

    }

    public List<? extends Entity> getQueryData() {
        return queryData;
    }

    /**
     * @return 需要本地DB存储和同步的数据集合
     */
    public List<? extends Entity> getSyncDataList() {
        return null;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    /**
     * 执行数据模拟，在needMockData为true的情况下，框架层会调用此方法，
     * 子类将自己需要模拟的数据设置到当前对象。
     */
    public void doMock() {

    }

    public void setQueryData(List<? extends Entity> queryData) {
        this.queryData = queryData;
    }
}

