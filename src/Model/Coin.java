package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import java.util.Random;

public class Coin extends Monster {
    private int iTimer;
    private int distance;
    private int animateIddleState;

    public Coin() {
        super("coin-d.png");
        iTimer=0;
        distance=4;
        animateIddleState=1;
        setDirection(randomDirection(Consts.DIRECTION.UP));

    }

    private Consts.DIRECTION randomDirection(Consts.DIRECTION old){
        double iRandDirection;
        Consts.DIRECTION dir = old;
        while (dir==old){
            iRandDirection = Math.random();
            if (iRandDirection < 0.40) {
                dir = Consts.DIRECTION.DOWN;
            }else if (iRandDirection > 0.40 && iRandDirection < 0.8) {
                dir =  Consts.DIRECTION.UP;
            } else if (iRandDirection > 0.8 && iRandDirection <0.9) {
                dir =  Consts.DIRECTION.LEFT;
            } else {
                dir =  Consts.DIRECTION.RIGHT;
            }
        }
        return dir;
    }

    /**
     * This monster cant shoot
     */
    public void shoot(){}


    public void autoDraw() {
        iTimer++;
        //change direction randomly
        if (iTimer >= Consts.PERIOD/8) {
            iTimer = 0;
            int tries=0;
            boolean moved =false;
            while (!moved && tries < 8) {
                tries++;
                if (getDirection().equals(Consts.DIRECTION.DOWN)) {
                    moved = this.moveDown();
                } else if (getDirection().equals(Consts.DIRECTION.UP)) {
                    moved = this.moveUp();
                } else if (getDirection().equals(Consts.DIRECTION.LEFT)) {
                    moved = this.moveLeft();
                } else {
                    moved = this.moveRight();
                }
                if(!moved)
                    setDirection(randomDirection(getDirection()));
            }
            distance--;
            if(distance==0){
                setDirection(randomDirection(getDirection()));
                distance=new Random().nextInt(10+1)+8;
            }
        }


        if (iTimer > Consts.PERIOD/40) {
            changeAppearance(getCharacterName()+animateIddleState+".png");
            animateIddleState++;
            if(animateIddleState==7)
                animateIddleState=1;
        }

        if (!Draw.isValidPosition(pPosition))
            this.getPosition().back();

        Draw.draw(this.iImage, pPosition.getColumn(), pPosition.getLine());
    }
}
