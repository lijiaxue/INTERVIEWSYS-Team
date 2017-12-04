package org.spring.springboot.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * Created by dell on 2017/11/13.
 */
@TableName("u_user_role")
public class SysUserRole extends Model<SysUserRole>{
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private String  uid;

    /**
     * 角色id
     */
    private String rid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
