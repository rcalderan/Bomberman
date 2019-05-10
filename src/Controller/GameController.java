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
            if(e.get(i) instanceof Bomb){
                Bomb b = (Bomb) e.get(i);
                if(b.getCountDown()>=Consts.TIMER_BOMB)
                    explode(b);
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

            if(bBomberman.getPosition().equals(eTemp.getPosition())) {/*Se o bomberman
                toca em qualquer coisa que é mortal ele perde uma vida.*/
                if(eTemp.isbMortal()) {
                    bBomberman.die();
                    if(bBomberman.getLives() <= 0 ){
                        //bomberman morre. O jogo deve terminar
                        //Como terminar o o jogo?
                    }

                }else{
                    if(eTemp.isbTransposable()){
                        if(eTemp instanceof PowerUp){
                            bBomberman.powerUp();
                            e.remove(eTemp);
                        }else
                        if(eTemp instanceof LifeUp){
                            bBomberman.setLives(bBomberman.getLives()+1);
                            e.remove(eTemp);
                        }else
                        if(eTemp instanceof BombUp){
                            bBomberman.setBombs(bBomberman.getBombs()+1);
                            e.remove(eTemp);
                        }else
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
            boolean randomElem = false;
            for(int j=1; j < e.size(); j++ ){
                //Se o objeto é um monstro ou uma parede de tijolos e está na mesma posíção da bomba ele some.
                Element eTemp2 = e.get(j);
                if(eTemp.getPosition().equals(eTemp2.getPosition())){
                    if((eTemp2 instanceof BombFire)){
                        if(eTemp instanceof Monster ){
                            e.remove(eTemp);
                        }else if(eTemp instanceof Brick){
                                e.remove(eTemp);
                                e.remove(eTemp2);
                                randomElem=true;
                        }else if(eTemp instanceof Bomb){
                            ((Bomb)eTemp).setCountDown(Consts.TIMER_BOMB);
                        }



                    }
                    if( (eTemp instanceof Monster ) && (eTemp2 instanceof BombFire)){
                        e.remove(eTemp);
                    }else
                    if( (eTemp instanceof Brick ) && (eTemp2 instanceof BombFire)) {

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

    /**
     * Create a bomb explosion
     * @param bomb bomb to explode
     */
    public void explode(Bomb bomb){
        if(bomb.getCountDown()>= Consts.TIMER_BOMB){
            boolean[] foundLimit = new boolean[]{true,true,true,true};
            BombFire f = new BombFire("explosao.png");
            f.setPosition(bomb.getPosition());
            Draw.getGameScreen().addElement(f);
            for(int i=1;i<=bomb.getPower();i++){

                BombFire fb = new BombFire("explosao.png");
                if(foundLimit[0] && fb.setPosition(bomb.getPosition().getLine(), bomb.getPosition().getColumn()-i)){
                    foundLimit[0] = Draw.getGameScreen().isValidPosition(fb.getPosition());
                    Draw.getGameScreen().addElement(fb);
                }

                BombFire fb2 = new BombFire("explosao.png");
                if(foundLimit[1] && fb2.setPosition(bomb.getPosition().getLine(), bomb.getPosition().getColumn()+i)) {
                    foundLimit[1] =Draw.getGameScreen().isValidPosition(fb2.getPosition());
                    Draw.getGameScreen().addElement(fb2);
                }

                BombFire fb3 = new BombFire( "explosao.png");
                if(foundLimit[2]&& fb3.setPosition(bomb.getPosition().getLine()-i, bomb.getPosition().getColumn())) {
                    foundLimit[2] =Draw.getGameScreen().isValidPosition(fb3.getPosition());
                    Draw.getGameScreen().addElement(fb3);
                }

                BombFire fb4 = new BombFire("explosao.png");
                if(foundLimit[3]&& fb4.setPosition(bomb.getPosition().getLine()+i, bomb.getPosition().getColumn())) {
                    foundLimit[3] =Draw.getGameScreen().isValidPosition(fb4.getPosition());
                    Draw.getGameScreen().addElement(fb4);
                }
            }
        }
    }

}
