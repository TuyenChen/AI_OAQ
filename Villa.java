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
public class Villa extends House {
    int quanID;
    int diemNhaQuan;
    public boolean coQuan;
    Shape shape = new Rectangle2D.Double(x, y, 65, 100);
    public void setQuanID(int x){
        this.quanID = x;
    }
    public int getDiemSo(){
        if (coQuan) return danSo+5;
        else return danSo;
    }
    public void paint(Graphics2D g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.blue);
        //Vẽ Nhà Dân
        g2d.draw(shape);           
        //Vẽ Dân trong nhà
        g2d.setColor(Color.gray);
        for (int i=1;i<=danSo;i++){
            g2d.fillOval(m, n, 10, 10);
            m+=arm[i];
            n+=arn[i];
        }
        if (coQuan) {
            g2d.setColor(Color.green);
            g2d.fillOval(m, n+30, 15, 18);
        }
    }
    
}
