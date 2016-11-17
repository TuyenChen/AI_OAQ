/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSQuares2;

/**
 *
 * @author Administrator
 */
public class BoardState {

    VillaSave q0Save, q6Save;
    int[] houseSave;
    Step buocDiTruoc;

    public BoardState() {
        this.q0Save = new VillaSave(0, true);
        this.q6Save = new VillaSave(0, true);
        this.houseSave = new int[12];
        for (int i = 1; i < 12; i++) {
            if (i == 6) continue;
            this.houseSave[i] = 5;
        }
        this.buocDiTruoc = new Step();
    }

    public BoardState(Board board, Step buocDiTruoc) {
        this.buocDiTruoc = new Step();
        this.buocDiTruoc.chose = buocDiTruoc.chose;
        this.buocDiTruoc.direc = buocDiTruoc.direc;
        this.q0Save = new VillaSave(board.q0.getDanSo(), board.q0.coQuan);
        this.q6Save = new VillaSave(board.q6.getDanSo(), board.q6.coQuan);

        houseSave = new int[12];
        for (int i = 1; i < 6; i++) {
            houseSave[i] = board.houses[i].getDanSo();
        }
        for (int i = 7; i < 12; i++) {
            houseSave[i] = board.houses[i].getDanSo();
        }
    }
}

class VillaSave {

    int soDan;
    boolean coQuan;

    public VillaSave() {

    }

    public VillaSave(int soDan, boolean coQuan) {
        this.soDan = soDan;
        this.coQuan = coQuan;
    }

}
