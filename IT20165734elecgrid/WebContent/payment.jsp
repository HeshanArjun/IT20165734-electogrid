<%@page import="com.paymentServices"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payment.js"></script>
</head>
<body>
<div class="container"><div class="row"><div class="col-6"> 

<h1>Payment Management</h1>
<form id="formpayment" name="formpayment">
Payment type: 
 <input id="pay_type" name="pay_type" type="text" 
 class="form-control form-control-sm">
  Payment amount: 
 <input id="amount" name="amount" type="text" 
 class="form-control form-control-sm">
  Customer ID: 
 <input id="cus_id" name="cus_id" type="text" 
 class="form-control form-control-sm">
  Bill ID: 
 <input id="bill_id" name="bill_id" type="text" 
 class="form-control form-control-sm">
  <input id="btnSave" name="btnSave" type="button" value="Save" 
 class="btn btn-primary">
 <input type="hidden" id="hidpaymentIDSave" 
 name="hidpaymentIDSave" value="">
 </form>
 
 <div id="alertSuccess" class="alert alert-success" style="display: none;"></div>
<div id="alertError" class="alert alert-danger"  style="display: none;"></div>


<div id="divpaymentGrid">
 <%
 paymentServices paymentObj = new paymentServices(); 
 out.print(paymentObj.viewPayments()); 
 %>
</div>

 
</div>
</div> </div> </div> 
</body>
</html>