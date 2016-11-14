package testSQuares2;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author VPC
 */
public class Player extends JPanel {

    private Game game;
    private Board board;            //Sân chơi hiện tại của game
    private boolean[] houseCoDan;  //Địa chỉ các nhà dân xem có dân bên trong không (1,2,3,4,5) ||  (10,9,8,7,6)
    static String playerName;
    public int currentScore;    //Điểm của người chơi = so Dân + anQUan*5
    public int soDanAnDuoc;
    public int soQuanAnDuoc;
    public int soDan;
    public int anQuan;
    protected int direction;        // Hướng đi đã chọn       
    protected int chosenHouse;     //CHọn nhà
    public int playerSide;         // Có 2 player, =1 nếu là player1, =2 là player2
    Step buocDi;
    int firstHouse;
    int lastHouse;
    public long timeFlag;
    Random random;

    //Khởi tạo player
    public Player(Game game, String name, int playerSide) {
        this.game = game;
        buocDi = new Step();
        this.playerName = name;
        this.board = game.board;
        this.playerSide = playerSide;
        soDan = 0;
        anQuan = 0;
        direction = 0;
        chosenHouse = 0;
        random = new Random();
        houseCoDan = new boolean[12];
        checkBoard();
    }

    //Reset nước đi mỗi lần nhận turn
    public void resetBuocDi() {
        chosenHouse = 0;
        direction = 0;
        this.buocDi.chose = 0;
        this.buocDi.direc = 0;
        if (playerSide == 2) {
            for (int i = 1; i <= 5; i++) {
                board.houses[i].chosen = false;
                board.houses[i].chosenSide = 0;
            }
        }
        if (playerSide == 1) {
            for (int i = 7; i <= 11; i++) {
                board.houses[i].chosen = false;
                board.houses[i].chosenSide = 0;
            }
        }
    }

    //Danh sach cac nha`
    public void setSide(int pS) {
        if (pS == 2) {
            this.firstHouse = 1;
            this.lastHouse = 5;
        } else {
            this.firstHouse = 7;
            this.lastHouse = 11;
        }
    }

    private void checkBoard() {
        for (int i = 1; i <= 5; i++) {
            houseCoDan[i] = (board.houses[i].getDanSo() > 0);
        }
        for (int i = 7; i <= 11; i++) {
            houseCoDan[i] = (board.houses[i].getDanSo() > 0);
        }
    }

    public void turn(long gameTime, Point mousePosition) {
        /* Click chuột vào 1 nhà phía mình đưa vào biến chosenHouse. 
        * Hiện ra 2 nút <-  và -> ở phía trên
        * bấm phím VK_LEFT or VK_RIGHT đưa vào biến direction
        
        Khi Thực hiện xong
        ĐƯa vào buocDi.chose và buocDi.direc để trong tài getStep()
        Cuối cùng trả game.turnToken = 3 hoặc 4 để đến lượt trọng tài
        
        
        if (playerSide = 1) game.turnToken =3
        if (playerSide = 2) game.turnToken =4
         */
        if (Canvas.mouseButtonState(1)) {

            //luot cua p1
            if (playerSide == 2) {
                for (int i = 1; i <= 5; i++) {
                    if ((board.houses[i].getDanSo() > 0) && (board.houses[i].shape.contains(mousePosition))) {
                        for (int j = 1; j <= 5; j++) {
                            board.houses[j].chosen = false;
                        }
                        board.houses[i].chosen = true;  //Nha dc chon to mau` vang
                        chosenHouse = i;              //nha dc chon la i
                    }
                }
            }

            //Luot cua p2
            if (playerSide == 1) {
                for (int i = 7; i <= 11; i++) {
                    if ((board.houses[i].getDanSo() > 0) && (board.houses[i].shape.contains(mousePosition))) {
                        for (int j = 7; j <= 11; j++) {
                            board.houses[j].chosen = false;
                        }
                        board.houses[i].chosen = true;  //Nha dc chon to mau` vang
                        chosenHouse = i;              //nha dc chon la i
                    }
                }
            }
        }

        //Bấm nút chọn nhà
        if (chosenHouse != 0) {
            if (Canvas.mouseButtonState(1)) {
                if (playerSide == 1) {
                    if (new Rectangle(board.houses[chosenHouse].x -104 + 207, board.houses[chosenHouse].y + 128, 71, 32).contains(mousePosition)) {
                        direction = -1;
                        board.houses[chosenHouse].chosenSide = -1;
                    }
                    if (new Rectangle(board.houses[chosenHouse].x -100 + 16, board.houses[chosenHouse].y + 124, 75, 34).contains(mousePosition)) {
                        direction = 1;
                        board.houses[chosenHouse].chosenSide = 1;
                    }
                }
                if (playerSide == 2) {
                    if (new Rectangle(board.houses[chosenHouse].x -100 + 24, board.houses[chosenHouse].y -100  + 40, 69, 30).contains(mousePosition)) {
                        direction = -1;
                        board.houses[chosenHouse].chosenSide = -1;
                    }
                    if (new Rectangle(board.houses[chosenHouse].x -100 + 204, board.houses[chosenHouse].y -100 + 40, 77, 37).contains(mousePosition)) {
                        direction = 1;
                        board.houses[chosenHouse].chosenSide = 1;
                    }
                }
            }
            if (Canvas.keyboardKeyState(KeyEvent.VK_LEFT)) {
                if (this.playerSide == 1) {
                    direction = 1;
                    board.houses[chosenHouse].chosenSide = 1;
                } else {
                    direction = -1;
                    board.houses[chosenHouse].chosenSide = -1;
                }
            }
            if (Canvas.keyboardKeyState(KeyEvent.VK_RIGHT)) {
                if (this.playerSide == 1) {
                    direction = -1;
                    board.houses[chosenHouse].chosenSide = -1;
                } else {
                    direction = 1;
                    board.houses[chosenHouse].chosenSide = 1;
                }
            }
        }

        this.buocDi.chose = chosenHouse;
        this.buocDi.direc = direction;

        if (direction != 0) {
            giveTurnToken(playerSide);
        }

    }

    //Auto khi qua thoi gian
    public void auto() {
        if (chosenHouse != 0)
            board.houses[chosenHouse].chosen = false;
        int temp = 0;
        if (playerSide == 1) {
            while (true) {                     //Lap lai viec random den khi dc nha` co dan
                temp = 7 + random.nextInt(5);  //Random tu 7 den 11
                if (board.houses[temp].getDanSo() > 0) {
                    break;
                }
            }
        }
        if (playerSide == 2) {
            while (true) {
                temp = 1 + random.nextInt(5);  //Random tu 1 den 5
                if (board.houses[temp].getDanSo() > 0) {
                    break;
                }
            }
        }
        this.buocDi.chose = temp;
        this.buocDi.direc = 1 - (random.nextInt(2) * 2); // Random 1 hoac -1
        board.houses[this.buocDi.chose].chosen = true;
        board.houses[this.buocDi.chose].chosenSide = this.buocDi.direc;
        giveTurnToken(playerSide);
    }

    //Tra luot cho trong tai
    public void giveTurnToken(int playerSide) {
        if (playerSide == 1) {
            game.turnToken = 3;
        } else {
            game.turnToken = 4;
        }
    }

    //Trả về bước đi
    public Step getStep() {
        return this.buocDi;
    }

}
