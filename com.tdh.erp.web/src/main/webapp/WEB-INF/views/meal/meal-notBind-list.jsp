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
                    <div class="layui-form-item">
                        <div class="layui-inline"><label class="layui-form-label">查询信息</label>
                            <div class="layui-input-inline"><input value="" type="text" placeholder="商品名称/商品编码"
                                                                   id="search_name" name="search_name"
                                                                   autocomplete="off" class="layui-input"></div>
                        </div>
                        <div class="layui-inline">
                            <button type="button" class="layui-btn layui-btn-primary" data-type="getInfo" id="btnQuery"
                                    name="btnQuery"><i class="layui-icon"></i> 搜 索
                            </button>

                            <a  class="layui-btn" id="bind-menu"><i class="layui-icon layui-icon-add-1"></i>绑定菜单</a>
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
        url: '/query-meal-notbind-list.do',
        toolbar: '#toolbarDemo',
        method: 'post',
        where:{
            meal_id: param
        },
        limits: [50, 100, 200],
        limit: 50,
        loading: true,
        end: '没有更多数据展示啦', /*没有数据之后的提示语*/
        cols: [[{type: 'checkbox'},
            {field: 'factory_name', title: '所属厂商'},
            {field: 'goods_name', title: '商品名称'},
            {field: 'goods_code', title: '商品编码'},
            {field: 'goods_img', title: '商品图片',templet: function(d){
                    return ' <div><img src="'+d.goods_img+'" style="width: 50px; height: 50px;" onclick="showBigImage(this)"></div>';
                }},
            {field: 'total', title: '数量'},
            {field: 'warning_num', title: '告警数量'}
            ]],
        done: function (res, curr, count) {
            keyboardEvent(event);
            layer.closeAll();
        }
    });
    $('#bind-menu').click(function () {
        var oldData = table.cache[layTableId];
        var repertory_ids;
        for(var i=0;i<oldData.length;i++) {
            if(oldData[i].LAY_CHECKED ==true){
                if(repertory_ids == undefined){
                    repertory_ids = oldData[i].repertory_id;
                }else{
                    repertory_ids = repertory_ids +","+oldData[i].repertory_id;
                }
            }
        }
        $.ajax({
            url: '/to-do-meal-bind.do',
            type: 'POST',
            data: {"repertory_ids":repertory_ids,"meal_id":param}
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
            var fileName = $('#search_name').val();
            if ($('#search_name').val()) {
                var index = layer.msg('处理中，请稍候...', {icon: 16, time: false, shade: 0});
                tableIns.reload({page: {curr: 1}, where: {name: fileName}});
                layer.close(index);
            } else {
                tableIns.reload({page: {curr: 1}, where: {name: ""}});
                layer.close(index);
            }
        }, add: function () {
            var index = layer.open({
                type: 2,
                title: "新增",
                content: '/to-repertory-add.do',
                maxmin: true,
                success: function (layer, index) {
                    var iframe = window['layui-layer-iframe' + index];
                    iframe.child("",factory_enum)
                }
            });
            layer.full(index);
        }, save: function () {
            var oldData = table.cache[layTableId];
            console.log(oldData);
            for (var i = 0, row; i < oldData.length; i++) {
                row = oldData[i];
                /* if (!row.dept_id) { layer.msg("没有新增数据", {icon: 5}); //提示 return; }*/
            }
            document.getElementById("jsonResult").innerHTML = JSON.stringify(table.cache[layTableId], null, 2);
            /*使用JSON.stringify() 格式化输出JSON字符串*/
            var reqData = JSON.stringify(table.cache[layTableId], null, 2);
            $.ajax({
                url: '/edit-factory.do', type: 'POST', data: {"data": reqData}, /*返回接收前*/ beforeSend: function () {
                    loading = layer.msg('处理中，请稍候...', {icon: 16, time: false, shade: 0});
                }
            })/*返回接收后*/.done(function (message) {
                layer.close(loading);
                var json = eval("(" + message + ")")
                if (json.code == "success") {
                    layer.msg(json.msg);
                }
                if (json.code == "fail") {
                    layer.msg(json.msg);
                }
            })
        }, updateRow: function (obj) {
            var oldData = table.cache[layTableId];
            console.log(oldData);
            for (var i = 0, row; i < oldData.length; i++) {
                row = oldData[i];
                if (row.factory_id == obj.factory_id) {
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
    /*监听select下拉选中事件*/
    form.on('select(sex)', function (data) {
        var elem = data.elem;
        /*得到select原始DOM对象*/
        $(elem).prev("a[lay-event='sex']").trigger("click");
    });
    /*监听select下拉选中事件*/
    form.on('select(compound_id)', function (data) {
        var elem = data.elem;
        /*得到select原始DOM对象*/
        $(elem).prev("a[lay-event='compound_id']").trigger("click");
    });
    form.on('select(role_id)', function (data) {
        var elem = data.elem;
        /*得到select原始DOM对象*/
        $(elem).prev("a[lay-event='role_id']").trigger("click");
    });
    /*监听工具条*/
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data, event = obj.event, tr = obj.tr;
        /*获得当前行 tr 的DOM对象;*/
        console.log(data);
        switch (event) {
            case "unit":
                //console.log(data);
                var select = tr.find("select[name='unit']");
                if (select) {
                    var selectedVal = select.val();
                    if (!selectedVal) {
                        layer.tips("请选择一个分类", select.next('.layui-form-select'), {tips: [3, '#FF5722']}); //吸附提示
                    }
                    console.log(selectedVal);
                    $.extend(obj.data, {'unit': selectedVal});
                    activeByType('updateRow', obj.data);	//更新行记录对象
                }
                break;
            case "edit":
                var goods_enum;
                var goods_img;
                $.ajax({
                    url: 'query-goodsenum-by-id.do',
                    type: 'POST',
                    async:false,
                    data: {"goodsId":data.goods_id}
                })
                    .done(function (message) {
                        var json = eval("(" + message + ")")
                        if (json.code == "success") {
                            goods_enum = json.data;
                        }
                        if (json.code == "fail") {
                            layer.msg(json.msg);
                        }
                    })
                $.ajax({
                    url: '/query-goods-detail.do',
                    type: 'POST',
                    data:{"detail_id": data.goods_id},
                    async: false
                })
                    .done(function (message) {
                        var json = eval("(" + message + ")")
                        if (json.code == "success") {
                            goods_img = json.data;
                        }
                        if (json.code == "fail") {
                            layer.msg(json.msg);
                        }
                    })
                var index = layer.open({
                    type: 2,
                    title: "修改",
                    content: '/to-repertory-edit.do',
                    maxmin: true,
                    success: function (layer, index) {
                        var iframe = window['layui-layer-iframe' + index];
                        iframe.child(data,factory_enum,goods_enum,goods_img);
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
                        url: '/del-repertory.do',
                        type: 'POST',
                        data: {"repertory_id": data.repertory_id}
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
window.viewObj ={
    //下拉参数构建
    renderSelectOptions: function(data, settings){
        return  renderDivHandle(data,settings);
    }
};
//字典变量
var factory_enum;
//字典内容初始化
$(document).ready(function () {

    $.ajax({
        url: '/enum-factory.do',
        type: 'POST',
        async:false,
        data: {"data":""}
    })
        .done(function (message) {
            var json = eval("(" + message + ")")
            if (json.code == "success") {
                factory_enum = json.data;
            }
            if (json.code == "fail") {
                layer.msg(json.msg);
            }
        })

});
</script>
</body>
</html>