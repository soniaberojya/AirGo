package com.valuemomentum.training3.airgo;
import java.sql.SQLException;
import java.util.Scanner;

public class App 
{
	static void dashBoard() throws Exception
	{
		while(true)
        {
			int input;    	
		   	   Scanner in = new Scanner(System.in); 
		   	   System.out.println("\n-------------------------------------->>>>DashBoard<<<<-----------------------------------------");
		     	   System.out.println("1. User Login\t 2. Admin Login\t 3. Exit");
		            input = in.nextInt();           
		            switch(input)
		            {
		            case 1:
		         	   userLogin ul= new userLogin();
		         	   ul.getUser();
		            	break;
		            	
		            case 2:
		         	   adminLogin al= new adminLogin();
		         	   al.getAdmin();
		            	break;
		            	
		            case 3:
		            	System.out.println("Exiting Portal.....");
		         	   System.exit(0);
		         	   break;
		            	
		            default: 
		            	System.out.println("*Invalid key"); 
   	      
                    }
        }
          
	}
    public static void main( String[] args ) throws Exception
    {
    	  	
        System.out.println("************************************** Air Go **************************************");
        System.out.println("-----------------------------Airline Reservation System-----------------------------\n");        
        dashBoard();
        
    }
       
}