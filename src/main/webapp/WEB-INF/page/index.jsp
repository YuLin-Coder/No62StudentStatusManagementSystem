<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>学生学籍管理系统</title>
    <meta name="keywords" content="layuimini,layui,layui模板,layui后台,后台模板,admin,admin模板,layui mini">
    <meta name="description"
          content="layuimini基于layui的轻量级前端后台管理框架，最简洁、易用的后台框架模板，面向所有层次的前后端程序,只需提供一个接口就直接初始化整个框架，无需复杂操作。">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="images/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/layui-src/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/layuimini.css?v=2.0.1" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/themes/default.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/public.css" media="all">
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/static/js/html5.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/respond.min.js"></script>
    <![endif]-->
    <style id="layuimini-bg-color">
    </style>
</head>
<body class="layui-layout-body layuimini-all">
<div class="layui-layout layui-layout-admin">

    <div class="layui-header header">
        <div class="layui-logo layuimini-logo layuimini-back-home"></div>

        <div class="layuimini-header-content">
            <a>
                <div class="layuimini-tool"><i title="展开" class="fa fa-outdent" data-side-fold="1"></i></div>
            </a>

            <!--电脑端头部菜单-->
            <ul class="layui-nav layui-layout-left layuimini-header-menu layuimini-menu-header-pc layuimini-pc-show">
            </ul>

            <!--手机端头部菜单-->
            <ul class="layui-nav layui-layout-left layuimini-header-menu layuimini-mobile-show">
                <li class="layui-nav-item">
                    <a href="javascript:;"><i class="fa fa-list-ul"></i> 选择模块</a>
                    <dl class="layui-nav-child layuimini-menu-header-mobile">
                    </dl>
                </li>
            </ul>

            <ul class="layui-nav layui-layout-right">

                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;" data-refresh="刷新"><i class="fa fa-refresh"></i></a>
                </li>
                <li class="layui-nav-item mobile layui-hide-xs" lay-unselect>
                    <a href="javascript:;" data-check-screen="full"><i class="fa fa-arrows-alt"></i></a>
                </li>
                <li class="layui-nav-item layuimini-setting">
                    <a href="javascript:;">
                        <c:if test="${type ==1}">${user.userName}</c:if>
                        <c:if test="${type ==2}">${user.teacherName}</c:if>
                        <c:if test="${type ==3}">${user.stuName}</c:if>

                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" layuimini-content-href="${pageContext.request.contextPath}/info" data-title="基本资料"
                                   data-icon="fa fa-gears">基本资料</a>
                            </dd>
                            <dd>
                                <a href="javascript:;" layuimini-content-href="${pageContext.request.contextPath}/pwd" data-title="修改密码"
                                   data-icon="fa fa-gears">修改密码</a>
                            </dd>
                            <dd>
                                <hr>
                            </dd>
                            <dd>
                                <a href="javascript:logout;" class="login-out">退出登录</a>
                            </dd>
                        </dl>
                </li>
                <li class="layui-nav-item layuimini-select-bgcolor" lay-unselect>
                    <a href="javascript:;" data-bgcolor="配色方案"><i class="fa fa-ellipsis-v"></i></a>
                </li>
            </ul>
        </div>
    </div>

    <!-- 无限极左侧菜单 -->
    <div class="layui-side layui-bg-black layuimini-menu-left">
    </div>

    <!-- 初始化加载层 -->
    <div class="layuimini-loader">
        <div class="layuimini-loader-inner"></div>
    </div>

    <!-- 手机端遮罩层 -->
    <div class="layuimini-make"></div>

    <!-- 移动导航 -->
    <div class="layuimini-site-mobile"><i class="layui-icon"></i></div>

    <div class="layui-body">

        <div class="layui-card layuimini-page-header layui-hide">
            <div class="layui-breadcrumb layuimini-page-title">
                <a lay-href="" href="/">主页</a><span lay-separator="">/</span>
                <a><cite>常规管理</cite></a><span lay-separator="">/</span>
                <a><cite>系统设置</cite></a>
            </div>
        </div>
        <div class="layuimini-content-page">
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/lib/layui-src/layui.js?<%=System.currentTimeMillis()%>" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/static/js/lay-config.js?v=2.0.0" charset="utf-8"></script>
<script>
    layui.use(['jquery', 'layer', 'miniAdmin'], function () {
        var $ = layui.jquery,
            layer = layui.layer,
            miniAdmin = layui.miniAdmin;

        var options = {
            iniUrl: "${pageContext.request.contextPath}/static/api/init.json",    //初始化接口
            clearUrl: "api/clear.json", //缓存清理接口
            renderPageVersion: true,    //初始化页面是否加版本号
            bgColorDefault: 3,      //主题默认配置
            loginType: ${type==null?1:type},  //默认登录权限
            multiModule: false,          //是否开启多模块
            menuChildOpen: true,       //是否默认展开菜单
            loadingTime: 0,             //初始化加载时间
            pageAnim: false,             //切换菜单动画
        };
        miniAdmin.render(options);

        //退出登录
        $('.login-out').on("click", function () {
            layer.confirm('确定退出吗？', function (index) {
                window.location.href = "${pageContext.request.contextPath}/logout";
            });
        });
    });
</script>
</body>
</html>
