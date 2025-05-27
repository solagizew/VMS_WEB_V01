/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms_web_v01;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import java.sql.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlSeeAlso;
import vms_web_v01.VehicleInsuranceInfo;
import vms_web_v01.VehicleInspectionInfo;
import vms_web_v01.VehicleInsuranceExpiryInfo;
import vms_web_v01.VehicleInspectionExpiryInfo;
import java.util.Calendar;

/**
 *
 * @author SOL-Laptop
 */
@WebService(serviceName = "VMS_WEB_V01")
public class VMS_WEB_V01 {

      ResultSet rs;
      Statement st;
      Connection con;
      
    private void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
    try { if (rs != null) rs.close(); } catch (Exception e) {}
    try { if (stmt != null) stmt.close(); } catch (Exception e) {}
    try { if (conn != null) conn.close(); } catch (Exception e) {}
}
    private void closeQuietly(PreparedStatement pst, ResultSet rs) {
    try { if (rs != null) rs.close(); } catch (Exception e) {}
    try { if (pst != null) pst.close(); } catch (Exception e) {}
}
    /**
     * Web service operation
     */
    @WebMethod(operationName = "user_registration")
    public String user_registration(@WebParam(name = "id_number") String id_number, @WebParam(name = "f_name") String f_name, @WebParam(name = "l_name") String l_name, @WebParam(name = "department") String department, @WebParam(name = "job_title") String job_title, @WebParam(name = "email") String email, @WebParam(name = "mobile") int mobile, @WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "otp") int otp, @WebParam(name = "status") int status, @WebParam(name = "privilege") String privilege) {
        //TODO write your implementation code here:
               String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        PreparedStatement ts = con.prepareStatement("INSERT INTO user_account values(?,?,?,?,?,?,?,?,?,?,?,?)");
            ts.setString(1, id_number);
            ts.setString(2, f_name);
            ts.setString(3, l_name);
            ts.setString(4, department);
            ts.setString(5, job_title);
            ts.setString(6, email);
            ts.setInt(7, mobile);
            ts.setString(8, username);
            ts.setString(9, password);
            ts.setInt(10, otp);
            ts.setInt(11, status);
            ts.setString(12, privilege);
                       
            ts.executeUpdate();
            bool="1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }

    /**
     * Web service operation
     * @param id_number
     * @param password
     * @param username
     * @return 
     */
@WebMethod(operationName = "validate_user")
public String validate_user(
    @WebParam(name = "id_number") String id_number,
    @WebParam(name = "username") String username,
    @WebParam(name = "password") String password // hashed password
) {
    String bool = "0"; 
    String query = "SELECT * FROM user_account WHERE id_number = ? AND username = ? AND password = ?";
    
    try {
        Class.forName("com.mysql.jdbc.Driver"); // Load MySQL driver
        try (
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms", "root", "");
            PreparedStatement pst = conn.prepareStatement(query)
        ) {
            pst.setString(1, id_number);
            pst.setString(2, username);
            pst.setString(3, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    bool = "1";
                }
            }
        }
    } catch (Exception e) {
        System.out.println("validate_user error: " + e.getMessage());
    }
    return bool;
}


    /**
     * Web service operation
     */
    @WebMethod(operationName = "user_type")
    public String user_type(@WebParam(name = "id_number") String id_number, @WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String query = "SELECT * FROM user_account WHERE id_number='"+id_number+"'AND username='"+username+"'AND password='"+password+"'";
        rs = st.executeQuery(query);
  
        while(rs.next())
            {
            bool= rs.getString(12);          
            con.close();
            }        
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
/////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_users_profile")
    public ArrayList view_users_profile(@WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[12];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from user_account";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
////////////////////////////////////////////////////////////////////////////////
    
    @WebMethod(operationName = "search_user_account")
    public ArrayList profile(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[12];
        String  val= "0";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from user_account where id_number='"+id_number+"'";
            st.execute(sql);
            rs=st.getResultSet();
      while(rs.next()){
          rec[0]=rs.getString(1);
          rec[1]=rs.getString(2);
          rec[2]=rs.getString(3);
          rec[3]=rs.getString(4);
          rec[4]=rs.getString(5);
          rec[5]=rs.getString(6);
          rec[6]=rs.getString(7);
          rec[7]=rs.getString(8);
          rec[8]=rs.getString(9);
          rec[9]=rs.getString(10);
          rec[10]=rs.getString(11);
          rec[11]=rs.getString(12);
     
        for (int i = 0; i < 12; i++) {
        boolean add = a.add(rec[i]);
        val="1";
   }
      } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    }
    
    ////////////////////////////////////////////////////////////////////////////////
    
    @WebMethod(operationName = "search_vehicle")
    public ArrayList search_vehicle(@WebParam(name = "vehicle_license_no") String vehicle_license_no) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[8];
        String  val= "0";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle where vehicle_license_no='"+vehicle_license_no+"'";
            st.execute(sql);
            rs=st.getResultSet();
      while(rs.next()){
          rec[0]=rs.getString(1);
          rec[1]=rs.getString(2);
          rec[2]=rs.getString(3);
          rec[3]=rs.getString(4);
          rec[4]=rs.getString(5);
          rec[5]=rs.getString(6);
          rec[6]=rs.getString(7);
          rec[7]=rs.getString(8);
               
        for (int i = 0; i < 8; i++) {
        boolean add = a.add(rec[i]);
        val="1";
   }
      } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    }
    
 ////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "is_idnumber_exist")
    public String is_idnumber_exist(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String query = "SELECT id_number FROM user_account WHERE id_number='"+id_number+"'";
        rs = st.executeQuery(query);
  
        if (rs.next())
            {
            bool="1";
            }
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "is_email_exist")
    public String is_email_exist(@WebParam(name = "email") String email) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String query = "SELECT email FROM user_account WHERE email='"+email+"'";
        rs = st.executeQuery(query);
  
        if (rs.next())
            {
            bool="1";
            }
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "is_mobile_exist")
    public String is_mobile_exist(@WebParam(name = "mobile") String mobile) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String query = "SELECT mobile FROM user_account WHERE mobile='"+mobile+"'";
        rs = st.executeQuery(query);
  
        if (rs.next())
            {
            bool="1";
            }
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }    
    
