<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <style>
        .layui-card-body ul {
            list-style: none;
        }

        .layui-card-body ul li {
            float: left;
            padding: 5px;
        }

        .layui-card-body ul li:hover {
            background-color: #f0f0f0;
        }

        .layui-card-body ul li table {
            width: 150px;
        }

        .layui-card-body ul li table i {
            font-size: 36px;
            color: #1aa094
        }

        .layui-card-body ul li table .number {
            font-size: 24px;
            color: red
        }

        .layui-card-body ul li table .txt {
            color: #888888;
        }
    </style>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15" style="margin-top: 10px;">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">修改密码</div>
                <div class="layui-card-body">
                    <div style="height: 520px">
                        <form class="layui-form">
                            <input type="hidden" name="type" value="${type}">
                            <input type="hidden" name="id" value="${user.id}">
                            <div class="layui-form-item">
                                <label class="layui-form-label">原密码</label>
                                <div class="layui-input-block">
                                    <input type="password" class="layui-input" lay-verify="required" name="sourcePwd">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">新密码</label>
                                <div class="layui-input-block">
                                    <input type="password" class="layui-input" lay-verify="required" name="newPwd">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">确认密码</label>
                                <div class="layui-input-block">
                                    <input type="password" class="layui-input" lay-verify="required|confirmPwd"
                                           name="newPwd2">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-submit
                                            lay-filter="save">
                                        <i class="fa fa-save"></i>
                                        保存
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    layui.use(['form'], function () {
        var $ = layui.jquery,
            form = layui.form;
        //自定义验证规则
        form.verify({
            confirmPwd: function (value) {
                //判断旧密码与新密码是否一致
                if ($('input[name=newPwd]').val() !== value)
                    return '两次密码输入不一致！';
            }
        });
        //修改密码
        form.on('submit(save)', function (data) {
            $.ajax({
                url: "${pageContext.request.contextPath}/pwd",
                type: "POST",
                dataType: 'json',
                data: {
                    sourcePwd: data.field.sourcePwd,
                    newPwd: data.field.newPwd,
                    type: data.field.type,
                    id: data.field.id,
                },
                success: function (data) {
                    layer.msg(data.msg, {time: 1000});
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
