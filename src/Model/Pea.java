package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;

/**
 * Represents a pea that can be shooted
 */
public class Pea extends Character {
    private int lifeTime;
    private int timer;
    public Pea( Consts.DIRECTION direction) {
        super("pea.png");
        this.setDirection(direction);
        timer=0;
        bMortal=true;
        lifeTime=0;
    }

    public void switchAppearance() {

        changeAppearance(getCharacterName() +"-d.png");
    }

    public int getLifeTime() {
        return lifeTime;
    }

    @Override
    public void autoDraw() {
        timer++;
        if(timer>Consts.PERIOD/20) {//control its velocity
            switch (getDirection()) {
                case DOWN:
                    moveDown();
                    break;
                case UP:
                    moveUp();
                    break;
                case LEFT:
                    moveLeft();
                    break;
                case RIGHT:
                    moveRight();
                    break;
            }
            timer=0;
            lifeTime++;
        }
        if(lifeTime>=5)
            die();
        Draw.draw(this.iImage, pPosition.getColumn(), pPosition.getLine());
    }
}
