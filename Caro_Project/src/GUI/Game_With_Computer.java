/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;
// Custtom
import Custom_Control.Button;
//
import DTO.Player;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import static sun.jvm.hotspot.oops.CellTypeState.bottom;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Custom_Control.Point;
import Custom_Control.XOButton;
import DTO.Player;

/**
 *
 * @author THANH NHUT
 */
public class Game_With_Computer extends javax.swing.JFrame {

    private Player competitor;
    private JButton[][] button;
    private int[][] competitorMatrix;
    private int[][] matrix;
    private int[][] userMatrix;
    
    //Set kích thước ma trận
     private final int size = 20;
     
    // Server Socket
    private Timer timer;
    private Integer second, minute;
    
    private int numberOfMatch;
    private String normalItem[];
    private String winItem[];
    private String iconItem[];
    private String preItem[];
    
    // AI
    private int row =20;
    private int col = 20;
    public XOButton[][] Buttons = new XOButton[col][row];
    private ArrayList<Point> availablesPoint = new ArrayList<Point>();
    private static final int winScore = 100000000;
    private int gameNumber = 0;
    private int userWin = 0;
    private int aIWin = 0;
    XOButton preButton;
    
    public Game_With_Computer() {
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Bài Tập Nhóm Super");
        //set Background
        setLayout(null);
        lbl_test.setIcon(ResizeImage(String.valueOf("assets/background/bg3.jpg")));
        //set image user
        lbl_user1.setIcon(ResizeImage2(String.valueOf("assets/background/avatar_1.jpg")));
        lbl_user2.setIcon(ResizeImage2(String.valueOf("assets/background/avatar_1.jpg")));
        //Set layout dạng lưới cho panel chứa button
        jPanel1.setLayout(new GridLayout(size, size));
        //Setup play button
        Buttons = new XOButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //Buttons[i][j].setBackground(Color.white);
                // set up button
                ImageIcon icon = new ImageIcon("assets/image/border_1.jpg");
                Image img = icon.getImage();
                Image newImg = img.getScaledInstance(34, 36, img.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(newImg);
                //Buttons[i][j].setIcon(image);
                //jPanel1.add(Buttons[i][j]);
                
                Point point = new Point(i, j);
                Buttons[i][j] = new XOButton(i, j);
                Buttons[i][j].addMouseListener( new MouseListener() {

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // TODO Auto-generated method stub
                        handleClickButton(point);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // TODO Auto-generated method stub

                    }
                });
                jPanel1.add(Buttons[i][j]);
                availablesPoint.add(point);
            }
        }
        gameNumber++;
        preButton=null;
        
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
        
        //function
        //setupButton();
        
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
        Image newImg = img.getScaledInstance(200, 200, img.SCALE_SMOOTH);
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
                            System.out.println("Chiều rộng là = "+ button[a][b].getWidth()+ "Chiều dài là = "+button[a][b].getHeight());
                            button[a][b].setDisabledIcon(new ImageIcon(normalItem[not(numberOfMatch % 2)]));
                            button[a][b].setEnabled(false);
                            second = 60;
                            minute = 0;
                            matrix[a][b] = 1;
                            userMatrix[a][b] = 1;
                            button[a][b].setEnabled(false);
                            numberOfMatch++;
                        } catch (Exception ex) {
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
    
//    private void updateScore(){
//        jLabel20.setText("Tỉ số: "+userWin+"-"+aIWin);
//    }
    private void handleClickButton(Point point) {

        // TODO: CALC LOGIC HERE
        point.log();
        Buttons[point.x][point.y].setState(true);
        Buttons[point.x][point.y].setEnabled(false);
        if (getScore(getMatrixBoard(), true, false) >= winScore) {
            JOptionPane.showMessageDialog(null, "Bạn đã thắng");
            userWin++;
            //updateScore();
            //displayUserWin();
            newGame();
            return;
        }

        int nextMoveX = 0, nextMoveY = 0;
        int[] bestMove = calcNextMove(3);

        if (bestMove != null) {
            nextMoveX = bestMove[0];
            nextMoveY = bestMove[1];
        }
        Buttons[nextMoveX][nextMoveY].setState(false);
        Buttons[nextMoveX][nextMoveY].setEnabled(false);
        if (getScore(getMatrixBoard(), false, true) >= winScore) {
            JOptionPane.showMessageDialog(null, "Bạn đã thua");
            aIWin++;
            //updateScore();
            //displayAIWin();
            newGame();
        }
    }

    private void newGame(){
        for (int i = 0; i < Buttons.length; i++) {
            for (int j = 0; j < Buttons.length; j++) {
                Buttons[i][j].resetState();
            }
        }
        
        gameNumber++;
        if (gameNumber % 2 == 0) {
            JOptionPane.showMessageDialog(rootPane, "Máy đi trước", "Ván mới",JOptionPane.INFORMATION_MESSAGE);
            Buttons[9][9].setState(false);
            Buttons[9][9].setEnabled(false);

            if (getScore(getMatrixBoard(), false, true) >= winScore) {
                JOptionPane.showMessageDialog(null, "Bạn đã thua");
                newGame();
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Bạn đi trước", "Ván mới",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
//    private void displayUserWin(){
//        String tmp = jTextArea1.getText();
//        tmp+="--Bạn đã thắng, tỉ số hiện tại là "+userWin+"-"+aIWin+"--\n";
//        jTextArea1.setText(tmp);
//        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
//    }
//    private void displayAIWin(){
//        String tmp = jTextArea1.getText();
//        tmp+="--Máy thắng, tỉ số hiện tại là "+userWin+"-"+aIWin+"--\n";
//        jTextArea1.setText(tmp);
//        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
//    }
    

    public int[] calcNextMove(int depth) {
        int[][] board = getMatrixBoard();
        Object[] bestMove = searchWinningMove(board);
        Object[] badMove = searchLoseMove(board);

        int[] move = new int[2];

        if (badMove[1] != null && badMove[2] != null) {

            move[0] = (Integer) (badMove[1]);
            move[1] = (Integer) (badMove[2]);
            return move;
        }

        if (bestMove[1] != null && bestMove[2] != null) {

            move[0] = (Integer) (bestMove[1]);
            move[1] = (Integer) (bestMove[2]);

        } else {

            bestMove = minimaxSearchAB(depth, board, true, -1.0, winScore);
            if (bestMove[1] == null) {
                move = null;
            } else {
                move[0] = (Integer) (bestMove[1]);
                move[1] = (Integer) (bestMove[2]);
            }
        }
        return move;
    }

    public int[][] playNextMove(int[][] board, int[] move, boolean isUserTurn) {
        int i = move[0], j = move[1];
        int[][] newBoard = new int[row][col];
        for (int h = 0; h < row; h++) {
            for (int k = 0; k < col; k++) {
                newBoard[h][k] = board[h][k];
            }
        }
        newBoard[i][j] = isUserTurn ? 2 : 1;
        return newBoard;
    }

    private Object[] searchWinningMove(int[][] matrix) {
        ArrayList<int[]> allPossibleMoves = generateMoves(matrix);
        System.out.println(allPossibleMoves.size());

        Object[] winningMove = new Object[3];

        for (int[] move : allPossibleMoves) {
            int[][] dummyBoard = playNextMove(matrix, move, false);

            // If the white player has a winning score in that temporary board, return the move.
            if (getScore(dummyBoard, false, false) >= winScore) {
                winningMove[1] = move[0];
                winningMove[2] = move[1];
                return winningMove;
            }
        }

        return winningMove;
    }

    private Object[] searchLoseMove(int[][] matrix) {
        ArrayList<int[]> allPossibleMoves = generateMoves(matrix);
        System.out.println(allPossibleMoves.size());

        Object[] losingMove = new Object[3];

        for (int[] move : allPossibleMoves) {
            int[][] dummyBoard = playNextMove(matrix, move, true);

            // If the white player has a winning score in that temporary board, return the move.
            if (getScore(dummyBoard, true, false) >= winScore) {
                losingMove[1] = move[0];
                losingMove[2] = move[1];
                return losingMove;
            }
        }

        return losingMove;
    }

    public Object[] minimaxSearchAB(int depth, int[][] board, boolean max, double alpha, double beta) {
        if (depth == 0) {
            Object[] x = {evaluateBoardForWhite(board, !max), null, null};
            return x;
        }

        ArrayList<int[]> allPossibleMoves = generateMoves(board);

        if (allPossibleMoves.size() == 0) {

            Object[] x = {evaluateBoardForWhite(board, !max), null, null};

            return x;
        }

        Object[] bestMove = new Object[3];

        if (max) {
            bestMove[0] = -1.0;

            for (int[] move : allPossibleMoves) {
                // Chơi thử với move hiện tại
                int[][] dummyBoard = playNextMove(board, move, false);

                Object[] tempMove = minimaxSearchAB(depth - 1, dummyBoard, !max, alpha, beta);

                // Cập nhật alpha
                if ((Double) (tempMove[0]) > alpha) {
                    alpha = (Double) (tempMove[0]);
                }
                // Cắt tỉa beta
                if ((Double) (tempMove[0]) >= beta) {
                    return tempMove;
                }
                if ((Double) tempMove[0] > (Double) bestMove[0]) {
                    bestMove = tempMove;
                    bestMove[1] = move[0];
                    bestMove[2] = move[1];
                }
            }

        } else {
            bestMove[0] = 100000000.0;
            bestMove[1] = allPossibleMoves.get(0)[0];
            bestMove[2] = allPossibleMoves.get(0)[1];
            for (int[] move : allPossibleMoves) {
                int[][] dummyBoard = playNextMove(board, move, true);

                Object[] tempMove = minimaxSearchAB(depth - 1, dummyBoard, !max, alpha, beta);

                // Cập nhật beta
                if (((Double) tempMove[0]) < beta) {
                    beta = (Double) (tempMove[0]);
                }
                // Cắt tỉa alpha
                if ((Double) (tempMove[0]) <= alpha) {
                    return tempMove;
                }
                if ((Double) tempMove[0] < (Double) bestMove[0]) {
                    bestMove = tempMove;
                    bestMove[1] = move[0];
                    bestMove[2] = move[1];
                }
            }
        }
        return bestMove;
    }

    public double evaluateBoardForWhite(int[][] board, boolean userTurn) {

        double blackScore = getScore(board, true, userTurn);
        double whiteScore = getScore(board, false, userTurn);

        if (blackScore == 0) {
            blackScore = 1.0;
        }

        return whiteScore / blackScore;

    }

    public ArrayList<int[]> generateMoves(int[][] boardMatrix) {
        ArrayList<int[]> moveList = new ArrayList<int[]>();

        int boardSize = boardMatrix.length;

        // Tìm những tất cả những ô trống nhưng có đánh XO liền kề
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {

                if (boardMatrix[i][j] > 0) {
                    continue;
                }

                if (i > 0) {
                    if (j > 0) {
                        if (boardMatrix[i - 1][j - 1] > 0
                                || boardMatrix[i][j - 1] > 0) {
                            int[] move = {i, j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if (j < boardSize - 1) {
                        if (boardMatrix[i - 1][j + 1] > 0
                                || boardMatrix[i][j + 1] > 0) {
                            int[] move = {i, j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if (boardMatrix[i - 1][j] > 0) {
                        int[] move = {i, j};
                        moveList.add(move);
                        continue;
                    }
                }
                if (i < boardSize - 1) {
                    if (j > 0) {
                        if (boardMatrix[i + 1][j - 1] > 0
                                || boardMatrix[i][j - 1] > 0) {
                            int[] move = {i, j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if (j < boardSize - 1) {
                        if (boardMatrix[i + 1][j + 1] > 0
                                || boardMatrix[i][j + 1] > 0) {
                            int[] move = {i, j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if (boardMatrix[i + 1][j] > 0) {
                        int[] move = {i, j};
                        moveList.add(move);
                        continue;
                    }
                }

            }
        }
        return moveList;

    }

    // Đánh giá bàn cờ dựa trên tổng số điểm hàng ngan, hàng dọc, và 2 đường chéo
    public int getScore(int[][] board, boolean forX, boolean blacksTurn) {

        return evaluateHorizontal(board, forX, blacksTurn)
                + evaluateVertical(board, forX, blacksTurn)
                + evaluateDiagonal(board, forX, blacksTurn);
    }

    public static int evaluateHorizontal(int[][] boardMatrix, boolean forX, boolean playersTurn) {

        int consecutive = 0;
        int blocks = 2;
        int score = 0;

        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[0].length; j++) {

                if (boardMatrix[i][j] == (forX ? 2 : 1)) {
                    //2. Đếm...
                    consecutive++;
                } // gặp ô trống
                else if (boardMatrix[i][j] == 0) {
                    if (consecutive > 0) {
                        // Ra: Ô trống ở cuối sau khi đếm. Giảm block rồi bắt đầu tính điểm sau đó reset lại ban đầu
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        // 1. Vào reset lại blocks = 1 rồi bắt đầu đếm
                        blocks = 1;
                    }
                } //gặp quân địch
                else if (consecutive > 0) {
                    // 2.Ra:  Ô bị chặn sau khi đếm. Tính điểm sau đó reset lại.
                    score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    //1. Vào: reset lại blocks = 2 rồi bắt đầu đếm
                    blocks = 2;
                }
            }

            // 3. Ra: nhưng lúc này đang ở cuối. Nếu liên tục thì vẫn tính cho đến hết dòng
            if (consecutive > 0) {
                score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);

            }
            // reset lại để tiếp tục chạy cho dòng tiếp theo
            consecutive = 0;
            blocks = 2;

        }
        return score;
    }
    // hàm tính toán đường dọc tương tự như đường ngan

    public static int evaluateVertical(int[][] boardMatrix, boolean forX, boolean playersTurn) {

        int consecutive = 0;
        int blocks = 2;
        int score = 0;

        for (int j = 0; j < boardMatrix[0].length; j++) {
            for (int i = 0; i < boardMatrix.length; i++) {
                if (boardMatrix[i][j] == (forX ? 2 : 1)) {
                    consecutive++;
                } else if (boardMatrix[i][j] == 0) {
                    if (consecutive > 0) {
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        blocks = 1;
                    }
                } else if (consecutive > 0) {
                    score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    blocks = 2;
                }
            }
            if (consecutive > 0) {
                score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);

            }
            consecutive = 0;
            blocks = 2;

        }
        return score;
    }
    // Hàm tính toán 2 đường chéo tương tự như hàng ngan

    public static int evaluateDiagonal(int[][] boardMatrix, boolean forX, boolean playersTurn) {

        int consecutive = 0;
        int blocks = 2;
        int score = 0;
        // Đường chéo /
        for (int k = 0; k <= 2 * (boardMatrix.length - 1); k++) {
            int iStart = Math.max(0, k - boardMatrix.length + 1);
            int iEnd = Math.min(boardMatrix.length - 1, k);
            for (int i = iStart; i <= iEnd; ++i) {
                int j = k - i;

                if (boardMatrix[i][j] == (forX ? 2 : 1)) {
                    consecutive++;
                } else if (boardMatrix[i][j] == 0) {
                    if (consecutive > 0) {
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        blocks = 1;
                    }
                } else if (consecutive > 0) {
                    score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    blocks = 2;
                }

            }
            if (consecutive > 0) {
                score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);

            }
            consecutive = 0;
            blocks = 2;
        }
        // Đường chéo \
        for (int k = 1 - boardMatrix.length; k < boardMatrix.length; k++) {
            int iStart = Math.max(0, k);
            int iEnd = Math.min(boardMatrix.length + k - 1, boardMatrix.length - 1);
            for (int i = iStart; i <= iEnd; ++i) {
                int j = i - k;

                if (boardMatrix[i][j] == (forX ? 2 : 1)) {
                    consecutive++;
                } else if (boardMatrix[i][j] == 0) {
                    if (consecutive > 0) {
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        blocks = 1;
                    }
                } else if (consecutive > 0) {
                    score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    blocks = 2;
                }

            }
            if (consecutive > 0) {
                score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);

            }
            consecutive = 0;
            blocks = 2;
        }
        return score;
    }

    public static int getConsecutiveSetScore(int count, int blocks, boolean currentTurn) {
        final int winGuarantee = 1000000;
        if (blocks == 2 && count <= 5) {
            return 0;
        }
        switch (count) {
            // Ăn 5 -> Cho điểm cao nhất
            case 5: {
                return winScore;
            }
            case 4: {
                // Đang 4 -> Tuỳ theo lược và bị chặn: winGuarantee, winGuarantee/4, 200
                if (currentTurn) {
                    return winGuarantee;
                } else {
                    if (blocks == 0) {
                        return winGuarantee / 4;
                    } else {
                        return 200;
                    }
                }
            }
            case 3: {
                // Đang 3: Block = 0
                if (blocks == 0) {
                    // Nếu lược của currentTurn thì ăn 3 + 1 = 4 (không bị block) -> 50000 -> Khả năng thắng cao. 
                    // Ngược lại không phải lược của currentTurn thì khả năng bị blocks cao
                    if (currentTurn) {
                        return 50000;
                    } else {
                        return 200;
                    }
                } else {
                    // Block == 1 hoặc Blocks == 2
                    if (currentTurn) {
                        return 10;
                    } else {
                        return 5;
                    }
                }
            }
            case 2: {
                // Tương tự với 2
                if (blocks == 0) {
                    if (currentTurn) {
                        return 7;
                    } else {
                        return 5;
                    }
                } else {
                    return 3;
                }
            }
            case 1: {
                return 1;
            }
        }
        return winScore * 2;
    }

    public int[][] getMatrixBoard() {
        int matrix[][] = new int[row][col];
        for (int i = 0; i < Buttons.length; i++) {
            for (int j = 0; j < Buttons.length; j++) {
                int value = Buttons[i][j].value;
                matrix[i][j] = value;
            }
        }

        return matrix;
    }
    
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        button_gradient2 = new Custom_Control.Button_gradient();
        button_gradient1 = new Custom_Control.Button_gradient();
        lbl_user1 = new javax.swing.JLabel();
        lbl_user2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbl_test = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        setForeground(new java.awt.Color(153, 204, 255));
        setSize(new java.awt.Dimension(1300, 800));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Số ván thắng");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 116, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Số ván chơi");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Số ván chơi");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));

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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        button_gradient2.setText("Xin hòa");
        button_gradient2.setColor1(new java.awt.Color(0, 204, 0));
        button_gradient2.setColor2(new java.awt.Color(51, 255, 255));
        button_gradient2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_gradient2ActionPerformed(evt);
            }
        });
        getContentPane().add(button_gradient2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        button_gradient1.setText("Xin hòa");
        button_gradient1.setColor1(new java.awt.Color(0, 204, 0));
        button_gradient1.setColor2(new java.awt.Color(51, 255, 255));
        button_gradient1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_gradient1ActionPerformed(evt);
            }
        });
        getContentPane().add(button_gradient1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 340, -1, -1));

        lbl_user1.setText("User_1");
        lbl_user1.setPreferredSize(new java.awt.Dimension(200, 200));
        getContentPane().add(lbl_user1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        lbl_user2.setText("user_2");
        lbl_user2.setPreferredSize(new java.awt.Dimension(200, 200));
        getContentPane().add(lbl_user2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 20, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Số ván chơi");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 240, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Số ván thắng");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 300, 116, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Số ván chơi");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 270, -1, -1));
        getContentPane().add(lbl_test, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 800));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button_gradient1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_gradient1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_gradient1ActionPerformed

    private void button_gradient2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_gradient2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_gradient2ActionPerformed

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
            java.util.logging.Logger.getLogger(Game_With_Computer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game_With_Computer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game_With_Computer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game_With_Computer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game_With_Computer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private Custom_Control.Button_gradient button_gradient1;
    private Custom_Control.Button_gradient button_gradient2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_test;
    private javax.swing.JLabel lbl_user1;
    private javax.swing.JLabel lbl_user2;
    // End of variables declaration//GEN-END:variables
}
