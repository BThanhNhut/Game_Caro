/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caro_Project_Client;

import DTO.Player;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;





public class Socket_handle implements Runnable{
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
    private int ID_Server;
    
    public Player getUserFromString(int start, String[] message){
        return new Player(Integer.parseInt(message[start]),
                message[start+1],
                message[start+2],
                message[start+3],
                message[start+4],
                Integer.parseInt(message[start+5]),
                Integer.parseInt(message[start+6]),
                Integer.parseInt(message[start+7]),
                Integer.parseInt(message[start+8]),
                Integer.parseInt(message[start+9]),
                Integer.parseInt(message[start+10]));
    }
    
    @Override
    public void run ()
    {
        try {
            // Gửi yêu cầu kết nối tới Server đang lắng nghe
            socketOfClient = new Socket("localhost", 2002);
            System.out.println("Kết nối thành công!");
            // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
            String message;
            while (true) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                //Lấy id sever
                String[] messageSplit = message.split(",");
                System.out.println("Vao dc socket");
                if(messageSplit[0].equals("server-send-id")){
                    ID_Server = Integer.parseInt(messageSplit[1]);
                }
                //
                if(messageSplit[0].equals("login-success")){
                    System.out.println("Đăng nhập thành công");
                    Player player = getUserFromString(1,messageSplit);
                    Client.player = player;
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                }
                // Tạo phòng và server trả về tên phòng
                if(messageSplit[0].equals("your-created-room"))
                {
                    Client.closeAllViews();
                    System.out.println("Vao dc phong");
                    Client.openView(Client.View.WAITROOM);
                }
                // Xử lý vào phòng chờ 1
                if(messageSplit[0].equals("your-wait-room"))
                {
                    int id_room = Integer.parseInt(messageSplit[1]);
                    
                    System.out.println("Vao dc phong");
                    Client.openView(Client.View.WAITROOM,id_room);
                }
                // Xử lý vào phòng chơ 2
                if(messageSplit[0].equals("go-to-room_wait"))
                {
                    System.out.println("Vào phòng bên socket phòng chờ 2");
                    int roomID = Integer.parseInt(messageSplit[1]);
                    String competitorIP = messageSplit[2];
                    int isStart = Integer.parseInt(messageSplit[3]);
                    Player competitor = getUserFromString(4, messageSplit);
             
                    Client.closeAllViews();
                    
                    System.out.println("Đã vào phòng: "+roomID);
                    // Vào phòng
                    Client.openView(Client.View.WAITROOM
                            , roomID
                            , competitor);
//                    Client.game_with_people.newgame();
                }
                //  Xử lý vào trận
                if(messageSplit[0].equals("go-to-room"))
                {
                    System.out.println("Vào phòng bên socket");
                    int roomID = Integer.parseInt(messageSplit[1]);
                    String competitorIP = messageSplit[2];
                    int isStart = Integer.parseInt(messageSplit[3]);
                    Player competitor = getUserFromString(4, messageSplit);
                    
                    Client.closeAllViews();
                    System.out.println("Đã vào phòng: "+roomID);
                    // Vào phòng
                    Client.openView(Client.View.GAMEWITHPEOPLE
                            , competitor
                            , roomID
                            ,isStart
                            ,competitorIP);
                    Client.game_with_people.newgame();
                }
                //Xử lý nhận thông tin, chat từ toàn server
                if(messageSplit[0].equals("chat-server")){
                    if(Client.home!=null){
                        Client.home.addMessage(messageSplit[1]);
                    }
                }
                // xử lý đánh caro
                if(messageSplit[0].equals("caro"))
                {
                    Client.game_with_people.addCompetitorMove(messageSplit[1], messageSplit[2]);
                }
                // xử lý đăng nhập thất bại
                if(messageSplit[0].equals("wrong-user")){
                    System.out.println("Thông tin sai");
                    Client.openView(Client.View.LOGIN);
                    Client.login.error(1);
                }
                
                // Xử lý ván mới
                if(messageSplit[0].equals("new-game")){
                    System.out.println("New game");
                    Thread.sleep(3000);
                    Client.game_with_people.updateNumberOfGame();
                    Client.game_with_people.newgame();
                }
                
             }
            
        } catch (IOException ex) {
            Logger.getLogger(Socket_handle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Socket_handle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void write(String message) throws IOException{
        System.out.println("Ket qua la" + message);
        os.write(message);
        os.newLine();
        os.flush();
    }
    
    public Socket getSocketOfClient() {
        return socketOfClient;
    }
}
