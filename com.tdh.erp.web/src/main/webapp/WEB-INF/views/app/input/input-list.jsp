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
<style>
    body {
        background-color: #ffffff;
    }
</style>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <fieldset class="table-search-fieldset">
            <legend>搜索信息</legend>
            <div style="margin: 10px 10px 10px 10px">
                <form class="layui-form layui-form-pane" action="">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">查询信息</label>
                            <div class="layui-input-inline">
                                <input value="" type="text" placeholder="商品名称/商品编码/套餐名称/套餐编码" id="goods_name" name="goods_name" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">批次号</label>
                            <div class="layui-input-inline">
                                <input value="" type="text" placeholder="批次号" id="app_no" name="app_no" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                <label class="layui-form-label">所属厂商</label>
                                <div class="layui-input-inline" id = "select_one">

                                </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label">是否为补单</label>
                                    <div class="layui-input-inline" id = "select_two">

                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label">是否为退货</label>
                                    <div class="layui-input-inline" id = "select_three">

                                    </div>
                                </div>
                            </div>
                            <div class="layui-inline"><label class="layui-form-label"
                                                             style="width : 120px">入库日期区间</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="input_date_start" id="input_date_start"
                                           lay-verify="input_date_start" placeholder="开始日期" autocomplete="off"
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="input_date_end" id="input_date_end" lay-verify="input_date_end"
                                           placeholder="结束日期" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                                <div class="layui-inline">
                                    <button type="button" class="layui-btn layui-btn-primary" data-type="getInfo"
                                            id="btnQuery"
                                            name="btnQuery"><i class="layui-icon"></i> 搜 索
                                    </button>

                                    <button type="button" name="btnAdd" class="layui-btn layui-btn-normal"
                                            data-type="add"><i
                                            class="layui-icon layui-icon-add-circle"></i>商品入库
                                    </button>
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
var PWD = document.getElementById("LAY-user-login-password");
var compound_enum;
var role_enum;
/*layui 模块化引用*/
layui.use(['jquery', 'table', 'layer', 'laydate'], function () {
    var $ = layui.$, table = layui.table, form = layui.form, layer = layui.layer, laydate = layui.laydate;
    var flow = layui.flow;
    /*数据表格实例化*/
    var tbWidth = $("#tableRes").width();
    var layTableId = "layTable";
    laydate.render({
        elem: '#input_date_start'
    });

    laydate.render({
        elem: '#input_date_end'
    });
    var tableIns = table.render({
        id: layTableId,
        elem: '#dataTable',
        url: '/query-input-list.do',
        toolbar: '#toolbarDemo',
        method: 'post',
        page: true,
        limits: [50, 100, 200],
        limit: 50,
        loading: true,
        end: '没有更多数据展示啦', /*没有数据之后的提示语*/
        cols: [[{title: '序号', type: 'numbers'},
            {field: 'app_no', title: '批次号',width: 200},
            {field: 'factory_name', title: '厂商名称'},
            {field: 'goods_name', title: '商品名称'},
            {field: 'goods_code', title: '商品编码'},
            {
                field: 'goods_img', title: '商品图片', templet: function (d) {
                    return ' <div><img src="' + d.goods_img + '" style="width: 50px; height: 50px;" onclick="showBigImage(this)"></div>';
                }
            },
            {field: 'input_date_tochar', title: '入库时间'},
            {field: 'input_num', title: '入库数量'},
            {field: 'input_price', title: '入库价格'},
            {field: 'is_supplement_chn', title: '是否为补单'},
            {field: 'is_returned_chn', title: '是否为退货'}

        ]],
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
            var fileName = $('#goods_name').val();
            var inputNameStart = $('#input_date_start').val();
            var inputNameEnd = $('#input_date_end').val();
            var factoryId =$('#factory_select').val();
            var isSupplement =$('#is_supplement').val();
            var isReturned =$('#is_returned').val();
            var appNo = $('#app_no').val();
            if ($('#goods_name').val() || $('#input_date_start').val() || $('#input_date_end').val()
            ||$('#factory_select').val()||$('#is_supplement').val() || $('#is_returned').val()||$('#app_no').val()) {
                var index = layer.msg('处理中，请稍候...', {icon: 16, time: false, shade: 0});
                tableIns.reload({
                    page: {curr: 1},
                    where: {name: fileName, start_date: inputNameStart, end_date: inputNameEnd,select_one:factoryId ,select_two:isSupplement ,select_three: isReturned }
                });
                layer.close(index);
            } else {
                tableIns.reload({page: {curr: 1}, where: {name: ""}});
                layer.close(index);
            }
        }, add: function () {
            var index = layer.open({
                type: 2,
                title: "入库单",
                content: '/to-input-add.do',
                maxmin: true,
                success: function (layer, index) {
                    var iframe = window['layui-layer-iframe' + index];
                    iframe.child("", factory_enum)
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
            case "bind":
                var index = layer.open({
                    type: 2,
                    title: "绑定信息",
                    content: '/to-meal-bind.do?meal_id=' + data.meal_id,
                    maxmin: true,
                    success: function (layer, index) {
                        // var iframe = window['layui-layer-iframe' + index];
                        // iframe.child(data);
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
                        url: '/del-meal.do',
                        type: 'POST',
                        data: {"meal_id": data.meal_id}
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


window.viewObj = {
    //下拉参数构建
    renderSelectOptions: function (data, settings) {
        return renderDivHandle(data, settings);
    }
};
//字典变量
var factory_enum;
//字典内容初始化
var is_supplement_enum;
var is_returned_enum;
$(document).ready(function () {
    $.ajax({
        url: '/query-dict-detail.do',
        type: 'POST',
        async: false,
        data: {"data":'QUERY_STATUS'}
    })
        .done(function (message) {
            var json = eval("(" + message + ")")
            if (json.code == "success") {
                for(var i =0 ;i<json.data.length;i++){
                    var obj = json.data[i];
                    for(var key in obj){
                        if('QUERY_STATUS'== key){
                            is_supplement_enum = json.data[i].QUERY_STATUS;
                            var supplement = renderSelectHandle(is_supplement_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#select_two").html( '<a lay-event="is_supplement"></a><select name="is_supplement" id = "is_supplement" lay-filter="is_supplement"><option value="">请选择分类</option>' + supplement + '</select> ');

                            is_returned_enum = json.data[i].QUERY_STATUS;
                            var returned = renderSelectHandle(is_returned_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#select_three").html( '<a lay-event="is_returned"></a><select name="is_returned"  id = "is_returned" lay-filter="is_returned"><option value="">请选择分类</option>' + returned + '</select> ');

                        }
                    }
                }
            }
            if (json.code == "fail") {
                layer.msg(json.msg);
            }
        })
    $.ajax({
        url: '/enum-factory.do',
        type: 'POST',
        async: false,
        data: {"data": ""}
    })
        .done(function (message) {
            var json = eval("(" + message + ")")
            if (json.code == "success") {
                factory_enum = json.data;
                var factory_info = renderSelectHandle(factory_enum, {valueField: "id", textField: "name", selectedValue:""});
                $("#select_one").html( '<a lay-event="factory_select"></a><select name="factory_select" id= "factory_select" lay-filter="factory_select"><option value="">请选择分类</option>' + factory_info + '</select> ');
            }
            if (json.code == "fail") {
                layer.msg(json.msg);
            }
        })
});
</script>
</body>
</html>