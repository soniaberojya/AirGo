package com.valuemomentum.training3.airgo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class userAccount 
{
	ResultSet res;
	Statement stmt;
	Connection con;
	int balance;
	int id;
	
	userAccount(int id)
	{
		this.id = id;
	}
	
	
	public void viewWallet() throws Exception
	{
		int balance;
		con = DBConnection.getConnection();
		stmt = con.createStatement();
		System.out.println("-------------------------------------->>>>e-Wallet<<<<--------------------------------------\n");
		res = stmt.executeQuery("select wallet from users where user_id="+this.id);
		while(res.next())
		{
			
			System.out.println("\nWallet Balance:  "+res.getInt(1)+"\n");
		}
		System.out.println("--------------------------------------------------------------------------------------------------\n");
 		
		res.close();
 		stmt.close();
 		con.close();
		
	}
	
	public void viewBookings() throws Exception
	{
		con = DBConnection.getConnection();
		stmt = con.createStatement();
		System.out.println("-------------------------------------->>>>Bookings<<<<--------------------------------------\n");
		res = stmt.executeQuery("SELECT pass_no,p.flight_no,p.pass_fname,p.pass_lname,fi.SOURCE,fi.s_date,fi.s_time,fi.destination,fi.d_date,fi.d_time FROM passengers p INNER JOIN flightinfo fi ON p.flight_no = fi.flight_number WHERE p.pass_uID ="+this.id);
     	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");				
 		System.out.println("Ticket Number   Flight Number   First Name     Last Name            FROM            DATE             TIME              TO            DATE            TIME");
 		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
 		while(res.next()) 
 		{
 			System.out.printf("%d %22d %15s %15s %18s %15s %15s %15s %15s %15s \n", res.getInt(1), res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9), res.getString(10));				
 		}
 		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

 		res.close();
 		stmt.close();
 		con.close();
		
	}
	
	public void AddAmount() throws Exception
	{
		con = DBConnection.getConnection();
		stmt = con.createStatement();
		System.out.println("\n>>Add Amount In Wallet\n");
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
        	
        	System.out.println("Wallet Balance  :\tRs. "+balance+"\n");
        	System.out.println("----------------------------------------------------------------------------------------------------------------------\n");

        	
     	res.close();
   		stmt.close();
   		pstmt.close();
   		con.close();
   		
        }
	}
	
}
