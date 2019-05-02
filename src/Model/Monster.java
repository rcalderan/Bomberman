/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;

import java.util.Random;

/**
 *
 * @author ICMC
 */
public class Monster extends Element {

    int iTimer = 0;

    public Monster(String sNomeImagePNG) {
        super(sNomeImagePNG);
    }

    public void autoDraw() {
        int iRandDirection;
        iTimer++;
        if (iTimer == Consts.PERIOD) {
            iTimer = 0;
            /*sortear um número de 1 a 4*/
            Random r = new Random();
            iRandDirection = r.nextInt(4);
            /*de acordo com o numero sorteado, mover o personagem*/
            if (iRandDirection == 0) {
                this.moveDown();
            } else if (iRandDirection == 1) {
                this.moveLeft();
            } else if (iRandDirection == 2) {
                this.moveUp();
            } else {
                this.moveRight();
            }
        }
        if (!Draw.isValidPosition(pPosition))
            this.getPosition().back();
        super.autoDraw();
    }
}
