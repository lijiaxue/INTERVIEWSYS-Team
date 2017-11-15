package org.spring.springboot.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.spring.springboot.dao.master.SysPermissionInitDao;
import org.spring.springboot.domain.SysPermissionInit;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author z77z
 * @since 2017-02-16
 */
@Service
public class SysPermissionInitService extends ServiceImpl<SysPermissionInitDao, SysPermissionInit> {
	
	public List<SysPermissionInit> selectAll() {
		return baseMapper.selectAll();
	}
}
