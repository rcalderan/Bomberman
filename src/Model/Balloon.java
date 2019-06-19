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
 * Moster that moves randomly
 */
public class Balloon extends Monster{
    
    public Balloon() {
        super("bichinho-d.png");
    }

    /**
     * This monster cant shoot
     */
    public void shoot(){}


    public void switchAppearance() {
        changeAppearance(getCharacterName()+"-d.png");
    }

    public String toString(){
        return "Monster";
    }
  
}
