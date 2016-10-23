/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSQuares2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author VPC
 */
class Board {
    final int START_X = 304;
    final int START_Y = 280;
    int x,y;
    //10 nha` dan
    House[] houses = new House[11];
    
    //2 nha quan
    Villa q0;
    Villa q5;
    
    
    public Board() {
        x=START_X;
        y=START_Y;
        for(int i=1;i<=10;i++){
            houses[i] = new House();
        }
        q0 = new Villa();
        q5 = new Villa();
        initBoard();
    }
    
    /*
    * Khoi tao san va set toa do cac nha`
    */
    
    public void initBoard(){
        
        //Nha dan player2
        for (int i=1;i<=5;i++){
            houses[i].setHouse(i, x, y,5);
            x+=100;
        }
        
        //Nha dan player1
        x=START_X;y=START_Y+102;
        for (int i=10;i>=6;i--){
            houses[i].setHouse(i, x, y,5);
            x+=100;
        }
        
        //Set nha Quan
        q0.setVilla(0,START_X -100,START_Y,true);
        q5.setVilla(5,START_X +403,START_Y,true);
    }
    //In điểm số
    public void paintScore(Graphics2D g2d,int scoreP1,int scoreP2){
        g2d.setColor(Color.MAGENTA);
	g2d.setFont(new Font("Verdana", Font.BOLD, 100));
	g2d.drawString(standScore(scoreP1), 940, 678);
        g2d.drawString(standScore(scoreP1), 185, 188);
    }
    //Chuẩn hóa điểm số 5 => 05, 0 =>00
    private String standScore(int score){
        if (score < 10)
        return "0"+String.valueOf(score);
        else return String.valueOf(score);
    }
    
    
    
    
    //Ve lai san
    public void paint(Graphics2D g2d){
        for (int i=1;i<=10;i++){
            houses[i].paint(g2d);
        }
        q0.paint(g2d);
        q5.paint(g2d);
    }
}
