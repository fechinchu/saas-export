<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../base.jsp"%>
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
            订单管理
            <small>订单表单</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">订单管理</a></li>
            <li class="active">订单表单</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">购销合同信息</div>
            <form id="editForm" action="${ctx}/cargo/packing/edit.do" method="post">
                <input type="hidden" name="packingListId" value="${packing.packingListId}">
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">卖方</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="卖方" name="seller"
                               value="${packing.seller}"/>
                    </div>

                    <div class="col-md-2 title">买方</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="买方" name="buyer" value="${packing.buyer}"/>
                    </div>

                    <div class="col-md-2 title">发票号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="发票号" name="invoiceNo"
                               value="${packing.invoiceNo}"/>
                    </div>

                    <div class="col-md-2 title">发票日期</div>
                    <div class="col-md-4 data">
                        <input type="date" class="form-control" placeholder="发票日期" name="invoiceDate" value="<fmt:formatDate value="${packing.invoiceDate}" pattern="yyyy-MM-dd"/>" id="invoiceDate"/>
                    </div>

                    <div class="col-md-2 title">唛头</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="唛头" name="marks" value="${packing.marks}"/>
                    </div>


                    <div class="col-md-2 title">描述</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="描述" name="descriptions" value="${packing.descriptions}"/>
                    </div>
                </div>
          </form>
        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->

</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#invoiceDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });

</script>
</html>