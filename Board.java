/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSQuares2;

import java.awt.Graphics2D;

/**
 *
 * @author VPC
 */
class Board {
    int x,y;
    House[] housesP1 = new House[6];
    House[] housesP2 = new House[6];
    State state;
    Villa q0,q5;
    public Board() {
        x=200;
        y=300;
        for(int i=1;i<=5;i++){
            housesP1[i] = new House();
        }
    }
    public void paint(Graphics2D g2d){
        for (int i=1;i<=5;i++){
            housesP2[i].setHouse(i, x, y,5);
            housesP2[i].paint(g2d);
            x+=50;
        }
        x=200;y=350;
        for (int i=10;i>=6;i--){
            housesP1[i].setHouse(i, x, y,5);
            housesP1[i].paint(g2d);
            x+=50;
        }
        g2d.drawArc(400, 300, 100, 100, 270, 180);
        g2d.drawArc(150, 300, 100, 100, 90, 180);
    }
}