<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
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
    <!--[if lt IE 9]>
    <script type="text/javascript" src="/H-ui-admin/lib/html5shiv.js"></script>
    <script type="text/javascript" src="/H-ui-admin/lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/css/H-ui.admin.css"/>
    <link rel="stylesheet" type="text/css" href="/H-ui-admin/lib/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/skin/default/skin.css" id="skin"/>
    <link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/css/style.css"/>
    <link rel="stylesheet" href="/layer/assets/layui/css/layui.css">
    <link rel="stylesheet" href="/layer/assets/common.css"/>

    <!--[if IE 6]>
    <script type="text/javascript" src="/H-ui-admin/lib/DD_belatedPNG_0.0.8a-min.js"></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <!--/meta 作为公共模版分离出去-->

    <title>《进销存系统》 Ver 2022</title>
    <meta name="keywords" content="《进销存系统》 Ver 2022">
    <meta name="description" content="《进销存系统》 Ver 2022">

</head>
<body class="childBody">
<div class="layui-card">
    <div class="layui-card-body layui-text">
        <div id="toolbar">
            <div>
                <input type="text" class="input-text" style="width:280px" placeholder="字典类型编码/字典类型名称" value=""
                       id="dict_type_name" name="dict_type_name">
                <button type="button" id="btnQuery" name="btnQuery" class="layui-btn" data-type="getInfo"><i
                        class="Hui-iconfont">&#xe665;</i> 查询
                </button>
                <button type="button" class="layui-btn layui-btn-sm" data-type="addRow" title="添加一行">
                    <i class="layui-icon layui-icon-add-1"></i> 添加一行
                </button>
                <button type="button" name="btnSave" class="layui-btn" data-type="save"><i
                        class="layui-icon layui-icon-ok-circle"></i>保存
                </button>


            </div>
        </div>
        <div id="tableRes" class="table-overlay">
            <table id="dataTable" lay-filter="dataTable" class="layui-hide"></table>
        </div>
        <div id="masonry">
            <input type="hidden" id="masonry_page" value="1">
        </div>

    </div>
</div>

<!--保存结果输出-->
<div class="layui-card" style="display:none;">
    <div class="layui-card-header">保存结果输出</div>
    <div class="layui-card-body layui-text">
        <blockquote class="layui-elem-quote layui-quote-nm">
            <pre id="jsonResult"><span class="layui-word-aux">请点击“保存”后查看输出信息……</span></pre>
        </blockquote>
    </div>
</div>
</body>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/layer/2.4/layer.js"></script>

