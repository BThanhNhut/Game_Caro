/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caro_Project_Client;

import GUI.Game_With_People;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import Caro_Project_Client.Socket_handle;
import DTO.Player;
import GUI.Home;
import GUI.Login;
import GUI.Wait_Room;


public class Client {
    public static Player player;
    //Giao diện
    public static Game_With_People game_with_people;
    public static Home home;
    public static Login login;
    public static Wait_Room wait_room;
    //Thiết lập socket
    public static Socket_handle socketHandle;
    
    public enum View{
        LOGIN,
        REGISTER,
        HOMEPAGE,
        WAITROOM,
        GAMEWITHPEOPLE
    }
//    public void connect ()
//    {
//        try {
//            Socket socket = new Socket("localhost",2002);
//            //sending
//            PrintStream ps = new PrintStream(socket.getOutputStream()); // dùng đối tượng printStream connect tới server
//            ps.println("Study and share");
//            // Nhận thông báo từ server gửi về
//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String respone = br.readLine();
//            if(respone != null)
//            {
//                System.out.println("server answered: " + respone);
//            }
//            //Receiving from server
//            
//        } catch (IOException ex) {
//            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public static void openView(View viewName){
        if(viewName != null){
            switch(viewName){
                case HOMEPAGE:
                    home = new Home();
                    home.setVisible(true);
                    break;
                case WAITROOM:
                    wait_room = new Wait_Room();
                    wait_room.setVisible(true);
                    break;
            }
        }
    }
    
    public static void openView(View viewName, Player competitor, int room_ID, int isStart, String competitorIP){
        if(viewName != null){
            switch(viewName){
                case GAMEWITHPEOPLE:
                    game_with_people = new Game_With_People(competitor, room_ID, isStart, competitorIP);
                    game_with_people.setVisible(true);
                    break;
            }
        }
    }
    
    public static void openView(View viewName, int id_room){
        if(viewName != null){
            switch(viewName){
                case WAITROOM:
                    wait_room = new Wait_Room(id_room);
                    wait_room.setVisible(true);
                    break;
            }
        }
    }
    
    public static void openView(View viewName, int id_room, Player competitor){
        if(viewName != null){
            switch(viewName){
                case WAITROOM:
                    wait_room = new Wait_Room(id_room, competitor);
                    wait_room.setVisible(true);
                    break;
            }
        }
    }
    
    public static void closeView(View viewName){
        if(viewName != null){
            switch(viewName){
                case WAITROOM:
                    wait_room.dispose();
                    break;
            }
            
        }
    }

    
    public static void closeAllViews(){
        if(home != null) home.dispose();
        if(login != null) login.dispose();
        if (wait_room != null) login.dispose();
     
    }
    
    public void init ()
    {  
        login = new Login();
        login.setVisible(true);
        socketHandle = new Socket_handle();
        socketHandle.run();
        
    }
    public static void main(String[] args) {
        new Client().init();
    }
}
