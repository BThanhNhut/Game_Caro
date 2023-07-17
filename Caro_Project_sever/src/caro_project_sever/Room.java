/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package caro_project_sever;

import DAO.PlayerDAO;
import java.io.IOException;

/**
 *
 * @author THANH NHUT
 */
public class Room {
    private int ID;
    private ServerThread user1;
    private ServerThread user2;
    private String password;
    private PlayerDAO playerDAO;
    
    public int getID() {
        return ID;
    }

    public ServerThread getUser2() {
        return user2;
    }

    public ServerThread getUser1() {
        return user1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getNumberOfUser(){
        return user2==null?1:2;
    }
    
    public void setUser2(ServerThread user2){
        this.user2 = user2;
    }
    
    public void increaseNumberOfGame(){
        playerDAO.addGame(user1.getPlayer().getID());
        playerDAO.addGame(user2.getPlayer().getID());
    }
    // sua
    
    public ServerThread getCompetitor(int ID_ClientNumber){
        if(user1.getClientNumber()==ID_ClientNumber)
            return user2;
        return user1;
    }
    
    
    public Room(ServerThread user1) {
        System.out.println("Tạo phòng thành công, ID là: "+Server.ID_room);
        this.password=" ";
        this.ID = Server.ID_room++;
        playerDAO = new PlayerDAO();
        this.user1 = user1;
        this.user2 = null;
    }

    public void boardCast(String message){
        try {
            user1.write(message);
            user2.write(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
