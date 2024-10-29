package com.galgram.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.galgram.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis-plus插入时间字段
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            String userName = SecurityUtils.getLoginUser().getUser().getUserName();
            this.setFieldValByName("createTime", new Date(), metaObject);
            this.setFieldValByName("createBy", userName, metaObject);
        } catch (Exception e) {
            this.setFieldValByName("createTime", new Date(), metaObject);
            this.setFieldValByName("createBy", "系统", metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            String userName = SecurityUtils.getLoginUser().getUser().getUserName();
            this.setFieldValByName("updateTime", new Date(), metaObject);
            this.setFieldValByName("updateBy", userName, metaObject);
        } catch (Exception e) {
            this.setFieldValByName("updateTime", new Date(), metaObject);
            this.setFieldValByName("updateBy", "系统", metaObject);
        }
    }
}

