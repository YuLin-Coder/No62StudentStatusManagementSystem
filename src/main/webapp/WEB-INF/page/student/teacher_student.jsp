<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main">
        <div style="margin: 10px">
            <form class="layui-form layui-form-pane">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">专业</label>
                        <div class="layui-input-inline">
                            <select name="subjectId" lay-filter="subjectId">
                                <option value="">请选择</option>
                                <c:forEach items="${subjects}" var="subject">
                                    <option value="${subject.id}">${subject.subjectName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">班级</label>
                        <div class="layui-input-inline">
                            <select name="clazzId" lay-filter="subjectId">
                                <option value="">请选择</option>
                                <c:forEach items="${clazzes}" var="clazz">
                                    <option value="${clazz.id}">${clazz.clazzName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <button class="layui-btn layui-btn-primary" lay-submit lay-filter="search-btn"><i
                                class="layui-icon"></i> 搜 索
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
    </div>
</div>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        //表单渲染
        form.render();
        //表格渲染
        table.render({
            elem: '#currentTableId',
            url: '${pageContext.request.contextPath}/student/teacher_student',
            method: "post",
            where: {
                teacherId:${teacher.id}
            },
            toolbar: '#toolbar',
            defaultToolbar: ['filter', 'exports', 'print'],
            page: true,
            cols: [[
                {type: "checkbox", width: 50},
                {field: 'id', width: 80, title: 'ID'},
                {field: 'stuNo', title: '学号'},
                {field: 'stuName', title: '姓名'},
                {field: 'cardNo', title: '身份证号'},
                {field: 'gender', title: '性别'},
                {field: 'phone', title: '手机号码'},
                {field: 'joinDate', title: '入学时间'},
                {field: 'subjectName', title: '专业', templet: '<div>{{d.subject.subjectName}}</div>'},
                {field: 'clazzName', title: '班级', templet: '<div>{{d.clazz.clazzName}}</div>'},
                {field: 'status', title: '状态'}
            ]],
            skin: 'line'
        });

        //监听搜索操作
        form.on('submit(search-btn)', function (data) {
            //执行搜索重载
            table.reload('currentTableId', {
                where: {
                    clazzId: data.field.clazzId,
                    subjectId: data.field.subjectId
                }
            }, 'data');
            return false;
        });
    });
</script>
</body>
</html>
