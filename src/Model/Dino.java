package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;


public class Dino extends Monster {
    private int iTimer;
    public Dino() {
        super("dino-d.png");
        points=300;
        iTimer=0;
    }

    /**
     *  shot a pea from this position in this direction
     */
    public void shoot(){}

    public void autoDraw(){
        if(lifeState.equals(STATE.DYING)){
            deadTime++;
            if (deadTime > Consts.PERIOD/40) {
                changeAppearance(getCharacterName()+"-death"+animateDeadState+".png");
                Draw.draw(this.iImage, pPosition.getColumn(), pPosition.getLine());
                animateDeadState++;
                deadTime=0;
            }
        }
        if(animateDeadState>7){
            lifeState=STATE.DEAD;//will be removed on next frame
            return;
        }
        double iRandDirection;
        iTimer++;
        //change direction randomly
        if (iTimer > Consts.PERIOD/10 && getLifeState().equals(STATE.ALIVE)) {
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

        Draw.draw(this.iImage, pPosition.getColumn(), pPosition.getLine());
    }

}
