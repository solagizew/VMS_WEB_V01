/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms_web_v01;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author SOL-Laptop
 */
public class DBConnection {
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver"); // For MySQL 5.x; use `com.mysql.cj.jdbc.Driver` for MySQL 8.x
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
    }
}
