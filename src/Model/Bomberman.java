/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Auxiliar.Position;
import Controller.Screen;
//import sun.plugin2.gluegen.runtime.CPU;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Junio
 */
public class Bomberman extends Character implements Serializable{

    private int lives;
    private int power;  //Bomb's fire range
    private int bombs;
    private int timer;
    private int invencibilityCountDown;//became invercible for a short time when die
    private boolean invencible;

    public Bomberman(String sImageNamePNG) {
        super(sImageNamePNG);
        timer=0;
        lives=3;
        power=1;
        bombs=1;
        invencible=false;
        invencibilityCountDown= Consts.INVENCIBILITY_TIME;
    }

    public boolean isInvencible() {
        return invencible;
    }
    public void setInvencible(boolean invencible) {
        invencibilityCountDown=0;
        this.invencible = invencible;
    }
    public int getNbomb(){
        return bombs;
    }
    public int getBombs(){
        return this.bombs;
    }
    public void setBombs(int nBomb){
        bombs=nBomb;
        Draw.getGameScreen().updateHUD();
    }
    public int getPower(){
        return this.power;
    }
    public int getLives(){
        return this.lives;
    }
    public void setLives(int lives) {
        //Lose life only when not invencible
        this.lives = lives;
        Draw.getGameScreen().updateHUD();
    }

    /**
     * @author Richard Calderan - 3672382
     * @author Leticia Burla - 10294950
     *
     * bomberman loses 1 live and resets his powers to default value
     * He must be invencible for some seconds
     * @return void
     */
    public void die(){
        if(!isInvencible()) {
            setLives(getLives() - 1);
            setPosition(new Position(0, 0));//returns to start position
            power = 1;
            bombs = 1;
            setInvencible(true);
            Draw.getGameScreen().updateHUD();
        }
    }

    /**
     * increases bomberman's bomb power
     * @return void
     */
    public void powerUp(){
        this.power = this.power + 1;
        Draw.getGameScreen().updateHUD();
    }

    public void autoDraw(){
        timer+=Consts.TIMER;

        if(timer== Consts.PERIOD/8){
            timer=0;
            if(invencibilityCountDown<=Consts.INVENCIBILITY_TIME){
                invencibilityCountDown++;
            }
            else
                setInvencible(false);
        }
        super.autoDraw();
    }
    /**
     * gests bomberman to his previus position
     * @return void
     */
    public void backToLastPosition(){
        this.pPosition.back();
    }
}
