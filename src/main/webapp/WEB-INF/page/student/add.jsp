<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/layui-src/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css" media="all">
    <script type="text/javascript">
    </script>
<body>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main width_60">
        <form class="layui-form">
            <div class="layui-form-item">
                <label class="layui-form-label">所属专业</label>
                <div class="layui-input-block">
                    <select name="subjectId" lay-filter="subjectId" lay-verify="required">
                        <option value="">请选择</option>
                        <c:forEach items="${subjects}" var="subject">
                            <option value="${subject.id}">${subject.subjectName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">班级</label>
                <div class="layui-input-block">
                    <select name="clazzId" id="clazzId" lay-filter="clazzId" lay-verify="required">
                        <option value="">请选择</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">入学时间</label>
                <div class="layui-input-block">
                    <input type="text" name="joinDate" autocomplete="off" lay-verify="date" placeholder="yyyy-MM-dd"
                           id="joinDate" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">学号</label>
                <div class="layui-input-block">
                    <input type="text" name="stuNo" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">姓名</label>
                <div class="layui-input-block">
                    <input type="text" name="stuName" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-block">
                    <input type="text" name="stuPwd" class="layui-input" value="123456">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">身份证号</label>
                <div class="layui-input-block">
                    <input type="text" name="cardNo" value="123123123123123123" lay-verify="identity"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">性别</label>
                <div class="layui-input-block">
                    <select name="gender" lay-verify="required">
                        <option value="男">男</option>
                        <option value="女">女</option>
                        <option value="不详">不详</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">手机号码</label>
                <div class="layui-input-block">
                    <input type="text" name="phone" value="18533333333" lay-verify="phone" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">父母姓名</label>
                <div class="layui-input-block">
                    <input type="text" name="pname" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">联系电话</label>
                <div class="layui-input-block">
                    <input type="text" name="telephone" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">家庭地址</label>
                <div class="layui-input-block">
                    <input type="text" name="addr" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary layui-btn-sm data-add-btn">
                        <i class="fa fa-refresh"></i>
                        重置
                    </button>
                    <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-submit lay-filter="save">
                        <i class="fa fa-save"></i>
                        保存
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/lib/layui-src/layui.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/static/js/lay-config.js?v=2.0.0" charset="utf-8"></script>
<script>
    layui.use(['form', 'jquery', 'laydate'], function () {
        var form = layui.form,
            $ = layui.jquery,
            laydate = layui.laydate;

        //日期渲染
        laydate.render({
            elem: "#joinDate"
        });

        //获取窗口索引
        var index = parent.layer.getFrameIndex(window.name);

        //监听提交
        form.on('submit(save)', function (data) {
            console.log(data);
            $.ajax({
                url: "${pageContext.request.contextPath}/student/create",
                type: "POST",
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data.field),
                success: function (data) {
                    layer.msg(data.msg, {time: 500}, function () {
                        parent.layer.close(index);
                    });
                }
            });
            return false;
        });

        //专业和班级联动。当选择专业时，专业对应的所有班级显示到班级下拉框
        form.on('select(subjectId)', function (data) {
            //先清空所有option，因为每一次append都会累加
            $('#clazzId').empty();
            //data.value是用户选中的专业的ID
            if (data.value != '') {
                //发送ajax请求，查询专业对应的班级信息
                $.ajax({
                    url: "${pageContext.request.contextPath}/clazz/query",
                    type: "POST",
                    contentType: 'application/json',
                    dataType: 'json',
                    data: JSON.stringify({"subjectId": data.value}),
                    success: function (data) { //data是响应回来的班级信息
                        //获取到班级下拉框，并添加第一个option
                        $('#clazzId').append('<option value="">请选择</option>');
                        data.data.forEach(function (value, index, array) {
                            //获取到班级下拉框，并添加option
                            $('#clazzId').append('<option value=' + value.id + '>' + value.clazzName + '</option>');
                        })
                        //表单重新渲染
                        form.render();
                    }
                });
            }

        });
    });
</script>
</body>
</html>
