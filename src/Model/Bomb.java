/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;

import java.io.Serializable;

/**
 *
 * @author aula
 */
public class Bomb extends Element implements Serializable{
    private int countDown;
    private int power;
    private boolean explode;
    public Bomb(String sImageNamePNG, int power) {

        super(sImageNamePNG);
        countDown =0;
        this.power=power;
        setExplode(false);
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
            explodeIt();
        }
        super.autoDraw();
    }


    public boolean getExplode() {
        return explode;
    }
    public void explodeIt(){
        if(getCountDown()>= Consts.TIMER_BOMB){
            boolean[] explosionLimit = new boolean[]{true,true,true,true};
            BombFire f = new BombFire("explosion.png");

            f.setPosition(getPosition());
            Draw.getGameScreen().addElement(f);
            for(int i=1;i<=getPower();i++){
                String expName;

                if(i==getPower())
                    expName="explosion-L.png";
                else
                    expName = "explosion-horizontal.png";
                BombFire fb = new BombFire(expName);
                if(explosionLimit[0] && fb.setPosition(getPosition().getLine(), getPosition().getColumn()-i)){
                    explosionLimit[0] = Draw.getGameScreen().isValidPosition(fb.getPosition());
                    Draw.getGameScreen().addElement(fb);
                }

                if(i==getPower())
                    expName="explosion-R.png";
                else
                    expName = "explosion-horizontal.png";
                BombFire fb2 = new BombFire(expName);
                if(explosionLimit[1] && fb2.setPosition(getPosition().getLine(), getPosition().getColumn()+i)) {
                    explosionLimit[1] =Draw.getGameScreen().isValidPosition(fb2.getPosition());
                    Draw.getGameScreen().addElement(fb2);
                }

                if(i==getPower())
                    expName="explosion-U.png";
                else
                    expName = "explosion-vertical.png";
                BombFire fb3 = new BombFire( expName);
                if(explosionLimit[2]&& fb3.setPosition(getPosition().getLine()-i, getPosition().getColumn())) {
                    explosionLimit[2] =Draw.getGameScreen().isValidPosition(fb3.getPosition());
                    Draw.getGameScreen().addElement(fb3);
                }

                if(i==getPower())
                    expName="explosion-D.png";
                else
                    expName = "explosion-vertical.png";
                BombFire fb4 = new BombFire(expName);
                if(explosionLimit[3]&& fb4.setPosition(getPosition().getLine()+i, getPosition().getColumn())) {
                    explosionLimit[3] =Draw.getGameScreen().isValidPosition(fb4.getPosition());
                    Draw.getGameScreen().addElement(fb4);
                }
            }
        }
    }

    public void setExplode(boolean explode) {
        this.explode = explode;
    }
}
