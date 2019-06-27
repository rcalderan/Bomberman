/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Auxiliar.Position;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 * @author Henrique Ruher - 9292538
 */
public class Bomb extends Element implements Serializable{
    private int countDown;
    private int power;  //explosion range
    private boolean readyToExplode;
    private BOMBTYPE type;

    public Bomb(String sImageNamePNG, int power) {
        super(sImageNamePNG);
        countDown =0;
        this.power=power;
        isReadyToExplode(false);
        type = BOMBTYPE.NORMAL;
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
    public boolean getReadyToExplode() {
        return readyToExplode;
    }

    public BOMBTYPE getType() { return type; }

    /**
     * Set bombType. Change bombe appearance if not normal type
     * @param type
     */
    public void setType(BOMBTYPE type) {
        this.type = type;
        if(type.equals(BOMBTYPE.REMOTE))
            changeAppearance("BombRemote.png");
    }

    public void autoDraw(){
            countDown += Consts.PERIOD;
            if(countDown == 80){
                this.bTransposable = false;
            }

            if(countDown >= Consts.TIMER_BOMB && type.equals(BOMBTYPE.NORMAL)){
                this.setbKill(true);
                isReadyToExplode(true);
            }
        super.autoDraw();
    }

    /**
     * Updates bomb information to make it ready to explode
     * @param readyToExplode true if ready to explode
     * @return void
     */
    public void isReadyToExplode(boolean readyToExplode) {
        if(readyToExplode)
            countDown = Consts.TIMER_BOMB;
        this.readyToExplode = readyToExplode;
    }

    @Override
    public String toString() {
        return "Bomb";
    }

    public enum BOMBTYPE{
        NORMAL, REMOTE;
    }
}
