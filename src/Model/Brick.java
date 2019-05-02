package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Auxiliar.Position;
import Controller.Screen;

import java.util.ArrayList;
import java.util.Random;

public class Brick extends Element {
    public Brick(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransposable = false;
    }
    public void autoDraw(){
        super.autoDraw();
        ArrayList<Element> posElements= Draw.getGameScreen().getElements(pPosition);

        if(posElements.size()>1){
            for(Element el : posElements){
                if(el instanceof StaticFire){//break this brick and random spaw a item with a small chance
                    Draw.getGameScreen().removeElement(this);
                    Draw.getGameScreen().removeElement(el);
                    Element elem = createRandomElement(pPosition);
                    if(elem!=null)
                        Draw.getGameScreen().addElement(elem);
                }
            }
        }
    }

    private Element createRandomElement(Position p){
        Random r = new Random();
        int rand = r.nextInt(100);
        if (rand >= 0 && rand<= Consts.LIFEUPPROBABILITY) {
            LifeUp lifeup = new LifeUp("lifeUp.png");
            lifeup.setPosition(p);
            return lifeup;
            //life
        }
        rand = r.nextInt(100);
        if (rand > 0 && rand<=Consts.POWERUPPROBABILITY) {
            PowerUp pUp =new PowerUp("powerUp.png");
            pUp.setPosition(p);
            return pUp;
        }
        return null;

    }
}