////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "is_username_exist")
    public String is_username_exist(@WebParam(name = "username") String username) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String query = "SELECT username FROM user_account WHERE username='"+username+"'";
        rs = st.executeQuery(query);
  
        if (rs.next())
            {
            bool="1";
            }
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "is_driver_exist")
    public String is_driver_exist(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String query = "SELECT username FROM user_account WHERE id_number='"+id_number+"' and privilege='Driver'";
        rs = st.executeQuery(query);
  
        if (rs.next())
            {
            bool="1";
            }
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "is_vehicle_exist")
    public String is_vehicle_exist(@WebParam(name = "vehicle_license_no") String vehicle_license_no) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String query = "SELECT vehicle_license_no FROM vehicle WHERE vehicle_license_no='"+vehicle_license_no+"'";
        rs = st.executeQuery(query);
  
        if (rs.next())
            {
            bool="1";
            }
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
    @WebMethod(operationName = "user_profile_update")
    public String user_profile_update(@WebParam(name = "id_number") String id_number, @WebParam(name = "f_name") String f_name, @WebParam(name = "l_name") String l_name, @WebParam(name = "department") String department, @WebParam(name = "job_title") String job_title, @WebParam(name = "email") String email, @WebParam(name = "mobile") int mobile, @WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "status") int status, @WebParam(name = "privilege") String privilege) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String sql="Update user_account set f_name='" + (f_name)+ "', l_name='" + (l_name)+ "',department='" + (department)+ "', job_title='" + (job_title)+ "', email='" + (email)+ "',mobile='" + (mobile)+ "' ,username='" + (username)+ "' ,password='" + (password)+ "',status='" + (status)+ "' ,privilege='" + (privilege)+ "' where id_number = '" + (id_number) + "'";
        st.executeUpdate(sql);
        bool = "1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    @WebMethod(operationName = "vehicle_registration")
    public String vehicle_registration(@WebParam(name = "vehicle_license_no") String vehicle_license_no, @WebParam(name = "vehicle_type") String vehicle_type, @WebParam(name = "initial_distance_covered") int initial_distance_covered, @WebParam(name = "current_distance_covered") int current_distance_covered, @WebParam(name = "total_distance_covered") int total_distance_covered, @WebParam(name = "calibration") float calibration, @WebParam(name = "assigned_driver_id") String assigned_driver_id, @WebParam(name = "driver_name") String driver_name) {
        //TODO write your implementation code here:
        
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        PreparedStatement ts = con.prepareStatement("INSERT INTO vehicle values(?,?,?,?,?,?,?,?)");
            ts.setString(1, vehicle_license_no);
            ts.setString(2, vehicle_type);
            ts.setInt(3, initial_distance_covered);
            ts.setInt(4, current_distance_covered);
            ts.setInt(5, total_distance_covered);            
            ts.setFloat(6, calibration);
            ts.setString(7, assigned_driver_id);
            ts.setString(8, driver_name);
                      
            ts.executeUpdate();
            bool="1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
////////////////////////////////////////////////////////////////////////////////
    
    @WebMethod(operationName = "search_driver_name")
    public ArrayList search_driver_name(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[7];
        String  val= "0";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select f_name from user_account where id_number='"+id_number+"' and privilege='Driver'";
            st.execute(sql);
            rs=st.getResultSet();
      while(rs.next()){
          rec[0]=rs.getString(1);
                         
        for (int i = 0; i < 1; i++) {
        boolean add = a.add(rec[i]);
        val="1";
   }
      } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    }
    
//////////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "vehicle_update")
    public String vehicle_update(@WebParam(name = "vehicle_license_no") String vehicle_license_no, @WebParam(name = "vehicle_type") String vehicle_type, @WebParam(name = "total_distance_covered") int total_distance_covered, @WebParam(name = "calibration") float calibration, @WebParam(name = "assigned_driver_id") String assigned_driver_id, @WebParam(name = "driver_name") String driver_name) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String sql="Update vehicle set vehicle_type='" + (vehicle_type)+ "', total_distance_covered='" + (total_distance_covered)+ "', calibration='" + (calibration)+ "', assigned_driver_id='" + (assigned_driver_id)+ "',driver_name='" + (driver_name)+ "' where vehicle_license_no = '" + (vehicle_license_no) + "'";
        st.executeUpdate(sql);
        bool = "1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    ////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "is_user_active")
    public String is_user_active(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String query = "SELECT * FROM user_account WHERE id_number='"+id_number+"' and status='1'";
        rs = st.executeQuery(query);
  
        if (rs.next())
            {
            bool="1";
            }
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
  ////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "vehicle_request")
    public String vehicle_request(@WebParam(name = "id_number") String id_number, @WebParam(name = "department") String department, @WebParam(name = "no_of_traveler") int no_of_traveler, @WebParam(name = "travel_destination") String travel_destination, @WebParam(name = "dt_of_request") String dt_of_request, @WebParam(name = "date_of_travel") String date_of_travel, @WebParam(name = "time_of_travel") String time_of_travel) {
        //TODO write your implementation code here:
        String assigned_vehicle_plate="";
            String assigned_driver_idno="";
            String driver_name = "";
            String status="Pending";
            String remark="";
            String dt_of_ar_made="";
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        PreparedStatement ts = con.prepareStatement("INSERT INTO vehicle_request values(default,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ts.setString(1, id_number);
            ts.setString(2, department);
            ts.setInt(3, no_of_traveler);
            ts.setString(4, travel_destination);
            ts.setString(5, dt_of_request);
            ts.setString(6, date_of_travel);
            ts.setString(7, time_of_travel);
            ts.setString(8, assigned_vehicle_plate);
            ts.setString(9, assigned_driver_idno);
            ts.setString(10, driver_name);
            ts.setString(11, status);
            ts.setString(12, remark);
            ts.setString(13, dt_of_ar_made);
                                             
            ts.executeUpdate();
            bool="1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    //////////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "update_vehicle_request")
    public String update_vehicle_request(@WebParam(name = "request_id") int request_id, @WebParam(name = "id_number") String id_number, @WebParam(name = "department") String department, @WebParam(name = "no_of_traveler") int no_of_traveler, @WebParam(name = "travel_destination") String travel_destination, @WebParam(name = "dt_of_request") String dt_of_request, @WebParam(name = "date_of_travel") String date_of_travel, @WebParam(name = "time_of_travel") String time_of_travel) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String sql="Update vehicle_request set department='" + (department)+ "', no_of_traveler='" + (no_of_traveler)+ "', travel_destination='" + (travel_destination)+ "',dt_of_request='" + (dt_of_request)+ "',date_of_travel='" + (date_of_travel)+ "',time_of_travel='" + (time_of_travel)+ "' where id_number = '" + (id_number) + "' AND request_id = '" + (request_id) + "'";
        st.executeUpdate(sql);
        bool = "1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    

     ////////////////////////////////////////////////////////////////////////////////
    
    @WebMethod(operationName = "search_vehicle_request")
    public ArrayList search_vehicle_request(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[8];
        String  val= "0";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select request_id, department, no_of_traveler, travel_destination, dt_of_request, date_of_travel, time_of_travel, status from vehicle_request where id_number='"+id_number+"' and status='Pending'";
            st.execute(sql);
            rs=st.getResultSet();
      while(rs.next()){
          rec[0]=rs.getString(1);
          rec[1]=rs.getString(2);
          rec[2]=rs.getString(3);
          rec[3]=rs.getString(4);     
          rec[4]=rs.getString(5);
          rec[5]=rs.getString(6);
          rec[6]=rs.getString(7); 
          rec[7]=rs.getString(8); 
               
        for (int i = 0; i < 8; i++) {
        boolean add = a.add(rec[i]);
        val="1";
   }
      } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "cancel_vehicle_request")
    public String cancel_vehicle_request(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String sql="Update vehicle_request set status='Canceled by User' where id_number = '" + (id_number) + "' and status='Pending'";
        st.executeUpdate(sql);
        bool = "1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    ////////////////////////////////////////////////////////////////////////////////
    
    @WebMethod(operationName = "search_pending_vehicle_request_byid")
    public ArrayList search_pending_vehicle_request_byid(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[14];
        String  val= "0";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where id_number='"+id_number+"' and status='Pending'";
            st.execute(sql);
            rs=st.getResultSet();
      while(rs.next()){
          rec[0]=rs.getString(1);
          rec[1]=rs.getString(2);
          rec[2]=rs.getString(3);
          rec[3]=rs.getString(4);
          rec[4]=rs.getString(5);
          rec[5]=rs.getString(6);
          rec[6]=rs.getString(7);
          rec[7]=rs.getString(8);
          rec[8]=rs.getString(9);
          rec[9]=rs.getString(10);
          rec[10]=rs.getString(11);
          rec[11]=rs.getString(12);
          rec[12]=rs.getString(13);
          rec[13]=rs.getString(14);
     
        for (int i = 0; i < 14; i++) {
        boolean add = a.add(rec[i]);
        val="1";
   }
      } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_all_vehicle_request")
    public ArrayList view_all_vehicle_request(@WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_pending_vehicle_request")
    public ArrayList view_pending_vehicle_request(@WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where status='Pending'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_approved_vehicle_request")
    public ArrayList view_approved_vehicle_request(@WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where status='Approved'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
     /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_rejected_vehicle_request")
    public ArrayList view_rejected_vehicle_request(@WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where status='Rejected'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_trip_closed_vehicle_request")
    public ArrayList view_trip_closed_vehicle_request(@WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where status='Trip Closed'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_canceled_vehicle_request")
    public ArrayList view_canceled_vehicle_request(@WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where status='Canceled by User'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_vehicle")
    public ArrayList view_vehicle(@WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[7];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    ////////////////////////////////////////////////////////////////////////////////
    
    @WebMethod(operationName = "search_driver_id_name")
    public ArrayList search_driver_id_name(@WebParam(name = "vehicle_license_no") String vehicle_license_no) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        String  val= "0";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select assigned_driver_id, driver_name from vehicle where vehicle_license_no='"+vehicle_license_no+"'";
            st.execute(sql);
            rs=st.getResultSet();
      while(rs.next()){
          rec[0]=rs.getString(1);
          rec[1]=rs.getString(2);
               
        for (int i = 0; i < 2; i++) {
        boolean add = a.add(rec[i]);
        val="1";
   }
      } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "respond_on_vehicle_request")
    public String respond_on_vehicle_request(@WebParam(name = "id_number") String id_number, @WebParam(name = "assigned_vehicle_plate") String assigned_vehicle_plate, @WebParam(name = "assigned_driver_idno") String assigned_driver_idno, @WebParam(name = "driver_name") String driver_name, @WebParam(name = "status") String status, @WebParam(name = "remark") String remark, @WebParam(name = "dt_of_ar_made") String dt_of_ar_made) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String sql="Update vehicle_request set assigned_vehicle_plate='" + (assigned_vehicle_plate)+ "', assigned_driver_idno='" + (assigned_driver_idno)+ "', driver_name='" + (driver_name)+ "',status='" + (status)+ "',remark='" + (remark)+ "',dt_of_ar_made='" + (dt_of_ar_made)+ "' where id_number = '" + (id_number) + "' AND status = 'Pending'";
        st.executeUpdate(sql);
        bool = "1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
     ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "is_user_have_pending_request")
    public String is_user_have_pending_request(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String query = "SELECT * FROM vehicle_request WHERE id_number='"+id_number+"' and status='Pending'";
        rs = st.executeQuery(query);
  
        if (rs.next())
            {
            bool="1";
            }
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_user_vehicle_request")
    public ArrayList view_user_vehicle_request(@WebParam(name = "id_number") String id_number, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where id_number='"+id_number+"'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_user_pending_vehicle_request")
    public ArrayList view_user_pending_vehicle_request(@WebParam(name = "id_number") String id_number, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where id_number='"+id_number+"' and status='Pending'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_user_approved_vehicle_request")
    public ArrayList view_user_approved_vehicle_request(@WebParam(name = "id_number") String id_number, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where id_number='"+id_number+"' and status='Approved'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_user_rejected_vehicle_request")
    public ArrayList view_user_rejected_vehicle_request(@WebParam(name = "id_number") String id_number, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where id_number='"+id_number+"' and status='Rejected'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_user_trip_closed_vehicle_request")
    public ArrayList view_user_trip_closed_vehicle_request(@WebParam(name = "id_number") String id_number, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where id_number='"+id_number+"' and status='Trip Closed'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
        /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_user_canceled_vehicle_request")
    public ArrayList view_user_canceled_vehicle_request(@WebParam(name = "id_number") String id_number, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where id_number='"+id_number+"' and status='Canceled by User'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_driver")
    public ArrayList view_driver(@WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[7];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from user_account where privilege='Driver'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    ////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "is_driver_assigned")
    public String is_driver_assigned(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String query = "SELECT * FROM vehicle WHERE assigned_driver_id='"+id_number+"'";
        rs = st.executeQuery(query);
  
        if (rs.next())
            {
            bool="1";
            }
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "recover_password_update")
    public String recover_password_update(@WebParam(name = "id_number") String id_number, @WebParam(name = "password") String password) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String sql="Update user_account set password='" + (password)+ "' where id_number = '" + (id_number) + "'";
        st.executeUpdate(sql);
        bool = "1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_username")
    public ArrayList view_username(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[1];
        int x=0;
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select username from user_account where id_number='"+id_number+"'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "driver_view_vehicle")
    public ArrayList driver_view_vehicle(@WebParam(name = "id_number") String id_number) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[8];
        String  val= "0";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle where assigned_driver_id='"+id_number+"'";
            st.execute(sql);
            rs=st.getResultSet();
      while(rs.next()){
          rec[0]=rs.getString(1);
          rec[1]=rs.getString(2);
          rec[2]=rs.getString(3);
          rec[3]=rs.getString(4);
          rec[4]=rs.getString(5);
          rec[5]=rs.getString(6);
          rec[6]=rs.getString(7);
          rec[7]=rs.getString(8);
     
        for (int i = 0; i < 8; i++) {
        boolean add = a.add(rec[i]);
        val="1";
   }
      } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_approved_vehicle_4_trip_close")
    public ArrayList view_approved_vehicle_4_trip_close(@WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[6];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select request_id, assigned_vehicle_plate, id_number, assigned_driver_idno, date_of_travel, time_of_travel from vehicle_request where status='Approved'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    ////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_approved_vehicle_by_plate")
    public ArrayList view_approved_vehicle_by_plate(@WebParam(name = "assigned_vehicle_plate") String assigned_vehicle_plate, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[5];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select assigned_vehicle_plate, id_number, assigned_driver_idno, date_of_travel, time_of_travel from vehicle_request where status='Approved' and assigned_vehicle_plate='"+assigned_vehicle_plate+"'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    ////////////////////////////////////////////////////////////////////////////////
    
    @WebMethod(operationName = "search_current_distance")
    public ArrayList search_current_distance(@WebParam(name = "vehicle_license_no") String vehicle_license_no) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[1];
        String  val= "0";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select current_distance_covered from vehicle where vehicle_license_no='"+vehicle_license_no+"'";
            st.execute(sql);
            rs=st.getResultSet();
      while(rs.next()){
          rec[0]=rs.getString(1);
    
        for (int i = 0; i < 1; i++) {
        boolean add = a.add(rec[i]);
        val="1";
   }
      } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    }
    
    ////////////////////////////////////////////////////////////////////////////////
    
    @WebMethod(operationName = "search_calibration")
    public ArrayList search_calibration(@WebParam(name = "vehicle_license_no") String vehicle_license_no) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[1];
        String  val= "0";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select calibration from vehicle where vehicle_license_no='"+vehicle_license_no+"'";
            st.execute(sql);
            rs=st.getResultSet();
      while(rs.next()){
          rec[0]=rs.getString(1);
    
        for (int i = 0; i < 1; i++) {
        boolean add = a.add(rec[i]);
        val="1";
   }
      } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "vehicle_current_read_update")
    public String vehicle_current_read_update(@WebParam(name = "vehicle_license_no") String vehicle_license_no, @WebParam(name = "current_distance_covered") int current_distance_covered) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String sql="Update vehicle set current_distance_covered='" + (current_distance_covered)+ "' where vehicle_license_no = '" + (vehicle_license_no) + "'";
        st.executeUpdate(sql);
        bool = "1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "update_tripclose_status")
    public String update_tripclose_status(@WebParam(name = "assigned_vehicle_plate") String assigned_vehicle_plate, @WebParam(name = "id_number") String id_number, @WebParam(name = "date_of_travel") String date_of_travel, @WebParam(name = "time_of_travel") String time_of_travel, @WebParam(name = "status") String status) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String sql="Update vehicle_request set status='" + (status)+ "' where assigned_vehicle_plate='" + (assigned_vehicle_plate)+ "' AND date_of_travel='" + (date_of_travel)+ "' AND time_of_travel='" + (time_of_travel)+ "' AND id_number = '" + (id_number) + "' AND status='Approved'";
        st.executeUpdate(sql);
        bool = "1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_all_trip_close")
    public ArrayList view_all_trip_close(@WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[14];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from trip_close where trip_status='Trip Closed'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
     //////////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "update_tripclose")
    public String update_tripclose(@WebParam(name = "vehicle_license_no") String vehicle_license_no, @WebParam(name = "requester_id_number") String requester_id_number, @WebParam(name = "driver_id_number") String driver_id_number, @WebParam(name = "date_of_travel") String date_of_travel, @WebParam(name = "time_of_travel") String time_of_travel, @WebParam(name = "date_of_return") String date_of_return, @WebParam(name = "time_of_return") String time_of_return, @WebParam(name = "start_KM_read") int start_KM_read, @WebParam(name = "return_KM_read") int return_KM_read, @WebParam(name = "total_KM") int total_KM, @WebParam(name = "total_fuels_consumed") float total_fuels_consumed, @WebParam(name = "trip_status") String trip_status, @WebParam(name = "trip_closed_by") String trip_closed_by){ 
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String sql="Update trip_close set start_KM_read='" + (start_KM_read)+ "', return_KM_read='" + (return_KM_read)+ "',"
                + "total_KM='" + (total_KM)+ "', "
                + "total_fuels_consumed='" + (total_fuels_consumed)+ "', "
                + "trip_status='" + (trip_status)+ "', "
                + "trip_closed_by='" + (trip_closed_by)+ "' where vehicle_license_no='" + (vehicle_license_no)+ "' AND date_of_travel='" + (date_of_travel)+ "' AND time_of_travel='" + (time_of_travel)+ "' AND requester_id_number = '" + (requester_id_number) + "'";
                       
        st.executeUpdate(sql);
        bool = "1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
    ////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_approved_vehicle_4_trip_close_bydriver")
    public ArrayList view_approved_vehicle_4_trip_close_bydriver(@WebParam(name = "assigned_driver_idno") String assigned_driver_idno, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[6];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select request_id, assigned_vehicle_plate, id_number, assigned_driver_idno, date_of_travel, time_of_travel from vehicle_request where status='Approved' and assigned_driver_idno='"+assigned_driver_idno+"'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    ///////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_all_trip_close_bydriver")
    public ArrayList view_all_trip_close_bydriver(@WebParam(name = "assigned_driver_idno") String assigned_driver_idno, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from trip_close where trip_status='Trip Closed' and driver_id_number='"+assigned_driver_idno+"'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_approved_vehicle_request_bydriver")
    public ArrayList view_approved_vehicle_request_bydriver(@WebParam(name = "assigned_driver_idno") String assigned_driver_idno, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where assigned_driver_idno='"+assigned_driver_idno+"' and status='Approved'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_all_vehicle_request_bydriver")
    public ArrayList view_all_vehicle_request_bydriver(@WebParam(name = "assigned_driver_idno") String assigned_driver_idno, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where (assigned_driver_idno='"+assigned_driver_idno+"' and status='Approved') or (assigned_driver_idno='"+assigned_driver_idno+"' and status='Trip Closed')";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_tripclosed_vehicle_request_bydriver")
    public ArrayList view_tripclosed_vehicle_request_bydriver(@WebParam(name = "assigned_driver_idno") String assigned_driver_idno, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from vehicle_request where assigned_driver_idno='"+assigned_driver_idno+"' and status='Trip Closed'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
    ////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_not_approved_trip_close_byuser")
    public ArrayList view_not_approved_trip_close_byuser(@WebParam(name = "id_number") String id_number, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[13];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select vehicle_license_no, requester_id_number, driver_id_number, date_of_travel, time_of_travel, date_of_return, time_of_return, start_KM_read, return_KM_read, total_KM, total_fuels_consumed, trip_status, trip_closed_by from trip_close where requester_id_number='"+id_number+"' AND final_remarks IS NULL";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    ////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_approved_trip_close_byuser")
    public ArrayList view_approved_trip_close_byuser(@WebParam(name = "id_number") String id_number, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[14];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from trip_close where requester_id_number ='"+id_number+"' AND final_remarks IS NOT NULL";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return a;
    } 
    
     //////////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "update_tripclose_byuser")
    public String update_tripclose_byuser(@WebParam(name = "final_remarks") String final_remarks, @WebParam(name = "vehicle_license_no") String vehicle_license_no, @WebParam(name = "date_of_travel") String date_of_travel, @WebParam(name = "time_of_travel") String time_of_travel, @WebParam(name = "requester_id_number") String requester_id_number){ 
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String sql="Update trip_close set final_remarks='"+(final_remarks)+"' where vehicle_license_no='" + (vehicle_license_no)+ "' AND date_of_travel='" + (date_of_travel)+ "' AND time_of_travel='" + (time_of_travel)+ "' AND requester_id_number = '" + (requester_id_number) + "'";
                       
        st.executeUpdate(sql);
        bool = "1";
        con.close();
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
    
     /////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "view_fuel_filled")
    public ArrayList view_fuel_filled(@WebParam(name = "vehicle_license_no") String vehicle_license_no, @WebParam(name = "x") int x) {
        //TODO write your implementation code here:
        ArrayList a=new ArrayList();
        String[] rec=new String[8];
        try{
            ResultSetMetaData rsmd;
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
            st=con.createStatement();
            String sql="Select * from fuel_filling where vehicle_license_no='"+vehicle_license_no+"'";
            st.execute(sql);
            rs=st.getResultSet();
            rsmd=rs.getMetaData();
      while(rs.next()){
      a.add(rs.getObject(x+1));
         } 
      con.close();
        }
        catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    } finally {
        try { if (con != null) con.close(); } catch (Exception ex) { /* ignore */ }
    }     
        return a;
    } 
    
    ////////////////////////////////////////////////////////////////////////////
   @WebMethod(operationName = "fuel_filling")
    public String fuel_filling(@WebParam(name = "vehicle_license_no") String vehicle_license_no,
    @WebParam(name = "fuel_type") String fuel_type,
    @WebParam(name = "date_of_fuel_filled") String date_of_fuel_filled,
    @WebParam(name = "amount_of_fuel_in_liter") float amount_of_fuel_in_liter,
    @WebParam(name = "price_per_liter") float price_per_liter,
    @WebParam(name = "total_price") float total_price,
    @WebParam(name = "KM_read_when_fuel_filled") int KM_read_when_fuel_filled,
    @WebParam(name = "receipt_number") String receipt_number){
    
    String bool = "0";
   
    try {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        
        String sql = "INSERT INTO fuel_filling VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, vehicle_license_no);
        ps.setString(2, fuel_type);
        ps.setString(3, date_of_fuel_filled);
        ps.setFloat(4, amount_of_fuel_in_liter);
        ps.setFloat(5, price_per_liter);
        ps.setFloat(6, total_price);
        ps.setInt(7, KM_read_when_fuel_filled);
        ps.setString(8, receipt_number);

        ps.executeUpdate();
        bool = "1";
        } 
    catch (Exception e) 
        {
        System.out.println("Error: " + e.getMessage());
        } 
    finally 
        {
        try { if (con != null) con.close(); } catch (Exception ex) { /* ignore */ }
        }
    return bool;
}
    //////////////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "update_fuel_filling")
    public String update_fuel_filling(
    @WebParam(name = "fuel_filling_id") int fuel_filling_id,
    @WebParam(name = "vehicle_license_no") String vehicle_license_no,
    @WebParam(name = "fuel_type") String fuel_type,
    @WebParam(name = "date_of_fuel_filled") String date_of_fuel_filled,
    @WebParam(name = "amount_of_fuel_in_liter") float amount_of_fuel_in_liter,
    @WebParam(name = "price_per_liter") float price_per_liter,
    @WebParam(name = "total_price") float total_price,
    @WebParam(name = "KM_read_when_fuel_filled") int KM_read_when_fuel_filled,
    @WebParam(name = "receipt_number") String receipt_number) {

    String status = "0";
    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms", "root", "");

        String sql = "UPDATE fuel_filling SET vehicle_license_no = ?, fuel_type = ?, date_of_fuel_filled = ?, " +
                     "amount_of_fuel_in_liter = ?, price_per_liter = ?, total_price = ?, " +
                     "KM_read_when_fuel_filled = ?, receipt_number = ? " +
                     "WHERE fuel_filling_id = '"+fuel_filling_id+"'";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, vehicle_license_no);
        ps.setString(2, fuel_type);
        ps.setString(3, date_of_fuel_filled);
        ps.setFloat(4, amount_of_fuel_in_liter);
        ps.setFloat(5, price_per_liter);
        ps.setFloat(6, total_price);
        ps.setInt(7, KM_read_when_fuel_filled);
        ps.setString(8, receipt_number);

        int result = ps.executeUpdate();
        if (result > 0) {
            status = "1";
        }
        ps.close();
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return status;
}
 ////////////////////////////////////////////////////////////////////////////
    @WebMethod(operationName = "close_trip")
    public String close_trip(@WebParam(name = "vehicle_license_no") String vehicle_license_no, @WebParam(name = "requester_id_number") String requester_id_number, @WebParam(name = "driver_id_number") String driver_id_number, @WebParam(name = "date_of_travel") String date_of_travel, @WebParam(name = "time_of_travel") String time_of_travel, @WebParam(name = "date_of_return") String date_of_return, @WebParam(name = "time_of_return") String time_of_return, @WebParam(name = "start_KM_read") int start_KM_read, @WebParam(name = "return_KM_read") int return_KM_read, @WebParam(name = "total_KM") int total_KM, @WebParam(name = "total_fuels_consumed") float total_fuels_consumed, @WebParam(name = "trip_status") String trip_status, @WebParam(name = "trip_closed_by") String trip_closed_by){ 
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        PreparedStatement ts = con.prepareStatement("INSERT INTO trip_close values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ts.setString(1, vehicle_license_no);
            ts.setString(2, requester_id_number);
            ts.setString(3, driver_id_number);
            ts.setString(4, date_of_travel);
            ts.setString(5, time_of_travel);
            ts.setString(6, date_of_return);
            ts.setString(7, time_of_return);
            ts.setInt(8, start_KM_read);
            ts.setInt(9, return_KM_read);
            ts.setInt(10, total_KM);
            ts.setFloat(11, total_fuels_consumed);
            ts.setString(12, trip_status);
            ts.setString(13, trip_closed_by);
                                             
            ts.executeUpdate();
            bool="1";
        con.close();
            }
        catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    } finally {
        try { if (con != null) con.close(); } catch (Exception ex) { /* ignore */ }
    }       
        return bool;
    }

    //////////////////////////////////////AI Jobs //////////////////////////
    @WebMethod
    public String closeTripAndLogFuel(
        String vehicleLicenseNo,
        String requesterId,
        String driverId,
        String dateOfTravel,
        String timeOfTravel,
        String dateOfReturn,
        String timeOfReturn,
        int startKM,
        int returnKM,
        String tripStatus,
        String tripClosedBy,
        String remarks
    ) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            int totalKM = returnKM - startKM;

            // 1. Get calibration
            String getCalibrationSql = "SELECT calibration FROM vehicle WHERE vehicle_license_no = ?";
            PreparedStatement ps1 = conn.prepareStatement(getCalibrationSql);
            ps1.setString(1, vehicleLicenseNo);
            ResultSet rs1 = ps1.executeQuery();

            double calibration = 0;
            if (rs1.next()) {
                calibration = rs1.getDouble("calibration");
            } else {
                throw new SQLException("Calibration not found for vehicle " + vehicleLicenseNo);
            }
            rs1.close();
            ps1.close();

            // 2. Get total fuel filled
            String fuelQuery = "SELECT SUM(amount_of_fuel_in_liter) AS total_fuel FROM fuel_filling WHERE vehicle_license_no = ? AND KM_read_when_fuel_filled <= ?";
            PreparedStatement ps2 = conn.prepareStatement(fuelQuery);
            ps2.setString(1, vehicleLicenseNo);
            ps2.setInt(2, returnKM);
            ResultSet rs2 = ps2.executeQuery();

            double totalFuelFilled = 0;
            if (rs2.next() && rs2.getBigDecimal("total_fuel") != null) {
                totalFuelFilled = rs2.getDouble("total_fuel");
            }
            rs2.close();
            ps2.close();

            // 3. Calculate fuel used and remaining
            double fuelUsed = totalKM * calibration;
            double remainingFuel = totalFuelFilled - fuelUsed;

            // 4. Insert into trip_close (without specifying trip_close_id)
            String insertTripClose = "INSERT INTO trip_close (vehicle_license_no, requester_id_number, driver_id_number, date_of_travel, time_of_travel, date_of_return, time_of_return, start_KM_read, return_KM_read, total_KM, total_fuels_consumed, trip_status, trip_closed_by, final_remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps3 = conn.prepareStatement(insertTripClose, Statement.RETURN_GENERATED_KEYS);
            ps3.setString(1, vehicleLicenseNo);
            ps3.setString(2, requesterId);
            ps3.setString(3, driverId);
            ps3.setString(4, dateOfTravel);
            ps3.setString(5, timeOfTravel);
            ps3.setString(6, dateOfReturn);
            ps3.setString(7, timeOfReturn);
            ps3.setInt(8, startKM);
            ps3.setInt(9, returnKM);
            ps3.setInt(10, totalKM);
            ps3.setDouble(11, fuelUsed);
            ps3.setString(12, tripStatus);
            ps3.setString(13, tripClosedBy);
            ps3.setString(14, remarks);
            ps3.executeUpdate();

            // Get the auto-generated trip_close_id
            ResultSet generatedKeys = ps3.getGeneratedKeys();
            int generatedTripCloseId = 0;
            if (generatedKeys.next()) {
                generatedTripCloseId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve trip_close_id.");
            }
            generatedKeys.close();
            ps3.close();

            // 5. Insert into fuel_consumption_log
            String insertFuelLog = "INSERT INTO fuel_consumption_log (trip_close_id, vehicle_license_no, total_fuel_filled, fuel_used, remaining_fuel) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps4 = conn.prepareStatement(insertFuelLog);
            ps4.setInt(1, generatedTripCloseId);
            ps4.setString(2, vehicleLicenseNo);
            ps4.setDouble(3, totalFuelFilled);
            ps4.setDouble(4, fuelUsed);
            ps4.setDouble(5, remainingFuel);
            ps4.executeUpdate();
            ps4.close();

            conn.commit();
            //return "Trip closed successfully. Trip ID: " + generatedTripCloseId;
            return "1";

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                return "0";
            }
            return "0";
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException e) {
                // Silent close
            }
        }
    }

    private Connection getConnection() throws SQLException {
        // Example connection setup — replace with your own
        //Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/eas_vms";
        String user = "root";
        String pass = "";
        return DriverManager.getConnection(url, user, pass);
    }
       
    //////////////////////////////////////Report Generating Sample//////////////////////////
 /*   
    @WebMethod
public String generateReport(@WebParam(name = "reportType") String reportType) {
    StringBuilder report = new StringBuilder();

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms", "root", "");

        String query = "";
        if ("vehicle_logs".equalsIgnoreCase(reportType)) {
            query = "SELECT * FROM vehicle_logs";
        } else if ("fuel_logs".equalsIgnoreCase(reportType)) {
            query = "SELECT * FROM fuel_filling";
        } else if ("trip_close".equalsIgnoreCase(reportType)) {
            query = "SELECT * FROM trip_close";
        } else if ("user_account".equalsIgnoreCase(reportType)) {
            query = "SELECT * FROM user_account";
        } else if ("vehicle_request".equalsIgnoreCase(reportType)) {
            query = "SELECT * FROM vehicle_request";
        } else {
            return "Invalid report type.";
        }

        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        // Estimate column widths
        int[] colWidths = new int[columnCount];
        String[][] data = new String[1000][columnCount]; // up to 1000 rows max
        int rowCount = 0;

        // Save data to memory and compute max width
        while (rs.next()) {
            for (int i = 0; i < columnCount; i++) {
                String val = rs.getString(i + 1);
                if (val == null) val = "NULL";
                data[rowCount][i] = val;
                if (val.length() > colWidths[i]) {
                    colWidths[i] = val.length();
                }
            }
            rowCount++;
        }

        // Include header width
        for (int i = 0; i < columnCount; i++) {
            int headerLength = meta.getColumnLabel(i + 1).length();
            if (headerLength > colWidths[i]) {
                colWidths[i] = headerLength;
            }
        }

        // Print header
        for (int i = 0; i < columnCount; i++) {
            String header = meta.getColumnLabel(i + 1);
            report.append(padRight(header, colWidths[i] + 2));
        }
        report.append("\n");

        // Separator
        for (int i = 0; i < columnCount; i++) {
            report.append(padRight("-", colWidths[i] + 2).replace(" ", "-"));
        }
        report.append("\n");

        // Print data
        for (int r = 0; r < rowCount; r++) {
            for (int i = 0; i < columnCount; i++) {
                report.append(padRight(data[r][i], colWidths[i] + 2));
            }
            report.append("\n");
        }

    } catch (Exception e) {
        return "Error: " + e.getMessage();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (stmt != null) stmt.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    return report.toString();
}

// Helper for right padding
private String padRight(String text, int width) {
    StringBuilder sb = new StringBuilder(text);
    while (sb.length() < width) {
        sb.append(' ');
    }
    return sb.toString();
}
*/
    /////////////////////////////////Fuel gage//////////////////////////////////////////////////
    @WebMethod
public List<FuelStatus> getFuelStatusList() {
    List<FuelStatus> list = new ArrayList<FuelStatus>();
    try {
        Class.forName("com.mysql.jdbc.Driver");

        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/eas_vms", "root", ""
        );

        String sql = "SELECT vehicle_license_no, remaining_fuel FROM fuel_consumption_log";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            FuelStatus fs = new FuelStatus();
            fs.setLicenseNo(rs.getString("vehicle_license_no"));
            fs.setRemainingFuel(rs.getInt("remaining_fuel"));
            list.add(fs);
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    /////////////////////////////////////////////////////////////////////////////
    @WebMethod
    public List<VehicleFuelInfo> getVehicleFuelInfoList() {
        List<VehicleFuelInfo> fuelInfoList = new ArrayList<VehicleFuelInfo>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/eas_vms", "root", "");

            String query = "SELECT f.vehicle_license_no, f.remaining_fuel, f.total_fuel_filled, " +
               "IFNULL(SUM(t.total_KM), 0) AS total_distance " +
               "FROM fuel_consumption_log f " +
               "LEFT JOIN trip_close t ON f.vehicle_license_no = t.vehicle_license_no " +
               "GROUP BY f.vehicle_license_no, f.remaining_fuel, f.total_fuel_filled";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VehicleFuelInfo fuelInfo = new VehicleFuelInfo();
                fuelInfo.setVehicleLicenseNo(resultSet.getString("vehicle_license_no"));
                fuelInfo.setFuelRemainingPercent(resultSet.getInt("remaining_fuel"));
                fuelInfo.setFuelInLiters(resultSet.getDouble("total_fuel_filled"));
                fuelInfo.setTotalKmTravelled(resultSet.getDouble("total_distance"));
                fuelInfoList.add(fuelInfo);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fuelInfoList;
    }

    // Inner static class
    public static class VehicleFuelInfo implements Serializable {
        private String vehicleLicenseNo;
        private int fuelRemainingPercent;
        private double fuelInLiters;
        private double totalKmTravelled;

        public VehicleFuelInfo() {
        }

        public String getVehicleLicenseNo() {
            return vehicleLicenseNo;
        }

        public void setVehicleLicenseNo(String vehicleLicenseNo) {
            this.vehicleLicenseNo = vehicleLicenseNo;
        }

        public int getFuelRemainingPercent() {
            return fuelRemainingPercent;
        }

        public void setFuelRemainingPercent(int fuelRemainingPercent) {
            this.fuelRemainingPercent = fuelRemainingPercent;
        }

        public double getFuelInLiters() {
            return fuelInLiters;
        }

        public void setFuelInLiters(double fuelInLiters) {
            this.fuelInLiters = fuelInLiters;
        }

        public double getTotalKmTravelled() {
            return totalKmTravelled;
        }

        public void setTotalKmTravelled(double totalKmTravelled) {
            this.totalKmTravelled = totalKmTravelled;
        }
    }
 //////////////////////////////////////////////////////////////////////////////
    @WebMethod
public String isVehicleTravelTimeAvailable(String dateOfTravel, String timeOfTravel) {
    String result = "AVAILABLE";
    try {
        Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/eas_vms", "root", "");
        String query = "SELECT * FROM vehicle_request " +
                       "WHERE date_of_travel = ? AND time_of_travel = ? " +
                       "AND status = 'Approved'";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, dateOfTravel);
        pst.setString(2, timeOfTravel);
        
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            result = "CONFLICT"; // There is an approved request at the same time
        }
        rs.close();
        pst.close();
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
        result = "ERROR";
    }
    return result;
}
////////////////////////////////////////////////////////////////////////////////
@WebMethod(operationName = "user_types")
    public String user_types(@WebParam(name = "id_number") String id_number, @WebParam(name = "username") String username) {
        //TODO write your implementation code here:
        String bool="0"; 
        try 
            {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms","root","");
        st = con.createStatement();
        String query = "SELECT * FROM user_account WHERE id_number='"+id_number+"'AND username='"+username+"'";
        rs = st.executeQuery(query);
  
        while(rs.next())
            {
            bool= rs.getString(12);          
            con.close();
            }        
            }
        catch (Exception e) 
            {
                System.out.println(e.getMessage());
            }        
        return bool;
    }
 /////////////////////////////////////VehicleInsuranceInfo Web Methods/////////////////
    // --- VEHICLE INSURANCE ---

@WebMethod
public String saveVehicleInsuranceInfo(
        @WebParam(name = "info") VehicleInsuranceInfo info,
        @WebParam(name = "updatedBy") String updatedBy) {

    //Connection conn = null;
    PreparedStatement pst = null;
    //ResultSet rss = null;

    try {
        con = getConnection();

        // Check if an insurance record already exists for this license, type, and policy number
        String checkSql = "SELECT status FROM vehicle_insurance WHERE vehicle_license_no=? AND insurance_type=? AND insurance_policy_no=?";
        pst = con.prepareStatement(checkSql);
        pst.setString(1, info.getVehicleLicenseNo());
        pst.setString(2, info.getInsuranceType());
        pst.setString(3, info.getInsurancePolicyNo());
        rs = pst.executeQuery();

        if (rs.next()) {
            String existingStatus = rs.getString("status");
            if (!"Pending".equalsIgnoreCase(existingStatus)) {
                return "Update denied. Only insurance records with status 'Pending' can be updated.";
            }

            // Update allowed
            String updateSql = "UPDATE vehicle_insurance SET " +
                    "insurance_provider=?, insurance_issue_date=STR_TO_DATE(?, '%d-%m-%Y'), " +
                    "insurance_expiry_date=STR_TO_DATE(?, '%d-%m-%Y'), insurance_amount=?, " +
                    "status=?, updated_by=?, updated_at=NOW() " +
                    "WHERE vehicle_license_no=? AND insurance_type=? AND insurance_policy_no=?";
            closeQuietly(pst, rs);
            pst = con.prepareStatement(updateSql);
            pst.setString(1, info.getInsuranceCompany());   // fixed
            pst.setString(2, info.getIssueDate());          // fixed
            pst.setString(3, info.getExpiryDate());         // fixed
            pst.setDouble(4, info.getInsuranceAmount()); // fixed
            pst.setString(5, info.getStatus());
            pst.setString(6, updatedBy);
            pst.setString(7, info.getVehicleLicenseNo());
            pst.setString(8, info.getInsuranceType());
            pst.setString(9, info.getInsurancePolicyNo());

            pst.executeUpdate();
            return "Insurance updated successfully.";

        } else {
            // No existing record, check for date overlap
            String overlapSql = "SELECT COUNT(*) FROM vehicle_insurance WHERE " +
                    "vehicle_license_no = ? AND insurance_type = ? AND (" +
                    "(insurance_issue_date <= STR_TO_DATE(?, '%d-%m-%Y') AND insurance_expiry_date >= STR_TO_DATE(?, '%d-%m-%Y')) " +
                    "OR (insurance_issue_date <= STR_TO_DATE(?, '%d-%m-%Y') AND insurance_expiry_date >= STR_TO_DATE(?, '%d-%m-%Y')) " +
                    "OR (insurance_issue_date >= STR_TO_DATE(?, '%d-%m-%Y') AND insurance_expiry_date <= STR_TO_DATE(?, '%d-%m-%Y')))";
            closeQuietly(pst, rs);
            pst = con.prepareStatement(overlapSql);
            pst.setString(1, info.getVehicleLicenseNo());
            pst.setString(2, info.getInsuranceType());
            pst.setString(3, info.getIssueDate());
            pst.setString(4, info.getIssueDate());
            pst.setString(5, info.getExpiryDate());
            pst.setString(6, info.getExpiryDate());
            pst.setString(7, info.getIssueDate());
            pst.setString(8, info.getExpiryDate());

            rs = pst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return "Overlap detected. Another insurance of this type already exists in the given date range.";
            }

            // Insert new insurance record
            String insertSql = "INSERT INTO vehicle_insurance " +
    "(vehicle_license_no, insurance_provider, insurance_policy_no, insurance_type, " +
    "insurance_issue_date, insurance_expiry_date, insurance_amount, status, updated_by, updated_at) " +
    "VALUES (?, ?, ?, ?, STR_TO_DATE(?, '%d-%m-%Y'), STR_TO_DATE(?, '%d-%m-%Y'), ?, ?, ?, NOW())";

            closeQuietly(pst, rs);
            pst = con.prepareStatement(insertSql);
            pst.setString(1, info.getVehicleLicenseNo());
            pst.setString(2, info.getInsuranceCompany());
            pst.setString(3, info.getInsurancePolicyNo());
            pst.setString(4, info.getInsuranceType());
            pst.setString(5, info.getIssueDate());      // dd-MM-yyyy
            pst.setString(6, info.getExpiryDate());     // dd-MM-yyyy
            pst.setDouble(7, info.getInsuranceAmount());
            pst.setString(8, info.getStatus());
            pst.setString(9, updatedBy);   

            pst.executeUpdate();
            return "Insurance saved successfully.";
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        return "Error: " + ex.getMessage();
    } finally {
        closeQuietly(pst, rs);
        if (con != null) {
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}


@WebMethod
public VehicleInsuranceInfo[] getVehicleInsuranceInfo_By_policy_no(@WebParam(name = "insurance_policy_no") String insurance_policy_no) {
    final String QUERY =
        "SELECT vehicle_license_no, insurance_provider, insurance_policy_no, insurance_type, " +
        "DATE_FORMAT(insurance_issue_date, '%d-%m-%Y') AS issue_date, " +
        "DATE_FORMAT(insurance_expiry_date, '%d-%m-%Y') AS expiry_date, " +
        "insurance_amount, status, updated_at, updated_by FROM vehicle_insurance WHERE insurance_policy_no =?";

    List<VehicleInsuranceInfo> insuranceList = new ArrayList<>();

    try (Connection con = getConnection();
         PreparedStatement pst = con.prepareStatement(QUERY)) {

        pst.setString(1, insurance_policy_no);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                insuranceList.add(mapResultSetToInsuranceInfo(rs));
            }
        }

        return insuranceList.toArray(new VehicleInsuranceInfo[0]);

    } catch (Exception ex) {
        ex.printStackTrace(); // Replace with proper logging if available
        return null;
    }
}

private VehicleInsuranceInfo mapResultSetToInsuranceInfo(ResultSet rs) throws SQLException {
    VehicleInsuranceInfo info = new VehicleInsuranceInfo();
    info.setVehicleLicenseNo(rs.getString("vehicle_license_no"));
    info.setInsuranceCompany(rs.getString("insurance_provider"));    // renamed field
    info.setInsurancePolicyNo(rs.getString("insurance_policy_no"));
    info.setInsuranceType(rs.getString("insurance_type"));
    info.setIssueDate(rs.getString("issue_date"));                    // renamed method
    info.setExpiryDate(rs.getString("expiry_date"));                  // renamed method
    info.setInsuranceAmount(rs.getDouble("insurance_amount"));
    info.setStatus(rs.getString("status"));
    info.setUpdatedBy(rs.getString("updated_by"));        // ✅ MUST be present
    info.setLastUpdated(rs.getString("updated_at"));      // ✅ MUST be present
    return info;
}
@WebMethod
public VehicleInsuranceInfo[] getAllVehicleInsuranceInfo() {
    List<VehicleInsuranceInfo> list = new ArrayList<>();

    String sql = "SELECT vehicle_license_no, insurance_provider, insurance_policy_no, insurance_type, " +
                 "insurance_issue_date, insurance_expiry_date, insurance_amount, status, updated_by, updated_at " +
                 "FROM vehicle_insurance";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rss = stmt.executeQuery()) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timestampFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        while (rss.next()) {
            VehicleInsuranceInfo info = new VehicleInsuranceInfo();

            info.setVehicleLicenseNo(rss.getString("vehicle_license_no"));
            info.setInsuranceCompany(rss.getString("insurance_provider")); // ✅ corrected column
            info.setInsurancePolicyNo(rss.getString("insurance_policy_no"));
            info.setInsuranceType(rss.getString("insurance_type"));

            Date issueDate = rss.getDate("insurance_issue_date");
            if (issueDate != null) {
                info.setIssueDate(dateFormat.format(issueDate));
            }

            Date expiryDate = rss.getDate("insurance_expiry_date");
            if (expiryDate != null) {
                info.setExpiryDate(dateFormat.format(expiryDate));
            }

            info.setInsuranceAmount(rss.getDouble("insurance_amount"));
            info.setStatus(rss.getString("status"));
            info.setUpdatedBy(rss.getString("updated_by"));

            Timestamp updatedAt = rss.getTimestamp("updated_at");
            if (updatedAt != null) {
                info.setLastUpdated(timestampFormat.format(updatedAt));
            }

            list.add(info);
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }

    return list.toArray(new VehicleInsuranceInfo[0]);
}


@WebMethod
public boolean isInsuranceOverlap(
    @WebParam(name = "vehicle_license_no") String vehicle_license_no,
    @WebParam(name = "insurance_type") String insurance_type,
    @WebParam(name = "start_date") String start_date,      // "dd-MM-yyyy"
    @WebParam(name = "end_date") String end_date           // "dd-MM-yyyy"
) {
    final String QUERY =
        "SELECT COUNT(*) FROM vehicle_insurance " +
        "WHERE vehicle_license_no = ? " +
        "AND insurance_type = ? " +
        "AND (" +
        "    (STR_TO_DATE(?, '%d-%m-%Y') BETWEEN insurance_issue_date AND insurance_expiry_date) " +
        " OR (STR_TO_DATE(?, '%d-%m-%Y') BETWEEN insurance_issue_date AND insurance_expiry_date) " +
        " OR (insurance_issue_date BETWEEN STR_TO_DATE(?, '%d-%m-%Y') AND STR_TO_DATE(?, '%d-%m-%Y')) " +
        ")";

    try (Connection con = getConnection();
         PreparedStatement pst = con.prepareStatement(QUERY)) {

        pst.setString(1, vehicle_license_no);
        pst.setString(2, insurance_type);
        pst.setString(3, start_date); // new start
        pst.setString(4, end_date);   // new end
        pst.setString(5, start_date); // range from new start
        pst.setString(6, end_date);   // to new end

        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

@WebMethod
public String saveVehicleInspectionInfo(
        @WebParam(name = "info") VehicleInspectionInfo info,
        @WebParam(name = "updatedBy") String updatedBy) {

    if (info == null || info.getInspectionDate() == null || info.getVehicleLicenseNo() == null) {
        return "Invalid inspection data.";
    }

    String inspectionDate = info.getInspectionDate(); // expected format: dd-MM-yyyy
    if (inspectionDate.length() < 10) return "Invalid inspection date format.";

    final String year = inspectionDate.substring(6, 10); // Extract year from dd-MM-yyyy

    try (Connection con = getConnection()) {

        // 1. Check existing inspection for that vehicle and year
        String checkSql = "SELECT inspection_result, status FROM vehicle_inspection " +
                          "WHERE vehicle_license_no = ? AND YEAR(inspection_date) = ?";
        try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
            checkStmt.setString(1, info.getVehicleLicenseNo());
            checkStmt.setString(2, year);

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    String existingResult = rs.getString("inspection_result");
                    String existingStatus = rs.getString("status");

                    if (!"Fail".equalsIgnoreCase(existingResult) ||
                        (!"Pending".equalsIgnoreCase(existingStatus) && !"Failed".equalsIgnoreCase(existingStatus))) {
                        return "Update denied. Inspection update allowed only if previous result was Fail and status is Pending or Failed.";
                    }

                    // 2. Update existing record
                    String updateSql = "UPDATE vehicle_inspection SET inspection_center=?, inspection_certificate_no=?, inspection_result=?, status=?, " +
                            "inspection_date=STR_TO_DATE(?, '%d-%m-%Y'), inspection_expiry_date=STR_TO_DATE(?, '%d-%m-%Y'), " +
                            "updated_by=?, updated_at=NOW() " +
                            "WHERE vehicle_license_no=? AND YEAR(inspection_date) = ?";
                    try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
                        updateStmt.setString(1, info.getInspectionCenter());
                        updateStmt.setString(2, info.getInspectionCertificateNo());
                        updateStmt.setString(3, info.getInspectionResult());
                        updateStmt.setString(4, info.getStatus());
                        updateStmt.setString(5, info.getInspectionDate());
                        updateStmt.setString(6, info.getInspectionExpiryDate());
                        updateStmt.setString(7, updatedBy);
                        updateStmt.setString(8, info.getVehicleLicenseNo());
                        updateStmt.setString(9, year);
                        int rowsUpdated = updateStmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            return "Inspection updated successfully.";
                        } else {
                            return "No inspection record updated.";
                        }
                    }
                }
            }
        }

        // 3. Insert new record
        String insertSql = "INSERT INTO vehicle_inspection " +
                "(vehicle_license_no, inspection_center, inspection_certificate_no, inspection_date, inspection_expiry_date, inspection_result, status, updated_by, updated_at) " +
                "VALUES (?, ?, ?, STR_TO_DATE(?, '%d-%m-%Y'), STR_TO_DATE(?, '%d-%m-%Y'), ?, ?, ?, NOW())";

        try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
            insertStmt.setString(1, info.getVehicleLicenseNo());
            insertStmt.setString(2, info.getInspectionCenter());
            insertStmt.setString(3, info.getInspectionCertificateNo());
            insertStmt.setString(4, info.getInspectionDate());
            insertStmt.setString(5, info.getInspectionExpiryDate());
            insertStmt.setString(6, info.getInspectionResult());
            insertStmt.setString(7, info.getStatus());
            insertStmt.setString(8, updatedBy);
            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                return "Inspection saved successfully.";
            } else {
                return "Failed to save inspection.";
            }
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        return "Error: " + ex.getMessage();
    }
}

@WebMethod
public VehicleInspectionInfo[] getVehicleInspectionInfo_By_certificate_no(
    @WebParam(name = "inspection_certificate_no") String inspection_certificate_no) {

    final String QUERY =
        "SELECT vehicle_license_no, inspection_center, inspection_certificate_no, " +
        "DATE_FORMAT(inspection_date, '%d-%m-%Y') AS inspection_date, " +
        "DATE_FORMAT(inspection_expiry_date, '%d-%m-%Y') AS expiry_date, " +
        "inspection_result, status, " +
        "DATE_FORMAT(updated_at, '%d-%m-%Y %H:%i:%s') AS updated_at, " +
        "updated_by " +
        "FROM vehicle_inspection " +
        "WHERE inspection_certificate_no = ?";

    List<VehicleInspectionInfo> inspectionList = new ArrayList<>();

    try (Connection conn = getConnection();
         PreparedStatement pst = conn.prepareStatement(QUERY)) {

        pst.setString(1, inspection_certificate_no);

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                VehicleInspectionInfo info = new VehicleInspectionInfo();
                info.setVehicleLicenseNo(rs.getString("vehicle_license_no"));
                info.setInspectionCenter(rs.getString("inspection_center"));
                info.setInspectionCertificateNo(rs.getString("inspection_certificate_no"));
                info.setInspectionDate(rs.getString("inspection_date")); // already formatted
                info.setInspectionExpiryDate(rs.getString("expiry_date")); // already formatted
                info.setInspectionResult(rs.getString("inspection_result"));
                info.setStatus(rs.getString("status"));
                info.setUpdatedAt(rs.getString("updated_at")); // <-- new
                info.setUpdatedBy(rs.getString("updated_by"));   // <-- new
                inspectionList.add(info);
            }
        }

        return inspectionList.toArray(new VehicleInspectionInfo[0]);

    } catch (Exception ex) {
        ex.printStackTrace();
        return null;
    }
}

@WebMethod
public VehicleInspectionInfo[] getAllVehicleInspectionInfo() {
    List<VehicleInspectionInfo> list = new ArrayList<VehicleInspectionInfo>();

    String sql = "SELECT vehicle_license_no, inspection_center, inspection_certificate_no, " +
                 "inspection_date, inspection_expiry_date, inspection_result, status, " +
                 "updated_by, updated_at FROM vehicle_inspection";

    try (Connection con = getConnection();
         PreparedStatement stmt = con.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timestampFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        while (rs.next()) {
            VehicleInspectionInfo info = new VehicleInspectionInfo();

            info.setVehicleLicenseNo(rs.getString("vehicle_license_no"));
            info.setInspectionCenter(rs.getString("inspection_center"));
            info.setInspectionCertificateNo(rs.getString("inspection_certificate_no"));

            Date inspectionDate = rs.getDate("inspection_date");
            if (inspectionDate != null) {
                info.setInspectionDate(dateFormat.format(inspectionDate));
            }

            Date expiryDate = rs.getDate("inspection_expiry_date");
            if (expiryDate != null) {
                info.setInspectionExpiryDate(dateFormat.format(expiryDate));
            }

            info.setInspectionResult(rs.getString("inspection_result"));
            info.setStatus(rs.getString("status"));
            info.setUpdatedBy(rs.getString("updated_by"));

            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                info.setUpdatedAt(timestampFormat.format(updatedAt));
            }

            list.add(info);
        }

    } catch (Exception ex) {
        ex.printStackTrace(); // Consider replacing with a logger in production
    }

    return list.toArray(new VehicleInspectionInfo[0]);
}

@WebMethod
public boolean isInspectionOverlap(
    @WebParam(name = "vehicle_license_no") String vehicle_license_no,
    @WebParam(name = "inspection_date") String inspection_date  // Format: "dd-MM-yyyy"
) {
    final String QUERY =
        "SELECT COUNT(*) FROM vehicle_inspection " +
        "WHERE vehicle_license_no = ? " +
        "AND YEAR(inspection_date) = YEAR(STR_TO_DATE(?, '%d-%m-%Y'))";

    try (Connection con = getConnection();
         PreparedStatement pst = con.prepareStatement(QUERY)) {

        pst.setString(1, vehicle_license_no);
        pst.setString(2, inspection_date);

        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // Overlap exists
            }
        }

    } catch (Exception e) {
        e.printStackTrace(); // Log the exception
    }

    return false; // Default: no overlap
}
private VehicleInspectionInfo mapResultSetToInspectionInfo(ResultSet rs) throws SQLException {
    VehicleInspectionInfo info = new VehicleInspectionInfo();

    info.setVehicleLicenseNo(rs.getString("vehicle_license_no"));
    info.setInspectionCenter(rs.getString("inspection_center"));
    info.setInspectionCertificateNo(rs.getString("inspection_certificate_no"));
    info.setInspectionDate(rs.getString("inspection_date"));             // formatted in SQL
    info.setInspectionExpiryDate(rs.getString("inspection_expiry_date")); // formatted in SQL
    info.setInspectionResult(rs.getString("inspection_result"));
    info.setStatus(rs.getString("status"));
    info.setUpdatedBy(rs.getString("updated_by"));
    info.setUpdatedAt(rs.getString("updated_at"));

    return info;
}
public class VehicleExpiryInfo {
    private String vehicleLicenseNo;
    private int insuranceDaysRemaining;
    private int inspectionDaysRemaining;

    // Getters and setters...
}
@WebMethod
public List<VehicleInsuranceExpiryInfo> getInsuranceExpiryInfoList(String vehicleLicenseNo) {
    List<VehicleInsuranceExpiryInfo> list = new ArrayList<>();
    try {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT vehicle_license_no, insurance_type, DATEDIFF(insurance_expiry_date, CURDATE()) AS insurance_remaining " +
                     "FROM vehicle_insurance " +
                     "WHERE vehicle_license_no = ? AND status = 'Approved' AND insurance_expiry_date >= CURDATE()";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, vehicleLicenseNo);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            VehicleInsuranceExpiryInfo info = new VehicleInsuranceExpiryInfo();
            info.setVehicleLicenseNo(rs.getString("vehicle_license_no"));
            info.setInsuranceType(rs.getString("insurance_type"));
            info.setInsuranceDaysRemaining(rs.getInt("insurance_remaining"));
            list.add(info);
        }
        rs.close();
        ps.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

@WebMethod
public VehicleInspectionExpiryInfo getInspectionExpiryInfo(String vehicleLicenseNo) {
    VehicleInspectionExpiryInfo info = null;
    try {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT vehicle_license_no, DATEDIFF(inspection_expiry_date, CURDATE()) AS inspection_remaining " +
                     "FROM vehicle_inspection " +
                     "WHERE vehicle_license_no = ? AND status = 'Passed' AND inspection_expiry_date >= CURDATE()";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, vehicleLicenseNo);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            info = new VehicleInspectionExpiryInfo();
            info.setVehicleLicenseNo(rs.getString("vehicle_license_no"));
            info.setInspectionDaysRemaining(rs.getInt("inspection_remaining"));
        }
        rs.close();
        ps.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return info;
}

@WebMethod
public String[] getAllVehicleLicenses() {
    List<String> licenseList = new ArrayList<String>();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eas_vms", "root", "");

        String query = "SELECT vehicle_license_no FROM vehicle";
        stmt = conn.prepareStatement(query);
        rs = stmt.executeQuery();

        while (rs.next()) {
            String license = rs.getString("vehicle_license_no");
            licenseList.add(license);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (stmt != null) stmt.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    return licenseList.toArray(new String[0]);
}

}
