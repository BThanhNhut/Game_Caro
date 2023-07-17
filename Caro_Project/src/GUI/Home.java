/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Caro_Project_Client.Client;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author THANH NHUT
 */
public class Home extends javax.swing.JFrame {


    
    public Home() {
        initComponents();
        // đặt vị trí của JFrame ở giữa màn hình
        setLocationRelativeTo(null);
        lbl_bg.setIcon(ResizeImage(String.valueOf("assets/background/bg8.jpg")));
      
        
        txt_choinhanh.addMouseListener(new MouseAdapter() {
            // Khi chuột được đưa vào JLabel
            public void mouseEntered(MouseEvent e) {
                Click_Sound();
                Font font = txt_choinhanh.getFont();
                float size = font.getSize() + 4.0f;
                txt_choinhanh.setFont(font.deriveFont(size));
                txt_choinhanh.setForeground(Color.GREEN);
                
            }
            // Khi chuột được đưa ra khỏi JLabel
            public void mouseExited(MouseEvent e) {
                Click_Sound();
                Font font = txt_choinhanh.getFont();
                float size = font.getSize() - 4.0f;
                txt_choinhanh.setFont(font.deriveFont(size));
                txt_choinhanh.setForeground(Color.WHITE);
            }
        });
        
        txt_taophong.addMouseListener(new MouseAdapter() {
            // Khi chuột được đưa vào JLabel
            public void mouseEntered(MouseEvent e) {
                Click_Sound();
                Font font = txt_taophong.getFont();
                float size = font.getSize() + 4.0f;
                txt_taophong.setFont(font.deriveFont(size));
                txt_taophong.setForeground(Color.GREEN);
                
            }
            // Khi chuột được đưa ra khỏi JLabel
            public void mouseExited(MouseEvent e) {
                Click_Sound();
                Font font = txt_taophong.getFont();
                float size = font.getSize() - 4.0f;
                txt_taophong.setFont(font.deriveFont(size));
                txt_taophong.setForeground(Color.WHITE);
            }
        });
        
        txt_ghepphong.addMouseListener(new MouseAdapter() {
            // Khi chuột được đưa vào JLabel
            public void mouseEntered(MouseEvent e) {
                Click_Sound();
                Font font = txt_ghepphong.getFont();
                float size = font.getSize() + 4.0f;
                txt_ghepphong.setFont(font.deriveFont(size));
                txt_ghepphong.setForeground(Color.GREEN);
                
            }
            // Khi chuột được đưa ra khỏi JLabel
            public void mouseExited(MouseEvent e) {
                Click_Sound();
                Font font = txt_ghepphong.getFont();
                float size = font.getSize() - 4.0f;
                txt_ghepphong.setFont(font.deriveFont(size));
                txt_ghepphong.setForeground(Color.WHITE);
            }
        });
        
        txt_huongdan.addMouseListener(new MouseAdapter() {
            // Khi chuột được đưa vào JLabel
            public void mouseEntered(MouseEvent e) {
                Click_Sound();
                Font font = txt_huongdan.getFont();
                float size = font.getSize() + 4.0f;
                txt_huongdan.setFont(font.deriveFont(size));
                txt_huongdan.setForeground(Color.GREEN);
                
            }
            // Khi chuột được đưa ra khỏi JLabel
            public void mouseExited(MouseEvent e) {
                Click_Sound();
                Font font = txt_huongdan.getFont();
                float size = font.getSize() - 4.0f;
                txt_huongdan.setFont(font.deriveFont(size));
                txt_huongdan.setForeground(Color.WHITE);
            }
        });
        
        txt_huongdan1.addMouseListener(new MouseAdapter() {
            // Khi chuột được đưa vào JLabel
            public void mouseEntered(MouseEvent e) {
                Click_Sound();
                Font font = txt_huongdan1.getFont();
                float size = font.getSize() + 4.0f;
                txt_huongdan1.setFont(font.deriveFont(size));
                txt_huongdan1.setForeground(Color.GREEN);
                
            }
            // Khi chuột được đưa ra khỏi JLabel
            public void mouseExited(MouseEvent e) {
                Click_Sound();
                Font font = txt_huongdan1.getFont();
                float size = font.getSize() - 4.0f;
                txt_huongdan1.setFont(font.deriveFont(size));
                txt_huongdan1.setForeground(Color.WHITE);
            }
        });
        
        
    }
    
