package Model;

import Auxiliar.Consts;


/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 */
public class Character extends Element {

    private Consts.DIRECTION direction;
    private String characterName;
    protected int deadTime;
    protected STATE lifeState;

    protected int animateDeadState;
    protected int animateMoveState;

    private boolean invencible;

    public Character(String sNomeImagePNG) {
        super(sNomeImagePNG);
        lifeState = STATE.ALIVE;
        deadTime=0;
        animateDeadState=1;
        animateMoveState=1;
        invencible=false;
        try {
            characterName = sNomeImagePNG.substring(0,sNomeImagePNG.indexOf("-d2.png"));
        }catch (Exception e){
            characterName = sNomeImagePNG.substring(0,sNomeImagePNG.indexOf("-d.png"));
        }
        direction=Consts.DIRECTION.DOWN;
    }

    public boolean isInvencible(){
        return invencible;
    }
    public void setInvencible(boolean isInvencible){
        invencible=isInvencible;
    }

    public String getCharacterName() {
        return characterName;
    }

    public Consts.DIRECTION getDirection(){
        return direction;
    }
    public void setDirection(Consts.DIRECTION direction){
        this.direction = direction;
    }

    public void setLifeState(STATE lifeState) {
        this.lifeState = lifeState;
    }

    public STATE getLifeState() {
        return lifeState;
    }

    public int getAnimateMoveState() {
        return animateMoveState;
    }

    public void setAnimateMoveState(int animateMoveState) {
        this.animateMoveState = animateMoveState;
    }

    public void die() {
        lifeState = STATE.DYING;
        bMortal=false;
    }

    public void switchAppearance() {
        String sulfix=".png",
                directionString="";

        switch (getDirection()){
            case UP:
                directionString="-u";
                break;
            case LEFT:
                directionString="-l";
                break;
            case RIGHT:
                directionString="-r";
                break;
            case DOWN:
                directionString="-d";
                break;
        }
        changeAppearance(characterName +directionString+sulfix);
    }

    @Override
    public void autoDraw() {
        if(lifeState.equals(STATE.DYING)){
            deadTime++;
            if (deadTime > Consts.PERIOD/40) {
                changeAppearance(characterName+"-death"+animateDeadState+".png");
                animateDeadState++;
                deadTime=0;
            }
        }

        if(animateDeadState>7){
            lifeState=STATE.DEAD;//will be removed on next frame
            return;
        }
        super.autoDraw();
    }

    @Override
    public boolean moveLeft() {
        if(lifeState.equals(STATE.ALIVE)){
            direction = Consts.DIRECTION.LEFT;
            switchAppearance();
            return super.moveLeft();
        }else
            return false;
    }

    @Override
    public boolean moveRight() {
        if(lifeState.equals(STATE.ALIVE)){
            direction = Consts.DIRECTION.RIGHT;
            switchAppearance();
            return super.moveRight();
        }else
            return false;
    }

    @Override
    public boolean moveUp() {
        if(lifeState.equals(STATE.ALIVE)){
            direction = Consts.DIRECTION.UP;
            switchAppearance();
            return super.moveUp();
        }else
            return false;
    }

    @Override
    public boolean moveDown() {
        if(lifeState.equals(STATE.ALIVE)){
            direction = Consts.DIRECTION.DOWN;
            switchAppearance();
            return super.moveDown();
        }
        else
            return false;
    }

    public enum STATE{
        DEAD,ALIVE, DYING
    }
}

