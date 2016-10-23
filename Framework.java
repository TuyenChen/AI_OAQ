package testSQuares2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Framework that controls the game (Game.java) that created it, update it and draw it on the screen.
 * 
 * @author www.gametutorial.net
 */

public class Framework extends Canvas {
    
    /**
     * Width of the frame.
     */
    public static int frameWidth;
    /**
     * Height of the frame.
     */
    public static int frameHeight;

    /**
     * Time of one second in nanoseconds.
     * 1 second = 1 000 000 000 nanoseconds
     */
    public static final long secInNanosec = 1000000000L;
    
    /**
     * Time of one millisecond in nanoseconds.
     * 1 millisecond = 1 000 000 nanoseconds
     */
    public static final long milisecInNanosec = 1000000L;
    
    /**
     * FPS - Frames per second
     * How many times per second the game should update?
     */
    private final int GAME_FPS = 60;
    /**
     * Pause between updates. It is in nanoseconds.
     */
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
    
    /**
     * Possible states of the game
     */
    public static enum GameState{STARTING, VISUALIZING,INTRODUCE, GAME_CONTENT_LOADING, MAIN_MENU, OPTIONS,RULES, PLAYING, GAMEOVER, DESTROYED,PAUSE}
    /**
     * Current state of the game
     */
    public static GameState gameState;
    
    public static GameState preState;
    /**
     * Elapsed game time in nanoseconds.
     */
    private long gameTime;
    // It is used for calculating elapsed time.
    private long lastTime;
    //Img background menu
    private BufferedImage bg_introduce;
    
    public static BufferedImage pause;
    //Menu chinh
    private BufferedImage bg_menu;
    private BufferedImage btn_start;
    private BufferedImage btn_rules;
    private BufferedImage btn_options;
    private BufferedImage btn_exit;
    
    //Luat choi = hinh anh
    public  BufferedImage[] rules;
    private int pageRule;
    // The actual game
    private Game game;
    
    
    public Framework ()
    {
        super();
        
        gameState = GameState.VISUALIZING;
        
        //We start game in new thread.
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }
    
    
   /**
     * Set variables and objects.
     * This method is intended to set the variables and objects for this class, variables and objects for the actual game can be set in Game.java.
     */
    private void Initialize()
    {
        rules = new BufferedImage[8];
        pageRule =1;
    }
    
