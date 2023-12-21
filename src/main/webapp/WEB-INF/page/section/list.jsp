<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>专业管理</title>
</head>
<body>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main">
        <div class="layui-row">
            <div class="layui-col-xs3">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
            <div class="layui-col-xs9">
                <script type="text/html" id="toolbar">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-event="add">
                            <i class="fa fa-plus"></i>
                            添加
                        </button>
                        <button class="layui-btn layui-btn-sm layui-btn-normal data-delete-btn" lay-event="update">
                            <i class="fa fa-pencil"></i>
                            修改
                        </button>
                        <button class="layui-btn layui-btn-sm layui-btn-danger data-delete-btn" lay-event="delete">
                            <i class="fa fa-remove"></i>
                            删除
                        </button>
                    </div>
                </script>
                <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
            </div>
        </div>

    </div>
</div>
<script>
    layui.use(['form', 'table', 'ztree'], function () {
        var $ = layui.jquery,
            form = layui.form,
            ztree = layui.ztree,
            table = layui.table;

        //异步树
        var setting = {
            async: {
                enable: true,
                type: "post",
                url: "${pageContext.request.contextPath}/section/tree"
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    if (treeNode.parentId != 0) {
                        var json = {};
                        json.clazzId = treeNode.id;
                        table.reload('currentTableId', {
                            contentType: 'application/json',
                            where: json
                        }, 'data');
                    }
                }
            }
        };
        //初始化树
        ztree.init($("#treeDemo"), setting);

        //表格渲染
        table.render({
            elem: '#currentTableId',
            url: '${pageContext.request.contextPath}/section/query',
            contentType: 'application/json',
            method: "post",
            toolbar: '#toolbar',
            defaultToolbar: ['filter', 'exports', 'print'],
            page: true,
            cols: [[
                {type: "checkbox", width: 50},
                {field: 'id', width: 80, title: 'ID'},
                {field: 'year', title: '年份'},
                {field: 'type', title: '类型'},
                {field: 'teacherName', title: '老师', templet: '<div>{{d.teacher.teacherName}}</div>'},
                {field: 'courseName', title: '课程名称', templet: '<div>{{d.course.courseName}}</div>'},
                {field: 'remark', title: '备注'}
            ]],
            skin: 'line'
        });

        //监听搜索操作
        form.on('submit(search-btn)', function (data) {
            //执行搜索重载
            table.reload('currentTableId', {
                contentType: 'application/json',
                where: data.field
            }, 'data');
            return false;
        });

        //toolbar事件监听
        table.on('toolbar(currentTableFilter)', function (obj) {
            if (obj.event === 'add') {   //监听添加操作
                var treeObj = ztree.getZTreeObj("treeDemo");
                var selectedNode = treeObj.getSelectedNodes();
                console.log(selectedNode);
                if (selectedNode.length == 0) {
                    layer.msg("请选择左侧班级", {time: 1000});
                    return;
                }

                var index = layer.open({
                    title: '添加用户',
                    type: 2,
                    shade: 0.2,
                    shadeClose: false,
                    area: ['50%', '50%'],
                    content: 'section/add?clazzId=' + selectedNode[0].id,
                    end: function () {
                        table.reload('currentTableId');
                    }
                });
            } else if (obj.event === 'update') {  //监听修改操作
                var checkStatus = table.checkStatus('currentTableId');
                var data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg("请选择一行数据修改", {time: 1000});
                    return;
                }
                var index = layer.open({
                    title: '修改用户',
                    type: 2,
                    shade: 0.2,
                    shadeClose: false,
                    area: ['50%', '50%'],
                    content: 'section/detail/' + data[0].id,
                    end: function () {
                        table.reload('currentTableId');
                    }
                });
            } else if (obj.event === 'delete') { //监听删除操作
                var checkStatus = table.checkStatus('currentTableId');
                var data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg("请选择行数据删除", {time: 1000});
                    return;
                }
                var arr = [];
                for (index in data) {
                    arr.push(data[index].id);
                }
                layer.confirm('真的删除行吗', function (index) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/section/delete",
                        type: "POST",
                        dataType: 'json',
                        data: "ids=" + arr.join(","),
                        success: function (data) {
                            layer.msg(data.msg, {time: 500}, function () {
                                table.reload("currentTableId");
                            });
                        }
                    });
                });
            }
        });
    });
</script>
</body>
</html>
