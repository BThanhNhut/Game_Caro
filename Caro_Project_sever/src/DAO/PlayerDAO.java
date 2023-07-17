/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import DTO.Player;
import java.util.ArrayList;
import java.sql.ResultSet;
/**
 *
 * @author THANH NHUT
 */
public class PlayerDAO {
    public PlayerDAO() {
        super();
    }
    
    public static ArrayList<Player> executeQuery (String sql){
        ArrayList<Player> ds = new ArrayList<Player>();
        try {
            ConnectionDB cn = new ConnectionDB();
            cn.getCn();
            ResultSet rs = cn.executeQuery(sql);
            while(rs.next()){
                Player player = new Player(
                        rs.getInt("ID"), 
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("NICKNAME"),
                        rs.getString("AVATAR"),
                        rs.getInt("NUMBEROFGAME"), 
                        rs.getInt("NUMBEROFWIN"), 
                        rs.getInt("NUMBEROFDRAW"), 
                        rs.getInt("ISONLINE"), 
                        rs.getInt("ISPLAYING"),
                        rs.getInt("RANK")
                        );
                ds.add(player);
            }
            cn.close();
        } catch (Exception e) {
            System.out.println("Lỗi không thể lấy dữ liệu bàn");
        }
        return ds;
    }
    
    public static int getPlayer (String username, String password)
    {
        String sql = "select * from player where USERNAME ='"+username.trim()+"' and PASSWORD = '"+password.trim()+"'";
        System.out.println("tk = " + username.trim() + "mk = " + executeQuery(sql).size());
        int i = 0;
        for (Player player : executeQuery(sql))
        {
            System.out.println("zo dc");
            i++;
            return 1;
        }
        return 0;
    }
    public static Player getPlayer1 (String username, String password)
    {
        
        String sql = "select * from player where USERNAME ='"+username.trim()+"' and PASSWORD = '"+password.trim()+"'";
        System.out.println("tk = " + username.trim() + "mk = " + executeQuery(sql).size());
        int i = 0;
        for (Player player : executeQuery(sql))
        {
            System.out.println("zo dc");
            i++;
            return player;
        }
        return null;
    }
    public static boolean check_online (String username, String password)
    {
        String sql = "select * from player where USERNAME ='"+username.trim()+"' and PASSWORD = '"+password.trim()+"'AND ISONLINE";
        System.out.println("tk = " + username.trim() + "ONLINE" + executeQuery(sql).size());
        if(executeQuery(sql).size() > 0)
        {
            return true; // có online
        }
        return false;
    }
    
//    public void updateToPlaying(int ID){
//        try {
//            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
//                    + "SET IsPlaying = 1\n"
//                    + "WHERE ID = ?");
//            preparedStatement.setInt(1, ID);
//            System.out.println(preparedStatement);
//            preparedStatement.executeUpdate();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
    
    public void updateToPlaying(int ID)
    {
        int i=0;
        try {
            ConnectionDB cn = new ConnectionDB();
            cn.getCn();
             i = cn.executeUpdate("UPDATE player SET IsPlaying = 0 WHERE ID = "+ID+"");
            cn.close();
            System.out.println("update người chơi đang chơi thành công");
            
        } catch (Exception e) {
            System.out.println("update người chơi đang chơi thất bại");
        }
    }
    
    public int getNumberOfGame (int ID)
    {
        int count = 0;
        try {
            ConnectionDB cn = new ConnectionDB();
            cn.getCn();
            ResultSet rs = cn.executeQuery("SELECT NUMBEROFGAME FROM PLAYER WHERE ID = "+ID+"");
            if (rs.next()) {
                count = rs.getInt(1);
            }
            cn.close();
        } catch (Exception e) {
            System.out.println("Lỗi không thể lấy dữ liệu bàn");
        }
        return count;
    }
    
    public static void addGame (int ID)
    {
        int i=0;
        try {
            ConnectionDB cn = new ConnectionDB();
            cn.getCn();
            i = cn.executeUpdate("UPDATE PLAYER SET NUMBEROFGAME = "+new PlayerDAO().getNumberOfGame(ID)+" WHERE ID = "+ID+"");
            cn.close();
            System.out.println("UPDATE số ván chơi người chơi");
            
        } catch (Exception e) {
            System.out.println("UPDATE thất bại số ván chơi người chơi");
        }
        if(i > 0)
            System.out.println("CSDL THANH CONG ");
    }
    
    public static int excuteNonQuery(String sql){
        int i=0;
        try {
            ConnectionDB cn = new ConnectionDB();
            cn.getCn();
             i = cn.executeUpdate(sql);
            cn.close();
            System.out.println("Thêm/xóa/sửa thành công");
            
        } catch (Exception e) {
            System.out.println("Thêm/xóa/sửa không thành công");
           
        }
        return i;
    }
}
