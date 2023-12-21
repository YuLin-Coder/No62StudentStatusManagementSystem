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
                <div class="layui-card-header">个人资料</div>
                <div class="layui-card-body">
                    <div style="height: 520px">
                        <form class="layui-form">
                            <c:if test="${type == 1}">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">ID</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" value="${user.id}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">账号</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.userName}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">姓名</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.name}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">备注</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.remark}">
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${type == 2}">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">ID</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.id}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">账号</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.teacherName}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">姓名</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.name}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">备注</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.remark}">
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${type == 3}">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">ID</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.id}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">学号</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.stuNo}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">姓名</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.stuName}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">身份证号</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.cardNo}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">性别</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.gender}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">出生日期</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.birthday}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">父母姓名</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.pname}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">电话</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.telephone}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">地址</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly value="${user.addr}">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">入学日期</label>
                                    <div class="layui-input-block">
                                        <input class="layui-input" readonly
                                               value="<fmt:formatDate value="${user.joinDate}" pattern="yyyy-M-dd"/>">
                                    </div>
                                </div>
                            </c:if>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
