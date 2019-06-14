package Model;

import Auxiliar.Consts;

/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 */
public class Character extends Element {

    private Consts.DIRECTION direction;

    public Character(String sNomeImagePNG) {
        super(sNomeImagePNG);
        direction=Consts.DIRECTION.DOWN;
    }

    public Consts.DIRECTION getDirection(){
        return direction;
    }
    public void setDirection(Consts.DIRECTION direction){
        this.direction = direction;
    }


}

