package org.spring.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.shiro.SecurityUtils;
import org.spring.springboot.BaseController.BaseController;
import org.spring.springboot.CustomPage;
import org.spring.springboot.FrontPage;
import org.spring.springboot.domain.SysUser;
import org.spring.springboot.domain.UserOnlineBo;
import org.spring.springboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {
	private static final Logger log = LogManager.getLogger(UserController.class);
	@Autowired
	SysUserService sysUserService;

	//跳转到个人首页
	@RequestMapping(value="/user")
	public String loginUser( Model modle) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		modle.addAttribute("user",user);
		return "/user/user";
	}

	//跳转到用户管理
	@RequestMapping(value="/index")
	public String userIndex(Model modle, String page,HttpServletRequest request) {
		Wrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
		int current =1;
		int size = 10;
		Map<String,String> map = super.getParam(request,new String[]{"nikname","email"});
		if(map.size()>0){
			modle.addAttribute("map", map);
			sysUserService.getWrapper(wrapper,map);
		}

		if(page!=null){
			current=Integer.parseInt(page);
		}
		Page pages = new Page<SysUser>(current,size);

		Page<SysUser> pageList = sysUserService.selectPage(pages, wrapper);
		List list =pageList.getRecords();
		pageList.setTotal(sysUserService.selectList(wrapper).size());
		modle.addAttribute("userList", list);
		modle.addAttribute("page", pageList);
		return "/user/userIndex";
	}

	// 跳转到用户管理页面
	@RequestMapping(value = "userPage")
	public String userPage(String edit, Model modle) {
		// edit判断是否编辑成功
		modle.addAttribute("edit", edit);
		return "user/user";
	}

	// 跳轉到編輯頁面edit
	@RequestMapping(value = "editPage/{Id}")
	public String editPage(@PathVariable("Id") String Id, Model model) {
		if (Id.equals("add")) {
		} else {
			SysUser user = sysUserService.selectById(Id);
			model.addAttribute("user", user);
		}
		return "user/edit";
	}

	// 增加和修改
	@RequestMapping(value = "edit")
	public String edit(SysUser user,String isEffective, Model model) {
		if(isEffective==null||isEffective==""){
			user.setStatus("0");
		}else{
			user.setStatus("1");
		}
		if (sysUserService.insertOrUpdate(user)) {
			return "forward:userPage?edit=true";
		} else {
			return "forward:userPage?edit=false";
		}
	}

	// 用户列表分页json
	@RequestMapping(value = "getUserListWithPager")
	@ResponseBody
	public String getUserListWithPager(FrontPage<SysUser> page) {
		Wrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
		String keyWords = page.getKeywords();
		if (keyWords != null && keyWords != "")
			wrapper.like("nickname", keyWords);
		Page<SysUser> pageList = sysUserService.selectPage(page.getPagePlus(), wrapper);
		CustomPage<SysUser> customPage = new CustomPage<SysUser>(pageList);
		return JSON.toJSONString(customPage);
	}

	// 刪除用户
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(value = "ids[]") String[] ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			sysUserService.deleteBatchIds(Arrays.asList(ids));
			resultMap.put("flag", true);
			resultMap.put("msg", "刪除成功！");
		} catch (Exception e) {
			resultMap.put("flag", false);
			resultMap.put("msg", e.getMessage());
		}
		return JSON.toJSONString(resultMap);
	}

	// 跳转到在线用户管理页面
	@RequestMapping(value = "onlineUserPage")
	public String onlineUserPage() {
		return "user/onlineUser";
	}

	// 在线用户列表json
	@RequestMapping(value = "onlineUsers")
	@ResponseBody
	public String OnlineUsers(FrontPage<UserOnlineBo> frontPage) {
		Page<UserOnlineBo> pageList = sysUserService.getPagePlus(frontPage);
		CustomPage<UserOnlineBo> customPage = new CustomPage<UserOnlineBo>(pageList);
		return JSON.toJSONString(customPage);
	}

	// 强制踢出用户
	@RequestMapping(value = "kickout")
	@ResponseBody
	public String kickout(@RequestParam(value = "ids[]") String[] ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			for (String sessionId : ids) {
				sysUserService.kickout(sessionId);
			}
			resultMap.put("flag", true);
			resultMap.put("msg", "强制踢出成功！");
		} catch (Exception e) {
			resultMap.put("flag", false);
			resultMap.put("msg", e.getMessage());
		}
		return JSON.toJSONString(resultMap);
	}
}
