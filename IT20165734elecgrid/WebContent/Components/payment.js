$(document).on("click", "#btnSave", function(event)
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validatepaymentForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------------
var type = ($("#hidpaymentIDSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
 url : "paymentAPI", 
 type : type, 
 data : $("#formpayment").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onpaymentSaveComplete(response.responseText, status); 
 } 
 }); 
});

function onpaymentSaveComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully saved."); 
 $("#alertSuccess").show(); 
 $("#divpaymentGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while saving."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving.."); 
 $("#alertError").show(); 
 }
$("#hidpaymentIDSave").val(""); 
$("#formpayment")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
		{ 
		$("#hidpaymentIDSave").val($(this).data("pay_id")); 
		 $("#pay_id").val($(this).closest("tr").find('td:eq(0)').text()); 
		 $("#pay_type").val($(this).closest("tr").find('td:eq(1)').text()); 
		 $("#amount").val($(this).closest("tr").find('td:eq(2)').text());
		 $("#cus_id").val($(this).closest("tr").find('td:eq(3)').text());
		 $("#bill_id").val($(this).closest("tr").find('td:eq(4)').text());
		});




$(document).on("click", ".btnRemove", function(event)
		{ 
		 $.ajax( 
		 { 
		 url : "paymentAPI", 
		 type : "DELETE", 
		 data : "pay_id=" + $(this).data("pay_id"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onpaymentDeleteComplete(response.responseText, status); 
		 } 
		 }); 
		});
		
function onpaymentDeleteComplete(response, status)
{ 
if (status == "success") 
 {
	location.reload();
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divpaymentGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}


// CLIENT-MODEL================================================================
function validatepaymentForm()
{
	// Payment type
	if ($("#pay_type").val().trim() == "")
	{
	return "Insert Payment Type.";
	}
	


// AMOUNT-------------------------------
if ($("#amount").val().trim() == ""){
	return "Insert payment amount.";
}
		// is numerical value
		var tmpamount = $("#amount").val().trim();
		if (!$.isNumeric(tmpamount))
	{
	return "Insert a numerical value for amount.";
	}
		//Customer id
		if ($("#cus_id").val().trim() == "")
		{
		return "Insert customer id.";
		}		
		
		//Bill id
		if ($("#bill_id").val().trim() == "")
		{
		return "Insert bill id.";
		}
		
		return true;

}