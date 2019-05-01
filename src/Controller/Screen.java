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
import java.util.logging.*;
import java.util.zip.*;
/**
 *
 * @author junio
 */
public class Screen extends javax.swing.JFrame implements MouseListener, KeyListener {

    private Bomberman bBomberman;
    private ArrayList<Element> eElements;
    private ArrayList<Element> fase1;
    private ArrayList<Element> fase2;
    private ArrayList<Element> fase3;
    private GameController gameController = new GameController();
    private Graphics graphics;
    /**
     * Creates new form tabuleiro
     */
    public Screen() {
        Draw.setScenario(this);
        initComponents();
        this.addMouseListener(this); /*mouse*/

        this.addKeyListener(this);   /*teclado*/
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

        eElements = new ArrayList<Element>(100);

        /*Cria eElements adiciona elementos*/
        bBomberman = new Bomberman("bomberman.png");
        bBomberman.setPosition(0, 7);
        this.addElement(bBomberman);

        BichinhoVaiVemHorizontal bBichinhoH = new BichinhoVaiVemHorizontal("bichinho.png");
        bBichinhoH.setPosition(3, 3);
        this.addElement(bBichinhoH);

        Caveira bV = new Caveira("caveira.png");
        bV.setPosition(9, 1);
        this.addElement(bV);
        
        Bomb b = new Bomb("bomba.png");
        b.setPosition(8, 8);
        this.addElement(b);
        
        RoboAleatorio rb = new RoboAleatorio("bichinho.png");
        rb.setPosition(1, 1);
        this.addElement(rb);
        
        ParedeDura t1 = new ParedeDura("parede.png");
        t1.setPosition(4, 4);
        BichinhoVaiVemHorizontal bBichinhoH2 = new BichinhoVaiVemHorizontal("bichinho.png");
        bBichinhoH2.setPosition(6, 6);
        this.addElement(bBichinhoH2);

        BichinhoVaiVemHorizontal bBichinhoH3 = new BichinhoVaiVemHorizontal("bichinho.png");
        bBichinhoH3.setPosition(2,9);
        this.addElement(t1);
        
        ParedeDura t2 = new ParedeDura("parede.png");
        t2.setPosition(4, 5);
        this.addElement(t2);

        ParedeDura t3 = new ParedeDura("parede.png");
        t3.setPosition(4, 6);
        this.addElement(t3);

        ParedeDura t4 = new ParedeDura("parede.png");
        t4.setPosition(0, 6);
        this.addElement(t4);

        ParedeDura t5 = new ParedeDura("parede.png");
        t5.setPosition(1, 6);
        this.addElement(t5);

        ParedeDura t6 = new ParedeDura("parede.png");
        t6.setPosition(1, 7);
        this.addElement(t6);
        
    }

    /**
     * get a game element in position
     * @param pos position
     * @return Element or null if not found
     */
    public ArrayList<Element> getElements(Position pos){
        ArrayList<Element> all =new ArrayList<>();
        for(Element el : eElements){
            if(el.getPosition().equal(pos))
                all.add(el);
        }
        return all;
    }
/*--------------------------------------------------*/
/*------Não se preocupe com o código a seguir-------*/
/*--------------------------------------------------*/
    public void addElement(Element aElement) {
        eElements.add(aElement);
    }

    public void removeElement(Element aElement) {
        eElements.remove(aElement);
    }

    public boolean isValidPosition(Position p){
        if(p.getColumn()>=Consts.RES)
            return false; //out of screen
        if(p.getLine()>=Consts.RES)
            return false; //out of screen
        return gameController.isValidPosition(this.eElements, p);
    }
    
    public Graphics getGraphicsBuffer(){
        return graphics;
    }
       
    public void paint(Graphics gOld) {
        Graphics g = this.getBufferStrategy().getDrawGraphics();
        /*Criamos um contexto gráfico*/
        graphics = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);
        /*Desenha cenário*/
        for (int i = 0; i < Consts.RES; i++) {
            for (int j = 0; j < Consts.RES; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "green.png");
                    graphics.drawImage(newImage,
                            j * Consts.CELL_SIDE, i * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);

                } catch (IOException ex) {
                    Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (!this.eElements.isEmpty()) {
            this.gameController.drawEverything(eElements);
            this.gameController.processEverything(eElements);
        }

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
        if (e.getKeyCode() == KeyEvent.VK_C) {
            this.eElements.clear();
        } else if (e.getKeyCode() == KeyEvent.VK_L) {
            try {
                File tanque = new File("c:\\temp\\POO.zip");
                FileInputStream canoOut = new FileInputStream(tanque);
                GZIPInputStream compactador = new GZIPInputStream(canoOut);
                ObjectInputStream serializador = new ObjectInputStream(compactador);
                this.eElements = (ArrayList<Element>)serializador.readObject();
                serializador.close();
            } catch (Exception ex) {
                Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            try {
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
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            bBomberman.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            bBomberman.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            bBomberman.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            bBomberman.moveRight();
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //bBomberman.soltaUmaBomba();
            Bomb b = new Bomb("bomba.png");
            b.setPosition(bBomberman.getPosition());
            this.addElement(b);
        }
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
