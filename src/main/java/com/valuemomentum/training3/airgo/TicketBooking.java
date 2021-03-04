package com.valuemomentum.training3.airgo;

import java.sql.Connection;

import java.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;

public class TicketBooking
{
	ResultSet res;
	Statement stmt;
	Connection con;
	PreparedStatement pstmt;
	int pass_no,pno,check = 0;
	int fare = 0;
	int balance = 0;
	int id;
	Scanner sc = new Scanner(System.in);
	
	TicketBooking(int id)
	{
		this.id = id;
	}
		
	
	public void flightSearch()
	{
		con = DBConnection.getConnection();
		System.out.print("\n********************************************************************************************************************\n");
		System.out.println("\n------------------------------------>>>>Search Flights<<<<------------------------------------\n");
		System.out.println("1. Flight Search\t 2. exit");
		int in = sc.nextInt();
		System.out.println();
		int occupied = 0;
		int capacity = 0;		
		switch(in)
		{
		case 1:
			System.out.println("\n1. Domestic Flight\t 2. International Flight");
			int type = sc.nextInt();
			System.out.println();
			System.out.print("From : \t");
			String src = sc.next();			
			System.out.print("To   : \t");
			String dest = sc.next();
			System.out.println();
			
			try
			{
				stmt = con.createStatement();
				res = stmt.executeQuery("Select flight_number,source, s_date, s_time, destination, d_date, d_time, fare from flightInfo where source='"+src+"'and destination='"+dest+"'");			
				System.out.print("----------------------------------------------------------------------------------------------------------------------\n");				
	     		
				System.out.println("PLANE_NO     FROM           DATE              TIME            TO            DATE              TIME              FARE");			
				System.out.println("----------------------------------------------------------------------------------------------------------------------");				
	     		
				while(res.next()) 
				{
					System.out.printf("%d %18s %15s %15s %15s %15s %15s %15d \n", res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getInt(8));
					check = res.getInt(1);
				}
				System.out.println("----------------------------------------------------------------------------------------------------------------------");				
	     		
			}
			catch(SQLException se)
			{
				System.out.println(se.getMessage());
			}
			
			
			
			if( check== 0)
			{
				System.out.println("\n\tNo Flights found on this route.....");
				flightSearch();				
			}
				
			System.out.print("\nEnter plane number         :  ");
			pno = sc.nextInt();			
			System.out.print("Enter number of passengers :  ");
			pass_no = sc.nextInt();
			System.out.println();
			System.out.println();
			System.out.println("Checking Availability of Seats.....\n");
			
			try
			{
				res = stmt.executeQuery("SELECT COUNT(pass_no) FROM passengers where flight_no ='"+pno+"' GROUP BY flight_no");			
				while(res.next())
				{
				   occupied = res.getInt(1);
				}			
				res = stmt.executeQuery("SELECT capacity FROM flightinfo WHERE flight_number ='"+pno+"'");			
				while(res.next())
				{
				   capacity = res.getInt(1);
				}
			}
			catch(SQLException se)
			{
				System.out.println(se.getMessage());
			}
		
		if(pass_no < (capacity - occupied))
		{
			System.out.println("\tSeates are available!!\n");
			try
			{
				res.close();
				stmt.close();
				con.close();
				try 
				{
					flightBook();
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			catch(SQLException se)
			{
				System.out.println(se.getMessage());
			}
		}
		else
		{
			System.out.println("\tNo Seats Available...");
		}
		break;
		
		case 2:
			userLogin ul1 = new userLogin(); 
			try
			{
				ul1.userAccess(this.id);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
   			
   			break;
   		default: 
   			System.out.println("*Invalid Input");
   				
   			
	}				
	}	
	public void flightBook()
	{
		
    	con = DBConnection.getConnection();
    	
    	System.out.println("----------------------------------------------------------------------------------------------------------------------\n");
		System.out.println("\n>>Enter Details:\n");
    	System.out.println();
    	try 
    	{
			con.setAutoCommit(false);
		} 
    	catch (SQLException e) 
    	{			
			e.printStackTrace();
		} 
    	int error = 0;
    	for(int i=0;i<pass_no;i++)
        {
    		do
    		{
    			try
        		{
    				error=0;
        			String str="INSERT INTO passengers(pass_uID,flight_no,pass_fname,pass_lname,pass_age,pass_gender,pass_phone, pass_email, pass_address)"+ "VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? );";
         		    
                	
                	System.out.println("Enter Details of Passenger " +(i+1)+"\n");
        		    System.out.print("Enter First Name                    :  ");
        		    String fname=sc.next();
        		    System.out.print("Enter Last Name                     :  ");
        		    String lname=sc.next();
        		    System.out.print("Enter Age                           :  ");
        		    int age=sc.nextInt();
        		    System.out.print("Enter Gender('male\\female\\other')   :  ");
        		    String gender=sc.next();
        		    System.out.print("Enter Phone Number                  :  ");
        		    String phone=sc.next();
        		    System.out.print("Enter Email ID                      :  ");
        		    String email=sc.next();
        		    System.out.print("Enter Address                       :  ");
        		    String address=sc.next();
        		 
        		    try
        		    {
        		    	pstmt = con.prepareStatement(str);
            		    pstmt.setInt(1,this.id);
                        pstmt.setInt(2,pno);
                        pstmt.setString(3,fname);
                        pstmt.setString(4,lname);
                        pstmt.setInt(5,age);
                        pstmt.setString(6,gender);
                        pstmt.setString(7,phone);
                        pstmt.setString(8,email);
                        pstmt.setString(9,address); 
                        
                        int rowAffected = pstmt.executeUpdate();
                        if(rowAffected == pass_no)
                         {
                         	System.out.println();
                         	System.out.println("\tDetails Submitted Successfully!!");
                         	res.close();
                         	stmt.close();
                 			pstmt.close();
             				payment(); 
                         }
        		    }
        		    catch(SQLIntegrityConstraintViolationException sic)
        		    {
        		    	System.out.println("\n\tEnter data in Specified Format\n");
        		    	error++;
        		    }
        		    catch(SQLException se)
        		    {
        		    	System.out.println("\n\tEnter data in Specified Format\n");
        		    	error++;
        		    }
        		    
        		 
        		}
        		catch(Exception e)
        		{
        			System.out.println("\n\tEnter data in Specified Format\n");
        			error++;
        		}
        		
        }while(error>0);
    		
    		
      }
    		
        	
	}
		   	
	public void payment() throws Exception
    {
		String user = "username";
		String pass = "password";
		String username, password;
		stmt = con.createStatement();
		res = stmt.executeQuery("select fare from flightInfo where flight_number ='"+pno+"'");
		while(res.next())
		{
			fare = res.getInt(1);			
		}
		System.out.println("----------------------------------------------------------------------------------------------------------------------");
		
		System.out.println();
		System.out.println("Total amount to be paid: Rs "+(pass_no * fare));		
		while(true)
		{

			System.out.println("\n1. - continue Payment\t 2.exit");
		   	   int next = sc.nextInt();		   	   
		   	   switch(next)
		   	   {
		   	   case 1:
		   		   System.out.println("-------------------------------------------->>>>e-Wallet<<<<--------------------------------------------\n");
		   		   System.out.println("Enter your e-wallet Username and Password");
		   		do {
		            System.out.print("Enter user Id  : ");
		            username=sc.next();
		            System.out.print("Enter password : ");
		            password=sc.next();
		            }while(!(username.contentEquals(user) && password.contentEquals(pass))); 
		   		res = stmt.executeQuery("select wallet from users where user_id="+this.id); 
		   		while(res.next())
		   		{
		   			balance = res.getInt(1);
		   		}
		   		int amount = pass_no * fare;
		   		if(amount<balance)
		   		{
		   			int final_amt = balance-amount;
		   			String update = "UPDATE users "+"SET wallet = ? "+"WHERE user_id = ? ";
                    pstmt = con.prepareStatement(update);
                    pstmt.setInt(1,final_amt);
                    pstmt.setInt(2,this.id);
                    int rowAffected = 0;
                    rowAffected = pstmt.executeUpdate();
                    if(rowAffected>0)
                    {
                 	  con.commit();
                 	  
           			  pstmt.close();
           			  
                  	
                    }
		   			System.out.println("\tPayment Successfully!!");
		   			System.out.println("\t***Ticket Booked***");
		   			System.out.print("\n*************************************************************************************************************************\n"); 
		   			con.commit();
		   			System.out.println("\n------------------------------------------------------");				
		     		
		   			System.out.println("\n\t    Ticket");
		   			System.out.println("--------------------------------------------------------");				
		     			res =stmt.executeQuery("select * from passengers order by pass_no DESC limit "+pass_no);	
		   				while(res.next())
			   			{
			   				System.out.println("Ticket Number : "+res.getInt(1));
			   				System.out.println("Flight Number : "+res.getInt(3));
			   				System.out.println("First Name    : "+res.getString(4));
			   				System.out.println("Last Name     : "+res.getString(5));
			   				System.out.println("Age           : "+res.getInt(6));
			   				System.out.println("Gender        : "+res.getString(7));
			   				System.out.println("Phone Number  : "+res.getString(8));
			   				System.out.println("E-Mail Id     : "+res.getString(9));
			   				System.out.println("Address       : "+res.getString(10));
			   				
			   				
			   				System.out.println("\n\tHave a Safe Journey!!\n");
			   				System.out.println("--------------------------------------------------------");
			   		    }
		   				res.close();
	                 	stmt.close();
	                 	con.close();
	            	    userLogin ul1 = new userLogin();                                              
	     	   			ul1.userAccess(this.id);
		   				
		   				
		   		}
		   		
    			
		   		else 
		   		{
		   			System.out.println("\t\tInsufficient Balance In Wallet\n");
		   			System.out.println("1. Add Amount In Wallet\t2. Exit");
		   			int choice = sc.nextInt();
		   			if(choice == 1)
		   			{
		   				res = stmt.executeQuery("select wallet from users where user_id="+this.id);
		   				while(res.next())
		   				{
		   					balance = res.getInt(1);
		   				}
		   				System.out.print("Enter Amount to be Added :\t");
		   				Scanner sc = new Scanner(System.in);			
		   				int amt = sc.nextInt();
		   				balance = balance + amt;
		   				String update = "UPDATE users "+"SET wallet = ? "+"WHERE user_id = ? ";
		   				PreparedStatement pstmt = con.prepareStatement(update);
		   		        pstmt.setDouble(1,balance);
		   		        pstmt.setInt(2,this.id);
		   		        int rowAffected = 0;
		   		        rowAffected = pstmt.executeUpdate();
		   		        if(rowAffected>0)
		   		        {
		   		        	System.out.println("\t\tAmount Has Been Added Successfully!!\n");
		   		        }
		   			}
		   			else
			   			{
		   				userLogin ul1 = new userLogin();                                              
			   			ul1.userAccess(this.id);
			   			
		   				}
		   			
		   		}
		   		
		   		break;
		   		   
		   	   case 2:
		   		userLogin ul1 = new userLogin();                                              
	   			ul1.userAccess(this.id);
		   	  default:
		   			   System.out.println("*Invalid Input");   		    
		   	   }
		}
	}
}