<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,call-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="/H-ui-admin/lib/html5shiv.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="/H-ui-admin/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="/H-ui-admin/lib/treeview/css/css.css" />
<!--[if IE 6]>
<script type="text/javascript" src="/H-ui-admin/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>进销存系统</title>
<meta name="keywords" content="进销存系统">
<meta name="description" content="进销存系统">
</head>
<body>
  	<div class="row" style="margin-right: 0px;">
        <div class="col-sm-12">
         <div class="page-container">
				<form id="form1" action="<%=basePath%>/menuchild-list.do" method="post">
				<div class="text-l">
						<input type="text" class="input-text" style="width:280px" placeholder="菜单名称/菜单地址" value="" id="hospital_code" name="hospital_code">
					<span class="select-box inline">
								<select class="select" name="status" size="1" onchange="$('#form1').submit();">
									<option value="" >全部</option>
									<option value="1" <c:if test="${1 ==params.status}">selected="selected" </c:if> >启用</option>
									<option value="0" <c:if test="${0 ==params.status}">selected="selected" </c:if> >禁用</option>
								</select>
						</span>
						<button type="submit" class="btn btn-success radius" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
					<a href="javascript:;" onclick="layer_show('添加子菜单','<%=basePath%>/menuchild-add.do',500,500)" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加子菜单</a>

				</div>
				<div class="cl pd-5 bg-1 bk-gray mt-5"> 
						<span class="l">
							<label>
							显示 <select name="pageSize" onchange="$('#form1').submit();">
								<c:forEach items="${pageList.psList }" var="ps" varStatus="i">
								<option value="${ps }" <c:if test="${pageList.pageSize==ps }">selected="selected"</c:if>>${ps }</option>
								</c:forEach>
								</select> 条 
								共有数据：<strong>${pageList.count }</strong> 条
							</label>
						</span> 
 						<span class="r">
							<label>
							当前页 <select name="curPage" onchange="$('#form1').submit();">
								<c:forEach var="s"  begin="1" end="${pageList.countPage }">
								<option value="${s }" <c:if test="${pageList.curPage==s }">selected="selected"</c:if>>${s }/${pageList.countPage }</option>
								</c:forEach>
								</select>  
								总页数
							</label>
						</span> 
					 </div>
				</form>	
				<div class="mt-5" id="table_detail" style="overflow: scroll;">
						<table class="table table-border table-bordered table-hover table-bg table-sort">
							<thead>
								<tr class="text-c">
									<th class="trSticky">序号</th>
									<th class="trSticky">菜单名称</th>
									<th class="trSticky">菜单地址</th>
									<th class="trSticky">菜单样式信息</th>
									<th class="trSticky">菜单类型</th>
									<th class="trSticky">菜单级别</th>
									<th class="trSticky">状态</th>
									<th class="trSticky">创建人</th>
									<th class="trSticky">创建时间</th>
									<th class="trSticky">修改人</th>
									<th class="trSticky">修改时间</th>
									<th class="trSticky">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageList.dataList }" var="item" varStatus="i">
									<tr class="text-c">
										<td >${i.index+1 }</td>

										<td class="trSticky">${item.title_name }</td>
										<td class="trSticky">${item.href }</td>
										<td class="trSticky">${item.icon }</td>
										<td class="trSticky">${item.target }</td>
										<td class="trSticky">${item.level_flag }</td>
										<td class="td-status">
											<c:if test="${item.status==1 }"><span class="label label-success radius">启动</span></c:if>
											<c:if test="${item.status==0 }"><span class="label label-danger radius">禁用</span></c:if>
										</td>
										<td class="trSticky">${item.create_by }</td>
										<td class=""><fmt:formatDate value="${item.create_time}" pattern="yyyy-MM-dd HH:mm"/></td>
										<td class="trSticky">${item.update_by }</td>
										<td class=""><fmt:formatDate value="${item.update_time}" pattern="yyyy-MM-dd HH:mm"/></td>
										<td class="">
											<a style="color: #23527c" onclick="javascript:layer_show('编辑','<%=basePath%>/menuchild-edit.do?id=${item.id}',500,500);">编辑</a>
											<c:if test="${item.status==1 }"><a style="color: #23527c" onclick="return confirm('禁用确认？');" href="javascript:layer_show('操作提示','<%=basePath%>/menuchild-disable.do?id=${item.id}',500,270)">禁用</a> </c:if>
											<c:if test="${item.status==0 }"><a style="color: #23527c" onclick="return confirm('启动确认？');" href="javascript:layer_show('操作提示','<%=basePath%>/menuchild-enable.do?id=${item.id}',500,270)">启动</a> </c:if>
                                            <a style="color: #23527c" onclick="return confirm('删除确认？');" href="javascript:layer_show('操作提示','<%=basePath%>/menuchild-delete.do?id=${item.id}',500,270)">删除</a>

                                        </td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				</div>
			</div> 
        </div>
   </div>
<!--_footer 作为公共模版分离出去-->
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/H-ui-admin/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript"  src="/H-ui-admin/lib/treeview/js/bootstrap-treeview.js"></script>
<script type="text/javascript">
function winClose(){
	layer.closeAll();
	$("#form1").submit();
}


$(function(){
	autodivheight();
});

function autodivheight(){ //函数：获取尺寸
	//获取浏览器窗口高度
	var winHeight=0;
	if (window.innerHeight)
		winHeight = window.innerHeight;
	else if ((document.body) && (document.body.clientHeight))
		winHeight = document.body.clientHeight;
	//通过深入Document内部对body进行检测，获取浏览器窗口高度
	if (document.documentElement && document.documentElement.clientHeight)
		winHeight = document.documentElement.clientHeight;
	document.getElementById("table_detail").style.height= winHeight-95 +"px";
}
window.onresize=autodivheight; //浏览器窗口发生变化时同时变化DIV高度
</script>
</body>
</html>