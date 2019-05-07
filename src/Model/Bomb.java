/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author aula
 */
public class Bomb extends Element implements Serializable{
    int iCountTime;
    int power;
    public Bomb(String sImageNamePNG, int power) {

        super(sImageNamePNG);

        iCountTime=0;
        this.power=power;
    }
    public int getPower(){
        return power;
    }

    public int getiCountTime(){
        return iCountTime;
    }
    
    public void autoDraw(){
        iCountTime += Consts.PERIOD;

        if(iCountTime == 80){
            this.bTransposable = false;
        }else{
            if(iCountTime == Consts.TIMER_BOMB){//bomb explode!
                /*
                this.changeAppearance("explosao.png");
                this.bMortal=true;
                boolean[] foundLimit = new boolean[]{true,true,true,true};

                iRadius=bomberman.getPower();

                for(int i= 1; i <= this.iRadius; i++){
                    BombFire fb = new BombFire("explosao.png");
                    if(foundLimit[0] && fb.setPosition(this.getPosition().getLine(), this.getPosition().getColumn()-i)){
                        foundLimit[0] =Draw.getGameScreen().isValidPosition(fb.pPosition);
                        Draw.getGameScreen().addElement(fb);
                    }

                    BombFire fb2 = new BombFire("explosao.png");
                    if(foundLimit[1] && fb2.setPosition(this.getPosition().getLine(), this.getPosition().getColumn()+i)) {
                        foundLimit[1] =Draw.getGameScreen().isValidPosition(fb2.pPosition);
                        Draw.getGameScreen().addElement(fb2);
                    }

                    BombFire fb3 = new BombFire( "explosao.png");
                    if(foundLimit[2]&& fb3.setPosition(this.getPosition().getLine()-i, this.getPosition().getColumn())) {
                        foundLimit[2] =Draw.getGameScreen().isValidPosition(fb3.pPosition);
                        Draw.getGameScreen().addElement(fb3);
                    }

                    BombFire fb4 = new BombFire("explosao.png");
                    if(foundLimit[3]&& fb4.setPosition(this.getPosition().getLine()+i, this.getPosition().getColumn())) {
                        foundLimit[3] =Draw.getGameScreen().isValidPosition(fb4.pPosition);
                        Draw.getGameScreen().addElement(fb4);
                    }

                }*/
            }else if(iCountTime >= Consts.TIMER_BOMB+80){
                this.bKill = true;
            }
        }
        super.autoDraw();
    }


    
    
    
    
}