<!--请在下方写此页面业务相关的脚本-->
<script src="/layer/assets/layui/layui.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.10.0/jquery.js"></script>
<script type="text/javascript">
    //layui 模块化引用
    layui.use(['jquery', 'table', 'layer'], function () {
        var $ = layui.$, table = layui.table, form = layui.form, layer = layui.layer;
        var flow = layui.flow;
        //数据表格实例化
        var tbWidth = $("#tableRes").width();
        var layTableId = "layTable";
        var tableIns = table.render({
            elem: '#dataTable',
            id: layTableId,
            width: tbWidth,
            url: "/dict-type-list.do",
            method: 'post',
            page: true,
            limits: [50, 100, 200],
            limit: 50,
            loading: true,
            end: '没有更多数据展示啦', //没有数据之后的提示语
            even: false, //不开启隔行背景
            cols: [[
                {title: '序号', type: 'numbers'},
                {field: 'dict_type_code', title: '字典代码', edit: 'text'},
                {field: 'dict_type_name', title: '字典名称', edit: 'text'},
                {
                    field: 'type', title: '操作', templet: function (d) {
                        return '<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="querybyId" lay-id="\'+ d.dict_type_code +\'"><i class="layui-icon layui-icon-table"></i>编辑</a>' +
                            '<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del" lay-id="' + d.dict_type_code + '"><i class="layui-icon layui-icon-delete"></i>移除</a>';

                    }
                }

            ]],
            done: function (res, curr, count) {
                keyboardEvent(event);
            }
        });


        //定义事件集合
        var active = {
            getInfo: function () {
                var fileName = $('#dict_type_name').val();
                if ($('#dict_type_name').val() || $('#md5Name').val() || $('#timeRange').val()) {
                    var index = layer.msg('查询中，请稍候...', {icon: 16, time: false, shade: 0});
                    tableIns.reload({
                        page: {
                            curr: 1
                        },
                        where: {
                            name: fileName
                        }
                    });
                    layer.close(index);
                } else {
                    tableIns.reload({
                        page: {
                            curr: 1
                        },
                        where: {
                            name: ""
                        }
                    });
                    table.reload('')
                }
            },
            addRow: function () {	//添加一行
                var oldData = table.cache[layTableId];
                console.log(oldData);
                var newRow = {dict_type_code: '请填写', dict_type_name: '请填写'};
                oldData.splice(0, 0, newRow);
                table.render({
                    elem: '#dataTable',
                    id: layTableId,
                    width: tbWidth,
                    data: oldData,
                    page: true,
                    limits: [50, 100, 200],
                    limit: 50,
                    loading: true,
                    end: '没有更多数据展示啦', //没有数据之后的提示语
                    even: false, //不开启隔行背景
                    cols: [[
                        {title: '序号', type: 'numbers'},
                        {field: 'dict_type_code', title: '字典代码', edit: 'text'},
                        {field: 'dict_type_name', title: '字典名称', edit: 'text'},
                        {
                            field: 'type', title: '操作', templet: function (d) {
                                return '<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="querybyId" lay-id="\'+ d.dict_type_code +\'"><i class="layui-icon layui-icon-table"></i>编辑</a>' +
                                    '<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del" lay-id="' + d.dict_type_code + '"><i class="layui-icon layui-icon-delete"></i>移除</a>';

                            }
                        }
                    ]],
                    done: function (res, curr, count) {
                        keyboardEvent(event);
                    }
                });
            },


            save: function () {
                var oldData = table.cache[layTableId];
                console.log(oldData);
                for (var i = 0, row; i < oldData.length; i++) {
                    row = oldData[i];
                    if (!row.dict_type_code) {
                        layer.msg("没有新增数据", {icon: 5}); //提示
                        return;
                    }
                }
                document.getElementById("jsonResult").innerHTML = JSON.stringify(table.cache[layTableId], null, 2);	//使用JSON.stringify() 格式化输出JSON字符串
                var reqData = JSON.stringify(table.cache[layTableId], null, 2);
                $.ajax({
                    url: '/dict-type-add.do',
                    type: 'POST',
                    data: {"data": reqData}
                })
                    .done(function (message) {
                        var json = eval("(" + message + ")")
                        if (json.code == "success") {
                            layer.msg(json.msg);
                        }
                        if (json.code == "fail") {
                            layer.msg(json.msg);
                        }
                    })
            }
        }

        //激活事件
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
        //注册按钮事件
        $('.layui-btn[data-type]').on('click', function () {
            var type = $(this).data('type');
            activeByType(type);
        });

        //监听select下拉选中事件
        form.on('select(type)', function (data) {
            var elem = data.elem; //得到select原始DOM对象
            $(elem).prev("a[lay-event='type']").trigger("click");
        });

        //监听工具条
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data, event = obj.event, tr = obj.tr; //获得当前行 tr 的DOM对象;
            console.log(data);
            switch (event) {
                case "type":
                    //console.log(data);
                    var select = tr.find("select[name='type']");
                    if (select) {
                        var selectedVal = select.val();
                        if (!selectedVal) {
                            layer.tips("请选择一个分类", select.next('.layui-form-select'), {tips: [3, '#FF5722']}); //吸附提示
                        }
                        console.log(selectedVal);
                        $.extend(obj.data, {'type': selectedVal});
                        activeByType('updateRow', obj.data);	//更新行记录对象
                    }
                    break;
                case "querybyId":
                    //弹出即全屏
                    var index = layer.open({
                        type: 2,
                        title: data.dict_type_code + " -- 选择字典类型",
                        content: '/to-dict-list.do?dict_type_code=' + data.dict_type_code,
                        maxmin: true,
                        success: function (dom) {

                        }
                    });
                    layer.full(index);
                    break;

                case "del":
                    layer.confirm('真的删除行么？', function (index) {
                        obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                        layer.close(index);
                        // activeByType('removeEmptyTableCache');
                        $.ajax({
                            url: '/dict-type-del.do',
                            type: 'POST',
                            data: {"dict_type_code": data.dict_type_code}
                        })
                            .done(function (message) {
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