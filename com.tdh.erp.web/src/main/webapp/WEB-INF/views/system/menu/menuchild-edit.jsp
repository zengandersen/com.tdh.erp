<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<link rel="Bookmark" href="/H-ui-admin//favicon.ico" >
	<link rel="Shortcut Icon" href="/H-ui-admin//favicon.ico" />
	<!--[if lt IE 9]>
	<script type="text/javascript" src="/H-ui-admin/lib/html5shiv.js"></script>
	<script type="text/javascript" src="/H-ui-admin/lib/respond.min.js"></script>
	<![endif]-->
	<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui/css/H-ui.min.css" />
	<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/css/H-ui.admin.css" />
	<link rel="stylesheet" type="text/css" href="/H-ui-admin/lib/Hui-iconfont/1.0.8/iconfont.css" />
	<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/skin/default/skin.css" id="skin" />
	<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/css/style.css" />
	<!--[if IE 6]>
	<script type="text/javascript" src="/H-ui-admin/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
	<script>DD_belatedPNG.fix('*');</script>
	<![endif]-->
	<!--/meta 作为公共模版分离出去-->

	<title>《进销存系统》 Ver 2022</title>
	<meta name="keywords" content="《进销存系统》 Ver 2022">
	<meta name="description" content="《进销存系统》 Ver 2022">
</head>
<body>
<article class="page-container" style="margin: 10px;">
	<form action="/menuchild-save.do" method="post" class="form form-horizontal" id="form-save" autocomplete="off">
		<input type="hidden" name="id" value="${params.id }"/>
		<c:if test="${params.level_flag>1 }"><div class="row cl">
			<label class="form-label col-xs-12 col-sm-12"><span class="c-red">*</span>父级菜单：</label>
			<div class="formControls col-xs-12 col-sm-12">
				<span class="select-box inline">
					<select class="select" name="parents" size="1">
							<option value="" selected="selected">暂无</option>
							<c:forEach items="${parents }" var="item" varStatus="i">
								<option  value="${item.id }" <c:if test="${params.parent_id ==item.id}">selected="selected" </c:if> >${item.title_name }</option>
							</c:forEach>
					</select>
				</span>
			</div>
		</div></c:if>
		<div class="row cl">
			<label class="form-label col-xs-12 col-sm-12"><span class="c-red">*</span>菜单名称：</label>
			<div class="formControls col-xs-12 col-sm-12">
				<input type="text" class="input-text" value="${params.title_name }" placeholder="" id="title_name" name="title_name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-12 col-sm-12"><span class="c-red">*</span>菜单地址：</label>
			<div class="formControls col-xs-12 col-sm-12">
				<input type="text" class="input-text" value="${params.href }" placeholder="" id="href" name="href">
			</div>
		</div>

		<div class="row cl">
			<label class="form-label col-xs-12 col-sm-12"><span class="c-red">*</span>菜单样式：</label>
			<div class="formControls col-xs-12 col-sm-12">
				<span class="select-box inline">
					<select class="select" name="icon" size="1">
						<option value="">菜单样式</option>
							<c:forEach items="${icon }" var="item" varStatus="i">
								<option value="${item.iconid }" <c:if test="${params.icon ==item.iconid}">selected="selected" </c:if> >${item.iconid }</option>
							</c:forEach>
					</select>
				</span>
			</div>
		</div>

		<div class="row cl">
			<label class="form-label col-xs-12 col-sm-12"><span class="c-red"></span>菜单类型：</label>
			<div class="formControls col-xs-12 col-sm-12">
				<input type="text" class="input-text" value="${params.target }" placeholder="" id="target" name="target">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-12 col-sm-12"><span class="c-red">*</span>菜单级别：</label>
			<div class="formControls col-xs-12 col-sm-12">
				<input type="text" class="input-text" value="${params.level_flag }" placeholder="" id="level_flag" name="level_flag">
			</div>
		</div>
		<div class="col-sm-12" style="margin-top: 15px" >
			<div class="row cl">
				<div class="formControls col-xs-12 col-sm-12 skin-minimal" style="text-align: center;">
					<input class="btn btn-primary radius"  type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
					<input class="btn btn-default radius" onclick="parent.winClose()" type="button" value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
				</div>
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/H-ui-admin/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
    $(function(){
        $("#form-save").validate({
            rules:{
                hospital_code:{
                    required:true,
                    minlength:2,
                    maxlength:16
                },
                hospital_name:{
                    required:true,
                    minlength:2,
                    maxlength:16
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid"
        });
    });
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>