package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class paymentServices {
	
	//connection
		private Connection connect() {
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				
				String url = String.format("jdbc:mysql://127.0.0.1:3306/elecgrid");
				String username = "root";
				String password = "";
				
				con = DriverManager.getConnection(url,username, password);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return con;
		}
		
		//insert payment details
		public String insertPayDetails(String pay_type, String amount, String cus_id, String bill_id) {
			
			String output = "";
			
			try {
				
				Connection con = connect();
				
				if(con == null)
				{return "Error while connecting to the database for inserting data";}
				
				String insertQuery = "insert into payment (`pay_id`, `pay_type`,`amount`, `cus_id`, `bill_id`)" + "values(?,?,?,?,?)";
				
				PreparedStatement ps = con.prepareStatement(insertQuery);
				ps.setInt(1, 0);
				ps.setString(2, pay_type);
				ps.setString(3, amount);
				ps.setString(4, cus_id);
				ps.setString(5, bill_id);

				ps.execute();
				con.close();
				
				String newPayment = viewPayments(); 
				output = "{\"status\":\"success\",\"data\":\""+newPayment+"\"}"; 
				

			} catch(Exception e) {
				output = "{\"status\":\"error\", \"data\":\"Error while inserting the Payment.\"}";
				//output = "Payment does not go through.. something went wrong!.";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		//view all payments
		public String viewPayments() {
			
			String output ="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for read the payments";}
				
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>id</th><th>Payment Type</th>" +
				"<th>Amount (Rs.)</th>"+"<th>Customer ID </th>"+"<th>Bill ID</th>" +
				"<th>Update</th><th>Remove</th></tr>";
				
				String query = "select * from payment";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()) {
					
					String pay_id = Integer.toString(rs.getInt("pay_id"));
					String pay_type = rs.getString("pay_type");
					String amount = rs.getString("amount");
					String cus_id = rs.getString("cus_id");
					String bill_id = rs.getString("bill_id");
					
					// Add into the html table
					output += "<tr><td><input id='hidpaymentIDUpdate' name='hidpaymentIDUpdate' type='hidden' value='"+pay_id+"'></td>"; 
					output += "<tr><td>" + pay_id + "</td>";
					output += "<td>" + pay_type + "</td>";
					output += "<td>" + amount + "</td>";
					output += "<td>" + cus_id + "</td>";
					output += "<td>" + bill_id + "</td>";
					
					// buttons
					/*output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary' data-pay_id='" + pay_id + "></td>"
					+ "<td><form method='post' action='items.jsp'>"
					+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger' data-pay_id='" + pay_id + ">"
					+ "<input name='cus_id' type='hidden' value='" + pay_id
					+ "'>" + "</form></td></tr>";  */
					
					 output += "<td><input name='btnUpdate' type='button' value='Update' "
							 + "class='btnUpdate btn btn-secondary' data-pay_id='" + pay_id + "'></td>"
							 + "<td><input name='btnRemove' type='button' value='Remove' "
							 + "class='btnRemove btn btn-danger' data-pay_id='" + pay_id + "'></td></tr>"; 
					
				}
				
				con.close();
				
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading payments";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		//update payment --> Payment update is unnecessary <--
		public String updatePayment(  String pay_id, String pay_type, String amount, String cus_id, String bill_id ) {
		
			String output="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for updating the " + pay_id;}
				
				// create a prepared statement
				String query = "UPDATE payment SET pay_type=?, amount=?, cus_id=?, bill_id=? WHERE pay_id=?";
				//String query = "UPDATE payment SET pay_type=?, amount=? WHERE pay_id=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setString(1, pay_type);
				preparedStmt.setString(2, amount);
				preparedStmt.setString(3, cus_id);
				preparedStmt.setString(4, bill_id);
				
				preparedStmt.setInt(5,Integer.parseInt(pay_id));
				
				// execute the statement
				preparedStmt.execute();
				
				con.close();
				
				String newPayments =viewPayments(); 
				output = "{\"status\":\"success\",\"data\":\""+newPayments+"\"}"; 
				
				//output = "Updated payment successfully";
				
			} catch (Exception e) {
				
				output = "{\"status\":\"error\",\"data\":\"Error while updating the payment.\"}"; 
				//output = "Error while updating the " + pay_id;
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		//delete
		public String deleteCustomer(String pay_id)
		{
			String output = "";
			try
			{
			Connection con = connect();
			
			if (con == null)
			{return "Error while connecting to the database for deleting."; }
			
			// create a prepared statement
			String query = "delete from payment WHERE pay_id=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pay_id));
			
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			String newPayments = viewPayments(); 
			 output = "{\"status\":\"success\",\"data\":\""+newPayments+"\"}"; 
			//output = "<h1>Deleted payment detail successfully</h1>";
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\",\"data\":\"Error while deleting the payment.\"}";
				//output = "Error while deleting the payment detail.";
				System.err.println(e.getMessage());
			}
		return output;
		}
		
}
