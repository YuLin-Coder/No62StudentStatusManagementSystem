<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生选课</title>
</head>
<body>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main">
        <div class="layui-row">
            <script type="text/html" id="toolbar">
                <div class="layui-btn-container">
                    <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-event="save">
                        <i class="fa fa-save"></i>
                        保存
                    </button>
                </div>
            </script>
            <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
        </div>

    </div>
</div>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        //表格渲染
        table.render({
            elem: '#currentTableId',
            url: '${pageContext.request.contextPath}/section/query_student_section',
            contentType: 'application/json',
            method: "post",
            toolbar: '#toolbar',
            defaultToolbar: ['filter', 'exports', 'print'],
            page: false,
            cols: [[
                {type: "checkbox", width: 50},
                {field: 'id', width: 80, title: 'ID'},
                {field: 'year', title: '年份'},
                {field: 'type', title: '类型'},
                {field: 'clazzName', title: '班级', templet: '<div>{{d.clazz.clazzName}}</div>'},
                {field: 'courseName', title: '课程', templet: '<div>{{d.course.courseName}}</div>'},
                {field: 'teacherName', title: '老师', templet: '<div>{{d.teacher.teacherName}}</div>'},
                {field: 'remark', title: '备注'}
            ]],

            //layui自己的东西 回显选中... 主要是让已经选择的课程处于选中状态
            done: function (res, page, count) {
                for (var index in res.data) {
                    if (res.data[index].selected >= 1) {
                        res.data[index]["LAY_CHECKED"] = 'true';
                        var index = res.data[index]['LAY_TABLE_INDEX'];
                        $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                        $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
                    }
                }
            },
            skin: 'line'
        });

        //toolbar事件监听
        table.on('toolbar(currentTableFilter)', function (obj) {
            if (obj.event === 'save') {   //监听添加操作
                var checkStatus = table.checkStatus('currentTableId');
                var data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg("请选择选课信息", {time: 1000});
                    return;
                }
                var sectionIdArr = [];
                var courseIdArr = [];
                //遍历data，将对应信息存储到数组中
                $.each(data, function (i, item) {
                    sectionIdArr.push(item.id);
                    courseIdArr.push(item.course.id);
                })
                //发送ajax请求，完成选课操作
                $.ajax({
                    url: "${pageContext.request.contextPath}/score/create",
                    type: "POST",
                    dataType: 'json',
                    data: {
                        sectionIds: sectionIdArr.join(","), //将数组转成以","分割的字符串
                        courseIds: courseIdArr.join(",")
                    },
                    success: function (data) {
                        layer.msg(data.msg, {time: 500}, function () {
                            table.reload("currentTableId");
                        });
                    }
                });
            }
        });
    });
</script>
</body>
</html>

