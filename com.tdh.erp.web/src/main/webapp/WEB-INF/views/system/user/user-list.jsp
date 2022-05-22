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

</head>
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
                                <input value="" type="text"  placeholder="用户编码/用户名称" id="user_name"  name="user_name" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <button type="button" class="layui-btn layui-btn-primary"
                                    data-type="getInfo"  id="btnQuery" name="btnQuery" ><i class="layui-icon"></i> 搜 索
                            </button>
                            <button type="button" class="layui-btn layui-btn-primary" data-type="addRow" title="添加一行">
                                <i class="layui-icon layui-icon-add-1"></i> 添加一行
                            </button>
                            <button type="button" name="btnSave" class="layui-btn layui-btn-normal" data-type="save"><i
                                    class="layui-icon layui-icon-ok-circle"></i>保存
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
</div>

<!--保存结果输出-->
<div class="layui-card" style = "display: none" >
    <div class="layui-card-header">保存结果输出</div>
    <div class="layui-card-body layui-text">
        <blockquote class="layui-elem-quote layui-quote-nm">
            <pre id="jsonResult"><span class="layui-word-aux">请点击“保存”后查看输出信息……</span></pre>
        </blockquote>
    </div>
</div>
</body>

<script type="text/javascript">
    //枚举对象
    var PWD = document.getElementById("LAY-user-login-password");
    var compound_enum;
    var role_enum;
    //layui 模块化引用
    layui.use(['jquery', 'table', 'layer'], function () {
        var $ = layui.$, table = layui.table, form = layui.form, layer = layui.layer;
        var flow = layui.flow;
        //数据表格实例化
        var tbWidth = $("#tableRes").width();

        var layTableId = "layTable";
        var tableIns =   table.render({
            id: layTableId,
            elem: '#dataTable',
            url: '/user-list.do',
            toolbar: '#toolbarDemo',
            method: 'post',
            page: true,
            limits: [50, 100, 200],
            limit: 50,
            loading: true,
            end: '没有更多数据展示啦', //没有数据之后的提示语
            cols: [[
                {title: '序号', type: 'numbers'},

                {title:'role_id',title:'角色名称',templet: function(d){
                        var options = viewObj.renderSelectOptions(role_enum, {valueField: "id", textField: "name", selectedValue: d.role_id});
                        return '<a lay-event="role_id"></a><select name="role_id" lay-filter="role_id"><option value="">请选择分类</option>' + options + '</select>';
                    }},
                {field: 'user_name', title: '用户名称', edit: 'text'},
                {field: 'user_code', title: '用户编码', edit: 'text'},
                {field: 'sex', title: '性别', templet: function(d){
                        var options = viewObj.renderSelectOptions(sex_enum, {valueField: "id", textField: "name", selectedValue: d.sex});
                        return '<a lay-event="sex"></a><select name="sex" lay-filter="sex"><option value="">请选择分类</option>' + options + '</select>';
                    }},
                {field :'input_password',title:'密码',edit:'text'},
                {field: 'job_number', title: '用户工号', edit: 'text'},
                {field: 'status', title: '是否启用', event: 'status', templet: function(d){
                        var html = ['<input type="checkbox" name="status" lay-skin="switch" lay-text="是|否"'];
                        html.push(d.status > 0 ? ' checked' : '');
                        html.push('>');
                        return html.join('');
                    }},
                {
                    field: 'type', title: '操作', templet: function (d) {
                        return'<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del" lay-id="\' + d.user_id + \'"><i class="layui-icon layui-icon-delete"></i>移除</a>';
                    }
                }

            ]],

            done: function (res, curr, count) {
                keyboardEvent(event);
                layer.closeAll();
            }
        });


        //定义事件集合
        var active = {
            getInfo: function () {
                var fileName = $('#user_name').val();
                if ($('#user_name').val()) {
                    var index = layer.msg('处理中，请稍候...', {icon: 16, time: false, shade: 0});
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
                    layer.close(index);
                }
            },
            addRow: function () {	//添加一行
                var oldData = table.cache[layTableId];
                console.log(oldData);
                var text = '请填写';
                var newRow = {user_name: text, user_code:text,sex:null,input_password:text,job_number:text,status:0};
                oldData.splice(0, 0, newRow);
                table.render({
                    id: layTableId,
                    elem: '#dataTable',
                    data:oldData,
                    toolbar: '#toolbarDemo',
                    method: 'post',
                    page: true,
                    limits: [50, 100, 200],
                    limit: 50,
                    loading: true,
                    end: '没有更多数据展示啦', //没有数据之后的提示语
                    even: false, //不开启隔行背景
                    skin: 'line',
                    cols: [[
                        {title: '序号', type: 'numbers'},
                        {title:'role_id',title:'角色名称',templet: function(d){
                                var options = viewObj.renderSelectOptions(role_enum, {valueField: "id", textField: "name", selectedValue: d.role_id});
                                return '<a lay-event="role_id"></a><select name="role_id" lay-filter="role_id"><option value="">请选择分类</option>' + options + '</select>';
                            }},
                        {field: 'user_name', title: '用户名称', edit: 'text'},
                        {field: 'user_code', title: '用户编码', edit: 'text'},
                        {field: 'sex', title: '性别', templet: function(d){
                                var options = viewObj.renderSelectOptions(sex_enum, {valueField: "id", textField: "name", selectedValue: d.sex});
                                return '<a lay-event="sex"></a><select name="sex" lay-filter="sex"><option value="">请选择分类</option>' + options + '</select>';
                            }},
                        {field :'input_password',title:'密码',edit:'text'},
                        {field: 'job_number', title: '用户工号', edit: 'text'},
                        {field: 'status', title: '是否启用', event: 'status', templet: function(d){
                                var html = ['<input type="checkbox" name="status" lay-skin="switch" lay-text="是|否"'];
                                html.push(d.status > 0 ? ' checked' : '');
                                html.push('>');
                                return html.join('');
                            }},
                        {
                            field: 'type', title: '操作', templet: function (d) {
                                return'<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del" lay-id="\' + d.user_id + \'"><i class="layui-icon layui-icon-delete"></i>移除</a>';
                            }
                        }

                    ]],

                    done: function (res, curr, count) {
                        keyboardEvent(event);
                        layer.closeAll();

                    }
                });
            },
            save: function () {
                var oldData = table.cache[layTableId];
                console.log(oldData);
                for (var i = 0, row; i < oldData.length; i++) {
                    row = oldData[i];
                    // if (!row.dept_id) {
                    //     layer.msg("没有新增数据", {icon: 5}); //提示
                    //     return;
                    // }
                }
                document.getElementById("jsonResult").innerHTML = JSON.stringify(table.cache[layTableId], null, 2);	//使用JSON.stringify() 格式化输出JSON字符串
                var reqData = JSON.stringify(table.cache[layTableId], null, 2);
                $.ajax({
                    url: '/user-add.do',
                    type: 'POST',
                    data: {"data": reqData},
                    //返回接收前
                    beforeSend: function(){
                        loading= layer.msg('处理中，请稍候...', {icon: 16, time: false, shade: 0});
                    }
                })
                //返回接收后
                    .done(function (message) {
                        layer.close(loading);
                        var json = eval("(" + message + ")")
                        if (json.code == "success") {
                            layer.msg(json.msg);
                        }
                        if (json.code == "fail") {
                            layer.msg(json.msg);
                        }
                    })
            },
            updateRow: function(obj){
                var oldData = table.cache[layTableId];
                console.log(oldData);
                for(var i=0, row; i < oldData.length; i++){
                    row = oldData[i];
                    if(row.user_id == obj.user_id){
                        $.extend(oldData[i], obj);
                        return;
                    }
                }
                tableIns.reload({
                    data : oldData
                });
            },
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
        form.on('select(sex)', function (data) {
            var elem = data.elem; //得到select原始DOM对象
            $(elem).prev("a[lay-event='sex']").trigger("click");
        });
        //监听select下拉选中事件
        form.on('select(compound_id)', function (data) {
            var elem = data.elem; //得到select原始DOM对象
            $(elem).prev("a[lay-event='compound_id']").trigger("click");
        });
        form.on('select(role_id)', function (data) {
            var elem = data.elem; //得到select原始DOM对象
            $(elem).prev("a[lay-event='role_id']").trigger("click");
        });
        //监听工具条
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data, event = obj.event, tr = obj.tr; //获得当前行 tr 的DOM对象;
            console.log(data);
            switch (event) {
                case "sex":
                    //console.log(data);
                    var select = tr.find("select[name='sex']");
                    if (select) {
                        var selectedVal = select.val();
                        if (!selectedVal) {
                            layer.tips("请选择一个分类", select.next('.layui-form-select'), {tips: [3, '#FF5722']}); //吸附提示
                        }
                        console.log(selectedVal);
                        $.extend(obj.data, {'sex': selectedVal});
                        activeByType('updateRow', obj.data);	//更新行记录对象
                    }
                    break;
                case "compound_id":
                    //console.log(data);
                    var select = tr.find("select[name='compound_id']");
                    if (select) {
                        var selectedVal = select.val();
                        if (!selectedVal) {
                            layer.tips("请选择一个分类", select.next('.layui-form-select'), {tips: [3, '#FF5722']}); //吸附提示
                        }
                        console.log(selectedVal);
                        $.extend(obj.data, {'compound_id': selectedVal});
                        activeByType('updateRow', obj.data);	//更新行记录对象
                    }
                    break;
                case "role_id":
                    //console.log(data);
                    var select = tr.find("select[name='role_id']");
                    if (select) {
                        var selectedVal = select.val();
                        if (!selectedVal) {
                            layer.tips("请选择一个分类", select.next('.layui-form-select'), {tips: [3, '#FF5722']}); //吸附提示
                        }
                        console.log(selectedVal);
                        $.extend(obj.data, {'role_id': selectedVal});
                        activeByType('updateRow', obj.data);	//更新行记录对象
                    }
                    break;
                case "status":
                    var stateVal = tr.find("input[name='status']").prop('checked') ? 1 : 0;
                    $.extend(obj.data, {'status': stateVal})
                    activeByType('updateRow', obj.data);	//更新行记录对象
                    break;

                case "user_pwd":
                    var stateVal = tr.find("input[name='user_pwd']");
                    $.extend(obj.data, {'user_pwd': stateVal})
                    activeByType('updateRow', obj.data);	//更新行记录对象
                    break;

                case "del":
                    layer.confirm('真的删除行么？', function (index) {
                        obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                        layer.close(index);
                        // activeByType('removeEmptyTableCache');
                        $.ajax({
                            url: '/user-del.do',
                            type: 'POST',
                            data: {"user_id": data.user_id}
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

    //loading 遮罩对象
    var loading ;
    var sex_enum ;
    window.viewObj ={
        //下拉参数构建
        renderSelectOptions: function(data, settings){
            return  renderSelectHandle(data,settings);
        }
    };
    //字典内容初始化
    $(document).ready(function () {
        $.ajax({
            url: '/query-dict-detail.do',
            type: 'POST',
            async: false,
            data: {"data":'SEX'}
        })
            .done(function (message) {
                var json = eval("(" + message + ")")
                if (json.code == "success") {
                    for(var i =0 ;i<json.data.length;i++){
                        var obj = json.data[i];
                        for(var key in obj){
                            if('SEX'== key){
                                sex_enum = json.data[i].SEX;
                            }
                        }
                    }
                }
                if (json.code == "fail") {
                    layer.msg(json.msg);
                }
            })



        $.ajax({
            url: '/query_role_enum.do',
            type: 'POST',
            async:false,
            data: {"data":""}
        })
            .done(function (message) {
                var json = eval("(" + message + ")")
                if (json.code == "success") {
                    role_enum = json.data;

                }
                if (json.code == "fail") {
                    layer.msg(json.msg);
                }
            })

    });
</script>
</body>
</html>