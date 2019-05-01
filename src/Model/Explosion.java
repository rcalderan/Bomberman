package Model;

import Auxiliar.Consts;
import Auxiliar.Draw;
import Auxiliar.Position;
import Controller.Screen;

import java.io.Serializable;
import java.util.ArrayList;

public class Explosion extends Element implements Serializable {

    private int power;
    private boolean exploded=false;
    private int time=0;
    private String imageName;
    private ArrayList<StaticFire> explosionFire;
    protected Explosion(String sImageNamePNG, Position pos, int power) {
        super(sImageNamePNG);
        imageName = sImageNamePNG;
        this.power=power;
        this.bMortal = true;
        pPosition=pos;
        this.explosionFire=new ArrayList<>();
    }

    private void explode(){
        exploded = true;
        Screen screen = Draw.getGameScreen();
        ArrayList<Element> aux;

        //try explode arround

        Position right = new Position(pPosition.getLine(),pPosition.getColumn()+1);
        Position left = new Position(pPosition.getLine(),pPosition.getColumn()-1);
        Position up = new Position(pPosition.getLine()-1,pPosition.getColumn());
        Position down = new Position(pPosition.getLine()+1,pPosition.getColumn());
        for(int i=1;i<=power;i++){
            aux =screen.getElements(right);
            if(aux.size() > 0 ){
                for(Element el : aux){
                    if(el instanceof Bomberman){
                        ((Bomberman)el).die();
                    }

                }
            }
            else {
                StaticFire f = new StaticFire("fire.png");
                f.bMortal=true;
                if(f.setPosition(right)) {
                    screen.addElement(f);
                    explosionFire.add(f);
                    right = new Position(right.getLine(),right.getColumn()+1);
                }
            }
            aux =screen.getElements(left);
            if(aux.size() > 0 ){
                for(Element el : aux){
                    if(el instanceof Bomberman){
                        ((Bomberman)el).die();
                    }
                }
            }
            else {
                StaticFire f = new StaticFire("fire.png");
                f.bMortal=true;
                if(f.setPosition(left)) {
                    screen.addElement(f);
                    explosionFire.add(f);
                    left = new Position(left.getLine(),left.getColumn()-1);
                }
            }

            aux =screen.getElements(up);
            if(aux.size() > 0 ){
                for(Element el : aux){
                    if(el instanceof Bomberman){
                        ((Bomberman)el).die();
                    }

                }
            }
            else {
                StaticFire f = new StaticFire("fire.png");
                f.bMortal=true;
                if(f.setPosition(up)) {
                    screen.addElement(f);
                    explosionFire.add(f);
                    up = new Position(up.getLine()-1,up.getColumn());
                }
            }

            aux =screen.getElements(down);
            if(aux.size() > 0 ){
                for(Element el : aux){
                    if(el instanceof Bomberman){
                        ((Bomberman)el).die();
                    }

                }
            }
            else {
                StaticFire f = new StaticFire("fire.png");
                f.bMortal=true;
                if(f.setPosition(down)) {
                    screen.addElement(f);
                    explosionFire.add(f);
                    down = new Position(down.getLine()+1,down.getColumn());
                }
            }
        }

    }

    @Override
    public void autoDraw() {
        super.autoDraw();
        if(!exploded)
            explode();
        //iCountTempo += Consts.PERIOD;
        time++;
        if(time>7){
            bKill=true;
            for(StaticFire f : explosionFire)
                f.bKill=true;

        }
    }
}
