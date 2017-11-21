<!DOCTYPE html>
<html lang="en">
<#assign contextPath = request.contextPath />
<head>
	<meta charset="UTF-8">
	<title>个人信息</title>
	<meta name="renderer" content="webkit">	
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">	
	<meta name="apple-mobile-web-app-status-bar-style" content="black">	
	<meta name="apple-mobile-web-app-capable" content="yes">	
	<meta name="format-detection" content="telephone=no">	
	<link rel="stylesheet" type="text/css" href="${contextPath}/common/layui/css/layui.css" media="all">
	<link rel="stylesheet" type="text/css" href="${contextPath}/common/bootstrap/css/bootstrap.css" media="all">
	<link rel="stylesheet" type="text/css" href="${contextPath}/common/global.css" media="all">
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/personal.css" media="all">
	<script src="${contextPath}/js/jquery-1.8.3.js"></script>
</head>
<body>
<section class="layui-larry-box">
	<div class="larry-personal">
	    <div class="layui-tab">
            <blockquote class="layui-elem-quote news_search">
		
		<div class="layui-inline">
		    <div class="layui-input-inline">
		    	<input value="" placeholder="请输入关键字" class="layui-input search_input" type="text">
		    </div>
		    <a class="layui-btn search_btn">查询</a>
		</div><div class="layui-inline">
			<a class="layui-btn layui-btn-normal newsAdd_btn">添加文章</a>
		</div>
		<div class="layui-inline">
			<a class="layui-btn recommend" style="background-color:#5FB878">推荐文章</a>
		</div>
		<div class="layui-inline">
			<a class="layui-btn audit_btn">审核文章</a>
		</div>
		<div class="layui-inline">
			<a class="layui-btn layui-btn-danger batchDel">批量删除</a>
		</div>
		<div class="layui-inline">
			<div class="layui-form-mid layui-word-aux">本页面刷新后除新添加的文章外所有操作无效，关闭页面所有数据重置</div>
		</div>
	</blockquote>
            
		    <div class="layui-tab-content larry-personal-body clearfix mylog-info-box">
		         <!-- 人员操作 -->
                <div class="layui-tab-item layui-field-box layui-show">
                     <table class="layui-table table-hover" lay-even="" lay-skin="nob">
                          <thead>
                              <tr>
                                  <th><input type="checkbox" id="selected-all"></th>
                                  <th>用户名</th>
                                  <th>用户权限</th>
                                  <th>email</th>
                                  <th>创建时间</th>
                                  <th>最后登录时间</th>
                                  <th>用户状态</th>
                                  <th>操作</th>
                              </tr>
                          </thead>
                          <tbody>
                          <#escape x as x!"">
                          <#list userList as user >
                              <tr>
                                <td><input type="checkbox"></td>
                                <td>${user.nickname}</td>
                                <td>超级管理员</td>
                                <td>${user.email}</td>
                                <td>${(user.createTime?string('yyyy-MM-dd hh:mm:ss'))!}</td>
                                <td>${(user.lastLoginTime?string('yyyy-MM-dd hh:mm:ss'))!}</td>
                                <#if user.status == "1">
                                <td>有效</td>
                                <#else>
                                <td>禁止登陆</td>
                                </#if>
                                <td>
                                 <button class="layui-btn layui-btn-primary layui-btn-sm">
                                    <i class="layui-icon">&#xe642;</i>
                                  </button>
                                  <button class="layui-btn layui-btn-primary layui-btn-sm">
                                    <i class="layui-icon">&#xe640;</i>
                                  </button>
                                      </td>
                              </tr>
                              </#list>
                              </#escape>
                          </tbody>
                     </table>
                     <div class="larry-table-page clearfix">
				          <div id="page" class="page"></div>

			         </div>
			    </div>
			    </div>
		    </div>
		</div>
	</div>
</section>
<script type="text/javascript" src="${contextPath}/common/layui/layui.js"></script>
<script type="text/javascript">
	layui.use(['jquery','layer','element','laypage'],function(){
	      window.jQuery = window.$ = layui.jquery;
	      window.layer = layui.layer;
          var element = layui.element(),
              laypage = layui.laypage;
         startAllAppoint = 1;//开始页数
         limitAllAppoint = ${page.size};//每页显示数据条数
         currentPageAllAppoint = ${page.current};//当前页数
         dataLength = ${page.total};//数据总条数
         totalPage = dataLength % limitAllAppoint == 0 ? dataLength / limitAllAppoint : Math.ceil(dataLength / limitAllAppoint)
         page =Math.ceil(dataLength/10);
         if(page>5){
            page=5;
         }
          laypage({
					cont: 'page',
					count:dataLength,
					groups:page,
					pages: totalPage //总页数
						,
					curr: currentPageAllAppoint,
					jump: function(obj, first) {
						//得到了当前页，用于向服务端请求对应数据
						if(!first) {
							//layer.msg('第 '+ obj.curr +' 页');
                                location.href="${contextPath}/user/index?page="+obj.curr;
						}
					}
				});
    });
</script>
</body>
</html>