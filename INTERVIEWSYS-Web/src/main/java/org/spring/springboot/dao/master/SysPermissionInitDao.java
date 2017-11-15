package org.spring.springboot.dao.master;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.spring.springboot.domain.SysPermissionInit;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author z77z
 * @since 2017-02-16
 */
public interface SysPermissionInitDao extends BaseMapper<SysPermissionInit> {

	List<SysPermissionInit> selectAll();

}