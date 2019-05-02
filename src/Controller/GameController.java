/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.*;
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

            if(bBomberman.getPosition().equals(eTemp.getPosition())) {/*Se o bomberman
                toca em qualquer coisa que é mortal ele perde uma vida.*/
                if(eTemp.isbMortal()) {
                    //bBomberman.setLives(bBomberman.getLives() - 1);
                    //bBomberman.setPosition(new Position(0,0));//returns to start position
                    if(bBomberman.getLives() <= 0 ){
                        //bomberman morre. O jogo deve terminar
                        //Como terminar o o jogo?
                    }


                }else{
                    if(eTemp.isbTransposable()){
                        if(eTemp instanceof LifeUp){
                            bBomberman.setLives(bBomberman.getLives() + 1);
                            e.remove(eTemp);
                        }
                        if(eTemp instanceof PowerUp){
                            bBomberman.powerUp();
                            e.remove(eTemp);
                        }
                        if(eTemp instanceof Door){
                            /*Verifica se não tem nenhum monstro no mapa,
                            se não tem, vai pra proxima fase.
                            */
                            int MonstrosVivos = 0;
                            for(int k = 1; k < e.size(); k++){
                                if(e.get(k)instanceof Monster){
                                    MonstrosVivos++;
                                }
                            }
                            if(MonstrosVivos == 0){/*Se não tem nenhum monstro vivo
                                pode ir pra próxima fase, se não não faz nada*/

                            }
                        }
                    }
                }
            }
            for(int j=1; j < e.size(); j++ ){
                //Se o objeto é um monstro ou uma parede de tijolos e está na mesma posíção da bomba ele some.
                Element eTemp2 = e.get(j);
                if(eTemp.getPosition().equals(eTemp2.getPosition())){
                    if( (eTemp instanceof Monster || eTemp instanceof Brick ) && eTemp2 instanceof Bomb){
                        e.remove(eTemp);
                    }
                }
            }
        }       
    }
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
