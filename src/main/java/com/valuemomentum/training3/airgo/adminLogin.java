package com.valuemomentum.training3.airgo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Scanner;

public class adminLogin 
{
    String a_name = "admin",a_pass = "admin";	
	ResultSet res;	
	Statement stmt;
	Connection con;
	
	String name,pass;
	
	Scanner s = new Scanner(System.in);
	
	public void getAdmin() throws Exception
	{
		System.out.print("\n********************************************************************************************************************\n"); 
		System.out.println("\n-------------------------------------->>>>Admin portal<<<<--------------------------------------\n");
		
		System.out.println("Enter your admin username and password");		
		do {
			System.out.print("Enter user Id  : ");
			name=s.next();
			System.out.print("Enter password : ");
			pass=s.next();
			}while(!(name.contentEquals(a_name) && pass.contentEquals(a_pass)));		
		this.adminAccess();			
	}
	
	public void adminAccess() throws Exception
	{
		System.out.println("\n----------------------------------->>>>Admin Access Portal<<<<------------------------------------\n");
		while(true)
		{
			con = DBConnection.getConnection();
			stmt = con.createStatement();
			System.out.println("1. Flight Details\t 2. Passenger Details");
			System.out.println("3. Add Flight\t         4. Delete Flight");
			System.out.println("5. exit");			
			int choice = s.nextInt();			
			switch(choice)
			{
			case 1:
				System.out.println("\n>>Flight Details :\n");
				System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");				
				
				System.out.println("FLIGHT_NO     Capacity          FROM         DATE             TIME            TO            DATE              TIME              FARE           Type(1-Domestiv\\2-International)");			
				System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");				
				
				res = stmt.executeQuery("select * from flightinfo");
				while(res.next()) 
				{
					System.out.printf("%d %15d %18s %15s %15s %15s %15s %15s %15d %15d \n", res.getInt(1), res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getInt(9), res.getInt(10));
			    }
				System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");				
				
				res.close();
				stmt.close();
				con.close();
				break;
				
			case 2:
				res = stmt.executeQuery("select * from passengers");
				System.out.println("\n>>Passengers Details :\n");
				System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");				
				System.out.println("Ticket Number   Flight Number   First Name     Last Name       Age         Gender      Phone Number      Email Id             Address");
         		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
				while(res.next()) 
				{
					System.out.printf("%d %22d %15s %15s %8d %15s %15s %20s %15s \n", res.getInt(1), res.getInt(3), res.getString(4), res.getString(5), res.getInt(6), res.getString(7), res.getString(8), res.getString(9), res.getString(10));		
	            }
				System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
				res.close();
				stmt.close();
				con.close();
				System.out.println();				
				break;			
			
			case 3:
				System.out.println("\n>>Insert details of flight to be added\n");
				Scanner s = new Scanner(System.in);
	            String str = "INSERT INTO flightinfo(capacity,source,s_date,s_time,destination,d_date,d_time,fare,type)"+" VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? )";
	             		                    		
	             PreparedStatement pstmt;
	             pstmt = con.prepareStatement(str);
	            System.out.print("Enter Capacity                                         :  ");
	            int c = s.nextInt();
	            System.out.print("Enter Source City                                      :  ");
	            String scity = s.next();
	            System.out.print("Enter Date of Departure(YYYY-MM-DD)                    :  ");
	            String sdate = s.next();
	            System.out.print("Enter Time of Departure(HH:MM:SS)                      :  ");
	            String stime = s.next();
	            System.out.print("Enter Destination City                                 :  ");
	            String dcity = s.next();
	            System.out.print("Enter Date of Arrival(YYYY-MM-DD)                      :  ");
	            String ddate = s.next();
	            System.out.print("Enter Time of Arrival(HH:MM:SS)                        :  ");
	            String dtime = s.next();
	            System.out.print("Enter Fare per Person                                  :  ");
	            int fare = s.nextInt();
	            System.out.print("Enter Type of Llight (1-> Domestic\\2-> International) :  ");
	            int ftype = s.nextInt();            
	            
                pstmt.setInt(1,c);
                pstmt.setString(2,scity);
                pstmt.setString(3,sdate);
                pstmt.setString(4,stime);
                pstmt.setString(5,dcity);
                pstmt.setString(6,ddate);
                pstmt.setString(7,dtime);
                pstmt.setInt(8,fare);
                pstmt.setInt(9,ftype);                
                
                int rowAffected = pstmt.executeUpdate();
                if(rowAffected>0)
                {
                	System.out.println("\n\tFlight Added Successfully!!\n");
                }
                System.out.println("------------------------------------------------------------------------------------------------------------");
	            pstmt.close();
	            stmt.close();
				con.close();
	            System.out.println();
	            break;
	            
			case 4:
				System.out.println(">>Insert flight number to be deleted : ");				
				Scanner sc = new Scanner(System.in);
				int f_no = sc.nextInt();				
				String str2 = "Delete from flightInfo where flight_number = "+f_no ;				
				int cnt= stmt.executeUpdate(str2);
	            if(cnt>0) 
	            {
	                System.out.println("\tFlight Deleted Successfully!!");
	            }
	            else
	            {
	            	System.out.println("*Record not found");
	            }
	            System.out.println("--------------------------------------------------------------------------------------------");
	           
	            stmt.close();
				con.close();
	            System.out.println();
				break;
				
			case 5:
				App ap = new App();
		   		   ap.dashBoard();		
				
			default :
				System.out.println("*Invalid input");	
				
			}
			 System.out.println("\n------------------------------------------------------------------------------------------------------------");
             
		System.out.println("\n");
		
		}
	}	
}