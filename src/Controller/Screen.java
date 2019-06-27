/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Screen.java
 *
 * Created on 03/03/2011, 18:28:20
 */
package Controller;

import Model.*;
import Auxiliar.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.Timer;
import java.util.logging.*;

/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 *
 */
public class Screen extends javax.swing.JFrame implements MouseListener, KeyListener {

    private String gameMessange;
    private Bomberman bBomberman;
    //private ArrayList<Element> eElements;
    private GameController gameController;
    private Graphics graphics;
    private ArrayList<Stage> gameStages;
    /**
     * Creates the game screen
     */
    public Screen() {
        gameStages = new ArrayList<>();
        gameMessange = "Stage 1";
        //create Stages
        Stage stage1 = new Stage("Stage 1");
        Stage stage2 = new Stage("Stage 2");
        Stage stage3 = new Stage("Stage 3");

        stage1.setNextStage(stage2);
        stage2.setNextStage(stage3);
        //stage3 next is null. It means the player finished this game

        //instanciate the GameController1
        gameController = new GameController(stage1);


        Draw.setScenario(this);
        initComponents();
        // create game events
        this.addMouseListener(this);
        this.addKeyListener(this);
        // set screen dimension
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.CELL_SIDE+Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

        /*Create eElements and add elements*/
        bBomberman = new Bomberman("bomberman-d2.png");
        bBomberman.setPosition(0, 0);

        //wont place monsters in "notHere" positions
        ArrayList<Position> notHere = new ArrayList<>();
        for (int i=0;i< gameController.getCurrentStage().getNumberOfElements();i++)
            notHere.add(gameController.getCurrentStage().getElement(i).getPosition());


        //create monsters -
        //Stage 1 - only Balloons
        //Stage 2 - Ballons and Coins
        //Stage 3 - Ballons, Coins and Dinos
        Random rand =new Random();
        int numberOfMonsters =rand.nextInt(8+1)+4;
        for(int i=0;i<numberOfMonsters;i++) {
            Monster monster;
            do{//add for stage 1
                monster = Stage.createRandomMonster();
                int x = rand.nextInt(Consts.RES), y = rand.nextInt(Consts.RES);
                Position aux = new Position(x, y);
                x = rand.nextInt(Consts.RES);
                y = rand.nextInt(Consts.RES);
                monster.setPosition(x, y);
            } while (!(monster instanceof Balloon));
            stage1.addElement(monster);
            int r = rand.nextInt(2)+1;
            switch (r){
                case 2:
                    monster=new Coin();
                    break;
                    default:
                        monster=new Balloon();
                        break;
            }
            //monsters for stage2
            int x = rand.nextInt(Consts.RES);
            int y = rand.nextInt(Consts.RES);
            monster.setPosition(x,y);
            stage2.addElement(monster);
            monster = Stage.createRandomMonster();
             x = rand.nextInt(Consts.RES);
             y = rand.nextInt(Consts.RES);
            Position aux = new Position(x, y);
            x = rand.nextInt(Consts.RES);
            y = rand.nextInt(Consts.RES);
            monster.setPosition(x, y);
            stage3.addElement(monster);

        }

    }

    /**
     * Get player instance
     * @return Bomberman
     */
    public Bomberman getBomberman(){
        return bBomberman;
    }

    public String getGameMessange(){return gameMessange;}
    public void setGameMessange(String msg){gameMessange=msg;}
    /**
     * Check if a bomb could be placed
     * @return true if could, false if could not
     */
    public boolean couldPlaceBomb(){
        int bombCount=0;
        for(int i=0; i<gameController.getCurrentStage().getNumberOfElements();i++){
            if(gameController.getCurrentStage().getElement(i).toString().equals("Bomb"))
            {
                bombCount++;
                if(bombCount>=bBomberman.getBombs())
                    return false;
            }
        }
        return true;
    }

    /**
     * update non static game Hud elements
     */
    public void updateHUD(){
        //lives
        graphics.clearRect(Consts.CELL_SIDE,  Consts.CELL_SIDE*11,Consts.CELL_SIDE,Consts.CELL_SIDE);
        Draw.getGameScreen().graphics.drawString(Integer.toString(bBomberman.getLives()),Consts.CELL_SIDE,  Consts.CELL_SIDE*12-5);
        //power
        graphics.clearRect(Consts.CELL_SIDE*3,  Consts.CELL_SIDE*11,Consts.CELL_SIDE,Consts.CELL_SIDE);
        Draw.getGameScreen().graphics.drawString(Integer.toString(bBomberman.getPower()),Consts.CELL_SIDE*3,  Consts.CELL_SIDE*12-5);
        //bombs
        graphics.clearRect(Consts.CELL_SIDE*5,  Consts.CELL_SIDE*11,Consts.CELL_SIDE,Consts.CELL_SIDE);
        Draw.getGameScreen().graphics.drawString(Integer.toString(bBomberman.getBombs()),Consts.CELL_SIDE*5,  Consts.CELL_SIDE*12-5);
        //score
        graphics.clearRect(Consts.CELL_SIDE*7,  Consts.CELL_SIDE*11,Consts.CELL_SIDE*4,Consts.CELL_SIDE);
        Draw.getGameScreen().graphics.drawString(Integer.toString(gameController.getCurrentStage().getScore()),Consts.CELL_SIDE*7,  Consts.CELL_SIDE*12-5);
    }


