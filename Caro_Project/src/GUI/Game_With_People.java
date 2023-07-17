/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;
// Custtom
import Caro_Project_Client.Client;
import Custom_Control.Button;
import DTO.Player;
//
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
//import static sun.jvm.hotspot.oops.CellTypeState.bottom;

// Nhạc trong java swing



/**
 *
 * @author THANH NHUT
 */
public class Game_With_People extends javax.swing.JFrame {

    private Player competitor;
    private JButton[][] button;
    private int[][] competitorMatrix;
    private int[][] matrix;
    private int[][] userMatrix;
    
    //Set kích thước ma trận
    private final int size = 20;
    // Khởi tạo nhạc
    private Clip clip;
     
     // Server Socket
    private Timer timer;
    private Integer second, minute;
    private JButton preButton;
    
    private int numberOfMatch = 0;
    private String normalItem[];
    private String winItem[];
    private String iconItem[];
    private String preItem[];
    
    private String competitorIP;
    
    public Game_With_People(Player competitor, int room_ID, int isStart, String competitorIP) {
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.numberOfMatch = isStart;
        this.competitor = competitor;
        this.competitorIP = competitorIP;
        
        //set Background
        this.setTitle("Bài Tập Nhóm Super");
        setLayout(null);
        lbl_test.setIcon(ResizeImage(String.valueOf("assets/background/bg3.jpg")));
        //set image user
//        lbl_user1.setIcon(ResizeImage2(String.valueOf("assets/background/play_on.png")));
        lbl_user2.setIcon(ResizeImage2(String.valueOf("assets/background/avatar_1.jpg")));
        //Set layout dạng lưới cho panel chứa button
        jPanel1.setLayout(new GridLayout(size, size,1,1));
        //Setup play button
        button = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button[i][j] = new JButton("");
                button[i][j].setBackground(Color.white);
                // set up button
                ImageIcon icon = new ImageIcon("assets/image/border_1.jpg");
                Image img = icon.getImage();
                Image newImg = img.getScaledInstance(34, 36, img.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(newImg);
                button[i][j].setDisabledIcon(image);
                jPanel1.add(button[i][j]);
            }
        }
        startMusic("assets/sound/nhac1.wav");
        //SetUp play Matrix
        competitorMatrix = new int[size][size];
        matrix = new int[size][size];
        userMatrix = new int[size][size];
        
