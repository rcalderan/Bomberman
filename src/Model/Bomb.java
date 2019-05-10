/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;

import java.io.Serializable;

/**
 *
 * @author aula
 */
public class Bomb extends Element implements Serializable{
    private int countDown;
    private int power;
    public Bomb(String sImageNamePNG, int power) {

        super(sImageNamePNG);
        countDown =0;
        this.power=power;
    }
    public int getPower(){
        return power;
    }

    public int getCountDown(){
        return countDown;
    }
    public void setCountDown(int time){
        countDown =time;
    }
    
    public void autoDraw(){
        countDown += Consts.PERIOD;

        if(countDown == 80){
            this.bTransposable = false;
        }else if(countDown >= Consts.TIMER_BOMB+80){
            this.bKill = true;
        }
        super.autoDraw();
    }


    
    
    
    
}
