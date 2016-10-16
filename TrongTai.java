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
   private float x,y;         //Tọa độ bàn tay
   Step buocDi;
   public TrongTai(Game game){
       this.game = game;
       x = 0;
       y = 0;
       danInHand = 0;
   }
   public void handle(Step buocDi){
       
   }
   public boolean checkContinueGame(Board board){
       return ((board.state.q0 >0)||(board.state.q5 >0));
       //Nhà Quan còn dân thì vẫn tiếp tục
   }
   public void paint(Graphics2D g){
   
   }
}
