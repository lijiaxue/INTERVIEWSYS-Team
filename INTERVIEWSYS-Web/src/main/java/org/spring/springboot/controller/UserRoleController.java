package org.spring.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.spring.springboot.BaseController.BaseController;
import org.spring.springboot.domain.SysRole;
import org.spring.springboot.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/11/27.
 */
@Controller
@RequestMapping(value = "user/role")
public class UserRoleController extends BaseController {
    @Autowired
    SysRoleService sysRoleService;

    //跳转到用户管理
    @RequestMapping(value="/roleIndex")
    public String userIndex(Model modle, String page,HttpServletRequest request) {
        Wrapper<SysRole> wrapper = new EntityWrapper<SysRole>();
        int current =1;
        int size = 10;
        Map<String,String> map = super.getParam(request,new String[]{"name"});
        if(map.size()>0){
            modle.addAttribute("map", map);
        }
        if(page!=null){
            current=Integer.parseInt(page);
        }
        Page pages = new Page<SysRole>(current,size);
        Page<SysRole> pageList = sysRoleService.selectPage(pages, wrapper);
        List list =pageList.getRecords();

        pageList.setTotal(sysRoleService.selectList(wrapper).size());
        int totalPageNum = (pageList.getTotal()  +  pageList.getSize()  - 1) /pageList.getSize();
        modle.addAttribute("Rolelist", list);
        modle.addAttribute("page", pageList);
        modle.addAttribute("totalPageNum", totalPageNum);
        return "/role/role";
    }

    // 跳轉到編輯頁面edit
    @RequestMapping(value = "editRole/{Id}")
    public String editPage(@PathVariable("Id") String id, Model model) {
        if (id.equals("add")) {
            model.addAttribute("role", new SysRole());

        } else {
            id = id.replaceAll(",","");
            long Id=Long.parseLong(id);;
            SysRole role = sysRoleService.selectById(Id);
            model.addAttribute("role", role);
        }

        return "/role/editRole";
    }

    // 保存
    @RequestMapping(value = "save")
    @ResponseBody
    public String saveAjax(@ModelAttribute("form") SysRole role) {
        Map<String,Object> map=new HashMap<>();
        String result = "";
        Boolean save = sysRoleService.insert(role);
        if(save){
           result="1";
        }else{
            result="0";
        }
        return result;
    }
    //批量删除
    @RequestMapping(value = "batDel")
    @ResponseBody
    public String batDel(HttpServletRequest request){
        String ids = request.getParameter("id");
        return null;
    }

    // 刪除用户
    @RequestMapping(value = "delete/{Id}")
    @ResponseBody
    public String delete(@PathVariable("Id") String Id) {
        Id = Id.replaceAll(",","");
        long id=Long.parseLong(Id);
        Map map=new HashMap();
        String result;
        Boolean del=sysRoleService.deleteById(id);
        if(del){
            map.put("status","1");
        }else{
            map.put("status","0");
        }
        JSONObject object = new JSONObject(map);
        result= object.toString();
        return result;
    }



}
