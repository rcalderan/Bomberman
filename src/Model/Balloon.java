/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import Auxiliar.Consts;
import Auxiliar.Draw;

/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 * @author Henrique Ruher - 9292538
 *
 * Moster that moves randomly
 */
public class Balloon extends Monster{
    private int iTimer;
    public Balloon() {
        super("balloon-d.png");
        iTimer=0;
    }

    /**
     * This monster cant shoot
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
        iTimer++;
        super.autoDraw();

        //Draw.draw(this.iImage, pPosition.getColumn(), pPosition.getLine());
    }

    public String toString(){
        return "Monster";
    }
  
}
