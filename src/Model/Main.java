/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Screen;

/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 * @author Henrique Ruher - 9292538
 *
 * Main class. It calls main method and starts the game
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Screen tScreen = new Screen();

                tScreen.setVisible(true);
                tScreen.createBufferStrategy(2);
                tScreen.go();
            }
        });
    }
}

