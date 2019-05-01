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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author aula
 */
public class Bomb extends Element implements Serializable{
    int iCountTime = 0;
    int iCountExplosion = 0;
    int iRadius = 0;
    boolean exploded=false;
    public Bomb(String sImageNamePNG) {
        super(sImageNamePNG);
    }
    
    public void autoDraw(){
        iCountTime += Consts.PERIOD;
        iRadius++;

        if(exploded){
            iCountExplosion++;

        }
        if(iCountTime > Consts.TIMER_BOMB && !exploded){
            Explosion explode = new Explosion("explosao_vetor.png",pPosition,2);
            Draw.getGameScreen().addElement(explode);
            Draw.getGameScreen().removeElement(this);

        }
        super.autoDraw();
    }


    private void changeAppearance(String sNewAppearance){
        try {        
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNewAppearance);
        } catch (IOException ex) {
            Logger.getLogger(Bomb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
