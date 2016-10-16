package testSQuares2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;



public class Game {

    public Board board;
    public Player p1,p2;
    public TrongTai trongTai;
    public int turnToken;  //Dùng để chia lượt
    public Game()
    {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();
                
                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }
    
    
   /**
     * Set variables and objects for the game.
     */
    private void Initialize()
    {
        this.board = new Board();
         p1 = new Player(this,"Tuyen",1);
         p2 = new Player(this,"Dung",2);
         trongTai = new TrongTai(this);
        turnToken =1;
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    private void LoadContent()
    {
    
    }    
    
    
    /**
     * Restart game - reset some variables.
     */
    public void RestartGame()
    {
        
    }
    
    
    /**
     * Update game logic.
     * 
     * @param gameTime gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public void UpdateGame(long gameTime, Point mousePosition)
    {
        switch(turnToken){
            case 1:
                p1.turn();break;
            case 2:
                p2.turn();break;
            case 3:
                trongTai.handle(p1.getStep());break;
            case 4:
                trongTai.handle(p2.getStep());break;
            case 0:
                Framework.gameState = Framework.GameState.GAMEOVER;
            
        }
        
    }
    
    public void Over(){
        
    }
    
    
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition)
    {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
        board.paint(g2d);      //Vẽ lại sân sau khi xử lí
        trongTai.paint(g2d);
    }
}
