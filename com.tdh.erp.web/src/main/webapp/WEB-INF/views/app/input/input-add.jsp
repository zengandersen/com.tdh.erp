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

	<link rel="stylesheet" type="text/css" href="/lib/layui-v2.6.3/css/layui1.css">
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
	<input type="text" name="goods_ids" id = "goods_ids" value=""  style="display: none">
	<input type="text" name="consumer_ids" id = "consumer_ids" value=""  style="display: none">

	<div class="layui-form-item">
		<label class="layui-form-label">所属厂商</label>
		<div class="layui-input-inline" id = "factory_id">

		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品选择</label>
		<div class="layui-input-inline">
			<input type="text" name="goods_id" placeholder="请输入" autocomplete="off" class="layui-input" id="goods_id" value="" ts-selected="">
		</div>
	</div>

	<div class="layui-form-item">
	<div class="layui-inline">
		<label class="layui-form-label">入库日期</label>
		<div class="layui-input-inline">
			<input type="text" name="input_date" id="input_date" lay-verify="input_date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
		</div>
	</div>
	</div>
		<div class="layui-form-item">
		<label class="layui-form-label">入库数量</label>
		<div class="layui-input-inline">
			<input type="number" name="input_num" placeholder="0"  class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">入库价格</label>
		<div class="layui-input-inline">
			<input type="number" name="input_price" placeholder="0"  class="layui-input">
		</div>
	</div>


	<div class="layui-form-item">
		<label class="layui-form-label">属性选择</label>
		<div class="layui-input-block" id="nature">
			<input type="checkbox" name="supplement" title="补货入库">
			<input type="checkbox" name="returned" title="退货入库">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">退货人信息</label>
		<div class="layui-input-inline">
			<input type="text" name="consumer_id" placeholder="请输入" autocomplete="off" class="layui-input" id="consumer_id" value="" ts-selected="">
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
			<button class="layui-btn" lay-submit="" lay-filter="demo2">添加退货人信息</button>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</div>
</form>


<script type="text/javascript" src="/js/lay-config.js"></script>

<script src="../lib/jq-module/zyupload/zyupload-1.0.0.min.js" charset="utf-8"></script>

<script type="text/javascript">
 var pathUrl;
 var goods_enum;
    function child(data,factory_enum) {
            $.ajax({
                url: '/query-input-detail.do',
                type: 'POST',
                async: false,
                data: {"detail_id":""}
            })
                .done(function (message) {
                    var json = eval("(" + message + ")")
                    if (json.code == "success") {
                        if(json.data == "create"){
                            var factory_info = renderSelectHandle(factory_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#factory_id").html( '<a lay-event="factory_id"></a><select name="factory_id" lay-filter="factory_id"><option value="">请选择分类</option>' + factory_info + '</select> ');

                            form.val('example', {
                                "input_date":"",
                                "input_num": "",
                                "input_price": "",
                                "remark":"",
								"goods_id":"",
								"consumer_id":""
                            })
                            form.render();
						}
                    }
                    if (json.code == "fail") {
                        layer.msg(json.msg);
                    }
                })
    }



 layui.use(['form', 'layedit', 'laydate','tableSelect','table'], function () {
     var layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
		 form = layui.form;
		  tableSelect = layui.tableSelect;
		  table = layui.table;
     //日期
     laydate.render({
         elem: '#input_date'
     });


		//消费者下拉监听监听
        form.on('select(factory_id)',function(data){
            var selectId = data.value;
            console.log(selectId);
            tableSelect.render({
                elem: '#goods_id',
                searchKey: 'my',
                checkedKey: 'name',
                searchPlaceholder: '自定义文字和name',
                table: {
                    page:false,
                    limits: [50, 100, 200],
                    limit: 50,
                    method:'post',
                    url: '/query-goods-by-factory-pageList.do?factoryId=' + selectId,
                    cols: [[
                        { type: 'checkbox' },
                                       {field: 'name', title: '商品名称'},
                                       {field: 'goods_img', title: '商品图片',templet: function(d){
                                               return ' <div><img src="'+d.goods_img+'" style="width: 60px; height: 60px;" onclick="showBigImage(this)"></div>';
                                           }},
                        {field: 'total', title: '库存数量'}
                    ]]
                },
                done: function (elem, data) {
                    var NEWJSON = [];
                    var id = [];
                    layui.each(data.data, function (index, item) {
                        NEWJSON.push(item.name);
                        id.push(item.id)
                    })
                    elem.val(NEWJSON.join(","))
                    $("#goods_ids").attr('value',id);

                }
            })

            tableSelect.render({
                elem: '#consumer_id',
                searchKey: 'name',
                checkedKey: 'id',
                searchPlaceholder: '自定义文字和name',
                table: {
                    page:false,
                    limits: [50, 100, 200],
                    limit: 50,
                    method:'post',
                    url: '/query-consumer-enum.do',
                    cols: [[
                        { type: 'radio' },
                        {field: 'name', title: '客户名称'},
                        {field: 'addr', title: '客户地址'}
                    ]]
                },
                done: function (elem, data) {
                    var NEWJSON = [];
                    var id = [];
                    layui.each(data.data, function (index, item) {
                        NEWJSON.push(item.name);
                        id.push(item.consumer_id)
                    })
                    elem.val(NEWJSON.join(","))
                    $("#consumer_ids").attr('value',id);
                }
            })
        });
        //监听提交
        form.on('submit(demo1)', function (data) {
            delete data.field.result_code;
            // var reqData =  JSON.stringify([data.field], null, 2);
			var reqData = JSON.stringify(data.field, null, 2);
			$.ajax({
                url: '/add-single-goods.do',
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
         type: 3,
         title: false,
         closeBtn: 1,
         shadeClose: true, //点击阴影关闭
         area:["50px","50px"],
         content: "<img src=" + $(e).attr('src') + " />"
     });
 }


</script>
</body>
</html>