    private void preStarting(){
        try{
            URL bg_introImgUrl = this.getClass().getResource("/testsquares2/resources/images/introduce.jpg");
            bg_introduce = ImageIO.read(bg_introImgUrl);
        }catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Load files - images, sounds, ...
     * This method is intended to load files for this class, files for the actual game can be loaded in Game.java.
     */
    private void LoadContent()
    {
        try
        {
            
            
            URL menuImgUrl = this.getClass().getResource("/testsquares2/resources/images/bg_menu.jpg");
            bg_menu = ImageIO.read(menuImgUrl);
            
            URL startImgUrl = this.getClass().getResource("/testsquares2/resources/images/btn_start.png");
            btn_start = ImageIO.read(startImgUrl);
            
            URL rulesImgUrl = this.getClass().getResource("/testsquares2/resources/images/btn_rules.png");
            btn_rules = ImageIO.read(rulesImgUrl);
            
            URL optionsImgUrl = this.getClass().getResource("/testsquares2/resources/images/btn_options.png");
            btn_options = ImageIO.read(optionsImgUrl);
            
            URL exitImgUrl = this.getClass().getResource("/testsquares2/resources/images/btn_exit.png");
            btn_exit = ImageIO.read(exitImgUrl);
            
            for (int i = 1; i<=7;i++){
                URL tempRuleImgUrl = this.getClass().getResource("/testsquares2/resources/images/rule_"+i+".jpg");
                rules[i] = ImageIO.read(tempRuleImgUrl);
            }
            
            URL pauseImgUrl = this.getClass().getResource("/testsquares2/resources/images/pause.png");
            pause = ImageIO.read(pauseImgUrl);
            
        }
        catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Load anh dan gian + Tri Tue nhan tao
        //Load am thanh
    }
    
    
    /**
     * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is updated and then the game is drawn on the screen.
     */
    private void GameLoop()
    {
        // This two variables are used in VISUALIZING state of the game. We used them to wait some time so that we get correct frame/window resolution.
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
        
        // This variables are used for calculating the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
        long beginTime, timeTaken, timeLeft;
        
        while(true)
        {
            beginTime = System.nanoTime();
            
            switch (gameState)
            {
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;
                    
                    game.UpdateGame(gameTime, mousePosition());
                    
                    lastTime = System.nanoTime();
                break;
                case GAMEOVER:
                    //...game.over();
                break;
                case PAUSE:
                    pause();
                break;
                case MAIN_MENU:
                    gameMenu();
                break;
                case OPTIONS:
                    //Tuy chinh am thanh
                break;
                case RULES:
                    gameRules();
                break;
                case GAME_CONTENT_LOADING:
                    //...
                break;
                
                case STARTING:
                    // Sets variables and objects.
                    Initialize();
                    // Load files - images, sounds, ...
                    LoadContent();
                    
                    try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                    // When all things that are called above finished, we change game status to main menu.
                    gameState = GameState.MAIN_MENU;
                break;
                case VISUALIZING:
                    
                    if(this.getWidth() > 1 && visualizingTime > secInNanosec)
                    {
                        frameWidth = this.getWidth();
                        frameHeight = this.getHeight();
                        preStarting();
                        // When we get size of frame we change status.
                        gameState = GameState.STARTING;
                    }
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                break;
            }
            
            // Repaint the screen.
            repaint();
            
            // 
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            // Thread FPS
            if (timeLeft < 10) 
                timeLeft = 10; //set a minimum
            try {
                 //Provides the necessary delay and also yields control so that other thread can do work.
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    
    /**
     * Draw the game to the screen. It is called through repaint() method in GameLoop() method.
     */
    @Override
    public void Draw(Graphics2D g2d)
    {
        g2d.setColor(Color.MAGENTA);
        g2d.setFont(new Font("SansSerif",Font.PLAIN, 40));
        g2d.drawString(String.valueOf(gameState), 0, 40);
        switch (gameState)
        {
            case PLAYING:
                game.Draw(g2d, mousePosition());
            break;
            case GAMEOVER:
                //Draw Ket qua
            break;
            case PAUSE:
                game.Draw(g2d, mousePosition());
                g2d.drawImage(pause, 240, 176, null);
            break;
            case GAME_CONTENT_LOADING:
            case MAIN_MENU:
                g2d.drawImage(bg_menu, 0, 0,frameWidth,frameHeight,null);
                g2d.drawImage(btn_start, frameWidth/2, frameHeight/3 + 70, null);
                g2d.drawImage(btn_rules, frameWidth/2 -17, frameHeight/3 + 165, null);
                g2d.drawImage(btn_options, frameWidth/2 -13, frameHeight/3 + 260, null);
                g2d.drawImage(btn_exit, frameWidth/2 + 13, frameHeight/3 + 355, null);               
            break;
            case OPTIONS:
                //Sound UI
            break;
            case RULES:
                g2d.drawImage(rules[pageRule],0 ,0 , null);
            break;
            case STARTING:
                g2d.drawImage(bg_introduce, 0, 0, null);
            break;
            
        }
    }
    
    /**
     * Game Menu
     */
    private void gameMenu(){
        if(Canvas.mouseButtonState(MouseEvent.BUTTON1)){
            if(new Rectangle(frameWidth/2, frameHeight/3 + 70,btn_start.getWidth(),btn_start.getHeight()).contains(mousePosition()))
                newGame();
        
            if(new Rectangle(frameWidth/2 -17, frameHeight/3 + 165,btn_rules.getWidth(),btn_rules.getHeight()).contains(mousePosition()))
                gameState = GameState.RULES;
            
            if(new Rectangle(frameWidth/2 -13, frameHeight/3 + 260,btn_options.getWidth(),btn_options.getHeight()).contains(mousePosition()))
                gameState = GameState.OPTIONS;
            
            if(new Rectangle(frameWidth/2 + 13, frameHeight/3 + 355,btn_exit.getWidth(),btn_exit.getHeight()).contains(mousePosition()))
                System.exit(0);
        }
        preState = GameState.MAIN_MENU;
    }
    //Luật chơi
    private void gameRules(){
        if(Canvas.keyboardKeyState(KeyEvent.VK_LEFT)&&(pageRule>1)){
            pageRule--;
            //Tam nghi
            try {
                 Thread.sleep(300);
            } catch (InterruptedException ex) { }
        }
        if(Canvas.keyboardKeyState(KeyEvent.VK_RIGHT)&&(pageRule<7)){
            pageRule++;
            //Tam nghi
            try {
                 Thread.sleep(300);
            } catch (InterruptedException ex) { }
        }
    }
    
    private void pause(){
        if(Canvas.mouseButtonState(MouseEvent.BUTTON1)){
            if(new Rectangle(550, 266,205,90).contains(mousePosition()))
                gameState = GameState.PLAYING;
            
            if(new Rectangle(550, 375,205,90).contains(mousePosition()))
                gameState = GameState.RULES;
            
            if(new Rectangle(550, 480,205,90).contains(mousePosition())){
                gameState = GameState.MAIN_MENU;
                try {
                 //Provides the necessary delay and also yields control so that other thread can do work.
                 Thread.sleep(100);
            } catch (InterruptedException ex) { }
            }
        }
        preState = GameState.PAUSE;
    }
    
    /**
     * Starts new game.
     */
    private void newGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game = new Game();
    }
    
    /**
     *  Restart game - reset game time and call RestartGame() method of game object so that reset some variables.
     */
    private void restartGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game.RestartGame();
        
        // We change game status so that the game can start.
        gameState = GameState.PLAYING;
    }
    
    
    /**
     * Returns the position of the mouse pointer in game frame/window.
     * If mouse position is null than this method return 0,0 coordinate.
     * 
     * @return Point of mouse coordinates.
     */
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }
    
    
    /**
     * This method is called when keyboard key is released.
     * 
     * @param e KeyEvent
     */
    @Override
    public void keyReleasedFramework(KeyEvent e)
    {
        switch (gameState)
        {
            case GAMEOVER:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    System.exit(0);
                else if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)
                    restartGame();
            break;
            case PLAYING:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    gameState = GameState.PAUSE;
            break;
            case PAUSE:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    gameState = GameState.PLAYING;
            break;
            case MAIN_MENU:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    System.exit(0);
            break;
            case RULES:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    gameState = preState;
            break;
            case OPTIONS:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    gameState = GameState.MAIN_MENU;
            break;
        }
    }
    
    /**
     * This method is called when mouse button is clicked.
     * 
     * @param e MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        
    }
}
