package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;

public class Coin extends Monster {
    private int iTimer;

    private int animateIddleState;

    public Coin(String sNomeImagePNG) {
        super(sNomeImagePNG);
        iTimer=0;
        animateIddleState=1;
    }

    public void shootPea(){

        Pea pea =new Pea("pea-d.png",getDirection());
        pea.setPosition(getPosition());
        Draw.getGameScreen().addElement(pea);
    }

    public void autoDraw() {
        double iRandDirection;
        iTimer++;
        //change direction randomly
        if (iTimer >= Consts.PERIOD/8) {
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