        //Setup UI
        normalItem = new String[2];
        normalItem[1] = "assets/image/o2.jpg";
        normalItem[0] = "assets/image/x2.jpg";
        winItem = new String[2];
        winItem[1] = "assets/image/owin.jpg";
        winItem[0] = "assets/image/xwin.jpg";
        iconItem = new String[2];
        iconItem[1] = "assets/image/o3.jpg";
        iconItem[0] = "assets/image/x3.jpg";
        preItem = new String[2];
        preItem[1] = "assets/image/o2_pre.jpg";
        preItem[0] = "assets/image/x2_pre.jpg";
        setupButton();
        //Set up phòng chơi
        //suer1
        txt_ten1.setText(Client.player.getNickname());
        System.out.println("Hình là " + Client.player.getAvatar());
        lbl_user1.setIcon(ResizeImage2(String.valueOf("assets/user/"+Client.player.getAvatar().trim()+"")));
        //user2
        txt_ten2.setText(competitor.getNickname());
        System.out.println("Hình là " + competitor.getAvatar());
        lbl_user2.setIcon(ResizeImage2(String.valueOf("assets/user/"+competitor.getAvatar().trim()+"")));
        
        
        
    }

    private Game_With_People() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ImageIcon ResizeImage (String link)
    {
        ImageIcon icon = new ImageIcon(link);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(lbl_test.getWidth(), lbl_test.getHeight(), img.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
    
    public ImageIcon ResizeImage2 (String link)
    {
        ImageIcon icon = new ImageIcon(link);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(lbl_user1.getWidth(), lbl_user1.getHeight(), img.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
    
    public void setEnableButton(boolean b) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0) {
                    button[i][j].setEnabled(b);
                }
            }
        }
    }
     
    public int not(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 0) {
            return 1;
        }
        return 0;
    }
    
    void setupButton() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int a = i, b = j;
                button[a][b].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            button[a][b].setDisabledIcon(new ImageIcon(normalItem[not(numberOfMatch % 2)]));
                            button[a][b].setEnabled(false);
                            second = 60;
                            minute = 0;
                            matrix[a][b] = 1;
                            userMatrix[a][b]=1;
                            button[a][b].setEnabled(false);
                            Click_Sound();
                            
                            System.out.println("number là = " + numberOfMatch); 
                            if(checkRowWin()==1||checkColumnWin()==1||checkRightCrossWin()==1||checkLeftCrossWin()==1)
                            {
                                win_sound();
                                setEnableButton(false);
                                Client.socketHandle.write("win,"+a+","+b);
                            }
                            else
                            {
                                 Client.socketHandle.write("caro,"+a+","+b);
                            }
                            setEnableButton(false);
                        } 
                        catch (Exception ex) 
                        {
                            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                        }
                    }
                });
                button[a][b].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        if(button[a][b].isEnabled()) {
                            button[a][b].setBackground(Color.GREEN);
                            button[a][b].setIcon(new ImageIcon(normalItem[not(numberOfMatch % 2)]));
                        }
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        if(button[a][b].isEnabled()){
                            button[a][b].setBackground(null);
                            // set up button
                            ImageIcon icon = new ImageIcon("assets/image/border_1.jpg");
                            Image img = icon.getImage();
                            Image newImg = img.getScaledInstance(35, 35, img.SCALE_SMOOTH);
                            ImageIcon image = new ImageIcon(newImg);
                            //
                            button[a][b].setIcon(image);
                        }
                    }
                });
            }
        }
    }
    // Hàm làm lại
    public int check_Row ()
    {
        int win = 0;
        List<JButton> list = new ArrayList<>();
        for(int i = 0; i < size; i++)
        {
            int row = 0;
            for(int j = 0; j < size; j++)
            {
                if(competitorMatrix[i][j] == 1)
                {
                    row++;
                    list.add(button[i][j]);
                    if (row > 4) 
                    {
                        for (JButton jButton : list) 
                        {
                            jButton.setDisabledIcon(new ImageIcon(winItem[(numberOfMatch % 2)]));
                        }
                        win = 1;
                        break;
                    }
                }
                else
                {
                    row = 0;
                    list.clear();
                }
            }
            list.clear();
        }
        return win;
    }
    
    public int check_Col ()
    {
        int win = 0;
        List<JButton> list = new ArrayList<>();
        for(int i = 0; i < size; i++)
        {
            int row = 0;
            for(int j = 0; j < size; j++)
            {
                if(competitorMatrix[j][i] == 1)
                {
                    row++;
                    list.add(button[j][i]);
                    if (row > 4) 
                    {
                        for (JButton jButton : list) 
                        {
                            jButton.setDisabledIcon(new ImageIcon(winItem[(numberOfMatch % 2)]));
                        }
                        win = 1;
                        break;
                    }
                }
                else
                {
                    row = 0;
                    list.clear();
                }
            }
            list.clear();
        }
        return win;
        
    }
    
    public int check_Diagonal_Left ()
    {
        int win = 0;
        for (int i = 0; i <= size - 5; i++) 
        {
            for (int j = size - 1; j >= 4; j--) {
                int Diagonal = 0;
                List<JButton> list = new ArrayList<>();
                for (int k = 0; k < 5; k++) {
                    if (competitorMatrix[i+k][j-k] == 1) {
                        Diagonal++;
                        list.add(button[i+k][j-k]);
                        if (Diagonal == 5) {
                            for (JButton jButton : list) 
                            {
                                jButton.setDisabledIcon(new ImageIcon(winItem[(numberOfMatch % 2)]));
                            }
                            return 1;
                        }
                    } 
                    else 
                    {
                        Diagonal = 0;
                        list.clear();
                    }
                }
            }
        }
        return win;
    }
    
    public int check_Diagonal_Right ()
    {
        int win = 0;
        for (int i = 0; i <= size - 5; i++) 
        {
            for (int j = 0; j <= size - 5; j++) 
            {
                int cheop = 0;
                List<JButton> list = new ArrayList<>();
                for (int k = 0; k < 5; k++) 
                {
                    if (competitorMatrix[i+k][j+k] == 1) 
                    {
                        cheop++;
                        list.add(button[i+k][j+k]);
                        if (cheop == 5) 
                        {
                            for (JButton jButton : list) 
                            {
                                jButton.setDisabledIcon(new ImageIcon(winItem[(numberOfMatch % 2)]));
                            }
                            return 1;
                        }
                    } 
                    else 
                    {
                        cheop = 0;
                        list.clear();
                    }
                }
            }
        }
        return win;
    }
    
    
    // Check Win
    public int checkRowWin() {
        int win = 0, hang = 0, n = 0, k = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (check) {
                    if (userMatrix[i][j] == 1) {
                        hang++;
                        list.add(button[i][j]);
                        if (hang > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        hang = 0;
                    }
                }
                if (userMatrix[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    hang++;
                } else {
                    list = new ArrayList<>();
                    check = false;
                }
            }
            list = new ArrayList<>();
            hang = 0;
        }
        return win;
    }

    public int checkColumnWin() {
        int win = 0, cot = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (check) {
                    if (userMatrix[i][j] == 1) {
                        cot++;
                        list.add(button[i][j]);
                        if (cot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        check = false;
                        cot = 0;
                        list = new ArrayList<>();
                    }
                }
                if (userMatrix[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    cot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            cot = 0;
        }
        return win;
    }

    public int checkRightCrossWin() {
        int win = 0, cheop = 0, n = 0, k = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = size-1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (check) {
                    if (n>=j && userMatrix[n - j][j] == 1) {
                        cheop++;
                        list.add(button[n - j][j]);
                        if (cheop > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheop = 0;
                    }
                }
                if (userMatrix[i][j] == 1) {
                    n = i + j;
                    check = true;
                    list.add(button[i][j]);
                    cheop++;
                } else {
                    check = false;
                    list = new ArrayList<>();
                }
            }
            cheop = 0;
            check = false;
            list = new ArrayList<>();
        }
        return win;
    }
    //
    public int checkLeftCrossWin() {
        int win = 0, cheot = 0, n = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = size-1; j >= 0; j--) {
                if (check) {
                    if (n - j - 2 * cheot>=0 && userMatrix[n - j - 2 * cheot][j] == 1) {
                        list.add(button[n - j - 2 * cheot][j]);
                        cheot++;
                        System.out.print("+" + j);
                        if (cheot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheot = 0;
                    }
                }
                if (userMatrix[i][j] == 1) {
                    list.add(button[i][j]);
                    n = i + j;
                    check = true;
                    cheot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            n = 0;
            cheot = 0;
            check = false;
        }
        return win;
    }
    
    // Xử lý nhạc
    public void Click_Sound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/sound/click.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    public void startMusic(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            // giảm âm lượng
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-8.0f); // giảm âm lượng xuống 50%
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
    public void win_sound ()
    {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/sound/sound_win.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    public void lose_sound ()
    {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/sound/sound_lose.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    
    // Xử lý khác
    public void newgame() {
        if (numberOfMatch % 2 == 0) {
            JOptionPane.showMessageDialog(rootPane, "Đến lượt bạn đi trước");
//            startTimer();
//            displayUserTurn();
//            timerjLabel19.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Đối thủ đi trước");
//            displayCompetitorTurn();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ImageIcon icon = new ImageIcon("assets/image/blank.jpg");
                Image img = icon.getImage();
                Image newImg = img.getScaledInstance(34, 36, img.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(newImg);
                
                
                button[i][j].setIcon(image);
                button[i][j].setDisabledIcon(image);
                button[i][j].setText("");
                competitorMatrix[i][j] = 0;
                matrix[i][j] = 0;
                userMatrix[i][j] = 0;
                //  
            }
        }
        setEnableButton(true);
        
        if(numberOfMatch % 2 != 0){
            blockgame();
        }
        
//        jLabel3.setIcon(new ImageIcon(iconItem[numberOfMatch % 2]));
//        jLabel5.setIcon(new ImageIcon(iconItem[not(numberOfMatch % 2)]));
        preButton = null;
        numberOfMatch++;
    }
    
    public void updateNumberOfGame(){
        competitor.setNumberOfGame(competitor.getNumberOfGame() + 1);
//        jLabel16.setText(Integer.toString(competitor.getNumberOfGame()));
        Client.player.setNumberOfGame(Client.player.getNumberOfGame() + 1);
//        jLabel13.setText(Integer.toString(Client.user.getNumberOfGame()));
    }
    
    
    
    public void addCompetitorMove(String x, String y)
    {
        setEnableButton(true);
        caro(x, y);
    }
    
    public void caro(String x, String y) {
        int xx, yy;
        xx = Integer.parseInt(x);
        yy = Integer.parseInt(y);
        // danh dau vi tri danh
        competitorMatrix[xx][yy] = 1;
        matrix[xx][yy] = 1;
        button[xx][yy].setEnabled(false);
        if(preButton!=null){
            preButton.setDisabledIcon(new ImageIcon(normalItem[numberOfMatch % 2]));
        }
//        preButton = button[xx][yy];
        // Buoc đi vừa đánh
        button[xx][yy].setDisabledIcon(new ImageIcon(normalItem[numberOfMatch % 2]));
        if(check_Row() == 1 || check_Col() == 1 || check_Diagonal_Left() == 1 || check_Diagonal_Right() ==1 )
        {
            setEnableButton(false);
//            increaseWinMatchToCompetitor();
//            Client.openView(Client.View.GAMENOTICE,"Bạn đã thua","Đang thiết lập ván chơi mới");
        }
    }
    
    public void blockgame() {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            button[i][j].setBackground(Color.white);
            ImageIcon icon = new ImageIcon("assets/image/border.jpg");
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(34, 36, img.SCALE_SMOOTH);
            ImageIcon image = new ImageIcon(newImg);
            
            button[i][j].setDisabledIcon(image);
            button[i][j].setText("");
            competitorMatrix[i][j] = 0;
            matrix[i][j] = 0;
        }
    }
    setEnableButton(false);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_vanchoi = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btn_off = new Custom_Control.Button_gradient();
        button_gradient1 = new Custom_Control.Button_gradient();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbl_test = new javax.swing.JLabel();
        lbl_text = new javax.swing.JLabel();
        txt_ten2 = new javax.swing.JLabel();
        txt_ten1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbl_user2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lbl_user1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        setForeground(new java.awt.Color(153, 204, 255));
        setSize(new java.awt.Dimension(1300, 800));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Số ván thắng");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Số ván chơi");

        txt_vanchoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_vanchoi.setText("Số ván chơi");

        jPanel1.setBackground(new java.awt.Color(163, 194, 30));
        jPanel1.setForeground(new java.awt.Color(163, 194, 30));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 750));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );

        btn_off.setText("Xin hòa");
        btn_off.setColor1(new java.awt.Color(0, 204, 0));
        btn_off.setColor2(new java.awt.Color(51, 255, 255));
        btn_off.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_offActionPerformed(evt);
            }
        });

        button_gradient1.setText("Xin hòa");
        button_gradient1.setColor1(new java.awt.Color(0, 204, 0));
        button_gradient1.setColor2(new java.awt.Color(51, 255, 255));
        button_gradient1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_gradient1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Số ván chơi");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Số ván thắng");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Số ván chơi");

        txt_ten2.setText("jLabel1");

        txt_ten1.setText("jLabel1");

        jPanel2.setBackground(new java.awt.Color(0, 255, 51));
        jPanel2.setForeground(new java.awt.Color(102, 255, 102));
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 200));

        lbl_user2.setBackground(new java.awt.Color(204, 153, 0));
        lbl_user2.setText("user_2");
        lbl_user2.setPreferredSize(new java.awt.Dimension(150, 150));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lbl_user2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(lbl_user2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel3.setBackground(new java.awt.Color(0, 255, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(200, 200));

        lbl_user1.setText("User_1");
        lbl_user1.setPreferredSize(new java.awt.Dimension(150, 150));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(lbl_user1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lbl_user1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_off, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txt_ten1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_vanchoi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(117, 117, 117)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(lbl_test, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(158, 158, 158)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_text)
                                .addGap(150, 150, 150))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(button_gradient1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_ten2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(lbl_text))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txt_ten2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6)
                        .addGap(10, 10, 10)
                        .addComponent(button_gradient1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(236, 236, 236)
                        .addComponent(lbl_test, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_ten1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_vanchoi)
                .addGap(10, 10, 10)
                .addComponent(jLabel4)
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addGap(10, 10, 10)
                .addComponent(btn_off, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button_gradient1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_gradient1ActionPerformed
        // TODO add your handling code here:
        startMusic("assets/sound/nhac1.wav");
    }//GEN-LAST:event_button_gradient1ActionPerformed

    private void btn_offActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_offActionPerformed
        // TODO add your handling code here:
        stopMusic();
    }//GEN-LAST:event_btn_offActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game_With_People.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game_With_People.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game_With_People.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game_With_People.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game_With_People().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Custom_Control.Button_gradient btn_off;
    private javax.swing.ButtonGroup buttonGroup1;
    private Custom_Control.Button_gradient button_gradient1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_test;
    private javax.swing.JLabel lbl_text;
    private javax.swing.JLabel lbl_user1;
    private javax.swing.JLabel lbl_user2;
    private javax.swing.JLabel txt_ten1;
    private javax.swing.JLabel txt_ten2;
    private javax.swing.JLabel txt_vanchoi;
    // End of variables declaration//GEN-END:variables

   
}
