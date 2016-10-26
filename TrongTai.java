/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSQuares2;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author VPC
 */
class TrongTai {

    private Game game;
    public Player player;
    public Board board;
    private Image hand;
    int danInHand;
    private int x, y;         //Tọa độ bàn tay
    Step buocDi;
    int selected;

    // Short Link
    House[] houseShortLink;
    Villa q0ShortLink;
    Villa q6ShortLink;
    Player p1ShortLink;
    Player p2ShortLink;

    public TrongTai(Game game) {
        this.game = game;
        x = game.board.START_X;
        y = game.board.START_Y;
        danInHand = 0;
        houseShortLink = game.board.houses;
        q0ShortLink = game.board.q0;
        q6ShortLink = game.board.q6;
        p1ShortLink = game.p1;
        p2ShortLink = game.p2;
    }

//    public void handle(Step buocDi) {
//
//    }
    /**
     * Thuc hien buoc di va an quan
     *
     * @param buocDi (chose, direc)
     */
    public void handle(Step buocDi) {

        int soDan;
        this.selected = buocDi.chose;

//        boolean flagAn = false;
        // Kiem tra so dan trong nha cuoi de di tiep
        while (true) {
            soDan = layQuan(this.selected);
            chuyenNhaKe(buocDi.direc);

            raiQuan(soDan, buocDi.direc);

            if (checkFinalHouse(this.selected, buocDi.direc) != 1) {
                break;
            }
        }

        // TH An Dan
        while (checkFinalHouse(this.selected, buocDi.direc) == 2) {
            chuyenNhaKe(buocDi.direc);

            An(buocDi, this.selected);
            // Chuyen den o tiep theo de xet
            chuyenNhaKe(buocDi.direc);
        }

        // set token de choi tiep
        setTurnToken(buocDi);
        if (!checkContinueGame(board)) {
            System.out.println("TrongTai thong bao: GameOver");
            this.game.turnToken = 0;
        }

        // Trong Truong hop khong du quan de rai khi het dan trong cac o
        // Score < 5
        // thi game over
        if (checkBoardPlayer(this.game.turnToken)) {
            themDan(this.game.turnToken);
        }

        resetBuocDi();
    }

    /**
     * Lay quan va set so quan la 0
     *
     * @param selected => O Dang xet
     * @return soQuan nhan duoc
     */
    public int layQuan(int selected) {
        int danSo = houseShortLink[selected].getDanSo();
        houseShortLink[selected].setDanSo(0);
//        repaint();
        return danSo;
    }

    public void chuyenNhaKe(int direction) {
        this.selected = tangNhaKe(direction, this.selected);
    }

    public int tangNhaKe(int direction, int current) {
        if (direction == 1) {
            if (current == 11) {
                current = 0;
            } else {
                current++;
            }
        } else if (current == 0) {
            current = 11;
        } else {
            current--;
        }

        return current;
    }

    /**
     * Lay So Dan va dai den khi het
     *
     * @param soDan => So Quan de rai
     * @param direction => Huong rai quan
     * @param selected => O Duoc chon
     * @return O Cuoi Cung khi het quan (so quan con lai de rai = 0)
     */
    public void raiQuan(int soDan, int direction) {

        for (int i = soDan; i > 0; i--) {

            // neu vao quan 0
            if (this.selected == 0) {
                q0ShortLink.tangDanSo();
            } // neu vao quan 6
            else if (this.selected == 6) {
                q6ShortLink.tangDanSo();
            } // neu khong vao quan
            else {
                houseShortLink[this.selected].tangDanSo();
            }
            chuyenNhaKe(direction);
        }

    }

