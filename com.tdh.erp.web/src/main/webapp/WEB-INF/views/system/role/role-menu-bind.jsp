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
			<legend>操作栏</legend>
			<div style="margin: 10px 10px 10px 10px">
				<form class="layui-form layui-form-pane" action="">
					<div class="layui-form-item">
						<div class="layui-inline">
							<button class="layui-btn layui-btn-primary" id="btn-refresh">刷新表格</button>
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
</div>
</body>

<script type="text/javascript" src="/H-ui-admin/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="/H-ui-admin/static/h-ui.admin/js/H-ui.admin.js"></script>

<script type="text/javascript">
    function child(data,menu_info_enum, menu_icon_enum){
        var config =  layui.config({
            base: '/layer/module/'
        }).extend({
            treetable: 'treetable-lay/treetable'
        });
        window.viewObj ={
            //下拉参数构建
            renderSelectOptions: function(data, settings){
                return  renderSelectHandle(data,settings);
            },

            renderDiv:function(data,settings){
                return renderDivHandle(data,settings);
            }
        };
        function winClose(){
            layer.closeAll();
            $("#table1").submit();
        }
        config.use(['layer', 'table', 'treetable','jquery'], function () {
            var $ = layui.$;
            var form = layui.form;
            var table = layui.table;
            var layer = layui.layer;
            var treetable = layui.treetable;
            var layTableId = "layTable";
            // 渲染表格
            var renderTable = function () {
                layer.load(2);
                treetable.render({
                    id :layTableId,
                    treeColIndex: 2,
                    treeSpid: -1,
                    treeIdName: 'id_no',
                    treePidName: 'pid_no',
                    treeDefaultClose: false,
                    treeLinkage: true,
                    elem: '#dataTable',
                    url: '/query-bind.do',
                    page: false,
					where:{
						data:data.role_id
					},
                    cols: [[
                        {type:'checkbox'},
                        {field: 'title_name', title: '菜单名称',templet:function(d){
                                var options = viewObj.renderDiv(menu_info_enum, {valueField: "id", textField: "name", selectedValue: d.title_name});
                                return options;
                            }},
                        {field: 'level_flag', title: '菜单级别'},

                    ]],
                    done: function () {
                        layer.closeAll();
                    }
                });
            };
            renderTable();

            $('#btn-refresh').click(function () {
                renderTable();
            });

            $('#bind-menu').click(function () {
                var oldData = table.cache[layTableId];
                var ids;
                for(var i=0;i<oldData.length;i++) {
                    if(oldData[i].LAY_CHECKED ==true){
                       if(ids == undefined){
                           ids = oldData[i].id;
					   }else{
                           ids = ids +","+oldData[i].id;
					   }
					}
                }
                $.ajax({
                    url: '/to-do-bind.do',
                    type: 'POST',
                    data: {"ids":ids,"data":data.role_id}
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
            table.on('tool(dataTable)', function (obj) {
                var data = obj.data;
                if (obj.event === 'edit') {
                    var index = layer.open({
                        title: '菜单编辑',
                        type: 2,
                        shade: 0.2,
                        maxmin:true,
                        shadeClose: true,
                        area: ['100%', '100%'],
                        content: '/menu-add.do',
                        success:function(layer,index){
                            var iframe = window['layui-layer-iframe'+index];
                            iframe.child(data.id,menu_info_enum,menu_icon_enum)
                        }
                    });
                    $(window).on("resize", function () {
                        layer.full(index);
                    });
                    return false;
                } else if (obj.event === 'del') {
                    layer.confirm('真的删除行么', function (index) {
                        obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                        layer.close(index);
                        $.ajax({
                            url: '/menu-delete.do',
                            type: 'POST',
                            async: false,
                            data: {"data":data.id}
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
                }
            });
        });
    }
</script>
</body>
</html>
