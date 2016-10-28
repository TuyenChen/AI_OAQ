package testSQuares2;

import java.awt.Color;
import java.awt.Font;
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
    public int lastToken; //Kiem tra sang luot khac
    private long timeCount;
    public  long timeFlag;
    public final int TIME_LIMIT = 2;
    public final int TIME_DELAY = 10;
    private BufferedImage background;
    public static BufferedImage[] soils; 
    public static BufferedImage quan0,quan6;
    public static BufferedImage houseChosen,houseChosen_Bot;
    public static BufferedImage ava_bot,ava_player;
    public static BufferedImage voSoi,choTayXuong,namTay;
    public static BufferedImage dieuCay,dieuCay1,dieuCay2,dieuCayGiua1,dieuCayGiua2,dieuCayGiua3;
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
        timeFlag = Framework.gameTime;
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
            
            URL quan0ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil/quan0.png");
            quan0 = ImageIO.read(quan0ImgUrl);
            
            URL quan6ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil/quan6.png");
            quan6 = ImageIO.read(quan6ImgUrl);
            
            URL soil_1ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil/soil_1.png");
            soils[1] = ImageIO.read(soil_1ImgUrl);
            
            URL soil_2ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil/soil_2.png");
            soils[2] = ImageIO.read(soil_2ImgUrl);
            
            URL soil_3ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil/soil_3.png");
            soils[3] = ImageIO.read(soil_3ImgUrl);
            
            URL soil_4ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil/soil_4.png");
            soils[4] = ImageIO.read(soil_4ImgUrl);
            
            URL soil_5ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil/soil_5.png");
            soils[5] = ImageIO.read(soil_5ImgUrl);
            
            URL soil_6ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil/soil_6.png");
            soils[6] = ImageIO.read(soil_6ImgUrl);
            
            URL soil_7ImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil/soil_7.png");
            soils[7] = ImageIO.read(soil_7ImgUrl);
            
            URL soilsImgUrl = this.getClass().getResource("/testsquares2/resources/images/soil/soils.png");
            soils[8] = ImageIO.read(soilsImgUrl);
            
            URL chosenImgUrl = this.getClass().getResource("/testsquares2/resources/images/chosen.png");
            houseChosen = ImageIO.read(chosenImgUrl);
            
            URL chosen_BotImgUrl = this.getClass().getResource("/testsquares2/resources/images/chosen_Bot.png");
            houseChosen_Bot = ImageIO.read(chosen_BotImgUrl);
            
            URL voSoiImgUrl = this.getClass().getResource("/testsquares2/resources/images/voSoi.png");
            voSoi = ImageIO.read(voSoiImgUrl);
            
            URL choTayXuongImgUrl = this.getClass().getResource("/testsquares2/resources/images/choTayXuong.png");
            choTayXuong = ImageIO.read(choTayXuongImgUrl);
            
            URL namTayImgUrl = this.getClass().getResource("/testsquares2/resources/images/namTay.png");
            namTay = ImageIO.read(namTayImgUrl);
            
            URL dieuCay_1ImgUrl = this.getClass().getResource("/testsquares2/resources/images/dieucay/dieucay_1.png");
            dieuCay1 = ImageIO.read(dieuCay_1ImgUrl);
            
            URL dieuCay_2ImgUrl = this.getClass().getResource("/testsquares2/resources/images/dieucay/dieucay_2.png");
            dieuCay2 = ImageIO.read(dieuCay_2ImgUrl);
            
            URL dieuCayGiua_1ImgUrl = this.getClass().getResource("/testsquares2/resources/images/dieucay/dieucaygiua_1.png");
            dieuCayGiua1 = ImageIO.read(dieuCayGiua_1ImgUrl);
            
            URL dieuCayGiua_2ImgUrl = this.getClass().getResource("/testsquares2/resources/images/dieucay/dieucaygiua_2.png");
            dieuCayGiua2 = ImageIO.read(dieuCayGiua_2ImgUrl);
            
            URL dieuCayGiua_3ImgUrl = this.getClass().getResource("/testsquares2/resources/images/dieucay/dieucaygiua_3.png");
            dieuCayGiua3 = ImageIO.read(dieuCayGiua_3ImgUrl);
            
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
        lastToken = turnToken;
        switch(turnToken){
            case 1:
                if((gameTime - timeFlag)/Framework.secInNanosec < TIME_LIMIT)
                    p1.turn(gameTime,mousePosition);
                else p1.auto();
                break;
            case 2:
                if((gameTime - timeFlag)/Framework.secInNanosec < TIME_LIMIT)
                    p2.turn(gameTime,mousePosition);
                else p2.auto();
                break;
            case 3:
                trongTai.handle(p1.getStep());break;
            case 4:
                trongTai.handle(p2.getStep());break;
            case 11:
                if((gameTime - timeFlag) >Framework.secInNanosec/TIME_DELAY) turnToken = 12;
            break;
            case 12:
                if((gameTime - timeFlag) >Framework.secInNanosec/TIME_DELAY) turnToken = 13;
            break;
            case 13:
                if((gameTime - timeFlag) >Framework.secInNanosec/TIME_DELAY) turnToken = 3;
            break;
            case 21:
                if((gameTime - timeFlag) >Framework.secInNanosec/TIME_DELAY) turnToken = 22;
            break;
            case 22:
                if((gameTime - timeFlag) >Framework.secInNanosec/TIME_DELAY) turnToken = 23;
            break;
            case 23:
                if((gameTime - timeFlag) >Framework.secInNanosec/TIME_DELAY) turnToken = 4;
            break;
            case 0:
                Framework.gameState = Framework.GameState.GAMEOVER;
            break;
        }
        if (lastToken != turnToken)  timeFlag = gameTime;
    }
    
    public void Over(){
        
    }
    
    
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition,long gameTime)
    {
        
        timeCount =  (gameTime-timeFlag);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(background, 0, 0, null);
        g2d.drawImage(ava_bot, 599, 84, null);
        g2d.drawImage(ava_player, 518, 557, null);
        g2d.setColor(new Color(51,153,153));
        g2d.setFont(new Font("SansSerif.bold", Font.BOLD, 60));
        if ((turnToken == 1)||(turnToken ==2)){
            g2d.drawString(String.valueOf(TIME_LIMIT - 1 - timeCount/Framework.secInNanosec), 1008, 200);
        }
         g2d.drawString(String.valueOf("TurnToken: "+turnToken), 900, 140);
        board.paint(g2d);      //Vẽ lại sân sau khi xử lí
        //trongTai.paint(g2d);
        g2d.setColor(new Color(51,153,153));
        board.paintScore(g2d, p1.currentScore, p2.currentScore);
        
        {g2d.setColor(Color.white);
        g2d.setFont(new Font("SansSerif.bold", Font.BOLD, 40));
        g2d.drawString(String.valueOf(Framework.gameState), 95, 755);}
        
        //Vẽ điếu cày
        switch (turnToken){
            case 1:g2d.drawImage(dieuCay1, 954, 229, null);break;
            case 2:g2d.drawImage(dieuCay2, 944, 380, null);break;
            case 23:
            case 11:g2d.drawImage(dieuCayGiua1, 933, 320, null);break;
            case 22:
            case 12:g2d.drawImage(dieuCayGiua2, 924, 374, null);break;
            case 13:
            case 21:g2d.drawImage(dieuCayGiua3, 927, 385, null);break;
        }
        
    }
}
