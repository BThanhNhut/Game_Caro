package caro_project_sever;

import DAO.PlayerDAO;
import DTO.Player;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ServerThread implements Runnable{
    private Player user;
    private Socket socketOfServer;
    private int clientNumber;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;
    private Room room;
    private PlayerDAO userDAO;
    private String clientIP;
    
    //
    public Player getPlayer() {
        return user;
    }
    
    public int getClientNumber() {
        return clientNumber;
    }
    
    public String getClientIP() {
        return clientIP;
    }
    
    
    public void goToPartnerRoom() throws IOException{
        write("go-to-room," + room.getID()+","+room.getCompetitor(this.getClientNumber()).getClientIP()+",0,"+getStringFromUser(room.getCompetitor(this.getClientNumber()).getPlayer()));
        room.getCompetitor(this.clientNumber).write("go-to-room,"+ room.getID()+","+this.clientIP+",1,"+getStringFromUser(user));
    }
    public void goToPartnerRoom_wait() throws IOException{
        write("go-to-room_wait," + room.getID()+","+room.getCompetitor(this.getClientNumber()).getClientIP()+",0,"+getStringFromUser(room.getCompetitor(this.getClientNumber()).getPlayer()));
    room.getCompetitor(this.clientNumber).write("go-to-room_wait,"+ room.getID()+","+this.clientIP+",1,"+getStringFromUser(user));
    }
    
    
    public ServerThread(Socket socketOfServer, int clientNumber) {
        this.socketOfServer = socketOfServer;
        this.clientNumber = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started");
        userDAO = new PlayerDAO();
        isClosed = false;
        room = null;
        //Trường hợp test máy ở server sẽ lỗi do hostaddress là localhost
        if(this.socketOfServer.getInetAddress().getHostAddress().equals("localhost")){
            clientIP = "localhost";
        }
        else{
            clientIP = this.socketOfServer.getInetAddress().getHostAddress();
        }
        
    }
    
    public String getStringFromUser(Player user1){
        return ""+user1.getID()+","+user1.getUsername()
                                +","+user1.getPassword()+","+user1.getNickname()+","+
                                user1.getAvatar()+","+user1.getNumberOfGame()+","+
                                user1.getNumberOfwin()+","+user1.getNumberOfDraw()+","+user1.getIsOnline()+","+user1.getIsPlaying()+","+user1.getRank();
    }
    
    public void run()
    {
        
        try {
            // Mở luồng vào ra trên Socket tại Server.
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            System.out.println("Khời động luông mới thành công, ID làaaaa: " + clientNumber );
//            write("server-send-id" + "," + this.clientNumber);
            String message;
            System.out.println("caro_project_sever.ServerThread.run()" + isClosed);
            while (!isClosed) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                System.out.println("Chuoi la "+ message);
                String[] messageSplit = message.split(",");
                // Tạo phòng
                if (messageSplit[0].equals("create-room")) 
                {
                    room = new Room(this);
                    write("your-created-room," + room.getID());
                    System.out.println("Tạo phòng mới thành công ID" + room.getID());
                    userDAO.updateToPlaying(this.user.getID());
                }
                // Xử lý vào phòng chờ (mới)
                if (messageSplit[0].equals("wait-room")) 
                {
                    room = new Room(this);               
                    write("your-wait-room," + room.getID());
                    System.out.println("Tạo phòng mới thành công ID" + room.getID());
                    userDAO.updateToPlaying(this.user.getID());
                }
                //Xử lý chat toàn server
                if(messageSplit[0].equals("chat-server")){
                    Server.serverThreadBLL.boardCast(clientNumber,messageSplit[0]+","+ user.getNickname()+" : "+ messageSplit[1]);
                }
                // xử lý vào phòng nhanh go-room
                if (messageSplit[0].equals("go-room")) 
                {          
                    boolean isFinded = false;
                    for (ServerThread serverThread : Server.serverThreadBLL.getListServerThreads()) 
                    {
                        if (serverThread.room != null && serverThread.room.getNumberOfUser() == 1 && serverThread.room.getPassword().equals(" ")) {
                            serverThread.room.setUser2(this);
                            this.room = serverThread.room;
                            room.increaseNumberOfGame();
                            System.out.println("Đã vào phòng " + room.getID());
                            goToPartnerRoom_wait();
                            userDAO.updateToPlaying(this.user.getID());
                            isFinded = true;
                            //Xử lý phần mời cả 2 người chơi vào phòng
                            break;
                        }
                    }
                    if (!isFinded) {
                        this.room = new Room(this);
                        userDAO.updateToPlaying(this.user.getID());
                        System.out.println("Không tìm thấy phòng, tạo phòng mới");
                    }
                }
                 
                // Kiểm tra mật khẩu
                if(messageSplit[0].equals("client-verify"))
                {
                    System.out.println("vào dc client ");
                    int ketqua = PlayerDAO.getPlayer(messageSplit[1],messageSplit[2]);
                    if(ketqua == 0)
                        write("wrong-user,"+messageSplit[1]+","+messageSplit[2]);
                    else
                    {
                        Player player = PlayerDAO.getPlayer1(messageSplit[1],messageSplit[2]);
                        this.user = player;
                        write("login-success,"+getStringFromUser(player));
                        System.out.println("ben sever : "+ getStringFromUser(player));
                        System.err.println("Thanh cong");
                    }
                    
                    
                }
                // Xử lý vào phòng
                if(messageSplit[0].equals("quick-room"))
                {
                    boolean isFinded = false;
                    for (ServerThread serverThread : Server.serverThreadBLL.getListServerThreads()) 
                    {
                        if (serverThread.room != null && serverThread.room.getNumberOfUser() == 2 && serverThread.room.getPassword().equals(" ")) {
                            serverThread.room.setUser2(this);
                            this.room = serverThread.room;
                            room.increaseNumberOfGame();
                            System.out.println("Đã vào phòng " + room.getID());
                            goToPartnerRoom();
                            userDAO.updateToPlaying(this.user.getID());
                            isFinded = true;
                            //Xử lý phần mời cả 2 người chơi vào phòng
                            break;
                        }
                    }
                    if (!isFinded) {
                        this.room = new Room(this);
                        userDAO.updateToPlaying(this.user.getID());
                        System.out.println("Không tìm thấy phòng, tạo phòng mới");
                    }
                }
                // Xử lý người chơi đánh
                if(messageSplit[0].equals("caro")){
                    room.getCompetitor(clientNumber).write(message);
                }
                // xử lý win 
                if(messageSplit[0].equals("win")){
//                    userDAO.addWinGame(this.user.getID());
                    room.increaseNumberOfGame();
                    room.getCompetitor(clientNumber).write("caro,"+messageSplit[1]+","+messageSplit[2]);
                    room.boardCast("new-game,");
                }
           
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void write(String message) throws IOException {
        os.write(message);
        os.newLine();
        os.flush();
    }
}
