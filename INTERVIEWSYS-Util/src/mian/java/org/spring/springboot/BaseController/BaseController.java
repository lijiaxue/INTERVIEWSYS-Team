package org.spring.springboot.BaseController;

import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理controller的公共方法
 */
public class BaseController {
    public Map<String,String> getParam(HttpServletRequest request, String[] param){
        Map<String,String> map =new HashMap<>();
        if(param.length>0){
            for(int i=0;i<param.length;i++){
                map.put(param[i],request.getParameter(param[i]));
            }
        }
        return map;
    }
}
