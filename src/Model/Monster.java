/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Auxiliar.Position;

import java.util.Random;

/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 */
public class Monster extends Character {

    private int iTimer;

    public Monster(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bMortal=true;
        this.bTransposable = true;
        this.iTimer =0;
        changeDirection();
    }

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

    private void move(){
        switch (getDirection()){
            case DOWN:
                moveDown();
                break;
            case UP:
                moveUp();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }

        Position next = getPosition();
        if (!Draw.isValidPosition(next)) {
            this.getPosition().back();
            changeDirection();
        }
    }


    public void autoDraw() {
        double iRandDirection;
        iTimer++;
        //change direction randomly
        if (iTimer == Consts.PERIOD/8) {
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
