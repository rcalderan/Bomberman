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
import Model.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 * @author Henrique Ruher - 9292538
 *
 *
 * This class get and process all elements in "current" Stage.
 */
public class GameController {

    //stage that is processing
    private Stage currentStage;

    //constructor
    public GameController(Stage currentStage){
        this.currentStage = currentStage;
    }

    public Stage getCurrentStage(){return currentStage;}
    public void setCurrentStage(Stage stage){
        this.currentStage = stage;
    }

    //Pause System
    private boolean paused=false;

    public boolean getGamePause(){
        return  paused;
    }
    public void setGamePaused(boolean pause){
        paused = pause;
    }
    /**
     * Draw changes on screen
     * @return void
     */
    public void drawEverything(/*ArrayList<Element> e*/){
        if(!paused){//pauses game
            //process bomberman
            Draw.getGameScreen().getBomberman().autoDraw();
            //process elements
            for(int i = 0; i < currentStage.getNumberOfElements();i++){
                currentStage.getElement(i).autoDraw();
            }

        }
    }

    /**
     * Update "changes" on elements of screen
     * @return void
     */
    public void processEverything(){
        Bomberman bBomberman = Draw.getGameScreen().getBomberman();
        Element eTemp;

        List<Element> aux;
        do {
            aux = new ArrayList<>();
            for(int i=0;i< currentStage.getNumberOfElements();i++)
                aux.add(currentStage.getElement(i));

            if (bBomberman.getLives() <= 0) {
                //Ends the game if bomberman lost all lives
                Draw.getGameScreen().setGameMessange("You lose. GAME OVER!");
                setGamePaused(true);
            } else {
                // process all elements one by one
                for (int i = 0; i < currentStage.getNumberOfElements(); i++) {
                    eTemp = currentStage.getElement(i);
                    if(eTemp.toString().equals("BombFire")){
                        if (eTemp.isbKill()) {//remove explosion fire then its extinguished
                            currentStage.removeElement(eTemp);
                        }
                    }
                    if (eTemp.isbKill()) {//remove all marcked elements.
                        currentStage.removeElement(eTemp);
                        
                    }
                    //process a bomb that is in eminent explision
                    if(eTemp instanceof Bomb){
                        Bomb b =(Bomb)eTemp;
                        if(b.getReadyToExplode()) {
                            explode(b);
                            drawEverything();//must draw it
                            return;
                        }

                    }
                    //remove all character that its dead!
                    if (eTemp instanceof Character) {
                        Character ch = (Character)eTemp;
                        if(ch.getLifeState().equals(Character.STATE.DEAD)) {
                            currentStage.removeElement(ch);
                        }
                    }

                    //Compare with bomberman's position. It means that element is interacting with bomberman!
                    if (bBomberman.getPosition().equals(eTemp.getPosition())) {
                        if (eTemp.isbMortal()) {
                            bBomberman.die();

                        } else {
                            if (eTemp.isbTransposable()) {
                                //Process an ITEM!
                                if (eTemp.toString().equals("PowerUp")) {
                                    bBomberman.powerUp();
                                    currentStage.removeElement(eTemp);
                                } else if (eTemp.toString().equals("LifeUp")) {
                                    bBomberman.setLives(bBomberman.getLives() + 1);
                                    currentStage.removeElement(eTemp);
                                } else if (eTemp.toString().equals("BombUp")) {
                                    bBomberman.setBombs(bBomberman.getBombs() + 1);
                                    currentStage.removeElement(eTemp);
                                } else if (eTemp.toString().equals("RemoteUp")) {
                                    bBomberman.setBombType(Bomb.BOMBTYPE.REMOTE);
                                    currentStage.removeElement(eTemp);

                                } else if (eTemp.toString().equals("Door")) {
                                    //PRocess the stage Door!
                                    if (currentStage.monsterCount() == 0) {
                                        if(currentStage.getNext() !=null){
                                            bBomberman.setPosition(0,0);
                                            setCurrentStage(currentStage.getNext());
                                        }
                                        else{
                                            Draw.getGameScreen().setGameMessange("CONGRATULATION!");
                                            setGamePaused(true);
                                            System.out.println("CONGRATULATIONS!!! YOU FINISHED THE GAME!!!!!");
                                        }

                                    }
                                }
                            }
                        }
                    }
                    //must check all positions...
                    boolean randomElem = false;
                    for (int j = 0; j < currentStage.getNumberOfElements(); j++) {
                        Element eTemp2 = currentStage.getElement(j);
                        if (eTemp.getPosition().equals(eTemp2.getPosition())) {
                            if ((eTemp2.toString().equals("BombFire"))) {
                                if (eTemp.toString().equals("Bomb")) {
                                    Bomb b = (Bomb)eTemp ;
                                    b.setbKill(true);
                                    b.isReadyToExplode(true);
                                } else {
                                    if (eTemp.toString().equals("Brick")) {
                                        randomElem = true;
                                    }
                                    if(!eTemp.toString().equals("Bomberman"))
                                    {
                                        if(eTemp instanceof Monster){
                                            Monster m = (Monster)eTemp;
                                            m.die();
                                            currentStage.setScore(m.getPoints()+currentStage.getScore());
                                        }else if(eTemp instanceof Door){
                                            Monster m = Stage.createRandomMonster();
                                            m.setPosition(eTemp.getPosition());
                                            currentStage.addElement(m);
                                        }else
                                            currentStage.removeElement(eTemp);
                                    }
                                }
                            }

                        }
                    }
                    // genetate a random element with a certain probability
                    if (randomElem) {
                        Item rand = createRandomElement(eTemp.getPosition());
                        if (rand != null)
                            currentStage.addElement(rand);
                    }
                }
            }
        }while (aux.size()!=currentStage.getNumberOfElements());
    }

