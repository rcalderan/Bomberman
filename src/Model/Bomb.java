/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;

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
    int iCountTime = 0;
    int iRadius = 0;
    public Bomb(String sImageNamePNG) {
        super(sImageNamePNG);
    }
    
    public void autoDraw(){
        iCountTime += Consts.PERIOD;
        iRadius++;
        if(iCountTime == 400){
            this.changeAppearance("explosao.png");
        }else if(iCountTime >= 960){
            this.bKill = true;
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
