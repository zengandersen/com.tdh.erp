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
    <input type="text" name="id" style="display: none">
    <input type="text" name="parent_id" style="display: none">
	<input type="text" name="target" style="display: none">
	<input type="text" name="image" style="display: none">
    <div class="layui-form-item">
		<label class="layui-form-label">父级菜单</label>
		<div class="layui-input-block" id = "parent_menu">

		</div>
	</div>
	<div class="layui-form-item">
		<label for="" class="layui-form-label">自定义图标</label>
		<div class="layui-input-block">
			<input type="text" id="icon"  name = "icon" value="fa-arrows" lay-filter="icon" class="hide">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">菜单名称</label>
		<div class="layui-input-inline">
			<input type="text" name="title_name" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
		</div>
		<div class="layui-form-mid layui-word-aux">请填写菜单编码(含大写英文及下划线）,之后再参数配置模块中的菜单信息下进行中文信息配置</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">菜单地址</label>
		<div class="layui-input-inline">
			<input type="text" name="href" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
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
	var menu_info;
	var id;
    var form;
    var index = parent.layer.getFrameIndex(window.name);
    function child(data, menu_info_enum, menu_icon_enum) {
        id = data;
            if(data!=""){
                $.ajax({
                    url: '/query-menu-detail.do',
                    type: 'POST',
                    async: false,
                    data: {"data":id}
                })
                    .done(function (message) {
                        var json = eval("(" + message + ")")
                        if (json.code == "success") {
                            var parent_title_code = renderSelectHandle(menu_info_enum, {valueField: "id", textField: "name", selectedValue:json.data.parent_title_code});
                            $("#parent_menu").html( '<a lay-event="parent_menu"></a><select name="parent_menu" lay-filter="parent_menu"><option value="">请选择分类</option>' + parent_title_code + '</select> ');
                            var icon = renderSelectHandle(menu_icon_enum, {valueField: "id", textField: "name", selectedValue: json.data.icon});
                            $("#icon").html( '<option value="请选择分类"></option>'+icon );
                            //表单初始赋值
                            form.val('example', {
                                "id": json.data.id,
                                "parent_id": json.data.parent_id,
                                "parent_menu": json.data.parent_title_code,
								"icon": json.data.icon,
								"title_name": json.data.title_name,
								"href": json.data.href,
								"target":"",
								"image":""
                            })
                            form.render();
                        }
                        if (json.code == "fail") {
                            layer.msg(json.msg);
                        }
                    })
            }else{
                var parent_title_code = renderSelectHandle(menu_info_enum, {valueField: "id", textField: "name", selectedValue:""});
                $("#parent_menu").html( '<a lay-event="parent_menu"></a><select name="parent_menu" lay-filter="parent_menu"><option value="">请选择分类</option>' + parent_title_code + '</select> ');
                var icon = renderSelectHandle(menu_icon_enum, {valueField: "id", textField: "name", selectedValue: ""});
                $("#icon").html( '<option value="请选择分类"></option>'+icon );
                //表单初始赋值
                form.val('example', {
                    "id": "",
                    "parent_id": "",
                    "title_name":"",
                    "href": "",
                    "target":"",
                    "image":""
                })
                form.render();
            }


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
                url: '/add_update_menu_info.do',
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

    layui.use(['iconPickerFa', 'form', 'layer'], function () {
        var iconPickerFa = layui.iconPickerFa,
            form = layui.form,
            layer = layui.layer,
            $ = layui.$;
        iconPickerFa.render({
            // 选择器，推荐使用input
            elem: '#icon',
            // fa 图标接口
            url: "/lib/less/variables.less",
            // 是否开启搜索：true/false
            search: true,
            // 是否开启分页
            page: true,
            // 每页显示数量，默认12
            limit: 12,
            // 点击回调
            click: function (data) {
                console.log(data);
            },
            // 渲染成功后的回调
            success: function (d) {
                console.log(d);
            }
        });

    });

</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>