package com.valuemomentum.training3.airgo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class userLogin 
{
	int choice, count=0;
	String uname,upass;
	
	ResultSet res;
	Statement stmt;
	Connection con;
	Scanner s = new Scanner(System.in);
	int id;
	userLogin() {}
	
	public void getUser() throws Exception
	{
		con = DBConnection.getConnection();
		System.out.println("\n-------------------------------------->>>>login<<<<-----------------------------------------\n");
		while(true)
		{
			System.out.println("1. login\t 2. Signup\t 3. Exit"); 
			choice =  s.nextInt();			
			switch(choice)
			{
			case 1:
				System.out.print("Username:  ");
				uname = s.next();
				System.out.print("Password:  ");
				upass = s.next();
				System.out.println();
				stmt = con.createStatement();
				res = stmt.executeQuery("select user_id,user_name,password from users");
				while(res.next())
				{
					String user=res.getString(2);
					String password=res.getString(3);
					if(uname.contentEquals(user) && upass.contentEquals(password))
					{
						id = res.getInt(1);
						count++;
						
						
					}
				}
				if(count>0)
				{
					res.close();				
					stmt.close();
					con.close();
					userAccess(id);
				}
				else
				{
					System.out.println("* Invalid username\\Password\n");
				}
				
												
			break;
			case 2:
				System.out.print("Username:  ");
				uname = s.next();
				System.out.print("Password:  ");
				upass = s.next();
				System.out.println();
				
				stmt = con.createStatement();
				res = stmt.executeQuery("select user_name from users");
				while(res.next())
				{
					String user=res.getString(1);
					if(uname.contentEquals(user))
					{
						System.out.println("Username already exist");
					}
				}
                String str = "INSERT INTO users(user_name,password)"+"VALUES( ? , ? )";
                
                PreparedStatement pstmt;
	            pstmt = con.prepareStatement(str);
                pstmt.setString(1,uname);
                pstmt.setString(2,upass);
                
                int rowAffected = pstmt.executeUpdate();
                if(rowAffected>0)
                {
                	System.out.println("\nUser Account Created Successfully!!\n");
                }
                pstmt.close();
                
				break;
				
			case 3:
				App ap = new App();
		   		   ap.dashBoard();
				
			default: 
				System.out.println("Invalid key");
			}			
		}			
	}
	
	void userAccess(int inputID) throws Exception
	{
		System.out.print("\n********************************************************************************************************************\n");
		System.out.println("--------------------------------->>>>Welcome to Air Go!!<<<<---------------------------------\n");

		int in = inputID;
		while(true)
		{
			System.out.println("1. Book Ticket\t           2. Cancel Ticket \n3. View Bookings\t   4. View Wallet\n5. Add Amount to Wallet    6.Exit");			
			int input = s.nextInt();			
			switch(input)
			{
			case 1:
				TicketBooking tb = new TicketBooking(in);
				tb.flightSearch();				
				break;
				
			case 2:
				ticketCancellation tc = new ticketCancellation(in);
				tc.cancelTicket();			
				break;
				
			case 3:
				userAccount uaa = new userAccount(in);
				uaa.viewBookings();
				break;
				
			case 4:
				userAccount uaa2 = new userAccount(in);
				uaa2.viewWallet();
				break;
				
			case 5:
				userAccount uaa3 = new userAccount(in);
				uaa3.AddAmount();
				break;
				
			case 6:
				App ap = new App();
		   		   ap.dashBoard();
			default:
				System.out.println("Invalid Input");
			}
		}
	}	
}