    public ImageIcon ResizeImage (String link)
    {
        ImageIcon icon = new ImageIcon(link);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(lbl_bg.getWidth(), lbl_bg.getHeight(), img.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
//    public ImageIcon ResizeImage2 (String link)
//    {
//        ImageIcon icon = new ImageIcon(link);
//        Image img = icon.getImage();
//        Image newImg = img.getScaledInstance(lbl_play.getWidth(), lbl_play.getHeight(), img.SCALE_SMOOTH);
//        ImageIcon image = new ImageIcon(newImg);
//        return image;
//    }
    
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_choinhanh = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        txt_taophong1 = new javax.swing.JLabel();
        btn_zo = new javax.swing.JPanel();
        txt_huongdan = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txt_ghepphong = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txt_taophong = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txt_taophong3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txt_huongdan1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        lbl_bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 500));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 102, 0));

        txt_choinhanh.setFont(new java.awt.Font("Sitka Text", 1, 25)); // NOI18N
        txt_choinhanh.setForeground(new java.awt.Color(255, 255, 255));
        txt_choinhanh.setText("Chơi nhanh");
        txt_choinhanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_choinhanhMouseClicked(evt);
            }
        });
        jPanel1.add(txt_choinhanh);

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));

        jPanel8.setBackground(new java.awt.Color(102, 102, 102));
        jPanel7.add(jPanel8);

        txt_taophong1.setFont(new java.awt.Font("Sitka Text", 1, 25)); // NOI18N
        txt_taophong1.setForeground(new java.awt.Color(255, 255, 255));
        txt_taophong1.setText("Tạo Phòng");
        txt_taophong1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_taophong1MouseClicked(evt);
            }
        });
        jPanel7.add(txt_taophong1);

        jPanel1.add(jPanel7);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 190, 30));

        btn_zo.setBackground(new java.awt.Color(255, 102, 0));

        txt_huongdan.setFont(new java.awt.Font("Sitka Text", 1, 25)); // NOI18N
        txt_huongdan.setForeground(new java.awt.Color(255, 255, 255));
        txt_huongdan.setText("Tạo Phòng");
        txt_huongdan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_huongdanMouseClicked(evt);
            }
        });
        btn_zo.add(txt_huongdan);

        getContentPane().add(btn_zo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, 190, 30));

        jPanel5.setBackground(new java.awt.Color(255, 102, 0));

        txt_ghepphong.setFont(new java.awt.Font("Sitka Text", 1, 25)); // NOI18N
        txt_ghepphong.setForeground(new java.awt.Color(255, 255, 255));
        txt_ghepphong.setText("Thoát");
        txt_ghepphong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_ghepphongMouseClicked(evt);
            }
        });
        jPanel5.add(txt_ghepphong);

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 410, 190, 30));

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));

        txt_taophong.setFont(new java.awt.Font("Sitka Text", 1, 25)); // NOI18N
        txt_taophong.setForeground(new java.awt.Color(255, 255, 255));
        txt_taophong.setText("Giới thiệu");
        txt_taophong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_taophongMouseClicked(evt);
            }
        });
        jPanel2.add(txt_taophong);

        jPanel9.setBackground(new java.awt.Color(102, 102, 102));

        jPanel10.setBackground(new java.awt.Color(102, 102, 102));
        jPanel9.add(jPanel10);

        txt_taophong3.setFont(new java.awt.Font("Sitka Text", 1, 25)); // NOI18N
        txt_taophong3.setForeground(new java.awt.Color(255, 255, 255));
        txt_taophong3.setText("Tạo Phòng");
        txt_taophong3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_taophong3MouseClicked(evt);
            }
        });
        jPanel9.add(txt_taophong3);

        jPanel2.add(jPanel9);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, 190, 30));

        jPanel4.setBackground(new java.awt.Color(255, 102, 0));

        txt_huongdan1.setFont(new java.awt.Font("Sitka Text", 1, 25)); // NOI18N
        txt_huongdan1.setForeground(new java.awt.Color(255, 255, 255));
        txt_huongdan1.setText("Tìm phòng");
        txt_huongdan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_huongdan1MouseClicked(evt);
            }
        });
        jPanel4.add(txt_huongdan1);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, 190, 30));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("<<Tin nhắn và tin tức>>\n");
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 260, 120));

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 180, -1));

        jButton10.setText("Gửi");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, -1, -1));

        lbl_bg.setText("jLabel1");
        getContentPane().add(lbl_bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_choinhanhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_choinhanhMouseClicked
        // TODO add your handling code here:
        Game_With_Computer frm = new Game_With_Computer();
        frm.setVisible(true);
    }//GEN-LAST:event_txt_choinhanhMouseClicked

    private void txt_ghepphongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_ghepphongMouseClicked
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            Client.socketHandle.write("quick-room,");
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txt_ghepphongMouseClicked

    private void txt_taophong1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_taophong1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_taophong1MouseClicked

    private void txt_taophongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_taophongMouseClicked
        // TODO add your handling code here:
        System.out.println("Ckick thanh cong");
        try {
            Client.socketHandle.write("create-room,");
            Client.closeAllViews();
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txt_taophongMouseClicked

    private void txt_taophong3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_taophong3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_taophong3MouseClicked

    private void txt_huongdanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_huongdanMouseClicked
        // TODO add your handling code here:
        System.out.println("Ckick thanh cong");
        try {
            Client.socketHandle.write("wait-room,");
            Client.closeAllViews();
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_txt_huongdanMouseClicked

    private void txt_huongdan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_huongdan1MouseClicked
        // TODO add your handling code here:
        try {
            Client.socketHandle.write("go-room,");
            Client.closeAllViews();
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txt_huongdan1MouseClicked

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if(evt.getKeyCode() == 10){
            sendMessage();
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        sendMessage();
        System.out.println("Ckick thanh cong");
    }//GEN-LAST:event_jButton10ActionPerformed
    private void sendMessage(){
        try {
            if (jTextField1.getText().isEmpty()) {
                throw new Exception("Vui lòng nhập nội dung tin nhắn");
            }
            String temp = jTextArea1.getText();
            temp += "Tôi: " + jTextField1.getText() + "\n";
            jTextArea1.setText(temp);
            Client.socketHandle.write("chat-server," + jTextField1.getText());
            jTextField1.setText("");
            jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }
    /**
     * @param args the command line arguments
     */
    public void addMessage(String message){
        String temp = jTextArea1.getText();
        temp+=message+"\n";
        jTextArea1.setText(temp);
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }
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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btn_zo;
    private javax.swing.JButton jButton10;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lbl_bg;
    private javax.swing.JLabel txt_choinhanh;
    private javax.swing.JLabel txt_ghepphong;
    private javax.swing.JLabel txt_huongdan;
    private javax.swing.JLabel txt_huongdan1;
    private javax.swing.JLabel txt_taophong;
    private javax.swing.JLabel txt_taophong1;
    private javax.swing.JLabel txt_taophong3;
    // End of variables declaration//GEN-END:variables
}
