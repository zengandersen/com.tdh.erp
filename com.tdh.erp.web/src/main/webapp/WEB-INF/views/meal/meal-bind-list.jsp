<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();%>
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
    <script type="text/javascript" src="/H-ui-admin/lib/html5shiv.js"></script>
    <script type="text/javascript" src="/H-ui-admin/lib/respond.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/lib/layui-v2.6.3/css/layui.css">
    <link rel="stylesheet" type="text/css" href="/css/public.css"><!--_footer 作为公共模版分离出去-->
    <script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script><!--请在下方写此页面业务相关的脚本-->
    <script src="/lib/layui-v2.6.3/layui.js"></script><!--/meta 作为公共模版分离出去--><title>《进销存系统》 Ver 2022</title>
    <meta name="keywords" content="《进销存系统》 Ver 2022">
    <meta name="description" content="《进销存系统》 Ver 2022">
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <fieldset class="table-search-fieldset">
            <legend>搜索信息</legend>
            <div style="margin: 10px 10px 10px 10px">
                <form class="layui-form layui-form-pane" action="">
                        <div class="layui-inline">
                            <button type="button" name="btnAdd" class="layui-btn layui-btn-normal" data-type="bind"><i
                                    class="layui-icon layui-icon-ok-circle"></i>绑定商品
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>
        <div id="tableRes" class="table-overlay">
            <table class="layui-hide" id="dataTable" lay-filter="dataTable"></table>
        </div>
    </div>
</div><!--保存结果输出-->
<div class="layui-card" style="display: none">
    <div class="layui-card-header">保存结果输出</div>
    <div class="layui-card-body layui-text">
        <blockquote class="layui-elem-quote layui-quote-nm">
            <pre id="jsonResult"><span class="layui-word-aux">请点击“保存”后查看输出信息……</span></pre>
        </blockquote>
    </div>
</div>
</body>

<script type="text/javascript"> /*枚举对象*/
var url = window.location.href;
var count = url.indexOf("=");
var index = url.substring(count+1);
var param = decodeURI(index);
var compound_enum;
var role_enum;
/*layui 模块化引用*/
layui.use(['jquery', 'table', 'layer'], function () {
    var $ = layui.$, table = layui.table, form = layui.form, layer = layui.layer;
    var flow = layui.flow;
    /*数据表格实例化*/
    var tbWidth = $("#tableRes").width();
    var layTableId = "layTable";
    var tableIns = table.render({
        id: layTableId,
        elem: '#dataTable',
        url: '/query-meal-bind-list.do',
        toolbar: '#toolbarDemo',
        method: 'post',
        limits: [15 , 50, 100, 200],
        limit: 15,
        loading: true,
        page: true,
        where:{
            meal_id: param
        },
        end: '没有更多数据展示啦', /*没有数据之后的提示语*/
        cols: [[{title: '序号', type: 'numbers'},
            {field: 'factory_name', title: '厂家名称'},
            {field: 'factory_code', title: '厂家编码'},
            {field: 'goods_name', title: '商品名称'},
            {field: 'goods_code', title: '商品编码'},
            {field: 'goods_img', title: '商品图片',templet: function(d){
                    return ' <div><img src="'+d.goods_img+'" style="width: 50px; height: 50px;" onclick="showBigImage(this)"></div>';
                }},
            {field: 'total', title: '库存剩余数量'},
            {field: 'purch_price', title: '套餐进货单价'},
            {field: 'unit_price', title: '商品单价'},
            {field: 'type', title: '操作',templet: function (d) {
                return '<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del" lay-id="\' + d.factory_id + \'"><i class="layui-icon layui-icon-delete"></i>移除</a>';
            }
        }]],
        done: function (res, curr, count) {
            keyboardEvent(event);
            layer.closeAll();
        }
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
    /*定义事件集合*/
    var active = {
        getInfo: function () {
            var fileName = $('#meal_name').val();
            if ($('#meal_name').val()) {
                var index = layer.msg('处理中，请稍候...', {icon: 16, time: false, shade: 0});
                tableIns.reload({page: {curr: 1}, where: {name: fileName}});
                layer.close(index);
            } else {
                tableIns.reload({page: {curr: 1}, where: {name: ""}});
                layer.close(index);
            }
        }, bind: function () {
            var index = layer.open({
                type: 2,
                title: "绑定商品",
                content: '/to-meal-notBind-list.do?meal_id='+param,
                maxmin: true,
                success: function (layer, index) {
                    // var iframe = window['layui-layer-iframe' + index];
                    // iframe.child("")
                }
            });
            layer.full(index);
        }, updateRow: function (obj) {
            var oldData = table.cache[layTableId];
            console.log(oldData);
            for (var i = 0, row; i < oldData.length; i++) {
                row = oldData[i];
                if (row.meal_id == obj.meal_id) {
                    $.extend(oldData[i], obj);
                    return;
                }
            }
            tableIns.reload({data: oldData});
        },
    }
    /*激活事件*/
    var activeByType = function (type, arg) {
        if (arguments.length === 2) {
            active[type] ? active[type].call(this, arg) : '';
        } else {
            active[type] ? active[type].call(this) : '';
        }
    }
    $('#btnQuery').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    /*注册按钮事件*/
    $('.layui-btn[data-type]').on('click', function () {
        var type = $(this).data('type');
        activeByType(type);
    });

    /*监听工具条*/
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data, event = obj.event, tr = obj.tr;
        /*获得当前行 tr 的DOM对象;*/
        console.log(data);
        switch (event) {

            case "edit":
                var index = layer.open({
                    type: 2,
                    title: "修改",
                    content: '/to-meal-info-edit.do',
                    maxmin: true,
                    success: function (layer, index) {
                        var iframe = window['layui-layer-iframe' + index];
                        iframe.child(data);
                    }
                });
                layer.full(index);
                break;

            case "del":
                layer.confirm('真的删除行么？', function (index) {
                    obj.del();
                    /*删除对应行（tr）的DOM结构，并更新缓存*/
                    layer.close(index);
                    /* activeByType('removeEmptyTableCache');*/
                    $.ajax({
                        url: '/del-bind.do',
                        type: 'POST',
                        data: {"meal_bind_id": data.meal_bind_id}
                    }).done(function (message) {
                        var json = eval("(" + message + ")")
                        if (json.code == "success") {
                            layer.msg(json.msg);
                        }
                        if (json.code == "fail") {
                            layer.msg(json.msg);
                        }
                    })
                });
                break;

        }
    });
});

</script>
</body>
</html>