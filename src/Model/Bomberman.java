/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Auxiliar.Position;
import Controller.Screen;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Junio
 */
public class Bomberman extends Element implements Serializable{

    private int lives;
    private int power;
    private int coolDown;

    public Bomberman(String sImageNamePNG) {
        super(sImageNamePNG);
        lives=2;
        power=1;
    }

    public void powerUp(){
        this.power = this.power + 1;
    }
    public int getPower(){
        return this.power;
    }

    public int getLives(){

        return this.lives;
    }
    public void setLives(int lives) {
            System.out.println(lives);
        this.lives = lives;
    }




    public void autoDraw(){
        super.autoDraw();
        Screen screen = Draw.getGameScreen();
        ArrayList<Element> elementsOnThisPosition = screen.getElements(pPosition);
        if(elementsOnThisPosition.size()>1)
            for (Element el :elementsOnThisPosition) {
                if (el.bMortal) {//die
                    setLives(getLives() - 1);
                    setPosition(new Position(0, 0));//returns to start position
                    if (getLives() == 0) {
                        //game over...
                    }
                }
                if(el instanceof PowerUp){
                    powerUp();

                }
                if(el instanceof LifeUp){
                    setLives(getLives()+1);
                }
            }
    }


    public void backToLastPosition(){
        this.pPosition.back();
    }
}
