/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 * @author Henrique Ruher - 9292538
 *
 * Represents an generic monster.
 */
public class Monster extends Character {

    private int iTimer;
    private boolean shooter;
    protected int points;

    public Monster(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bMortal=true;
        this.bTransposable = true;
        this.iTimer =0;
        this.shooter=false;
        this.points=100;
        changeDirection();
    }

    public int getPoints(){return points;}

    public boolean isShooter(){return shooter;}

    public void setShooter(boolean isShooter){this.shooter = isShooter;}

    //Randonly change moster's direction
    private void changeDirection(){
        Random r = new Random();
        Consts.DIRECTION nextDirection;
        int iRandDirection = r.nextInt(4);

        if (iRandDirection == 0) {
            nextDirection=Consts.DIRECTION.DOWN;
        } else if (iRandDirection == 1) {
            nextDirection=Consts.DIRECTION.LEFT;
        } else if (iRandDirection == 2) {
            nextDirection=Consts.DIRECTION.UP;
        } else {
            nextDirection=Consts.DIRECTION.RIGHT;
        }
        setDirection(nextDirection);
    }

    /**
     *  shot a pea from this position in this direction
     */
    public void shoot(){
        Pea pea =new Pea(getDirection());
        pea.setPosition(getPosition());
        Draw.getGameScreen().addElement(pea);
    }

    public void autoDraw() {


        double iRandDirection;
        iTimer++;
        //change direction randomly
        if (iTimer > Consts.PERIOD/6 && getLifeState().equals(STATE.ALIVE)) {
            iTimer = 0;
            /*row a number between 0 e 1 and move it*/
            iRandDirection = Math.random();
            if (iRandDirection < 0.40) {
                this.moveDown();
            }else if (iRandDirection > 0.40 && iRandDirection < 0.8) {
                this.moveUp();
            } else if (iRandDirection > 0.8 && iRandDirection <0.9) {
                this.moveLeft();
            } else {
                this.moveRight();
            }
        }
        if (!Draw.isValidPosition(pPosition))
            this.getPosition().back();

        super.autoDraw();
    }

}