    /**
     * Explode a bomb
     * @param bomb to be exploded!
     */
    public void explode(Bomb bomb){
        /*explosionLimit is the flag that informs if the fire in each direction
        stops or keeps moving. Each index represents a direction
        0=Left / 1=Right / 2=Up / 3=Down. if explosionLimit is 'false', than
        there is no more fire in that direction. 'true' means the oposite
        * */
        boolean[] explosionLimit = new boolean[]{true, true, true, true};
        BombFire f = new BombFire("explosion.png");

        f.setPosition(bomb.getPosition());
        Draw.getGameScreen().addElement(f);
        for (int i = 1; i <= bomb.getPower(); i++) {
            String expName;

            if (i == bomb.getPower())
                expName = "explosion-L.png";
            else
                expName = "explosion-horizontal.png";
            BombFire fb = new BombFire(expName);
            if (explosionLimit[0] && fb.setPosition(bomb.getPosition().getLine(), bomb.getPosition().getColumn() - i)) {
                Position p = new Position(bomb.getPosition().getLine(), bomb.getPosition().getColumn() - i);
                if (isExplosable( p)) {
                    explosionLimit[0] = Draw.getGameScreen().isValidPosition(fb.getPosition());
                    Draw.getGameScreen().addElement(fb);
                } else {
                    explosionLimit[0] = false;
                }
            }

            if (i == bomb.getPower())
                expName = "explosion-R.png";
            else
                expName = "explosion-horizontal.png";
            BombFire fb2 = new BombFire(expName);
            if (explosionLimit[1] && fb2.setPosition(bomb.getPosition().getLine(), bomb.getPosition().getColumn() + i)) {
                Position p = new Position(bomb.getPosition().getLine(), bomb.getPosition().getColumn() + i);
                if (isExplosable( p)) {
                    explosionLimit[1] = Draw.getGameScreen().isValidPosition(fb2.getPosition());
                    Draw.getGameScreen().addElement(fb2);
                } else {
                    explosionLimit[1] = false;
                }
            }

            if (i == bomb.getPower())
                expName = "explosion-U.png";
            else
                expName = "explosion-vertical.png";
            BombFire fb3 = new BombFire(expName);
            if (explosionLimit[2] && fb3.setPosition(bomb.getPosition().getLine() - i, bomb.getPosition().getColumn())) {
                Position p = new Position(bomb.getPosition().getLine() - i, bomb.getPosition().getColumn());
                if (isExplosable( p)) {
                    explosionLimit[2] = Draw.getGameScreen().isValidPosition(fb3.getPosition());
                    Draw.getGameScreen().addElement(fb3);
                } else {
                    explosionLimit[2] = false;
                }
            }

            if (i == bomb.getPower())
                expName = "explosion-D.png";
            else
                expName = "explosion-vertical.png";
            BombFire fb4 = new BombFire(expName);
            if (explosionLimit[3] && fb4.setPosition(bomb.getPosition().getLine() + i, bomb.getPosition().getColumn())) {
                Position p = new Position(bomb.getPosition().getLine() + i, bomb.getPosition().getColumn());
                if (isExplosable( p)) {
                    explosionLimit[3] = Draw.getGameScreen().isValidPosition(fb4.getPosition());
                    //explosionLimit[3] = !isBrick(elements,p);
                    Draw.getGameScreen().addElement(fb4);
                } else {
                    explosionLimit[3] = false;
                }
            }
        }
    }


