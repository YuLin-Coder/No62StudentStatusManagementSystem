<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div class="layui-card" style="height: 125px">
                <div class="layui-card-header">系统概览</div>
                <div class="layui-card-body">
                    <ul>
                        <li>
                            <table>
                                <tr>
                                    <td rowspan="2" align="center">
                                        <i class="layui-icon layui-icon-form"></i>
                                    </td>
                                    <td class="number" id="subject">${subjectCnt}</td>
                                </tr>
                                <tr>
                                    <td class="txt">专业数量</td>
                                </tr>
                            </table>
                        </li>
                        <li>
                            <table>
                                <tr>
                                    <td rowspan="2" align="center">
                                        <i class="layui-icon layui-icon-carousel"></i>
                                    </td>
                                    <td class="number" id="clazz">${clazzCnt}</td>
                                </tr>
                                <tr>
                                    <td class="txt">班级数量</td>
                                </tr>
                            </table>
                        </li>
                        <li>
                            <table>
                                <tr>
                                    <td rowspan="2" align="center">
                                        <i class="layui-icon layui-icon-file"></i>
                                    </td>
                                    <td class="number" id="course">${courseCnt}</td>
                                </tr>
                                <tr>
                                    <td class="txt">课程数量</td>
                                </tr>
                            </table>
                        </li>
                        <li>
                            <table>
                                <tr>
                                    <td rowspan="2" align="center">
                                        <i class="layui-icon layui-icon-group"></i>
                                    </td>
                                    <td class="number" id="teacher">${teacherCnt}</td>
                                </tr>
                                <tr>
                                    <td class="txt">老师数量</td>
                                </tr>
                            </table>
                        </li>
                        <li>
                            <table>
                                <tr>
                                    <td rowspan="2" align="center">
                                        <i class="layui-icon layui-icon-menu-fill"></i>
                                    </td>
                                    <td class="number" id="section">${sectionCnt}</td>
                                </tr>
                                <tr>
                                    <td class="txt">开课数量</td>
                                </tr>
                            </table>
                        </li>
                        <li>
                            <table>
                                <tr>
                                    <td rowspan="2" align="center">
                                        <i class="layui-icon layui-icon-user"></i>
                                    </td>
                                    <td class="number" id="student">${studentCnt}</td>
                                </tr>
                                <tr>
                                    <td class="txt">学生数量</td>
                                </tr>
                            </table>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-row layui-col-space15" style="margin-top: 10px;">
        <div class="layui-col-md8">
            <div class="layui-card">
                <div class="layui-card-header">班级学生数量</div>
                <div class="layui-card-body">
                    <div style="height: 300px" id="container1">
                        ddd
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">功能区</div>
                <div class="layui-card-body">
                    <div style="height: 300px">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8">
            <div class="layui-card">
                <div class="layui-card-header">课程平均成绩</div>
                <div class="layui-card-body">
                    <div style="height: 300px" id="container2">

                    </div>
                </div>
            </div>
        </div>

        <div class="layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">功能区</div>
                <div class="layui-card-body">
                    <div style="height: 300px">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    //引入echarts
    layui.use(['echarts'], function () {
        var echarts = layui.echarts;

        //获取对应功能区的dom对象
        var dom1 = document.getElementById("container1");
        //初始化echarts
        var echartsRecord = echarts.init(dom1);
        option = {
            tooltip: {
                show: true
            },
            xAxis: {
                type: 'category',
                data: [
                    <c:forEach items="${mapList}" var="map">
                    '${map.name}',
                    </c:forEach>
                ]
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: [
                    <c:forEach items="${mapList}" var="map">
                    ${map.cnt},
                    </c:forEach>
                ],
                type: 'line'
            }]
        };
        //设置option
        echartsRecord.setOption(option);

        //获取对应功能区的dom对象
        var dom2 = document.getElementById("container2");
        //初始化echarts
        var echartsRecord2 = echarts.init(dom2);
        option2 = {
            tooltip: {
                show: true
            },
            xAxis: {
                type: 'category',
                data: [
                    <c:forEach items="${mapList2}" var="map">
                    '${map.clazzName}-${map.courseName}',
                    </c:forEach>
                ]
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: [
                    <c:forEach items="${mapList2}" var="map">
                    ${map.avgScore},
                    </c:forEach>
                ],
                type: 'bar'
            }]
        };
        //设置option
        echartsRecord2.setOption(option2);
    });
</script>
</body>
</html>
