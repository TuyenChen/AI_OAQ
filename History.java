/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSQuares2;

import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class History {
    ArrayList<BoardState> stack;
    static final int x = 100;
    static final int y = 180;
    static final Shape shape = new Rectangle2D.Double(x, y, 160, 75);
    int preToken = 0;   // to return player token after rollback
    Game game;
    House[] houseShortLink;
    Villa q0ShortLink;
    Villa q6ShortLink;
    Player p1ShortLink;
    Player p2ShortLink;
    
    public History(Game game) {
        this.stack = new ArrayList<BoardState>();
        BoardState initState = new BoardState();
        this.stack.add(initState);
        this.game = game;
        houseShortLink = game.board.houses;
        q0ShortLink = game.board.q0;
        q6ShortLink = game.board.q6;
    }
    
    public void add(BoardState state) {
        stack.add(state);
    }
    
    /**
     * Ham Thuc Hien Roll back ban
     * Goi khi turnToken = 5
     */
    public void Rollback() {
        
        // Check number of instance in history
        // if < 2 return
        BoardState lastState = null;
        if (stack.size() < 2) {
            game.turnToken = this.preToken;
            this.preToken = 0;
            return;
        }
        
        // else remove 2 instance
        stack.remove(stack.size() - 1);
        stack.remove(stack.size() - 1);
        
        // take last instance
        lastState = stack.get(stack.size() - 1);
        
        // roll back board according to last State
        q0ShortLink.coQuan = lastState.q0Save.coQuan;
        q0ShortLink.danSo = lastState.q0Save.soDan;
        q6ShortLink.coQuan = lastState.q6Save.coQuan;
        q6ShortLink.danSo = lastState.q6Save.soDan;
        // set Dan
        for (int i = 1; i < 12; i++) {
            if (i == 6) continue;
            houseShortLink[i].danSo = lastState.houseSave[i];
        }
        
        // set token for player continue playing
        game.turnToken = this.preToken;
        
        this.preToken = 0;      
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return;
    }
 
    
    // ve nut di lai
    public void paint(Graphics2D g2d) {
        g2d.drawImage(Game.diLai, x, y, null);
    }
}
