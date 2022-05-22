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
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,role-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<link rel="Bookmark" href="/H-ui-admin//favicon.ico" >
	<link rel="Shortcut Icon" href="/H-ui-admin//favicon.ico" />
	<!--[if lt IE 9]>
	<script type="text/javascript" src="/H-ui-admin/lib/html5shiv.js"></script>
	<script type="text/javascript" src="/H-ui-admin/lib/respond.min.js"></script>
	<![endif]-->
	<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui/css/H-ui.min.css" />
	<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/css/H-ui.admin.css" />
	<link rel="stylesheet" type="text/css" href="/H-ui-admin/lib/Hui-iconfont/1.0.8/iconfont.css" />
	<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/skin/default/skin.css" id="skin" />
	<link rel="stylesheet" type="text/css" href="/H-ui-admin/static/h-ui.admin/css/style.css" />
	<link rel="stylesheet" href="/layer/assets/layui/css/layui.css">
	<link rel="stylesheet" href="/layer/assets/common.css"/>

	<!--[if IE 6]>
	<script type="text/javascript" src="/H-ui-admin/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
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
					<button type="button" class="layui-btn layui-btn-sm" data-type="addRow" title="添加一行">
						<i class="layui-icon layui-icon-add-1"></i> 添加一行
					</button>
				</div>
			</div>
			<div id="tableRes" class="table-overlay">
				<table id="dataTable" lay-filter="dataTable" class="layui-hide"></table>
			</div>
			<div id="action" class="text-center">
				<button type="button" name="btnSave" class="layui-btn" data-type="save"><i class="layui-icon layui-icon-ok-circle"></i>保存</button>
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
</body>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/lib/layer/2.4/layer.js"></script>


