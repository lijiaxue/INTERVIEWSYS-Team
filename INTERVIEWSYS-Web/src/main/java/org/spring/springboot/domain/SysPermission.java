package org.spring.springboot.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;


/**
 * Created by dell on 2017/11/13.
 */
@TableName("u_permission")
public class SysPermission extends Model<SysPermission> {

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * url地址
     */
	private String url;
    /**
     * url描述
     */
	private String name;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
