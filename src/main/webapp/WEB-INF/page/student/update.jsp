<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <input type="hidden" name="id" value="${student.id}">
            <div class="layui-form-item">
                <label class="layui-form-label">所属专业</label>
                <div class="layui-input-block">
                    <select name="subjectId" lay-filter="subjectId" lay-verify="required">
                        <option value="">请选择</option>
                        <c:forEach items="${subjects}" var="subject">
                            <option value="${subject.id}"
                                    <c:if test="${student.subjectId == subject.id}">selected</c:if>>${subject.subjectName}</option>
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
                    <input type="text" name="joinDate" autocomplete="off"
                           value='<fmt:formatDate value="${student.joinDate}" pattern="yyyy-MM-dd"/>' lay-verify="date"
                           placeholder="yyyy-MM-dd" id="joinDate" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">学号</label>
                <div class="layui-input-block">
                    <input type="text" name="stuNo" class="layui-input" value="${student.stuNo}">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">姓名</label>
                <div class="layui-input-block">
                    <input type="text" name="stuName" value="${student.stuName}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">身份证号</label>
                <div class="layui-input-block">
                    <input type="text" name="cardNo" value="${student.cardNo}" lay-verify="identity"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">性别</label>
                <div class="layui-input-block">
                    <select name="gender" lay-verify="required">
                        <option value="男" <c:if test="${student.gender == '男'}">selected</c:if>>男</option>
                        <option value="女" <c:if test="${student.gender == '女'}">selected</c:if>>女</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">手机号码</label>
                <div class="layui-input-block">
                    <input type="text" name="phone" value="${student.phone}" lay-verify="phone" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">父母姓名</label>
                <div class="layui-input-block">
                    <input type="text" name="pname" value="${student.pname}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">联系电话</label>
                <div class="layui-input-block">
                    <input type="text" name="telephone" value="${student.telephone}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">家庭地址</label>
                <div class="layui-input-block">
                    <input type="text" name="addr" value="${student.addr}" class="layui-input">
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
            $.ajax({
                url: "${pageContext.request.contextPath}/student/update",
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

        //初始化班级列表（初始化的时候加载一次）
        initClazzList();

        function initClazzList(data) {
            $('#clazzId').empty();
            var that = data;
            var subjectId;
            if (!data) {
                subjectId = "${student.subjectId}"; //初始化的时候执行的
            } else {
                subjectId = data.value; //手动切换
            }
            //根据专业id查询班级信息
            $.ajax({
                url: "${pageContext.request.contextPath}/clazz/query",
                type: "POST",
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify({"subjectId": subjectId}),
                success: function (data) {
                    $('#clazzId').append('<option value="">请选择</option>');
                    data.data.forEach(function (value, index, array) {
                        var selected;
                        console.log(!that);
                        if (!that) {
                            if (value.id == "${student.clazzId}") {
                                selected = "selected";
                            }
                        }
                        $('#clazzId').append('<option ' + selected + ' value=' + value.id + '>' + value.clazzName + '</option>');
                    })
                    //表格重新加载
                    form.render();
                }
            });
        }

        //手动切换
        form.on('select(subjectId)', function (data) {
            initClazzList(data);
        });
    });
</script>
</body>
</html>
