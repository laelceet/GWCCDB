import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;

public class GirlsWhoCode {
    private String connectionUrl =
    "jdbc:sqlserver://cxp-sql-02\\agp63;"
    + "databaseName=GirlsWhoCodeClub;" 
    + "user=sa;"
    + "password=M8CcGH3:^4)So1;" 
    + "encrypt=true;"
    + "trustServerCertificate=true;"
    + "loginTimeout=15;";

    private Scanner sc; 

    //USE CASE 1
    public void facilitatorAssistAttendance() {
        String callStoredProc = "{call dbo.assists_attendance(?,?)}"; 

        System.out.println("Enter the following parameters for this use case: "); 
        //ask for user input
        sc = new Scanner(System.in); 
        System.out.println("\tEnter a semester (Fall or Spring): "); 
        String semester = sc.nextLine(); 
        System.out.println("\tEnter the semester year: "); 
        String year = sc.nextLine(); 

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement prepsGetCurriculum = connection.prepareCall(callStoredProc);) {
                            
            prepsGetCurriculum.setString(1, semester); 
            prepsGetCurriculum.setString(2, year);

            if(prepsGetCurriculum.execute()) {
                ResultSet resultSet =  prepsGetCurriculum.getResultSet(); 
                System.out.println("Facilitator Name : Total Sessions Assisted: ");

                while(resultSet.next()) {
                    System.out.println("\t" + resultSet.getString(1) + " : " + resultSet.getString(2));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //USE CASE 2
    public void dropStudent() {
        //ask for user input
        sc = new Scanner(System.in);

        //call procedure to list all existing students so can use ID in stored procedure below
        System.out.println("Enter the following parameters for this use case: ");

        System.out.println("\tEnter the first name of the student: "); 
        String firstName = sc.nextLine();  
        System.out.println("\tEnter the last name of the student: "); 
        String lastName = sc.nextLine(); 

        String callStoredProc = "{call dbo.drop_student(?,?,?)}"; 

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement callableStatement = connection.prepareCall(callStoredProc);) {

            int numStudentsWithName = getStudentsWithNameCount(firstName, lastName); 
            
            if(numStudentsWithName == 1) {
                connection.setAutoCommit(false);
                
                callableStatement.setString(1, firstName); 
                callableStatement.setString(2, lastName);
                callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);

                callableStatement.execute();
                connection.commit();

                System.out.println("Successfully removed student with ID " + callableStatement.getInt(3) + " (" + firstName + " " + lastName + ") from the database.");
            }
            //else, if there are two students with the same name, specify the id of student that want to delete 
            else {
                callStoredProc = "{call dbo.list_all_students_with_name(?,?)}";
                CallableStatement callableStatement2 = connection.prepareCall(callStoredProc);
                callableStatement2.setString(1, firstName); 
                callableStatement2.setString(2, lastName);
                if(callableStatement2.execute()) {
                    ResultSet allNames = callableStatement2.getResultSet(); 
                    System.out.println("\nStudent ID : Student Name: ");

                    while(allNames.next()) {
                        System.out.println("" + allNames.getString(1) + " : " + allNames.getString(2) + " " + allNames.getString(3));
                    }

                    deleteStudentWithId(); 
                }    
                
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //USE CASE 3
    public void semesterCurriculum() {
        String callStoredProc = "{call dbo.semester_curriculum(?,?,?)}"; 

        //ask for user input
        sc = new Scanner(System.in);
        System.out.println("Enter the following parameters for this use case: "); 
        System.out.println("\tEnter a semester (Fall or Spring): "); 
        String semester = sc.nextLine(); 
        System.out.println("\tEnter a year: "); 
        String year = sc.nextLine(); 
        System.out.println("\tEnter type of session (Python or Arduino): "); 
        String type = sc.nextLine(); 

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement callableStatement = connection.prepareCall(callStoredProc);) {
                            
            callableStatement.setString(1, semester); 
            callableStatement.setString(2, year);
            callableStatement.setString(3, type);

            if(callableStatement.execute()) {
                ResultSet resultSet =  callableStatement.getResultSet(); 
                System.out.println("Session # : Curriculum: ");

                int i = 1; 
                while(resultSet.next()) {
                    System.out.println("\t" + i + " : " + resultSet.getString(1));
                    i++; 
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //USE CASE 4
    public void getSessionLeadersAndTopics() {
        String callStoredProc = "{call dbo.session_leaders(?,?)}"; 

        //ask for user input
        System.out.println("Enter the following parameters for this use case: ");
        sc = new Scanner(System.in); 
        System.out.println("\tEnter type of session (Python or Arduino): "); 
        String type = sc.nextLine(); 
        System.out.println("\tEnter date for session: "); 
        String date = sc.nextLine(); 

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement callableStatement = connection.prepareCall(callStoredProc);) {
                            
            callableStatement.setString(1, type); 
            callableStatement.setString(2, date);

            if(callableStatement.execute()) {
                ResultSet resultSet =  callableStatement.getResultSet(); 
                System.out.println("\nFacilitators leading on " + date + ": "); 

                while(resultSet.next()) {
                    System.out.println("\t" + resultSet.getString(1) + " " + resultSet.getString(2));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //USE CASE 5
    public void updateSessionTopic() {
        //ask for user input
        sc = new Scanner(System.in);
        System.out.println("Enter the following parameters for this use case: ");
        System.out.println("\tEnter type of session (Python or Arduino): "); 
        String type = sc.nextLine();  
        System.out.println("\tEnter a session date (YYYY-MM-DD): "); 
        String date = sc.nextLine(); 
        System.out.println("\tEnter a new title for this session: "); 
        String newTitle = sc.nextLine(); 

        String callStoredProc = "{call dbo.update_session_topic(?,?,?)}"; 

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement callableStatement = connection.prepareCall(callStoredProc);) {
            
            connection.setAutoCommit(false);
                
            callableStatement.setString(1, type); 
            callableStatement.setString(2, date);
            callableStatement.setString(3, newTitle);

            callableStatement.execute();
            connection.commit();
            System.out.println("Successfully updated " + type + " session on " + date + " to: " + newTitle);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //USE CASE 6
    public void insertNewStudent() {
        //ask for user input
        sc = new Scanner(System.in);
        System.out.println("Enter the following parameters for this use case: ");
        System.out.println("\tEnter the first name of the student: "); 
        String firstName = sc.nextLine();  
        System.out.println("\tEnter the last name of the student: "); 
        String lastName = sc.nextLine(); 
        System.out.println("\tEnter their grade level (freshman, sophomore, junior, senior): "); 
        String grade = sc.nextLine(); 
        System.out.println("\tEnter the name of their high school: "); 
        String highSchool = sc.nextLine(); 
        System.out.println("\tEnter the course they are enrolling in (Python or Arduino): "); 
        String courseType = sc.nextLine(); 
        System.out.println("\tEnter a semester (Fall or Spring): "); 
        String semester = sc.nextLine(); 
        System.out.println("\tEnter the semester year: "); 
        String year = sc.nextLine(); 

        String callStoredProc = "{call dbo.enroll_new_student(?,?,?,?,?,?,?)}"; 

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement callableStatement = connection.prepareCall(callStoredProc);) {
            
            connection.setAutoCommit(false);
                
            callableStatement.setString(1, firstName); 
            callableStatement.setString(2, lastName);
            callableStatement.setString(3, grade);
            callableStatement.setString(4, highSchool); 
            callableStatement.setString(5, courseType);
            callableStatement.setString(6, semester);
            callableStatement.setString(7, year);

            callableStatement.execute();
            connection.commit();
            System.out.println("Successfully added " + firstName + " " + lastName + " as a student for " + courseType + " sessions in " + semester + " " + year);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //TEST TRIGGER 
        //trigger updates exec member to be 'Co' + [PositionName] if two facilitators try to be added to the same position in the same academic year
    public void insertExecMember() {
        String callStoredProc = "{call dbo.insert_new_exec_member(?,?,?)}"; 

        sc = new Scanner(System.in);
        System.out.println("Enter the following parameters for this use case: ");
        System.out.println("\tEnter the academic year of that this facilitator will serve on exec: "); 
        String academicYear = sc.nextLine();
        //see what facilitators are currently non-exec members for that academic year
        getAllNonExecMembersForAcademicYear(academicYear);
        System.out.println("\n\tEnter the ID of the facilitator to be added (non-exec members shows in list above): "); 
        String facId = sc.nextLine(); 
        System.out.println("\tEnter the exec role for this facilitator: "); 
        String role = sc.nextLine(); 

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement callableStatement = connection.prepareCall(callStoredProc);) {
            
            connection.setAutoCommit(false);
                
            callableStatement.setString(1, role); 
            callableStatement.setString(2, facId);
            callableStatement.setString(3, academicYear);

            callableStatement.execute();
            connection.commit();
            System.out.println("Successfully made facilitator with ID " + facId + " to " + role + " for the  " + academicYear + " academic year.");
            getResultingExecMembers(academicYear);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //helper for testing trigger to get all non-exec members for the specified academic year
    private void getAllNonExecMembersForAcademicYear(String academicYear) {
        String callStoredProc = "{call dbo.list_all_non_exec_members(?)}"; 

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement callableStatement = connection.prepareCall(callStoredProc);) {
                            
            callableStatement.setString(1, academicYear); 

            if(callableStatement.execute()) {
                System.out.println("\n\tCurrent Facilitators with No Exec Positions for " + academicYear + ": ");
                System.out.println("\t\tID: Facilitator Name");
                ResultSet results = callableStatement.getResultSet();
                while(results.next()) {
                    System.out.println("\t\t" + results.getString(1) + ": " + results.getString(2) + " " + results.getString(3));
                } 
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //helper method for trigger that shows trigger updates exec_members correctly
    private void getResultingExecMembers(String academicYear) {
        String query = "SELECT * FROM exec_member"; 

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();
        ) {
            System.out.println("\n\tCurrent Exec Members for " + academicYear + ": ");
            System.out.println("\t\tFacilitator ID : Exec Position");

            while(results.next()) {
                if(results.getString(3).equals(academicYear)) {
                    System.out.println("\t\t" + results.getString(2) + " : " + results.getString(1));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //helper method for Use Case 2 that sees if there are multiple students with same name
    private int getStudentsWithNameCount(String firstName, String lastName) {
        String callStoredProc = "{call dbo.list_all_students_with_name(?,?)}"; 

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement callableStatement = connection.prepareCall(callStoredProc);) {
                            
            callableStatement.setString(1, firstName); 
            callableStatement.setString(2, lastName);

            if(callableStatement.execute()) {
                int countNames = 0; 
                while(callableStatement.getResultSet().next()) {
                    countNames++; 
                } 
                return countNames; 
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; 
    }

    //helper method for use case 2 that deletes a student based on id, not name
    private void deleteStudentWithId() {
        String callStoredProc = "{call dbo.drop_student_with_id(?, ?)}";
        sc = new Scanner(System.in); 

        try (Connection connection = DriverManager.getConnection(connectionUrl);
            CallableStatement callableStatement = connection.prepareCall(callStoredProc);) {
            
                System.out.println("\nEnter the ID of the student to delete: "); 
                String student_id = sc.nextLine(); 

                connection.setAutoCommit(false);
                callableStatement.setString(1, student_id);
                callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
                callableStatement.execute();
                connection.commit();

                System.out.println("Successfully removed student with ID " + callableStatement.getInt(2) + " from the database.");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}