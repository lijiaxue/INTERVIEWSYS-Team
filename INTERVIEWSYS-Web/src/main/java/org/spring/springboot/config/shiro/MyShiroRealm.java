package org.spring.springboot.config.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.spring.springboot.domain.SysUser;
import org.spring.springboot.service.SysPermissionService;
import org.spring.springboot.service.SysRoleService;
import org.spring.springboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SysRoleService sysRoleService;

    //用户登录次数计数  redisKey 前缀
    private String SHIRO_LOGIN_COUNT = "shiro_login_count_";

    //用户登录是否被锁定    一小时 redisKey 前缀
    private String SHIRO_IS_LOCK = "shiro_is_lock_";

/*          1、检查提交的进行认证的令牌信息
            2、根据令牌信息从数据源(通常为数据库)中获取用户信息
            3、对用户信息进行匹配验证。
            4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
            5、验证失败则抛出AuthenticationException异常信息。
            而在我们的应用程序中要做的就是自定义一个Realm类，继承AuthorizingRealm抽象类，重载doGetAuthenticationInfo ()，重写获取用户信息的方法。*/
   //授权:链接权限的实现
/*    当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行，
    所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限认证方法：MyShiroRealm.doGetAuthenticationInfo()");

        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();

        //实际开发，当前登录用户的角色和权限信息是从数据库来获取的，我这里写死是为了方便测试
        Set<String> roleSet = new HashSet<String>();
        //这两个用来添加权限
        roleSet.add("100002");
        info.setRoles(roleSet);

        Set<String> permissionSet = new HashSet<String>();
        permissionSet.add("权限添加");
        info.setStringPermissions(permissionSet);
        return null;
    }
    //登录认证实现
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("身份认证方法：MyShiroRealm.doGetAuthenticationInfo()");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());
        //访问一次，计数一次
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.increment(SHIRO_LOGIN_COUNT+name, 1);
        //计数大于5时，设置用户被锁定一小时
        if(Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT+name))>=5){
            opsForValue.set(SHIRO_IS_LOCK+name, "LOCK");
            stringRedisTemplate.expire(SHIRO_IS_LOCK+name, 1, TimeUnit.HOURS);
        }
        if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK+name))){
            throw new DisabledAccountException("由于密码输入错误次数大于5次，帐号已经禁止登录！");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nickname", name);
        map.put("pswd", password);
        SysUser user = null;
        // 从数据库获取对应用户名密码的用户
        List<SysUser> userList = sysUserService.selectByMap(map);
        if(userList.size()!=0){
            user = userList.get(0);
        }
        if (null == user) {
            throw new AccountException("帐号或密码不正确！");
        }else if(user.getStatus().equals("0")){
            /**
             * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
             */
            throw new DisabledAccountException("帐号已经禁止登录！");
        }else{
            //更新登录时间 last login time
            user.setLastLoginTime(new Date());
            sysUserService.updateById(user);
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("userSession", user);
            session.setAttribute("userSessionId", user.getId());
            //清空登录计数
            opsForValue.set(SHIRO_LOGIN_COUNT+name, "0");
        }
        Logger.getLogger(getClass()).info("身份认证成功，登录用户："+name);
        return new SimpleAuthenticationInfo(user, user.getPswd(), getName());
    }
}
