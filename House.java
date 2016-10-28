/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSQuares2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author VPC
 */
class House {

    public int x, y;
    public int houseID;
    public int danSo;
    boolean chosen;
    Shape shape;
    public Shape chosenShape;

    public House() {
        x = 0;
        y = 0;
        houseID = 0;
    }

    public void setHouse(int ID, int x, int y, int danSo) {
        this.x = x;
        this.y = y;
        this.houseID = ID;
        this.danSo = danSo;
        this.shape = new Rectangle2D.Double(x, y, 100, 100);
        this.chosenShape = new Rectangle2D.Double(x + 6, y + 6, 88, 88);

    }

    public void chosen() {
        chosen = true;
    }

    public void setID(int id) {
        this.houseID = id;
    }

    public int getID() {
        return this.houseID;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDanSo() {
        return danSo;
    }

    public void setDanSo(int pop) {
        this.danSo = pop;
    }

    public void tangDanSo() {
        this.danSo++;
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.blue);
        //Vẽ Nhà Dân màu
        //g2d.draw(this.shape);           
        if ((chosen)&&(houseID>6)) {
            g2d.drawImage(Game.houseChosen, x - 100, y, null);
        }
        if ((chosen)&&(houseID<6)) {
            g2d.drawImage(Game.houseChosen_Bot, x - 100, y-100, null);
        }
        //Vẽ Dân trong nhà
        g2d.setColor(Color.gray);
        if (danSo == 0); else {
            if ((danSo <= 7) && (danSo >= 1)) {
                g2d.drawImage(Game.soils[danSo], x, y, null);
            } else {
                g2d.drawImage(Game.soils[8], x, y, null);
            }
        }
        
        
        //Vẽ số lượng dân
        if(danSo > 5){
        g2d.setColor(Color.gray);
        g2d.setFont(new Font("Tw Cen MT Bold Italic", Font.BOLD, 20));
        g2d.drawString(String.valueOf(danSo),x+5 , y+92);
        }
    }
}
