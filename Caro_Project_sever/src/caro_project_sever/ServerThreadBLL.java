/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package caro_project_sever;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author THANH NHUT
 */
public class ServerThreadBLL {
    private List<ServerThread> listServerThreads;
    
    public List<ServerThread> getListServerThreads() {
        return listServerThreads;
    }

    public ServerThreadBLL() {
        listServerThreads = new ArrayList<>();
    }
    
    public void add(ServerThread serverThread){
        listServerThreads.add(serverThread);
    }
    
    public int getLength(){
        return listServerThreads.size();
    }
    public void boardCast(int id, String message){
        for(ServerThread serverThread : Server.serverThreadBLL.getListServerThreads()){
            if (serverThread.getClientNumber() == id) {
                continue;
            } else {
                try {
                    serverThread.write(message);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
