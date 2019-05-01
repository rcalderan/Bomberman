/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Bomb;
import Model.Element;
import Model.Bomberman;
import Auxiliar.Position;
import java.util.ArrayList;

/**
 *
 * @author junio
 */
public class GameController {
    public void drawEverything(ArrayList<Element> e){
        for(int i = 0; i < e.size(); i++){
            e.get(i).autoDraw();
        }
    }
    public void processEverything(ArrayList<Element> e){
        Bomberman bBomberman = (Bomberman)e.get(0);
        Element eTemp;
        for(int i = 1; i < e.size(); i++){
            eTemp = e.get(i);
            if(eTemp.isbKill()){
                e.remove(eTemp);
                continue;
            }
            if(bBomberman.getPosition().equal(eTemp.getPosition())&& eTemp instanceof Bomb == false )
                if(eTemp.isbTransposable())
                    e.remove(eTemp);
        }       
    }
    public boolean isValidPosition(ArrayList<Element> e, Position p){
        Element eTemp;
        for(int i = 1; i < e.size(); i++){
            eTemp = e.get(i);
            if(!eTemp.isbTransposable())
                if(eTemp.getPosition().equal(p))
                    return false;
        }
        return true;
    }


}
