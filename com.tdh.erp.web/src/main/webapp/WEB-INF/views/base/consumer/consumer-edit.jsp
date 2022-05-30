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
	<link rel="stylesheet" href="/lib/jq-module/zyupload/zyupload-1.0.0.min.css" media="all">

	<script type="text/javascript" src="/H-ui-admin/lib/html5shiv.js"></script>
	<script type="text/javascript" src="/H-ui-admin/lib/respond.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/lib/layui-v2.6.3/css/layui.css">
	<link rel="stylesheet" type="text/css" href="/css/public.css">

	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script>
	<!--请在下方写此页面业务相关的脚本-->
	<script src="/lib/layui-v2.6.3/layui.js"></script>

	<!--/meta 作为公共模版分离出去-->

	<title>《智能分诊系统》 Ver 2019</title>
	<meta name="keywords" content="《智能分诊系统》 Ver 2019">
	<meta name="description" content="《智能分诊系统》 Ver 2019">
	<style>
		body {
			background-color: #ffffff;
		}
	</style>
</head>
<body>
<form class="layui-form layui-form-pane" action="" lay-filter="example">

	<input type="text" name="consumer_id"  style="display: none">
	<div class="layui-form-item">
		<label class="layui-form-label">客户姓名</label>
		<div class="layui-input-inline">
			<input type="text" name="name"  autocomplete="off" placeholder="请输入客户姓名" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">性别</label>
		<div class="layui-input-inline" id = "sex">

		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">电话</label>
		<div class="layui-input-inline">
			<input type="text" name="tel"  autocomplete="off" placeholder="请输入客户电话" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">客户地址</label>
		<div class="layui-input-inline">
			<input type="text" name="addr"  autocomplete="off" placeholder="请输入地址" class="layui-input">
		</div>
		<div class="layui-form-mid layui-word-aux">请务必输入"XX省XX市"信息,以便系统自动获取省份数据</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">属性选择</label>
		<div class="layui-input-block" id="nature">
			<input type="checkbox" name="return" title="存在退货">
			<input type="checkbox" name="friend" title="亲友">
			<input type="checkbox" name="black" title="黑名单成员">
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
<script src="../lib/jq-module/zyupload/zyupload-1.0.0.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript">
    function child(data,sex_enum) {
            $.ajax({
                url: '/query-consumer-detail.do',
                type: 'POST',
                async: false,
                data: {"detail_id":data.consumer_id}
            })
                .done(function (message) {
                    var json = eval("(" + message + ")")
                    if (json.code == "success") {
                        if(json.data != "create"){
                            var sex_info = renderSelectHandle(sex_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#sex").html( '<a lay-event="sex"></a><select name="sex" lay-filter="sex"><option value="">请选择分类</option>' + sex_info + '</select> ');

                            form.val('example', {
                                "name": json.data.name,
                                "sex": json.data.sex,
                                "tel": json.data.tel,
                                "addr": json.data.addr,
                                "return":json.data.return,
                                "friend":json.data.friend,
                                "black":json.data.black,
                                "remark":json.data.remark,
								"consumer_id": json.data.consumer_id
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
                if(row.item_id == obj.item_id){
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
            delete data.field.result_code;
            // var reqData =  JSON.stringify([data.field], null, 2);
			var reqData = JSON.stringify(data.field, null, 2);
			$.ajax({
                url: '/edit-consumer.do',
                type: 'POST',
                data:{"data": reqData},
                async: false
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
</body>
</html>