    /**
     * Kiem tra o ket thuc
     *
     * @param selected
     * @param direction => Huong di de xet theo truong hop
     * @return 0 => Dung (2 O Trong Hoac Quan) 1 => Choi Tiep (O Co chua soi va
     * khong la quan) 2 => An Dan (O Trong sau do la 1 o dan co quan) 3 => An
     * Quan
     */
    public int checkFinalHouse(int current, int direction) {
        // vao o quan
        if (current == 0 || current == 6) {
            return 0;
        }

        // Khong Gap O Quan
        // So Dan khac 0 Choi Tiep
        if (houseShortLink[current].getDanSo() != 0) {
            return 1;
        }

        // So dan o do = 0
        // So dan o tiep theo 
        int checked = tangNhaKe(direction, current);
        // = 0 Dung
        // != 0 An
        if (checkEmpty(current)) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Kiem tra nha trong hay khong
     *
     * @param current
     * @return
     */
    public boolean checkEmpty(int current) {
        if (current == 0) {
            if (q0ShortLink.coQuan || q0ShortLink.getDanSo() != 0) {
                return false;
            }
            return true;
        }
        if (current == 6) {
            if (q6ShortLink.coQuan || q6ShortLink.getDanSo() != 0) {
                return false;
            }
            return true;
        }
        if (houseShortLink[current].getDanSo() != 0) {
            return false;
        }
        return true;
    }

    public void An(Step buocDi, int current) {
        if (current == 0 || current == 6) {
            AnQuan(buocDi, current);
        } else {
            AnDan(buocDi, current);
        }
    }

    /**
     * An Dan
     *
     * @param selected
     * @param direction => Chieu an quan
     * @return
     */
    public void AnDan(Step buocDi, int current) {

        if (isPlayer1(buocDi)) {
            p1ShortLink.currentScore += layQuan(current);

        } else {
            p2ShortLink.currentScore += layQuan(current);
        }

    }

    public void AnQuan(Step buocDi, int current) {

        // Quan 0
        if (current == 0) {
            if (isPlayer1(buocDi)) {
                p1ShortLink.currentScore = p1ShortLink.currentScore + q0ShortLink.getDanSo();
                q0ShortLink.setDanSo(0);
                if (q0ShortLink.coQuan) {
                    q0ShortLink.coQuan = false;
                    p1ShortLink.currentScore += 10;
                }
            } else {
                p2ShortLink.currentScore = p2ShortLink.currentScore + q0ShortLink.getDanSo();
                q0ShortLink.setDanSo(0);
                if (q0ShortLink.coQuan) {
                    q0ShortLink.coQuan = false;
                    p2ShortLink.currentScore += 10;
                }
            }
        } // Quan 6
        else if (isPlayer1(buocDi)) {
            p1ShortLink.currentScore = p1ShortLink.currentScore + q6ShortLink.getDanSo();
            q6ShortLink.setDanSo(0);
            if (q6ShortLink.coQuan) {
                q6ShortLink.coQuan = false;
                p1ShortLink.currentScore += 10;
            }
        } else {
            p2ShortLink.currentScore = p2ShortLink.currentScore + q6ShortLink.getDanSo();
            q6ShortLink.setDanSo(0);
            if (q6ShortLink.coQuan) {
                q6ShortLink.coQuan = false;
                p2ShortLink.currentScore += 10;
            }
        }

    }

    public boolean checkContinueGame(Board board) {
        if (checkEmpty(0) && checkEmpty(6)) {
            return false;
        }
        return true;
    }

    public boolean isPlayer1(Step buocDi) {
        if (buocDi.chose > 6) {
            return true;
        }
        return false;
    }

    /**
     * Turn tiep theo la player 1
     *
     * @param token
     * @return true neu dung false neu sai
     */
    public boolean nextTurnIsPlayer1(int token) {
        if (token == 1) {
            return true;
        }
        return false;
    }

    // set token cho luot tiep theo
    public void setTurnToken(Step buocDi) {

        if (isPlayer1(buocDi)) {
            game.turnToken = 2;
        } else {
            game.turnToken = 1;
        }
    }

    // kiem tra 
    // neu so quan tren ban het thi them
    public boolean checkBoardPlayer(int token) {
        boolean allEmpty = true;
        if (nextTurnIsPlayer1(token)) {
            for (int i = 7; i <= 11; i++) {
                if (houseShortLink[i].getDanSo() != 0) {
                    allEmpty = false;
                }
            }
        } else {
            for (int i = 1; i < 6; i++) {
                if (houseShortLink[i].getDanSo() != 0) {
                    allEmpty = false;
                }
            }
        }
        return allEmpty;
    }

    /**
     * Them 1 dan vao moi o cua player co luot tiep
     *
     * @param token
     */
    public void themDan(int token) {
        if (nextTurnIsPlayer1(token)) {
            for (int i = 7; i <= 11; i++) {
                houseShortLink[i].setDanSo(1);
            }
            // Tru diem cua player
            if (p1ShortLink.currentScore < 5) {
                this.game.turnToken = 0;
            }
            p1ShortLink.currentScore -= 5;
        } else {
            for (int i = 1; i <= 5; i++) {
                houseShortLink[i].setDanSo(1);
            }
            // Tru diem cua player
            if (p2ShortLink.currentScore < 5) {
                this.game.turnToken = 0;
            }
            p2ShortLink.currentScore -= 5;
        }
    }

    public void resetBuocDi() {
        p1ShortLink.resetBuocDi();
        p2ShortLink.resetBuocDi();
    }

    public void paint(Graphics2D g2d) {
        //g2d.drawImage(Game.voSoi, x, y - 50, null);
        
                
    }
}
