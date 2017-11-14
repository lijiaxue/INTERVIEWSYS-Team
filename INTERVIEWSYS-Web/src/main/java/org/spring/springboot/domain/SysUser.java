package org.spring.springboot.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 李佳学
 */
@TableName("u_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 邮箱|登录帐号
     */
    private String email;
    /**
     * 密码
     */
    private String pswd;
    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;
    /**
     * 1:有效，0:禁止登录
     */
    private String status;


    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    public SysUser() {
    }

    public SysUser(SysUser user) {
        super();
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.pswd = user.getPswd();
        this.createTime = user.getCreateTime();
        this.lastLoginTime = user.getLastLoginTime();
        this.status = user.getStatus();


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }





    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}