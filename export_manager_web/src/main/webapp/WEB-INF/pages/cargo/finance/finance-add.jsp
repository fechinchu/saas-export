<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">

    <!-- 页面meta /-->
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            货运管理
            <small>合同下货物详情</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">货物添加及列表</li>
        </ol>
    </section>
    <!-- 内容头部 /-->
    <form id="editForm" action="${ctx}/cargo/finance/edit.do" method="post">
        <!-- 正文区域 -->
        <section class="content">

            <!--订单信息-->
            <div class="panel panel-default">
                <div class="panel-heading">财务运算单</div>

                <input type="hidden" name="shippingOrderId" value="${finance.financeId}">
                <div class="row data-type" style="margin: 0px">

                    <div class="col-md-2 title">制单日期</div>
                    <div class="col-md-4 data">
                        <input type="date" class="form-control" placeholder="制单日期" name="inputDate"
                               value="<fmt:formatDate value="${finance.inputDate}" pattern="yyyy-MM-dd"/>"
                               id="inputDate"/>

                    </div>


                    <div class="col-md-2 title">制单人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="制单人" name="inputBy"
                               value="${finance.inputBy}">
                    </div>
                </div>

            </div>
            <!--订单信息/-->

            <!--工具栏-->
            <div class="box-tools text-center">
                <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存
                </button>
                <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
            </div>
            <!--工具栏/-->

        </section>
        <!-- 正文区域 /-->

        <section class="content">

            <!-- .box-body -->
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">装箱单列表</h3>
                </div>

                <div class="box-body">

                    <!-- 数据表格 -->
                    <div class="table-box">
                        <!--数据列表-->
                        <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                            <thead>
                            <tr>
                                <th class="" style="padding-right:0px;">

                                </th>

                                <th class="sorting">BL_NO</th>
                                <th class="sorting">贸易条款</th>
                                <th class="sorting">创建人</th>
                                <th class="sorting">船舰部门</th>
                                <th class="sorting">创建时间</th>
                                <th class="sorting">状态</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="o" varStatus="status">
                                <tr>
                                    <td><input type="radio" name="id" value="${o.invoiceId}"/></td>

                                    <td>${o.blNo}</td>
                                    <td>${o.tradeTerms}</td>
                                    <td>${o.createBy}</td>
                                    <td>${o.createDept}</td>
                                    <td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd"/></td>
                                    <td>
                                        <c:if test="${o.state==0}">草稿</c:if>
                                        <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
                                    </td>
                                    <td>
                                        <a href="${ctx }/cargo/invoice/toUpdate.do?id=${o.invoiceId}">[编辑]</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <!--数据列表/-->
                        <!--工具栏/-->
                    </div>
                    <!-- 数据表格 /-->
                </div>
                <!-- /.box-body -->

                <!-- .box-footer-->
                <div class="box-footer">
                    <jsp:include page="../../common/page.jsp">
                        <jsp:param value="/cargo/finance/toAdd.do" name="pageUrl"/>
                    </jsp:include>
                </div>
                <!-- /.box-footer-->
            </div>

        </section>
    </form>
</div>
<!-- 内容区域 /-->
</body>
<script>
    $('#inputDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>


</html>