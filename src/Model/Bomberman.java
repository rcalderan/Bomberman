/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Auxiliar.Draw;
import Controller.Screen;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Junio
 */
public class Bomberman extends Element implements Serializable{

    private int lives;
    private int bombPower;

    public Bomberman(String sImageNamePNG) {
        super(sImageNamePNG);
        lives=3;
        bombPower=1;
    }

    public void die(){
        lives--;
        System.out.println("Bomberman dead! Lives: "+lives);
        if(lives==0){
            System.out.println("Game Over");
        }
        else{
            backToLastPosition();
        }
    }

    public void autoDraw(){
        super.autoDraw();
        Screen screen = Draw.getGameScreen();
        ArrayList<Element> elementsOnThisPosition = screen.getElements(pPosition);
        if(elementsOnThisPosition.size()>1)
            for (Element el :elementsOnThisPosition)
                if(el.bMortal) {
                    die();
                    break;
                }
    }


    public void backToLastPosition(){
        this.pPosition.back();
    }
}
