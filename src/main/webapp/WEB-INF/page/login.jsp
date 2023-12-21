<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生学籍管理系统-登陆</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/layui-src/css/layui.css" media="all">
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/static/js/html5.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/respond.min.js"></script>
    <![endif]-->
    <style>
        html, body {
            width: 100%;
            height: 100%;
            overflow: hidden
        }

        body {
            background: url("${pageContext.request.contextPath}/static/images/bg.jpg");
        }

        body:after {
            content: '';
            background-repeat: no-repeat;
            background-size: cover;
            -webkit-filter: blur(3px);
            -moz-filter: blur(3px);
            -o-filter: blur(3px);
            -ms-filter: blur(3px);
            filter: blur(3px);
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            z-index: -1;
        }

        .layui-container {
            width: 100%;
            height: 100%;
            overflow: hidden
        }

        .admin-login-background {
            width: 450px;
            height: 360px;
            position: absolute;
            left: 50%;
            top: 30%;
            margin-left: -180px;
            margin-top: -100px;
        }

        .logo-title {
            text-align: center;
            letter-spacing: 2px;
            padding: 14px 0;
        }

        .logo-title h1 {
            color: #1E9FFF;
            font-size: 25px;
            font-weight: bold;
        }

        .login-form {
            background-color: #fff;
            border: 1px solid #fff;
            border-radius: 3px;
            padding: 14px 20px;
            box-shadow: 0 0 8px #eeeeee;
        }

        .login-form .layui-form-item {
            position: relative;
        }

        .login-form .layui-form-item label {
            position: absolute;
            left: 1px;
            top: 1px;
            width: 38px;
            line-height: 36px;
            text-align: center;
            color: #d2d2d2;
        }

        .login-form .layui-form-item input {
            padding-left: 36px;
        }

        .captcha-img img {
            height: 34px;
            border: 1px solid #e6e6e6;
            height: 36px;
            width: 100%;
        }
    </style>
</head>
<body>
<div class="layui-container">
    <div class="admin-login-background">
        <div class="layui-form login-form">
            <form class="layui-form">
                <div class="layui-form-item logo-title">
                    <h1>学生学籍管理系统</h1>
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-username"></label>
                    <input type="text" name="userName" lay-verify="required" placeholder="用户名" autocomplete="off"
                           class="layui-input" value="admin">
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-password"></label>
                    <input type="password" name="password" lay-verify="required" placeholder="密码" autocomplete="off"
                           class="layui-input" value="123456">
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-username"></label>
                    <select name="type" lay-verify="required">
                        <option value="">请选择用户类型</option>
                        <option value="1">管理员</option>
                        <option value="2">老师</option>
                        <option value="3">学生</option>
                    </select>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-inline">
                        <input type="text" name="captcha" style="width: 200px;" lay-verify="required" placeholder="验证码"
                               autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-input-inline">
                        <img type="image" src="${pageContext.request.contextPath}/captcha/code" id="captchaImg"
                             style="cursor:pointer;margin-left: 10px;"/>
                    </div>

                </div>
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn layui-btn-normal layui-btn-fluid" lay-submit=""
                            lay-filter="login">登录
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/lib/jquery-3.4.1/jquery-3.4.1.min.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/static/lib/layui-src/layui.js?<%=System.currentTimeMillis()%>" charset="utf-8"></script>
<script>
    layui.use(['form', 'jquery'], function () {
        var form = layui.form,
            $ = layui.jquery,
            layer = layui.layer;

        //点击切换验证码
        $('#captchaImg').click(function () {
            //修改图片的src属性
            $(this).attr("src", "captcha/code?" + new Date().getTime());
        });

        //进行登录操作
        form.on('submit(login)', function (data) {
            $.ajax({
                url: "${pageContext.request.contextPath}/login",
                type: "POST",
                dataType: "json",
                data: data.field,
                success: function (data) {
                    if (data.code == "1000") {
                        location.href = "index";
                    } else {
                        layer.msg(data.msg, {time: 1000});
                    }
                }
            });
            return false; //这里已经结束了Ajax的请求，"return false"是阻止了ajax之后的相关操作（如表单的提交）往下执行。
        });
    });
</script>
</body>
</html>