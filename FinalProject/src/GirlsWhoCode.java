import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GirlsWhoCode {
    private String connectionUrl =
    "jdbc:sqlserver://cxp-sql-02\\abc123;" // change this
    + "database=university;" // change this perhaps
    + "user=dbuser;"
    + "password=WhateverItIs;" // change this
    + "encrypt=true;"
    + "trustServerCertificate=true;"
    + "loginTimeout=15;";

    //USE CASE 1
    public void facilitatorAttendance() {

    }

    //USE CASE 2
    public void popularHighSchools() {

    }

    //USE CASE 3
    public void curriculumBySemester() {

    }

    //USE CASE 4
    public void execMembers() {

    }

    //USE CASE 5
    public void weeklySessionLeaders() {

    }

    //USE CASE 6
    public void returningStudents() {

    }


    /*
     * "interactive" part
     *      ask for user input to what they want to do
     *      
     * method for each use case
     *      ask for user input for procedure inputs
     *      call stored procedures 
     */
}