<!--请在下方写此页面业务相关的脚本-->
<script src="/layer/assets/layui/layui.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.10.0/jquery.js"></script>
<script type="text/javascript">
    //layui 模块化引用
    var countNum=0;
	var url = window.location.href;
	var count = url.indexOf("=");
	var index = url.substring(count+1);
    var param = decodeURI(index);
    layui.use(['jquery', 'table', 'layer'], function(){
        var $ = layui.$, table = layui.table, form = layui.form, layer = layui.layer;
        //数据表格实例化
        var tbWidth = $("#tableRes").width();
        var layTableId = "layTable";
        var tableIns = table.render({
            elem: '#dataTable',
            id: layTableId,
      		url:'/dict-list.do',
            width: tbWidth,
            page: true,
            loading: true,
            even: false, //不开启隔行背景
			method:'post',
            where:{
                dict_type_code : param
            },
            cols: [[
                {title: '序号', type: 'numbers'},
                {field: 'dict_type_code', title: '字典类型编码'},
                {field: 'dict_code', title: '字典编码',edit: 'text'},
                {field: 'dict_name', title: '字典名称', edit: 'text'},
                {field: 'status', title: '是否启用', event: 'status', templet: function(d){
                        var html = ['<input type="checkbox" name="status" lay-skin="switch" lay-text="是|否"'];
                        html.push(d.status > 0 ? ' checked' : '');
                        html.push('>');
                        return html.join('');
                    }},
                {field: 'type', title: '操作', templet: function(d){
                        return'<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del" lay-id="'+ d.dict_id +'"><i class="layui-icon layui-icon-delete"></i>移除</a>';
                }}
            ]],

            done: function(res, curr, count){
                keyboardEvent(event);
            }
        });

        //定义事件集合
        var active = {
            addRow: function(){
               //添加一行
                var oldData = table.cache[layTableId];
                //限制添加行数
                for(var i=0;i<oldData.length;i++){
                    if(oldData.length == 0){
                        break;
					}
					//当没有主键id时 视为新增数据
                    if(oldData[i].dict_id == undefined){
                        countNum = countNum+1;
                    }
                    //超出设定数量之后，只有保存成功之后才能进行再次添加
                    if(countNum > 2){
                        layer.msg('超出添加数量，请核对已编辑数据，点击保存后，再进行后续操作...', {icon: 0, time: 2000, shade: 0})
                        return false
                    }
                }
                console.log(oldData);
                var newRow = {dict_type_code: param,  dict_name: '请填写',dict_code:'请填写' ,status: 0};
                oldData.splice(0, 0, newRow);
                table.render({
                    elem: '#dataTable',
                    id: layTableId,
                    data:oldData,
                    width: tbWidth,
                    page: true,
                    loading: true,
                    even: false, //不开启隔行背景
                    cols: [[
                        {title: '序号', type: 'numbers'},
                        {field: 'dict_type_code', title: '字典类型编码'},
                        {field: 'dict_code', title: '字典编码',edit:"text"},
                        {field: 'dict_name', title: '字典名称', edit: 'text'},
                        {field: 'status', title: '是否启用', event: 'status', templet: function(d){
                                var html = ['<input type="checkbox" name="status" lay-skin="switch" lay-text="是|否"'];
                                html.push(d.status > 0 ? ' checked' : '');
                                html.push('>');
                                return html.join('');
                            }},
                        {field: 'type', title: '操作', templet: function(d){
                                return'<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del" lay-id="'+ d.dict_id +'"><i class="layui-icon layui-icon-delete"></i>移除</a>';
                            }}
                    ]],

                    done: function(res, curr, count){
                        keyboardEvent(event);
                    }
                });
            },
            updateRow: function(obj){
                var oldData = table.cache[layTableId];
                console.log(oldData);
                for(var i=0, row; i < oldData.length; i++){
                    row = oldData[i];
                    if(row.dict_id == obj.dict_id){
                        $.extend(oldData[i], obj);
                        return;
                    }
                }
                tableIns.reload({
                    data : oldData
                });
            },
            save: function(){
                var oldData = table.cache[layTableId];
                countNum = 0;
                console.log(oldData);
                for(var i=0, row; i < oldData.length; i++){
                    row = oldData[i];
                }
                document.getElementById("jsonResult").innerHTML = JSON.stringify(table.cache[layTableId], null, 2);	//使用JSON.stringify() 格式化输出JSON字符串
                var reqData = JSON.stringify(table.cache[layTableId], null, 2);
				$.ajax({
                    url: '/dict-add.do',
                    type: 'POST',
					data:{"data":reqData}
                })
                    .done(function (message) {
                        index = 0;
                        var json = eval("("+message+")")
                        if(json.code=="success"){
                            layer.msg(json.msg);
                            //刷新数据
                            tableIns.reload({});
                            layer.close(index);                       }
                        if(json.code =="fail"){
                            layer.msg(json.msg);
                        }
                    })
            }
        }

        //激活事件
        var activeByType = function (type, arg) {
            if(arguments.length === 2){
                active[type] ? active[type].call(this, arg) : '';
            }else{
                active[type] ? active[type].call(this) : '';
            }
        }

        //注册按钮事件
        $('.layui-btn[data-type]').on('click', function () {
            var type = $(this).data('type');
            activeByType(type);
        });

        //监听select下拉选中事件
        form.on('select(type)', function(data){
            var elem = data.elem; //得到select原始DOM对象
            $(elem).prev("a[lay-event='type']").trigger("click");
        });


        //监听工具条
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data, event = obj.event, tr = obj.tr; //获得当前行 tr 的DOM对象;
            console.log(data);
            switch(event){
                case "type":
                    //console.log(data);
                    var select = tr.find("select[name='type']");
                    if(select){
                        var selectedVal = select.val();
                        if(!selectedVal){
                            layer.tips("请选择一个分类", select.next('.layui-form-select'), { tips: [3, '#FF5722'] }); //吸附提示
                        }
                        console.log(selectedVal);
                        $.extend(obj.data, {'type': selectedVal});
                        activeByType('updateRow', obj.data);	//更新行记录对象
                    }
                    break;

                case "status":
                    var stateVal = tr.find("input[name='status']").prop('checked') ? 1 : 0;
                    $.extend(obj.data, {'status': stateVal})
                    activeByType('updateRow', obj.data);	//更新行记录对象
                    break;
                case "del":
                    layer.confirm('真的删除行么？', function(index){
                        obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                        layer.close(index);
                        $.ajax({
                            url: '/dict-del.do',
                            type: 'POST',
                            data:{"dict_id":data.dict_id}
                        })
                            .done(function (message) {
                                var json = eval("("+message+")")
                                if(json.code=="success"){
                                    layer.msg(json.msg);
                                }
                                if(json.code =="fail"){
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