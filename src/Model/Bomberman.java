
/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 *
 */
package Model;

import Auxiliar.Draw;
import Auxiliar.Position;

import java.io.Serializable;

/**
 *
 */
public class Bomberman extends Character implements Serializable{

    private int lives;
    private int power;  //Bomb's fire range
    private int bombs;
    private Bomb.BOMBTYPE bombType;


    public Bomberman(String sImageNamePNG) {
        super(sImageNamePNG);
        lives=3;
        power=1;
        bombs=1;
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

    public void die(){
        setLives(getLives() - 1);
        setPosition(new Position(0, 0));//returns to start position
        setLifeState(STATE.ALIVE);
        power = 1;
        bombs = 1;
        bombType = Bomb.BOMBTYPE.NORMAL;

        Draw.getGameScreen().updateHUD();
        super.die();
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
}