    /**
     * Check if the element in position p
     * is explosable or not (Walls are not explosable)
     * @param p position of element to check
     * @return true if it is, false if isn't
     */
    private boolean isExplosable(Position p) {
        for (int i=0; i< currentStage.getNumberOfElements();i++) {
            Element e = currentStage.getElement(i);
            if (e.getPosition().equals(p)) {
                if (e.toString().equals("Wall")) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Spaw a random ITEM in position
     * @param p position to spaw
     * @return an item Element
     */
    public Item createRandomElement(Position p){
        Random r = new Random();
        //try add the stage door first
        if(currentStage.getDoor()==null){
            Door d = new Door();
            d.setPosition(p);
            if(currentStage.brickCount()==0){
                return d;
            }
            else{
                int rand = r.nextInt(100);
                if (rand >= 0 && rand<=20) {
                    return d;
                }
            }
        }
        //now the iten
        Item upItem=null;
        int rand = r.nextInt(100);
        if (rand >= 0 && rand<= Consts.LIFEUP_PROBABILITY) {
            upItem = new LifeUp("lifeUp.png");
            upItem.setPosition(p);
            return upItem;
        }
        rand = r.nextInt(100);
        if (rand > 0 && rand<=Consts.REMOTEBOMB_PROBABILITY) {
            upItem =new RemoteUp("RemoteUp.png");
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
        if (rand > 0 && rand<=Consts.NORMALBOMB_PROBABILITY) {
            upItem =new BombUp("bombUp.png");
            upItem.setPosition(p);
            return upItem;
        }
        return null;
    }

    /**
     * Check if a position is valid or not
     * @param p position to check
     * @return true if ok, false if not
     */
    public boolean isValidPosition( Position p){
        Element eTemp;
        for(int i = 0; i < currentStage.getNumberOfElements(); i++){
            eTemp = currentStage.getElement(i);
            if(!eTemp.isbTransposable())
                if(eTemp.getPosition().equals(p))
                    return false;
        }
        return true;
    }

    /**
     * for tests. Kills all monsters in current stage! It will open the door!
     */
    public void killAllMonsters(){
        for (int i=0;i<getCurrentStage().getNumberOfElements();i++){
            Element e = currentStage.getElement(i);
            if(e instanceof Monster)
            {
                Monster m = (Monster)e;
                m.die();

                currentStage.setScore(m.getPoints()+currentStage.getScore());
            }
        }
    }

}
