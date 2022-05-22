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
	<input type="text" name="handle_type" style="display: none">
	<input type="text" name="base_driver_code" style="display: none">

	<div class="layui-form-item">
		<label class="layui-form-label">连接编码</label>
		<div class="layui-input-inline">
			<input type="text" name="connect_code"  autocomplete="off" placeholder="请输入连接编码" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">数据库名称</label>
		<div class="layui-input-inline">
			<input type="text" name="base_name"  autocomplete="off" placeholder="请输入数据库名称" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">数据库类型</label>
		<div class="layui-input-inline" id = "base_type">

		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">数据库版本</label>
		<div class="layui-input-inline">
			<input type="text" name="base_version"  autocomplete="off" placeholder="请输入标题" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">数据库驱动</label>
		<div class="layui-input-inline" id = "base_driver">

		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">数据库地址</label>
		<div class="layui-input-block">
			<input type="text" name="base_url"  autocomplete="off" placeholder="请输入标题" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
	<label class="layui-form-label">用户名</label>
	<div class="layui-input-inline">
		<input type="text" name="base_user_name"  autocomplete="off" placeholder="请输入标题" class="layui-input">
	</div>
</div>
	<div class="layui-form-item">
		<label class="layui-form-label">密码</label>
		<div class="layui-input-inline">
			<input type="password" name="base_password"  autocomplete="off" placeholder="请输入标题" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">字符集</label>
		<div class="layui-input-inline">
			<input type="text" name="base_bits"  autocomplete="off" placeholder="请输入标题" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">初始连接数</label>
		<div class="layui-input-inline">
			<input type="text" name="base_init_connect_num"  autocomplete="off" placeholder="请输入标题" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">最大等待时间</label>
		<div class="layui-input-inline">
			<input type="text" name="base_max_wait_time"  autocomplete="off" placeholder="请输入标题" class="layui-input">
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
			<button class="layui-btn-yellow" lay-submit="" lay-filter="demo2">测试</button>
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
    var loading;
    function child(data,base_type_enum,base_driver_enum,service_id) {
            $.ajax({
                url: '/query-connectBase-detail.do',
                type: 'POST',
                async: false,
                data: {"data":service_id}
            })
                .done(function (message) {
                    var json = eval("(" + message + ")")
                    if (json.code == "success") {
                        if(json.data != undefined){
                            var base_type_code = renderSelectHandle(base_type_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#base_type").html( '<a lay-event="base_type"></a><select name="base_type" lay-filter="base_type"><option value="">请选择分类</option>' + base_type_code + '</select> ');
                            var base_driver = renderSelectHandle(base_driver_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#base_driver").html( '<a lay-event="base_driver"></a><select name="base_driver" lay-filter="base_driver"><option value="">请选择分类</option>' + base_driver + '</select> ');
                            //表单初始赋值
                            form.val('example', {
                                "connect_code": json.data.connect_code,
                                "base_name": json.data.base_name,
                                "base_type": json.data.base_type,
                                "base_version": json.data.base_version,
                                "base_driver": json.data.base_driver,
                                "base_url": json.data.base_url,
                                "base_user_name": json.data.base_user_name,
                                "base_password": json.data.base_password,
                                "base_bits": json.data.base_bits,
                                "base_init_connect_num": json.data.base_init_connect_num,
                                "base_max_wait_time": json.data.base_max_wait_time,
                                "remark": json.data.remark,
                                "connect_id":json.data.connect_id,
                                "service_id":service_id,
								"handle_type": 'connect_test'
                            })
                            form.render();
						}else{
                            var base_type_code = renderSelectHandle(base_type_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#base_type").html( '<a lay-event="base_type"></a><select name="base_type" lay-filter="base_type"><option value="">请选择分类</option>' + base_type_code + '</select> ');
                            var base_driver = renderSelectHandle(base_driver_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#base_driver").html( '<a lay-event="base_driver"></a><select name="base_driver" lay-filter="base_driver"><option value="">请选择分类</option>' + base_driver+ '</select> ');
                            //表单初始赋值
                            form.val('example', {
                                "connect_code": "",
                                "base_name": "",
                                "base_type": "",
                                "base_version": "",
                                "base_driver": "",
                                "base_url": "",
                                "base_user_name": "",
                                "base_password": "",
                                "base_bits": "",
                                "base_init_connect_num": "",
                                "base_max_wait_time": "",
                                "remark": "",
                                "service_id":service_id,
                                "connect_id":"",
                                "handle_type": 'connect_test'
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
                if(row.connect_id == obj.connect_id){
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
                url: '/connectBase-add.do',
                type: 'POST',
                data: data.field,
                async: false,
                //返回接收前

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

        form.on('submit(demo2)', function (data) {
            $.ajax({
                url: '/test-database.do',
                type: 'POST',
                data: data.field,
                beforeSend: function () {
                    loading = layer.msg('处理中，请稍候...', {icon: 16, time: false, shade: 0});
                }
            })
                .done(function (message) {
                    layer.close(loading);
                    var json = eval("(" + message + ")")
                    if (json.code == "success") {
                        layer.msg(json.msg);
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