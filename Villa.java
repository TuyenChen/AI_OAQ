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
import javafx.scene.shape.Arc;

/**
 *
 * @author VPC
 */
public class Villa extends House {
    int quanID;
    int diemNhaQuan;
    public boolean coQuan;
    Shape shape = new Rectangle2D.Double(x, y, 65, 100);
    
    public Villa(){
    
    }
    
    public void setVilla(int ID,int x,int y,boolean coQuan){
        this.quanID = ID;
        this.x = x;
        this.y = y;
        this.coQuan = coQuan;
        
    }
    
    
    public void setQuanID(int ID){
        this.quanID = ID;
    }
    public int getDiemSo(){
        if (coQuan) return danSo+5;
        else return danSo;
    }
    public void paint(Graphics2D g2d){
        g2d.setColor(Color.red);
        //Vẽ Nhà Quan
        if(quanID ==5){
            g2d.drawArc(x, y, 200, 200, -90, 180);
        }
        else g2d.drawArc(x,y,200,200,90,180);
        
        g2d.setColor(Color.gray);
        
        //Toa do Quan
        if(quanID == 0)m=x+50;
        else m=x+140;
        n=y+80;
        
        //Vẽ Dân trong nhà
        for (int i=1;i<=danSo;i++){
            g2d.fillOval(m, n, 10, 10);
        }
        //Ve Quan
        if (coQuan) {
            g2d.setColor(Color.gray);
            g2d.fillOval(m, n, 30, 44);
        }
    }
    
}
