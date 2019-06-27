
/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 * @author Henrique Ruher - 9292538
 */
package Model;

import Auxiliar.Draw;

import java.io.Serializable;

/**
 *
 */
public class Bomberman extends Character implements Serializable{

    private int lives;
    private int power;  //Bomb's fire range
    private int bombs;
    private Bomb.BOMBTYPE bombType;
    private int points;


    public Bomberman(String sImageNamePNG) {
        super(sImageNamePNG);
        lives=3;
        power=1;
        bombs=1;
        points=0;
        bombType = Bomb.BOMBTYPE.NORMAL;
    }

    //gets sets
    public int getNbomb(){
        return bombs;
    }
    public int getBombs(){
        return this.bombs;
    }
    public void setBombs(int nBomb){
        bombs=nBomb;
        Draw.getGameScreen().updateHUD();
    }
    public int getPower(){
        return this.power;
    }
    public int getLives(){
        return this.lives;
    }
    public void setLives(int lives) {
        //Lose life only when not invencible
        this.lives = lives;
        Draw.getGameScreen().updateHUD();
    }

    public void setBombType(Bomb.BOMBTYPE bombType) {
        this.bombType = bombType;
    }

    public Bomb.BOMBTYPE getBombType() {
        return bombType;
    }


    public int getPoints(){ return points;}
    public void setPoints(int points){
        this.points = points;
    }

    public void die(){
        if(!isInvencible()) {
            setInvencible(true);
            setLives(getLives() - 1);
            setLifeState(STATE.DYING);
            power = 1;
            bombs = 1;
            bombType = Bomb.BOMBTYPE.NORMAL;

            Draw.getGameScreen().updateHUD();
            super.die();
        }
    }
    /**
     * increases bomberman's bomb power
     * @return void
     */
    public void powerUp(){
        this.power = this.power + 1;
        Draw.getGameScreen().updateHUD();
    }

    public void autoDraw(){
        super.autoDraw();

        if(animateDeadState>7){
            animateDeadState=1;
            lifeState=STATE.ALIVE;
            changeAppearance(getCharacterName()+"-d2.png");//only bomberman should go back to life
            setInvencible(false);
            setPosition(0,0);

        }
    }

    int oscilate=1;
    @Override
    public void switchAppearance() {
        String sulfix=".png",
            directionString="";
        int moveState =getAnimateMoveState();
        switch (getDirection()){
            case UP:
                directionString="-u"+moveState;
                break;
            case LEFT:
                directionString="-l"+moveState;
                break;
            case RIGHT:
                directionString="-r"+moveState;
                break;
            case DOWN:
                directionString="-d"+moveState;
                break;
        }
        if(moveState==3)
            oscilate=-1;
        else if(getAnimateMoveState()==1)
            oscilate=1;
        setAnimateMoveState(moveState+oscilate);

        changeAppearance(getCharacterName() +directionString+sulfix);
    }

    /**
     * gests bomberman to his previus position
     * @return void
     */
    public void backToLastPosition(){
        this.pPosition.back();
    }

    public void updatePoints(Element eTemp) {
        int aux=0;
        if(eTemp.toString().equals("Monster")){
            //Baloon
            aux=100;
        }else if(eTemp.toString().equals("Coin")){
            aux = 200;
        }else if(eTemp.toString().equals("Dino")){
            aux = 300;
        }
        setPoints(getPoints()+aux);
    }
}
