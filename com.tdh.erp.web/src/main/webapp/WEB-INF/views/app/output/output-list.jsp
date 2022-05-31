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
                                <input value="" type="text" placeholder="商品名称/商品编码" id="goods_name" name="goods_name" autocomplete="off" class="layui-input">
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
                                    <label class="layui-form-label">是否为刷单</label>
                                    <div class="layui-input-inline" id = "select_two">

                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label">是否为赠品</label>
                                    <div class="layui-input-inline" id = "select_three">

                                    </div>
                                </div>
                            </div>
                            <div class="layui-inline"><label class="layui-form-label"
                                                             style="width : 120px">出库日期区间</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="out_date_start" id="out_date_start"
                                           lay-verify="out_date_start" placeholder="开始日期" autocomplete="off"
                                           class="layui-input">
                                </div>
                                <div class="layui-input-inline">
                                    <input type="text" name="out_date_end" id="out_date_end" lay-verify="out_date_end"
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
                                            class="layui-icon layui-icon-add-circle"></i>商品出库
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
        url: '/query-output-list.do',
        toolbar: '#toolbarDemo',
        method: 'post',
        page: true,
        limits: [50, 100, 200],
        limit: 50,
        loading: true,
        end: '没有更多数据展示啦', /*没有数据之后的提示语*/
        cols: [[{title: '序号', type: 'numbers'},
            {field: 'factory_name', title: '厂商名称'},
            {field: 'goods_name', title: '商品名称'},
            {field: 'goods_code', title: '商品编码'},
            {
                field: 'goods_img', title: '商品图片', templet: function (d) {
                    return ' <div><img src="' + d.goods_img + '" style="width: 50px; height: 50px;" onclick="showBigImage(this)"></div>';
                }
            },
            {field: 'output_date_tochar', title: '出库时间'},
            {field: 'output_num', title: '出库数量'},
            {field: 'output_price', title: '出库价格'},
            {field: 'is_click_farming_chn', title: '是否为刷单'},
            {field: 'is_gift_chn', title: '是否为赠品'}

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
            var inputNameStart = $('#output_date_start').val();
            var inputNameEnd = $('#output_date_end').val();
            var factoryId =$('#factory_select').val();
            var isClickFarming =$('#is_click_farming').val();
            var isGift =$('#is_gift').val();
            if ($('#goods_name').val() || $('#output_date_start').val() || $('#output_date_end').val()
            ||$('#factory_select').val()||$('#is_click_farming').val() || $('#is_gift').val() ) {
                var index = layer.msg('处理中，请稍候...', {icon: 16, time: false, shade: 0});
                tableIns.reload({
                    page: {curr: 1},
                    where: {name: fileName, start_date: inputNameStart, end_date: inputNameEnd,select_one:factoryId ,select_two:isClickFarming ,select_three: isGift }
                });
                layer.close(index);
            } else {
                tableIns.reload({page: {curr: 1}, where: {name: ""}});
                layer.close(index);
            }
        }, add: function () {
            var index = layer.open({
                type: 2,
                title: "出库单",
                content: '/to-output-add.do',
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
var is_click_farming_enum;
var is_gift_enum;
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
                            is_click_farming_enum = json.data[i].QUERY_STATUS;
                            var clickFarming = renderSelectHandle(is_click_farming_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#select_two").html( '<a lay-event="is_click_farming"></a><select name="is_click_farming" id = "is_click_farming" lay-filter="is_click_farming"><option value="">请选择分类</option>' + clickFarming + '</select> ');

                            is_gift_enum = json.data[i].QUERY_STATUS;
                            var gift = renderSelectHandle(is_gift_enum, {valueField: "id", textField: "name", selectedValue:""});
                            $("#select_three").html( '<a lay-event="is_returned"></a><select name="is_gift"  id = "is_gift" lay-filter="is_gift"><option value="">请选择分类</option>' + gift + '</select> ');

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