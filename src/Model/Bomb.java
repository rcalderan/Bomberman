/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Auxiliar.Position;
import Controller.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author aula
 */
public class Bomb extends Element implements Serializable{
    int iCountTime = 0;
    int iRadius;
    public Bomb(String sImageNamePNG) {

        super(sImageNamePNG);
        Bomberman bomberman = Draw.getGameScreen().getBomberman();
        iRadius=bomberman.getPower();
    }
    
    public void autoDraw(){
        iCountTime += Consts.PERIOD;
        //iRadius++;

        if(iCountTime == 80){/*Algum tempo depois de colocar a bomba ela fica
            intransponivel*/
            this.bTransposable = false;
        }else{
            if(iCountTime == Consts.TIMER_BOMB){
                this.changeAppearance("explosao_vetor.png");
                this.bMortal=true;
                boolean[] foundLimit = new boolean[]{true,true,true,true};


                for(int i= 1; i <= this.iRadius; i++){
                    StaticFire fb = new StaticFire("explosao.png");
                    if(foundLimit[0] && fb.setPosition(this.getPosition().getLine(), this.getPosition().getColumn()-i)){
                        foundLimit[0] =Draw.getGameScreen().isValidPosition(fb.pPosition);
                        Draw.getGameScreen().addElement(fb);
                    }

                    StaticFire fb2 = new StaticFire("explosao.png");
                    if(foundLimit[1] && fb2.setPosition(this.getPosition().getLine(), this.getPosition().getColumn()+i)) {
                        foundLimit[1] =Draw.getGameScreen().isValidPosition(fb2.pPosition);
                        Draw.getGameScreen().addElement(fb2);
                    }

                    StaticFire fb3 = new StaticFire( "explosao.png");
                    if(foundLimit[2]&& fb3.setPosition(this.getPosition().getLine()-i, this.getPosition().getColumn())) {
                        foundLimit[2] =Draw.getGameScreen().isValidPosition(fb3.pPosition);
                        Draw.getGameScreen().addElement(fb3);
                    }

                    StaticFire fb4 = new StaticFire("explosao.png");
                    if(foundLimit[3]&& fb4.setPosition(this.getPosition().getLine()+i, this.getPosition().getColumn())) {
                        foundLimit[3] =Draw.getGameScreen().isValidPosition(fb4.pPosition);
                        Draw.getGameScreen().addElement(fb4);
                    }

                }
            }else if(iCountTime >= Consts.TIMER_BOMB+280){
                this.bKill = true;
            }
        }
        super.autoDraw();
    }


    private void changeAppearance(String sNewAppearance){
        try {        
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNewAppearance);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            Logger.getLogger(Bomb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
