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
				<form id="form1" action="<%=basePath%>/save-bindInfo.do" method="post">
				<input type="hidden" name="parent_id" value="${parent_id}"/>
				<div class="text-l">
						<input type="text" class="input-text" style="width:280px" placeholder="项目编码/名称" value="" id="title_name" name="title_name">
						<button type="button" onclick="seach_()"  class="btn btn-warning radius" id="" name=""><i class="Hui-iconfont">&#xe684;</i> 查找</button>
						<button type="button" onclick="submit_()" style="float: right;margin-right: 5px" class="btn btn-primary radius" id="" name=""><i class="Hui-iconfont">&#xe6e1;</i> 保存绑定</button>
						<button type="button" onclick="delAll()" style="float: right;margin-right: 5px" class="btn btn-danger radius" id="" name=""><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</button>
						<button type="button" onclick="queryExamItem()" style="float: right;margin-right: 5px" class="btn btn-success radius" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 选择项目</button>
				</div>
				<div class="cl pd-5 bg-1 bk-gray mt-5"> 
						<span class="l">
							<label>
								共绑定项目：<strong>${fn:length(equipmentBindExams) }</strong> 条
							</label>
						</span> 
					 </div>
				<div class="mt-5" id="table_detail" style="overflow: scroll;">
						<table class="table table-border table-bordered table-hover table-bg table-sort">
							<thead>
								<tr class="text-c">
									<th class="trSticky">操作</th>
									<th class="trSticky">序号</th>
									<th class="trSticky">菜单名称</th>
									<th class="trSticky">菜单地址</th>
									<th class="trSticky">菜单样式</th>
									<th class="trSticky">菜单位置</th>
								</tr>
							</thead>
							<tbody id="tbody_info">
								<c:forEach items="${params }" var="item" varStatus="i">
									<tr class="text-c" ids="${item.id }">
										<td>
										<input type="hidden" value="${item.id }" name="ids">
										<a href="javascript:delItem('${item.id }')" style="color: red">删除</a>
										</td>
										<td >${i.index+1 }</td>
										<td class="text-l">${item.title_name }</td>
										<td class="text-l">${item.href }</td>
										<td class="text-l">${item.icon }</td>
										<td class="text-l">${item.target }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				</div>
				</form>	
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

function seach_(){
	var txt=$("input[type=text]").val();
	if($.trim(txt)!=""){
        $("tbody tr").hide().filter(":contains('"+txt+"')").show();
    }else{
        $("tbody tr").show();
    }
}


function submit_(){
	 if(confirm("确认提交保存？")){
		 $("#form1").submit();
	 }
}

function delItem(ids){
	 if(confirm("确实要删除吗？")){
		 $("tr[ids='"+ids+"']").remove();
	 }
}

function delAll(){
	if(confirm("确实所有绑定关系？")){
		$("tbody").empty();
	}
}

function addTitle(title_names,hrefs,icons,target,ids){
	var tr = "<tr class=\"text-c\" id=\""+ids+"\">";
	tr = tr+"<td><input type=\"hidden\" value=\""+ids+"\" name=\"ids\"><a href=\"javascript:delItem('"+ids+"')\" style=\"color: red\">删除</a></td>";
	tr = tr+"<td></td>";
	tr = tr+"<td class=\"text-l\">"+title_names+"</td>";
	tr = tr+"<td class=\"text-l\">"+hrefs+"</td>";
    tr = tr+"<td class=\"text-l\">"+icons+"</td>";
	tr = tr+"<td class=\"text-l\">"+target+"</td>";
	tr = tr+"</tr>";
	$("#tbody_info").append(tr);
}

/**
 * 判定页面项目是否存在
 */
function isExamItem(id){
	var obj = $("input[value='"+id+"']");
	if($(obj).val()==id){
		return true;
	}else{
		return false;
	}
}

function queryExamItem(){
	//弹出即全屏
	var index = layer.open({
	  type: 2,
	  title: "未绑定菜单列表",
	  content: './menu-notbind.do',
	  maxmin: true
	});
	layer.full(index);
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