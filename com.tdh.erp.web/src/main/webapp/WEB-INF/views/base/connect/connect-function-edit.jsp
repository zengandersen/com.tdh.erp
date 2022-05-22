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
	<meta name="viewport"
		  content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,role-scalable=no"/>
	<meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="Bookmark" href="/H-ui-admin//favicon.ico">
	<link rel="Shortcut Icon" href="/H-ui-admin//favicon.ico"/>
	<link rel="stylesheet" href="/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">

	<script type="text/javascript" src="/H-ui-admin/lib/html5shiv.js"></script>
	<script type="text/javascript" src="/H-ui-admin/lib/respond.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/lib/layui-v2.6.3/css/layui.css">
	<link rel="stylesheet" type="text/css" href="/css/public.css">

	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script>
	<!--请在下方写此页面业务相关的脚本-->
	<script src="/lib/layui-v2.6.3/layui.js"></script>

	<!--/meta 作为公共模版分离出去-->

	<title>《进销存系统》 Ver 2022</title>
	<meta name="keywords" content="《进销存系统》 Ver 2022">
	<meta name="description" content="《进销存系统》 Ver 2022">
	<style>
		body {
			background-color: #ffffff;
		}
	</style>
</head>
<body>
<form class="layui-form layui-form-pane" action="" lay-filter="example">
	<input type="text" name="service_id" style="display: none">
	<input type="text" name="connect_id" style="display: none">

	<div class="layui-form-item">
		<label class="layui-form-label">连接编码</label>
		<div class="layui-input-inline">
			<input type="text" name="connect_code"  autocomplete="off" placeholder="请输入连接编码" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">功能名称</label>
		<div class="layui-input-inline">
			<input type="text" name="function_name"  autocomplete="off" placeholder="请输入功能名称" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">功能地址</label>
		<div class="layui-input-inline">
			<input type="text" name="function_url"  autocomplete="off" placeholder="请输入功能地址" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">备注</label>
		<div class="layui-input-block">
			<textarea placeholder="请输入内容" class="layui-textarea" name="remark"></textarea>
		</div>
	</div>

	<div class="layui-form-item">
		<div class="layui-input-block">
			<button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</div>
</form>

<script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="/js/lay-config.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript">
    var form;
    var index = parent.layer.getFrameIndex(window.name);
    function child(data,service_id) {
            $.ajax({
                url: '/query-connectFunction-detail.do',
                type: 'POST',
                async: false,
                data: {"data":service_id}
            })
                .done(function (message) {
                    var json = eval("(" + message + ")")
                    if (json.code == "success") {
                        if(json.data != undefined){

                            //表单初始赋值
                            form.val('example', {
                                "connect_code": json.data.connect_code,
                                "function_name": json.data.function_name,
                                "function_url": json.data.function_url,
                                "remark": json.data.remark,
                                "connect_id":json.data.connect_id,
                                "service_id":service_id
                            })
                            form.render();
						}else{

                            //表单初始赋值
                            form.val('example', {
                                "connect_code": "",
                                "function_name": "",
                                "function_url": "",
                                "remark": "",
                                "connect_id":"",
                                "service_id":service_id
                            })
                            form.render();
						}
                    }
                    if (json.code == "fail") {
                        layer.msg(json.msg);
                    }
                })
    }

    //定义事件集合
    var active = {
        updateRow: function(obj){
            var oldData = table.cache[layTableId];
            console.log(oldData);
            for(var i=0, row; i < oldData.length; i++){
                row = oldData[i];
                if(row.dept_id == obj.dept_id){
                    $.extend(oldData[i], obj);
                    return;
                }
            }
            table.reload({
                data : oldData
            });
        },
    }

    layui.use(['form', 'layedit', 'laydate'], function () {
        var
            layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;
        form = layui.form;

        //监听提交
        form.on('submit(demo1)', function (data) {
            $.ajax({
                url: '/connectFunction-add.do',
                type: 'POST',
                data: data.field,
                async: false,
            })
                .done(function (message) {
                    var json = eval("(" + message + ")")
                    if (json.code == "success") {
                        layer.msg(json.msg);
                        setTimeout(function(){
                            window.parent.location.reload();
                        }, 1000);
                    }
                    if (json.code == "fail") {
                        layer.msg(json.msg);
                    }
                })
            return false;
        });

    });


</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>