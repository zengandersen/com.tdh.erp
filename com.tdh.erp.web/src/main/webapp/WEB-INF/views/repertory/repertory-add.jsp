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

	<div class="layui-form-item">
		<label class="layui-form-label">选择厂商</label>
		<div class="layui-input-inline" id = "factory_id">

		</div>
	</div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">选择商品</label>
            <div class="layui-input-inline" id="goods_id">

            </div>
            <div style="width: 230px;"><p style="color: #CC0000;">说明:选择对应商品,即可显示图片</p></div>
        </div>
        <div class="layui-inline">

            <div class="layui-input-inline">
                <img type="text" id="uploadComplate_info" src="" style="width: 100px; height: 100px;"
                     onclick="showBigImage(this)">

            </div>
        </div>
    </div>


    <div class="layui-form-item">
		<label class="layui-form-label">商品总数</label>
		<div class="layui-input-inline">
			<input type="number" name="total"  placeholder="0" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">警告总数</label>
		<div class="layui-input-inline">
			<input type="number" name="warning_num" placeholder="0"  class="layui-input">
		</div>
	</div>





	<div class="layui-form-item layui-form-text"style="margin-top:10px">
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
	var pathUrl ;
	var goods_enum;
    function child(data,factory_enum) {
            $.ajax({
                url: '/query-goods-detail.do',
                type: 'POST',
                async: false,
                data: {"detail_id":data.goods_id}
            })
                .done(function (message) {
                    var json = eval("(" + message + ")")
                    if (json.code == "success") {
                        if(json.data == "create"){
                            var factory_info = renderSelectHandle(factory_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#factory_id").html( '<a lay-event="factory_id"></a><select name="factory_id" lay-filter="factory_id" ><option value="">请选择分类</option>' + factory_info + '</select> ');
                            //表单初始赋值
                            $("#goods_id").html( '<a lay-event="goods_id"></a><select name="goods_id" lay-filter="goods_id" ><option value="">请选择分类</option></select> ');
                            form.val('example', {
                                "factory_id": "",
                                "goods_name": "",
                                "goods_code": "",
                                "unit": "",
                                "purch_price":"",
                                "unit_price":"",
                                "remark":"",
                                "goods_id":""
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

        //厂商下拉监听
        form.on('select(factory_id)',function(data){
            var selectId = data.value;
            console.log(selectId);
            $.ajax({
                url: '/query-goods-by-factory.do',
                type: 'POST',
                data:{"factoryId": selectId},
                async: false
            })
                .done(function (message) {
                    var json = eval("(" + message + ")")
                    if (json.code == "success") {
                        goods_enum = json.data;
                        var goods_info = renderSelectHandle(goods_enum, {valueField: "id", textField: "name", selectedValue:""});
                        $("#goods_id").html( '<a lay-event="goods_id"></a><select name="goods_id" lay-filter="goods_id" ><option value="">请选择分类</option>' + goods_info + '</select> ');
                        form.render();
                    }
                    if (json.code == "fail") {
                        layer.msg(json.msg);
                    }
                })
        });

        form.on('select(goods_id)',function(data){
            var selectId = data.value;
            $.ajax({
                url: '/query-goods-detail.do',
                type: 'POST',
                data:{"detail_id": selectId},
                async: false
            })
                .done(function (message) {
                    var json = eval("(" + message + ")")
                    if (json.code == "success") {
                        $("#uploadComplate_info").attr('src',json.data.goods_img);
                        form.render();
                    }
                    if (json.code == "fail") {
                        layer.msg(json.msg);
                    }
                })
        });


        //监听提交
        form.on('submit(demo1)', function (data) {
            delete data.field.result_code;
            // var reqData =  JSON.stringify([data.field], null, 2);
			var reqData = JSON.stringify(data.field, null, 2);
			$.ajax({
                url: '/add-repertory.do',
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




    showBigImage = function (e) {
        console.log(e);
        layer.open({
            type: 1,
            title: false,
            closeBtn: 1,
            shadeClose: true, //点击阴影关闭
            area: [$(e).width + 'px', $(e).height + 'px'], //宽高
            content: "<img src=" + $(e).attr('src') + " />"
        });
    }
</script>
</body>
</html>