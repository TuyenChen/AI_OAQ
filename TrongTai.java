/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSQuares2;

import java.awt.Color;
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
    private float x, y;         //Tọa độ bàn tay
    Step buocDi;
    int flagQuan;

    // Short Link
    House[] houseShortLink;
    Villa q0ShortLink;
    Villa q5ShortLink;
    Player p1ShortLink;
    Player p2ShortLink;

    public TrongTai(Game game) {
        this.game = game;
        x = game.board.START_X;
        y = game.board.START_Y;
        danInHand = 0;
        houseShortLink = game.board.houses;
        q0ShortLink = game.board.q0;
        q5ShortLink = game.board.q5;
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
        int selected = buocDi.chose;
        int[] result = null;
        flagQuan = 1;

        // Kiem tra so dan trong nha cuoi de di tiep
        while (true) {
            soDan = layQuan(selected);

            result = raiQuan(soDan, selected, selected);
            selected = result[1];
            flagQuan = result[0];
            if (checkFinalHouse(selected, buocDi.direc, flagQuan) != 1) {
                break;
            }
        }

        // TH An Dan
        while (checkFinalHouse(selected, buocDi.direc, flagQuan) == 2 || checkFinalHouse(selected, buocDi.direc, flagQuan) == 3) {

            int resultCheck = checkFinalHouse(selected, buocDi.direc, flagQuan);
            if (resultCheck == 2) {
                selected = AnDan(buocDi, selected, buocDi.direc);
            } else if (resultCheck == 3) {
                selected = AnQuan(buocDi, selected, buocDi.direc);
            }
        }

        if (!checkContinueGame(board)) {
//            this.game.state = 0;
        }

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

    /**
     * Lay So Dan va dai den khi het
     *
     * @param soDan => So Quan de rai
     * @param direction => Huong rai quan
     * @param selected => O Duoc chon
     * @return O Cuoi Cung khi het quan (so quan con lai de rai = 0)
     */
    public int[] raiQuan(int soDan, int direction, int selected) {
        int flagRaiQuan = 0;
        // rai cung chieu kim dong ho
        if (direction == 1) {

            flagRaiQuan = 0;

            selected++;
            if (selected == 11) {
                selected = 0;
            }
            // 0: Chua vao quan
            // 1: Da vao quan
            // Lay so dan dai den khi het i = 0 out vong lap
            for (int i = soDan; i > 0; i--) {

                // neu vao quan 0
                if (selected == 0) {
                    q0ShortLink.tangDanSo();
                    selected++;
                } // neu vao quan 6
                else if (selected == 6 && flagRaiQuan == 0) {
                    q5ShortLink.tangDanSo();
                    flagQuan = 1;
                } // neu khong vao quan
                else {
                    // neu vua di qua quan 6 set lai flagQuan = 0
                    if (selected == 6) {
                        flagQuan = 0;
                    }
                    houseShortLink[selected].tangDanSo();
                    selected++;
                    // neu o tiep theo la sau o 10 giam selected ve 0
                    if (selected == 11) {
                        selected = 0;
                    }

                }
            }
        } // rai theo chieu nguoc kim dong ho
        else {
            flagRaiQuan = 0; // kiem tra dieu kien vao quan tai selected = 5
            // 0: Chua vao quan
            // 1: Da vao quan
            selected--;

            // Lay so dan dai den khi het i = 0 out vong lap
            for (int i = soDan; i > 0; i--) {

                // neu vao quan 0
                if (selected == 0) {
                    q0ShortLink.tangDanSo();
                    selected = 10;
                } // neu vao quan 5
                else if (selected == 5 && flagRaiQuan == 0) {
                    q5ShortLink.tangDanSo();
                    flagQuan = 1;
                } // neu khong vao quan
                else {
                    // neu vua di qua quan 6 set lai flagQuan = 0
                    if (selected == 5) {
                        flagQuan = 0;
                    }
                    houseShortLink[selected].tangDanSo();
                    selected--;
                }
            }
        }

        int[] result = null;
        result[0] = flagRaiQuan;
        result[1] = selected;

        return result;
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
    public int checkFinalHouse(int selected, int direction, int flagQuan) {
        if (selected == -1) {
            return 0;
        }
        // neu rai theo chieu kim dong ho
        if (direction == 1) {
            // Gap O Quan
            if (selected == 0 || (selected == 6 && flagQuan == 0)) {
                return 0;
            }
            // Khong Gap O Quan
            // So Dan khac 0 Choi Tiep
            if (houseShortLink[selected].getDanSo() != 0) {
                return 1;
            }
            // So dan o do = 0
            // So dan o tiep theo 
            // = 0 Dung
            // != 0 An
            if ((selected == 5 && q5ShortLink.getDanSo() != 0) || (selected == 10 && q0ShortLink.getDanSo() != 0)) {
                return 3;
            }
            if (houseShortLink[selected + 1].getDanSo() != 0) {
                return 2;
            } else {
                return 0;
            }
        } // neu nguoc lai
        // direction nguoc chieu kim dong ho
        else {
            if (selected == 0 || (selected == 5 && flagQuan == 0)) {
                return 0;
            }
            // Khong Gap O Quan
            // So Dan khac 0 Choi Tiep
            if (houseShortLink[selected].getDanSo() != 0) {
                return 1;
            }
            // So dan o do = 0
            // So dan o tiep theo 
            // = 0 Dung
            // != 0 An
            if ((selected == 6 && q5ShortLink.getDanSo() != 0) || (selected == 1 && q0ShortLink.getDanSo() != 0)) {
                return 3;
            }
            if (houseShortLink[selected - 1].getDanSo() != 0) {
                return 2;
            } else {
                return 0;
            }
        }
    }

    /**
     * An Dan
     *
     * @param selected
     * @param direction => Chieu an quan
     * @return selected
     */
    public int AnDan(Step buocDi, int selected, int direction) {
        if (direction == 1) {
            selected++;
            if (buocDi.chose > 5) {
                p1ShortLink.currentScore += layQuan(selected);
            } else {
                p2ShortLink.currentScore += layQuan(selected);
            }
            // An den nha cuoi cung sat quan
            if (selected == 5 || selected == 10) {
                return -1;
            }
            selected++;
        } else {
            selected--;
            if (buocDi.chose > 5) {
                p1ShortLink.currentScore += layQuan(selected);
            } else {
                p2ShortLink.currentScore += layQuan(selected);
            }
            // An den nha cuoi cung sat quan

            if (selected == 1 || selected == 6) {
                return -1;
            }
            selected--;
        }

        this.flagQuan = 0;

        return selected;
    }

    public int AnQuan(Step buocDiStep, int selected, int direction) {
        if (direction == 1) {
            if (selected == 0) {
                if (q0ShortLink.coQuan) {
                    q0ShortLink.coQuan = false;
                    if (buocDi.chose > 5) {
                        p1ShortLink.currentScore = p1ShortLink.currentScore + 10 + q0ShortLink.getDanSo();
                        q0ShortLink.setDanSo(0);
                    } else {
                        p2ShortLink.currentScore = p2ShortLink.currentScore + 10 + q0ShortLink.getDanSo();
                        q0ShortLink.setDanSo(0);
                    }
                }

                selected++;
            } else {
                if (q5ShortLink.coQuan) {
                    q5ShortLink.coQuan = false;
                    if (buocDi.chose > 5) {
                        p1ShortLink.currentScore = p1ShortLink.currentScore + 10 + q5ShortLink.getDanSo();
                        q5ShortLink.setDanSo(0);
                    } else {
                        p2ShortLink.currentScore = p2ShortLink.currentScore + 10 + q5ShortLink.getDanSo();
                        q5ShortLink.setDanSo(0);
                    }
                }
                selected++;
                // Dong nay xem lai
                this.flagQuan = 1;
            }
        } else if (selected == 0) {
            if (q0ShortLink.coQuan) {
                q0ShortLink.coQuan = false;
                if (buocDi.chose > 5) {
                    p1ShortLink.currentScore = p1ShortLink.currentScore + 10 + q0ShortLink.getDanSo();
                    q0ShortLink.setDanSo(0);
                } else {
                    p2ShortLink.currentScore = p2ShortLink.currentScore + 10 + q0ShortLink.getDanSo();
                    q0ShortLink.setDanSo(0);
                }
            }

            selected = 10;
        } else {
            if (q5ShortLink.coQuan) {
                q5ShortLink.coQuan = false;
                if (buocDi.chose > 5) {
                    p1ShortLink.currentScore = p1ShortLink.currentScore + 10 + q5ShortLink.getDanSo();
                    q5ShortLink.setDanSo(0);
                } else {
                    p2ShortLink.currentScore = p2ShortLink.currentScore + 10 + q5ShortLink.getDanSo();
                    q5ShortLink.setDanSo(0);
                }
            }
            selected--;
            // Dong nay xem lai
            this.flagQuan = 1;
        }
        return selected;
    }

    public boolean checkContinueGame(Board board) {
        //Nhà Quan còn dân thì vẫn tiếp tục        
//        return !(q0ShortLink.getDanSo() == 0 && !q0ShortLink.coQuan && q5ShortLink.getDanSo() == 0 && !q5ShortLink.coQuan);
        return true;
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(Game.houseChosen, x - 100, y - 100, null);
        g2d.drawImage(Game.tayChoXuong, x - 100, y - 100, null);
    }

    public void paintLayQuan(Graphics2D g, int selected) {

    }
}
