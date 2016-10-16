/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSQuares2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author VPC
 */
class House {
    private int houseID;
    protected int danSo;
    protected int x,y;        //Tọa độ nhà dân
    public Shape shape;
    public Shape chosenShape = new Rectangle2D.Double(x+3, y+3, 44, 44);
    boolean chosen = false; 
    int m = x+10;      //Tọa độ của dân trong nhà
    int n = y+10;
    int[] arm = {0,0,20,-20,15,0,-10};
    int[] arn = {0,0,10,10,-30,35,-10};
    public void setHouse(int ID,int x,int y, int danSo){
        this.danSo = danSo;
        this.x = x;
        this.y = y;
        this.houseID = ID;
        this.shape = new Rectangle2D.Double(x, y, 50, 50);
    }
    public House(){
        x =0;
        y=0;
        houseID = 0;
    }
    
    public void paint(Graphics2D g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.blue);
        //Vẽ Nhà Dân
        g2d.draw(shape);           
        if(chosen) {
            g2d.setColor(Color.pink);
            g2d.draw(chosenShape);
        }
        //Vẽ Dân trong nhà
        g2d.setColor(Color.gray);
        for (int i=1;i<=danSo;i++){
            g2d.fillOval(m, n, 10, 10);
            m+=arm[i];
            n+=arn[i];
        }
    }
    public void chosen(){
        chosen = true;
    }
    public void setID(int id){
        this.houseID = id;
    }
    public int getID(){
        return this.houseID;
    }
    public void setXY(int x,int y){
        this.x = x;
        this.y = y;
    }
    public int getDanSo(){
        return danSo;
    }
}
