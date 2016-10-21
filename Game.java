package testSQuares2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;



public class Game {

    public Board board;
    public Player p1,p2;
    public TrongTai trongTai;
    public int turnToken;  //Dùng để chia lượt
    
    private BufferedImage background;
    public static BufferedImage[] soils; 
    public static BufferedImage quan0,quan5;
    public static BufferedImage houseChosen;
    public static BufferedImage ava_bot,ava_player;
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
        soils = new BufferedImage[10];
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    private void LoadContent()
    {
         try
        {
            URL bgImgUrl = this.getClass().getResource("/testsquares2/resources/images/background.jpg");
            background = ImageIO.read(bgImgUrl);
            
            URL ava_botImgUrl = this.getClass().getResource("/testsquares2/resources/images/ava_bot.png");
            ava_bot = ImageIO.read(ava_botImgUrl);
            
            URL ava_playerImgUrl = this.getClass().getResource("/testsquares2/resources/images/ava_player.png");
            ava_player = ImageIO.read(ava_playerImgUrl);
            
            URL quan0ImgUrl = this.getClass().getResource("/testsquares2/resources/images/quan0.png");
            quan0 = ImageIO.read(quan0ImgUrl);
            
            URL quan5ImgUrl = this.getClass().getResource("/testsquares2/resources/images/quan5.png");
            quan5 = ImageIO.read(quan5ImgUrl);
            
            URL soil_1ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil_1.png");
            soils[1] = ImageIO.read(soil_1ImgUrl);
            
            URL soil_2ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil_2.png");
            soils[2] = ImageIO.read(soil_2ImgUrl);
            
            URL soil_3ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil_3.png");
            soils[3] = ImageIO.read(soil_3ImgUrl);
            
            URL soil_4ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil_4.png");
            soils[4] = ImageIO.read(soil_4ImgUrl);
            
            URL soil_5ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil_5.png");
            soils[5] = ImageIO.read(soil_5ImgUrl);
            
            URL soil_6ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil_6.png");
            soils[6] = ImageIO.read(soil_6ImgUrl);
            
            URL soil_7ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil_7.png");
            soils[7] = ImageIO.read(soil_7ImgUrl);
            
            URL soilsImgUrl = this.getClass().getResource("/testsquares2/resources/images/soils.png");
            soils[8] = ImageIO.read(soilsImgUrl);
            
            URL chosenImgUrl = this.getClass().getResource("/testsquares2/resources/images/chosen.png");
            houseChosen = ImageIO.read(chosenImgUrl);
            
        }
        catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                p1.turn(mousePosition);break;
            case 2:
                p2.turn(mousePosition);break;
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
        g2d.drawImage(background, 0, 0, null);
        g2d.drawImage(ava_bot, 560, 75, null);
        g2d.drawImage(ava_player, 460, 570, null);
        board.paint(g2d);      //Vẽ lại sân sau khi xử lí
        //trongTai.paint(g2d);
    }
}
