/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Model.*;
import Auxiliar.Position;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    public void drawEverything(ArrayList<Element> e){
        for(int i = 0; i < e.size(); i++){
            e.get(i).autoDraw();
            //seek and destroy!
            if(e.get(i).toString().equals("Bomb")){
                Bomb b = (Bomb) e.get(i);
                if(b.getReadyToExplode())
                    b.explodeIt();
            }
            else if(e.get(i).toString().equals("BombFire") && Draw.getGameScreen().getBomberman().getPosition().equals(e.get(i).getPosition())) {
                Draw.getGameScreen().getBomberman().die();
            }
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

            if(bBomberman.getPosition().equals(eTemp.getPosition())) {
                if(eTemp.isbMortal()) {
                    bBomberman.die();
                    if(bBomberman.getLives() <= 0 ){
                        System.exit(0);
                    }

                }else{
                    if(eTemp.isbTransposable()){
                        if(eTemp.toString().equals("PowerUp")){
                            bBomberman.powerUp();
                            e.remove(eTemp);
                        }else
                        if(eTemp.toString().equals("LifeUp")){
                            bBomberman.setLives(bBomberman.getLives()+1);
                            e.remove(eTemp);
                        }else
                        if(eTemp.toString().equals("BombUp")){
                            bBomberman.setBombs(bBomberman.getBombs()+1);
                            e.remove(eTemp);
                        }else
                        if(eTemp.toString().equals("Door")){
                            int monstersAlive = 0;
                            for(int k = 1; k < e.size(); k++){
                                if(e.get(k).toString().equals("Monster")){
                                    monstersAlive++;
                                }
                            }
                            if(monstersAlive == 0){//game ends when there are no monsters to kill
                                System.exit(0);
                            }
                        }
                    }
                }
            }

            boolean randomElem = false;
            for(int j=1; j < e.size(); j++ ){
                Element eTemp2 = e.get(j);
                if(eTemp.getPosition().equals(eTemp2.getPosition())){
                    if((eTemp2.toString().equals("BombFire"))){
                        if(eTemp.toString().equals("Monster" )){
                            e.remove(eTemp);
                        }else if(eTemp.toString().equals("Brick")){
                                e.remove(eTemp);
                                Draw.getGameScreen().removeElement((eTemp2));
                                randomElem=true;
                        }else if(eTemp.toString().equals("Bomb")){
                            ((Bomb)eTemp).isReadyToExplode(true);
                        }else if(eTemp.toString().equals("PowerUp")||eTemp.toString().equals("LifeUp")||eTemp.toString().equals("BombUp")){
                            e.remove(eTemp);
                        }

                    }
                }
            }
            if(randomElem){
                Item rand =createRandomElement(eTemp.getPosition());
                if(rand!=null)
                    e.add(rand);
            }
        }
    }

    /**
     * Spaw a random ITEM in position
     * @param p position to spaw
     * @return an item Element
     */
    public Item createRandomElement(Position p){
        Random r = new Random();
        Item upItem=null;
        int rand = r.nextInt(100);
        if (rand >= 0 && rand<= Consts.LIFEUP_PROBABILITY) {
            upItem = new LifeUp("lifeUp.png");
            upItem.setPosition(p);
            return upItem;
        }
        rand = r.nextInt(100);
        if (rand > 0 && rand<=Consts.POWERUP_PROBABILITY) {
            upItem =new PowerUp("powerUp.png");
            upItem.setPosition(p);
            return upItem;
        }
        rand = r.nextInt(100);
        if (rand > 0 && rand<=Consts.BOMB_PROBABILITY) {
            upItem =new BombUp("bombUp.png");
            upItem.setPosition(p);
            return upItem;
        }
        return null;
    }

    /**
     * Check if a position is valid or not
     * @param e list of all elements
     * @param p position to check
     * @return true if ok, false if not
     */
    public boolean isValidPosition(ArrayList<Element> e, Position p){
        Element eTemp;
        for(int i = 1; i < e.size(); i++){
            eTemp = e.get(i);
            if(!eTemp.isbTransposable())
                if(eTemp.getPosition().equals(p))
                    return false;
        }
        return true;
    }


}
