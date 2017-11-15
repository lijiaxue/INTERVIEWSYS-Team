package org.spring.springboot.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.spring.springboot.dao.master.SysUserDao;
import org.spring.springboot.domain.SysUser;
import org.springframework.stereotype.Service;

/**
 * Created by dell on 2017/11/10.
 */
@Service
public class SysUserService extends ServiceImpl<SysUserDao, SysUser> {
}
