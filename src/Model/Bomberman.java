/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Auxiliar.Position;
import Controller.Screen;
import sun.plugin2.gluegen.runtime.CPU;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Junio
 */
public class Bomberman extends Character implements Serializable{

    private int lives;
    private int power;
    private int bombs;
    private int timer;
    private int invencibilityCountDown;

    public Bomberman(String sImageNamePNG) {
        super(sImageNamePNG);
        timer=0;
        lives=2;
        power=1;
        bombs=1;
    }

    /* bomberman lose 1 live and reset his powers to default value
    He must be invencible for some seconds
     */
    public void die(){
        setLives(getLives() - 1);
        setPosition(new Position(0,0));//returns to start position
        power=1;
        bombs=1;
        invencibilityCountDown=0;//become invencible
    }
    public int getNbomb(){
        return bombs;
    }
    public void setBombs(int nBomb){
        bombs=nBomb;
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

    /*
    Lose life only when not invencible
     */
    public void setLives(int lives) {
        if(getLives()<lives)
            this.lives = lives;
        else if(getLives()>lives && invencibilityCountDown > Consts.INVENCIBILITY_TIME){
            this.lives = lives;
        }
    }

    public int getBombs(){
        return this.bombs;
    }

    public void autoDraw(){
        timer+=Consts.TIMER;
        if(timer== Consts.PERIOD){
            timer=0;
            if(invencibilityCountDown<=Consts.INVENCIBILITY_TIME){
                invencibilityCountDown++;
            }
        }
        super.autoDraw();
    }



    public void backToLastPosition(){
        this.pPosition.back();
    }
}
