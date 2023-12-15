package JDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {




			private static final String URL="jdbc:mysql://localhost:3306/school14";
			private static final String username="root";
			private static final String password="Nikitha3112";
			
			//creating an object for the Scanner class
			public static Scanner obj=new Scanner(System.in);
			
			//insertRecord method will insert record into the database
			public static void insertData(Connection connect) throws SQLException
			{
				
				int RollNo,Cls=0,Marks=0;
				String Name="",Section="",DOB="",Subject="";
				//prepared statement for table 1(details)
				PreparedStatement ps=connect.prepareStatement("insert into StudentDetails values(?,?,?,?,?);");
				
				//taking input form the user using sanner class
				System.out.println("Enter student Roll Number:");
				 RollNo=obj.nextInt();
		        System.out.println("Enter student name:");
		        obj.nextLine();
		        Name=obj.nextLine();
		        System.out.println("Enter student class:");
		        Cls=obj.nextInt();
		        System.out.println("Enter student section:");
		        Section=obj.next();
		        System.out.println("Enter the student date-of-birth:");
		        DOB=obj.next();
		        //setters for setting the values that is to be inserted, which is taken as input from user
				ps.setInt(1,RollNo);
				ps.setString(2,Name);
				ps.setInt(3, Cls);
				ps.setString(4, Section);
				ps.setString(5, DOB);
				//preapre statemet for table 2(marks)
		        PreparedStatement ps1=connect.prepareStatement("insert into StudentMarks values(?,?,?);");
				
				//taking the inputs from user and setting the values for parameters
				 System.out.println("Enter student subject:");
				 Subject=obj.next();
				 System.out.println("Enter student marks:");
				 Marks=obj.nextInt();
				 //setters for setting the values that is inserted by the user
				 ps1.setInt(1, RollNo);
				 ps1.setString(2, Subject);
				 ps1.setInt(3, Marks);
				
				//displays the result of the operation
		        int rows=ps.executeUpdate();
		        int rows1=ps1.executeUpdate();
				
				if(rows==0|| rows1==0)
				{
					System.out.println("Error occured during Insertion...Try again!!");
					System.out.println("-----------------------------------------------");
				}
				else
				{
					System.out.println("Insertion completed..");
					System.out.println("-----------------------------------------------");
				}
			}
			

			//method to display the data
			public static void displayData(Connection connect) throws SQLException
			{
			    //query to display the data
				//left join is used to display the data from both the tables by comparing rollno row
				String query = "SELECT SD.RollNo, SD.Name, SD.Cls, SD.Section, SD.DOB, SM.Subject, SM.Marks " +
		                "FROM StudentDetails SD " +
		                "LEFT JOIN StudentMarks SM ON SD.RollNo = SM.RollNo;";
				//prepared statement to execute the query
				PreparedStatement ps=connect.prepareStatement(query);
				//result set store the data of the query
				ResultSet rs= ps.executeQuery();
				//result set point to the 1st column of the table so to move the result set rs.next() is a predefined method
				while(rs.next())
				{
					//getters for getting the values from the database to display
					int RollNo=rs.getInt("RollNo");
					String Name=rs.getString("Name");
					int Cls=rs.getInt("Cls");
					String Section=rs.getString("Section");
					String DOB=rs.getString("DOB");
					String Subject=rs.getString("Subject");
					int Marks=rs.getInt("Marks");
					
					System.out.println("-----------------------------------------------");
					System.out.println("Roll Number: "+RollNo+"\nName: "+Name+"\nClass: "+Cls+"\nSection: "+Section+"\nDate-of-Birth: "+DOB+"\nSubject: "+Subject+"\nMarks: "+Marks);
					System.out.println("-----------------------------------------------");
				}
			}
			
			//updating the records in database
			public static void updateData(Connection connect) throws SQLException
			{
				//prepared statement for tale 1
				PreparedStatement ps=connect.prepareStatement("update StudentDetails set Cls=? where RollNo=?");
				
				//taking the inputs from user and setting the values for parameters
				System.out.println("Enter RollNo");
				int RollNo=obj.nextInt();
				System.out.println("Enter the class");
				int Cls=obj.nextInt();
				//setters for updating the values given by the user
				ps.setInt(1, Cls);
				ps.setInt(2, RollNo);
				// prepared statement for table 2
				PreparedStatement ps1=connect.prepareStatement("update StudentMarks set Marks=? where RollNo=?");
				System.out.println("Enter the marks:");
				int Marks=obj.nextInt();
				//setters for updating
				ps1.setInt(1, Marks);
				ps1.setInt(2, RollNo);
				
				//displays the result of the operation
				 int rows=ps.executeUpdate();
				 ps1.executeUpdate();
					
					if(rows==0)
					{
						System.out.println("Try again!!");
						System.out.println("-----------------------------------------------");
					}
					else
					{
						System.out.println("Data updated..");
						System.out.println("-----------------------------------------------");
					}
				
			} 
			
			//deleteRecord will delete a specific row from database
			
			public static void deleteData(Connection connect) throws SQLException
			{
				//prepare statement 1 for table 1
				PreparedStatement ps=connect.prepareStatement("delete from StudentDetails where RollNo=?");
				
				System.out.println("Enter the Roll Number of the student:");
				int RollNo=obj.nextInt();
				//setter for taking the value to be deleted from the database
				ps.setInt(1, RollNo);
				//pre[are statement 2 for table2
				PreparedStatement ps1=connect.prepareStatement("delete from StudentMarks where RollNo=?");
				//setter for taking the value to be deleted from the database
				 ps1.setInt(1, RollNo);
				
				//displays the result of the operation
				 int rows=ps.executeUpdate();
				 int rows1=ps1.executeUpdate();
					
					if(rows==0||rows1==0)
					{
						System.out.println("Try again!!");
						System.out.println("-----------------------------------------------");
					}
					else
					{
						System.out.println("Deleted Sucessfully..");
						System.out.println("-----------------------------------------------");
					}
			}
            //main method starts
			public static void main(String[] args) throws ClassNotFoundException, SQLException {
				
				int choice=0,tableNum=0; //choice variable contains the user preferred operation
				
				//loading the driver
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				//connection establishment
				Connection connect=DriverManager.getConnection(URL,username,password);
				
				//calling the create table methods to create tables in the database
				  School14Table1.createTable1(connect);
				  School14table2.createTable2(connect);
				 
				  
				 while(choice!=5)
				 {
					 System.out.println("Enter your Choice");
					 System.out.println("1.Insert Data");
					 System.out.println("2.Delete Data");
					 System.out.println("3.Update Data");
					 System.out.println("4.Display Data");
					 System.out.println("5.Exit");

					 
					 choice=obj.nextInt();
					 
					 switch(choice)
					 {
					     case 1:
					    	 insertData(connect);
					    	 displayData(connect);
					    	 break;
					     case 2:
					    	 deleteData(connect);
					    	 displayData(connect);
					    	 break;
					     case 3:
					    	 updateData(connect);
					    	 break;
					     case 4:
					    	 displayData(connect);
					    	 break;
					     case 5:
					    	 System.out.println("Thanks for visiting....");
					    	 return;
					     default:
					    	 System.out.println("Enter an valid option");
					 }
				 }

			}
	}


