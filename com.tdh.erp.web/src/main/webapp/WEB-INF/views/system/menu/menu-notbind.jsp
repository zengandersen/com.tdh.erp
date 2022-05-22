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
				<form id="form1" action="<%=basePath%>/examItem-miniList.do" method="post">
				<div class="text-l">
						<input type="text" class="input-text" style="width:280px" placeholder="页面名称/页面地址/页面样式" value="${title_name}" id="title_name" name="title_name">
						<button type="submit" class="btn btn-success radius" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
						<button type="button" onclick="check_all()" class="btn btn-primary radius" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 全选</button>
				</div>

				</form>	
				<div class="mt-5" id="table_detail" style="overflow: scroll;">
						<table class="table table-border table-bordered table-hover table-bg table-sort">
							<thead>
								<tr class="text-c">
									<th class="trSticky">操作</th>
									<th class="trSticky">序号</th>
									<th class="trSticky">页面名称</th>
									<th class="trSticky">页面地址</th>
									<th class="trSticky">页面样式</th>
									<th class="trSticky">页面位置</th>
									<th class="trSticky">状态</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${params }" var="item" varStatus="i">
									<tr class="text-c">
										<td>
										<a style="color: #23527c;" attr="title" title_names="${item.title_name }"  hrefs="${item.href }" icons="${item.icon }" targets="${item.target }" ids="${item.id }" onclick="selectTitle(this)" >绑定</a>
										</td>
										<td >${i.index+1 }</td>
										<td class="text-l">${item.title_name }</td>
										<td class="text-l">${item.href }</td>
										<td class="text-l">${item.icon }</td>
										<td class="text-l">${item.target }</td>

										<td class="td-status">
											<c:if test="${item.status==1 }"><span class="label label-success radius">启动</span></c:if>
											<c:if test="${item.status==0 }"><span class="label label-error radius">禁用</span></c:if>
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

$(function(){
	$("a[attr='title']").each(function(){
		var id = $(this).attr("ids");
		var p = parent.isExamItem(id);
		if(p){
			$(this).css("color","red");
			$(this).text("已绑");
		}else{
			$(this).css("color","#23527c");
			$(this).text("绑定");
		}
  });
 $("#title_name").focus();
});


function check_all(){
	if(confirm('是否确认全选')){
		$("a[attr='title']").each(function(){
			if($(this).text()=='绑定'){
				$(this).click();
			}
		});
	}
}

function selectTitle(obj){
	if($(obj).text()=='绑定'){
		parent.addTitle($(obj).attr('title_names'),$(obj).attr('hrefs'),$(obj).attr('icons'),$(obj).attr('targets'),$(obj).attr('ids'));
		$(obj).css("color","red");
		$(obj).text("已绑");
	}
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