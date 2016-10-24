package testSQuares2;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author VPC
 */
public class Player extends JPanel {

    private Game game;
    private Board board;            //Sân chơi hiện tại của game
    private House[] houses;  //Địa chỉ các nhà dân mà mình quản lý (1,2,3,4,5) ||  (10,9,8,7,6)
    static String playerName;
    public int currentScore;    //Điểm của người chơi = số dân thu về
    protected int direction;        // Hướng đi đã chọn       
    protected int chosenHouse;     //CHọn nhà
    public int playerSide;         // Có 2 player, =1 nếu là player1, =2 là player2
    Step buocDi;
    int firstHouse;
    int lastHouse;

    //Khởi tạo player
    public Player(Game game, String name, int playerSide) {
        this.game = game;
        buocDi = new Step();
        this.playerName = name;
        this.board = game.board;
        this.playerSide = playerSide;
        currentScore = 0;
        direction = 0;
        chosenHouse = 0;

    }

    //Reset nước đi mỗi lần nhận turn
    public void resetBuocDi() {
        chosenHouse = 0;
        direction = 0;
        this.buocDi.chose = 0;
        this.buocDi.direc = 0;
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

    public void turn(Point mousePosition) {
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
                    if (board.houses[i].shape.contains(mousePosition)) {
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
                    if (board.houses[i].shape.contains(mousePosition)) {
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
            if (Canvas.keyboardKeyState(KeyEvent.VK_LEFT)) {
                if (this.playerSide == 1) {
                    direction = 1;
                } else {
                    direction = -1;
                }
            }
            if (Canvas.keyboardKeyState(KeyEvent.VK_RIGHT)) {
                if (this.playerSide == 1) {
                    direction = -1;
                } else {
                    direction = 1;
                }
            }
        }

        this.buocDi.chose = chosenHouse;
        this.buocDi.direc = direction;

        if (direction != 0) {
            if (playerSide == 1) {
                game.turnToken = 3;
            } else {
                game.turnToken = 4;
            }
        }

    }

    //Trả về bước đi
    public Step getStep() {
        return this.buocDi;
    }
    

}
