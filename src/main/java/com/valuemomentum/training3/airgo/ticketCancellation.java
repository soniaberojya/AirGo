package com.valuemomentum.training3.airgo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ticketCancellation 
{
    Scanner s = new Scanner(System.in);
    Statement stmt;
    ResultSet res ;
    Connection con;
    
	int id, fareamount,flightnumber,balance;
    ticketCancellation(int id)
    {
    	this.id = id;
    }

    void cancelTicket() throws SQLException,Exception   
    
    {
    	int count =0;
    	System.out.println("\n********************************************************************************************************************\n");    	
    	System.out.println("-------------------------------------->>>>Ticket Cancellation<<<<--------------------------------------\n");   
    	System.out.println();
    	
    	while(true)
		{
			System.out.println("1. Cancel the Ticket\t 2.exit");			
	        int input = s.nextInt();
	        
	        switch(input)
	        {
	        case 1:
	        	
	        	con = DBConnection.getConnection(); 
	        	stmt = con.createStatement();
	        	
	        	System.out.println(">>Your Bookings");	         	
	         	res = stmt.executeQuery("SELECT pass_no,p.flight_no,p.pass_fname,p.pass_lname,fi.SOURCE,fi.s_date,fi.s_time,fi.destination,fi.d_date,fi.d_time FROM passengers p INNER JOIN flightinfo fi ON p.flight_no = fi.flight_number WHERE p.pass_uID ="+this.id);
	         	System.out.println("----------------------------------------------------------------------------------------------------------------------");				
	     		System.out.println("Ticket Number   Flight Number   First Name     Last Name         From             Date              Time              To           Date             Time");
	     		System.out.println("----------------------------------------------------------------------------------------------------------------------");
	     		while(res.next()) 
	     		{
	     			System.out.printf("%d %22d %15s %15s %15s %15s %15s %15s %15s %15s \n", res.getInt(1), res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9), res.getString(10));				
	     		}
	     		System.out.println("----------------------------------------------------------------------------------------------------------------------\n");
	     		
	         	
	         	
	             System.out.print("Enter Ticket Number : ");        
	             Scanner s = new Scanner(System.in);
	             int t_no = s.nextInt();
	             res = stmt.executeQuery("select pass_no from passengers where pass_uID ="+this.id);
	             while(res.next())
	             {
	            	 if(t_no == res.getInt(1))
		             		count++;		              
	             }
	             	
	             if(count>0)
	             {
	             	res =stmt.executeQuery("select * from passengers where pass_no ="+t_no);
	             	System.out.println("\n----------------------------------------------------------------------------------------------------------------------");				
	         		System.out.println("Ticket Number   Flight Number   First Name     Last Name       Age         Gender      Phone Number      Email Id             Address");
	         		System.out.println("----------------------------------------------------------------------------------------------------------------------");
	         		
	         		while(res.next()) 
	         		{
	         			flightnumber = res.getInt(2);
	         			System.out.printf("%d %22d %15s %15s %8d %15s %15s %20s %15s \n", res.getInt(1), res.getInt(3), res.getString(4), res.getString(5), res.getInt(6), res.getString(7), res.getString(8), res.getString(9), res.getString(10));		
	         		}		
	         		System.out.println("----------------------------------------------------------------------------------------------------------------------\n");
	         		
	             
	         		res =stmt.executeQuery("SELECT fare FROM flightinfo f INNER JOIN passengers p ON f.flight_number = p.flight_no WHERE p.pass_no ="+t_no);
	         		while(res.next())
	         		{
	         			fareamount = res.getInt(1);
	         		}

	         		double deduction = (fareamount/10);
	         		double final_amt = fareamount-(deduction);
	         		con.setAutoCommit(false);
	         		String str3 = "Delete from passengers where pass_no="+t_no;
	         		int cnt1 = 0;
     	            cnt1= stmt.executeUpdate(str3);
     	            if(cnt1>0) 
     	            {
     	                System.out.println("\tTicket Has Been Cancelled......");
     	                System.out.println("\nTicket Charges   :"+fareamount);
        	            System.out.println("Deduction        :"+(deduction));
        	            System.out.println("Final Amount     :"+final_amt);
        	            System.out.println("\n'Rs. "+final_amt+"' will be Refunded to you in a while");
        	            
        	            System.out.println("----------------------------------------------------------------------------------------------------------------------\n");
    	         		
        	            
        	            res = stmt.executeQuery("select wallet from users where user_id="+this.id);
        	    		while(res.next())
        	    		{
        	    			balance = res.getInt(1);
        	    		}
        	           double final_amt2 = balance + final_amt;
   		   		       String update = "UPDATE users "+"SET wallet = ? "+"WHERE user_id = ? ";
   		   		       PreparedStatement pstmt = con.prepareStatement(update);
                       pstmt.setDouble(1,final_amt);
                       pstmt.setInt(2,this.id);
                       int rowAffected = 0;
                       rowAffected = pstmt.executeUpdate();
                       if(rowAffected>0)
                       {
                    	   con.commit();
                    	   pstmt.close();
                       }
                       res.close();
              	       stmt.close();
              		   con.close();
        	             
                       break;
     	            }
     	           else
    	            {
    	            	System.out.println("Record not found\n");
    	            }
	             }
	             else
	             {
	             	System.out.println("\nNo Booking for Ticket Number '"+this.id+"' Found\n");  
	             	break;
	             }
	          
	             
	        case 2:

	        	userLogin ul1 = new userLogin();                                              
	   			ul1.userAccess(this.id);
	            
	            default:
	            	System.out.println("\n*Invalid Input");
	            	break;
	        	
	        }
		}
    }
}