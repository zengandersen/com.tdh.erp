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
	<input type="text" name="goods_img" id = "goods_img" style="display: none">
	<input type="text" name="goods_id"  style="display: none">

	<div class="layui-form-item">
		<label class="layui-form-label">所属厂商</label>
		<div class="layui-input-inline" id = "factory_id">

		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">商品名称</label>
		<div class="layui-input-inline">
			<input type="text" name="goods_name"  autocomplete="off" placeholder="请输入商品名称" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">商品编码</label>
		<div class="layui-input-inline">
			<input type="text" name="goods_code"  autocomplete="off" placeholder="请输入商品编码" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">单位</label>
		<div class="layui-input-inline" id = "unit">

		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">进货单价</label>
		<div class="layui-input-inline">
			<input type="text" name="purch_price"  autocomplete="off" placeholder="请选择进货单价" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">商品单价</label>
		<div class="layui-input-inline">
			<input type="text" name="unit_price"  autocomplete="off" placeholder="请选择进货单价" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item" id="imgdiv">
		<label class="layui-form-label">当前商品图片</label>
		<div class="layui-input-inline" >
			<img type="text"  id="uploadComplate_info"  src = "" style="width: 150px; height: 150px;" onclick="showBigImage(this)">
			<div style="width: 230px;"><p style="color: #CC0000;">说明:重新上传新图，即可对原图进行替换</p></div>
		</div>
	</div>


	<div class="layui-form-item ">
		<label class="layui-form-label">图片上传</label>
		<div id="zyupload" class="zyupload" style="margin-left: 110px"></div>
	</div>


	<div class="layui-form-item layui-form-text"style="margin-top:160px">
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
    function child(data,factory_enum,unit_enum) {
            $.ajax({
                url: '/query-goods-detail.do',
                type: 'POST',
                async: false,
                data: {"detail_id":data.goods_id}
            })
                .done(function (message) {
                    var json = eval("(" + message + ")")
                    if (json.code == "success") {
                        if(json.data != "create"){
                            var factory_info = renderSelectHandle(factory_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#factory_id").html( '<a lay-event="factory_id"></a><select name="factory_id" lay-filter="factory_id"><option value="">请选择分类</option>' + factory_info + '</select> ');
                            var unit_info = renderSelectHandle(unit_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#unit").html( '<a lay-event="unit"></a><select name="unit" lay-filter="unit"><option value="">请选择分类</option>' + unit_info + '</select> ');
                            //表单初始赋值
                            $("#uploadComplate_info").attr('src',json.data.goods_img);
                            form.val('example', {
                                "factory_id": json.data.factory_id,
                                "goods_name": json.data.goods_name,
                                "goods_code": json.data.goods_code,
                                "unit": json.data.unit,
                                "purch_price":json.data.purch_price,
                                "unit_price":json.data.unit_price,
                                "remark":json.data.remark,
                                "goods_id":json.data.goods_id,
								"goods_img":json.data.goods_img
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
                url: '/edit-goods.do',
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

    $(function () {
        // 初始化插件
        $("#zyupload").zyUpload({
            width: "650px",                 // 宽度
            height: "30px",                 // 宽度
            itemWidth: "140px",                 // 文件项的宽度
            itemHeight: "100px",                 // 文件项的高度
            url: "/upload-goods.do",  // 上传文件的路径
            fileType: ["jpg", "png"],// 上传文件的类型
            fileSize: 51200000,                // 上传文件的大小
            multiple: false,                    // 是否可以多个文件上传
            dragDrop: false,                    // 是否可以拖动上传文件
            tailor: false,                    // 是否可以裁剪图片
            del: true,                    // 是否可以删除文件
            finishDel: false,  				  // 是否在上传文件完成后删除预览
            /* 外部获得的回调接口 */
            loading: true,
            onSelect: function (selectFiles, allFiles) {    // 选择文件的回调方法  selectFile:当前选中的文件  allFiles:还没上传的全部文件
                console.info("当前选择了以下文件：");
                console.info(selectFiles);
            },
            onDelete: function (file, files) {              // 删除一个文件的回调方法 file:当前删除的文件  files:删除之后的文件
                console.info("当前删除了此文件：");
                console.info(file.name);
            },
            onSuccess: function (file, response) {
                console.info("此文件上传成功：");
                console.info(file.name);
                console.info("此文件上传到服务器地址：");
                console.info(response);
                $("#uploadInf").append("<p>上传成功，文件地址是：" + response + "</p>");
                var json = eval("(" + response + ")")
                if(pathUrl == undefined){
                    pathUrl = json.data;
				}else{
                    pathUrl = pathUrl+","+json.data;
				}
            },
            onFailure: function (file, response) {          // 文件上传失败的回调方法
                console.info("此文件上传失败：");
                console.info(file.name);
            },
            onComplete: function (response) {           	  // 上传完成的回调方法
                console.info("文件上传完成");
                console.info(response);
                $('#goods_img').val(pathUrl);
            }
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