    /*--------------------------------------------------*/
    /*------Não se preocupe com o código a seguir-------*/
    /*--------------------------------------------------*/
    public void addElement(Element aElement) {
        gameController.getCurrentStage().addElement(aElement);
    }
    /**
    public void removeElement(Element aElement) {
        eElements.remove(aElement);
    }
    */
    public boolean isValidPosition(Position p){
        if(p.getColumn()>=Consts.RES)
            return false; //out of screen
        if(p.getLine()>=Consts.RES)
            return false; //out of screen
        return gameController.isValidPosition(p);
    }

    public Graphics getGraphicsBuffer(){
        return graphics;
    }

    public void paint(Graphics gOld) {
        Graphics g = this.getBufferStrategy().getDrawGraphics();
        String backgroundName="green.png";

        /*Criamos um contexto gráfico*/
        graphics = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);

        //prints PAUSED message
        if(gameController.getGamePause()) {

            graphics.setColor(new Color(255,255,255));
            graphics.setFont(new Font("Arial", Font.PLAIN, 50));
            //backgroundName="black.png";
            graphics.drawString(getGameMessange(), Consts.CELL_SIDE, Consts.CELL_SIDE * 5);
        }
        else
        /*Desenha cenário*/
        for (int i = 0; i < Consts.RES; i++) {
            for (int j = 0; j <= Consts.RES; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + backgroundName);
                    graphics.drawImage(newImage,
                            j * Consts.CELL_SIDE, i * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);

                } catch (IOException ex) {
                    Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (this.gameController.getCurrentStage().getNumberOfElements() !=0) {
            this.gameController.drawEverything();
            this.gameController.processEverything();
        }
        //create game hud: static
        try{
            graphics.setFont(new Font("Arial", Font.PLAIN, 50));
            Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "lifeUp.png");
            graphics.drawImage(newImage,0,  Consts.CELL_SIDE*11, null);

            newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "powerUp.png");
            graphics.drawImage(newImage,Consts.CELL_SIDE*2,  Consts.CELL_SIDE*11, null);


            newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "bombUp.png");
            graphics.drawImage(newImage,Consts.CELL_SIDE*4,  Consts.CELL_SIDE*11, null);

            newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "score.png");
            graphics.drawImage(newImage,Consts.CELL_SIDE*6,  Consts.CELL_SIDE*11, null);


        }catch (IOException er){
            System.out.println(er.getMessage());
        }

        updateHUD();
        g.dispose();
        graphics.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    public void go() {
        TimerTask task = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.PERIOD);
    }

    public void keyPressed(KeyEvent e) {
        boolean gameState = gameController.getGamePause();
         if (e.getKeyCode() == KeyEvent.VK_P) {
            graphics.clearRect(Consts.CELL_SIDE,  Consts.CELL_SIDE*11,Consts.CELL_SIDE,Consts.CELL_SIDE);
            setGameMessange("Game Paused!");
            gameController.setGamePaused(!gameState);
            paint(graphics);
            //clearScreen();
            //this.eElements.clear();
        }if (e.getKeyCode() == KeyEvent.VK_C) {
            for(int i=0; i<gameController.getCurrentStage().getNumberOfElements();i++){
                Element el =gameController.getCurrentStage().getElement(i);
                if(el.toString().equals("Bomb")){
                    var b = (Bomb)el;
                    if(b.getType().equals(Bomb.BOMBTYPE.REMOTE))
                    {
                        b.setbKill(true);
                        b.isReadyToExplode(true);
                        break;
                    }
                }
            }
            //explode imediattly the first trigger bomb placed
        }

        else if (e.getKeyCode() == KeyEvent.VK_K) {
            gameController.killAllMonsters();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            /*try {
                File tanque = new File("c:\\temp\\POO.zip");
                tanque.createNewFile();
                FileOutputStream canoOut = new FileOutputStream(tanque);
                GZIPOutputStream compactador = new GZIPOutputStream(canoOut);
                ObjectOutputStream serializador = new ObjectOutputStream(compactador);
                serializador.writeObject(this.eElements);
                serializador.flush();
                serializador.close();
            } catch (IOException ex) {
                Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            // check if game is paused. BomberMan couldnd move or place bomb if its paused.
        } else if (e.getKeyCode() == KeyEvent.VK_UP && !gameState) {
            bBomberman.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && !gameState) {
            bBomberman.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && !gameState) {
            bBomberman.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !gameState) {
            bBomberman.moveRight();
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameState) {
            if(couldPlaceBomb()){
                Bomb b = new Bomb("bomba.png",getBomberman().getPower());
                b.setType(bBomberman.getBombType());
                b.setPosition(bBomberman.getPosition());
                gameController.getCurrentStage().addElement(b);
            }
        }
        // check if game is paused. BomberMan couldnd move if its paused
        if (!this.isValidPosition(bBomberman.getPosition())) {
            bBomberman.backToLastPosition();
        }

        this.setTitle("-> Cell: " + (bBomberman.getPosition().getColumn()) + ", "
                + (bBomberman.getPosition().getLine()));

        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
    }

    public void mousePressed(MouseEvent e) {
        /* Clique do mouse desligado
         int x = eElements.getX();
         int y = eElements.getY();
     
         this.setTitle("X: "+ x + ", Y: " + y +
         " -> Cell: " + (y/Consts.CELL_SIDE) + ", " + (x/Consts.CELL_SIDE));
        
         this.bBomberman.getPosition().setPosition(y/Consts.CELL_SIDE, x/Consts.CELL_SIDE);
         */
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2015-1 - Adventures of lolo");
        setAutoRequestFocus(false);
        setPreferredSize(new java.awt.Dimension(500, 500));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
