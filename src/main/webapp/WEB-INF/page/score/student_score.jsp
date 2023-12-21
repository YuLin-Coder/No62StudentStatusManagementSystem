<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生成绩</title>
</head>
<body>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main">
        <div class="layui-row">
            <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
        </div>

    </div>
</div>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            table = layui.table;

        //表格渲染
        table.render({
            elem: '#currentTableId',
            url: '${pageContext.request.contextPath}/score/query_student_score',
            contentType: 'application/json',
            method: "post",
            toolbar: '#toolbar',
            defaultToolbar: ['filter', 'exports', 'print'],
            page: false,
            cols: [[
                {field: 'stuName', title: '姓名', templet: '<div>{{d.student.stuName}}</div>'},
                {field: 'courseName', title: '课程名称', templet: '<div>{{d.course.courseName}}</div>'},
                {field: 'year', title: '年份', templet: '<div>{{d.section.year}}</div>'},
                {field: 'type', title: '类型', templet: '<div>{{d.section.type}}</div>'},
                {field: 'score', title: '分数'}
            ]],
            skin: 'line'
        });
    });
</script>
</body>
</html>
