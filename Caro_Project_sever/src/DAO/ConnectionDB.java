/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.*;


public class ConnectionDB {
    private Connection cn;

    public Connection getCn() {
        try {
            String url = "jdbc:sqlserver://localhost;databaseName=ql_caro;user=sa;password=123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(url);
            System.out.println("Kết nối csdl thành công");
        } catch (Exception e) {
            System.out.println("Kết nối csdl không thành công");
        }
        return cn;
    }
    
    public void close(){
        try {
            this.cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet executeQuery(String sql){
        ResultSet rs = null;
        try {
            Statement stm = cn.createStatement();
            rs = stm.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int executeUpdate(String sql){
        int i = -1;
        try {
            Statement stm = cn.createStatement();
            i = stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
