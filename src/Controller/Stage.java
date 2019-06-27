

package Controller;

import Auxiliar.Consts;
import Auxiliar.Position;
import Model.*;

import java.util.ArrayList;
import java.util.Random;

import static Auxiliar.Draw.isValidPosition;


/**
 * represents a game stage
 *
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 * @author Henrique Ruher - 9292538
 */
public class Stage {

    private String stageName;
    private static int score;

    private ArrayList<Element> elements;
    private int numberOfElements;

    private Stage next;

    public Stage(String name){
        this.stageName = name;
        this.elements = new ArrayList<>();

        this.setScore(0);
        numberOfElements = 0;


        // set indestrutible wall
        for(int i = 0; i< Consts.RES; i++){
            if(i%2!=0)
                for (int j=0;j<Consts.RES;j++){
                    if(j%2!=0) {
                        Wall wall = new Wall();
                        wall.setPosition(i, j);
                        addElement(wall);
                    }
                }
        }

        //set Bricks
        for(int i = 0; i < (Consts.RES);i++){
            for(int j = 0; j < (Consts.RES); j++){
                if(Math.random() < 0.35 && (i != 0 && j!= 0)){
                    if((i%2 == 0 || j%2 == 0) && (i != 0 && j != 10)){
                        Brick p = new Brick();
                        p.setPosition(i,j);
                        addElement(p);
                    }
                }
            }
        }
    }


    public void setNextStage(Stage nextStage){
        this.next = nextStage;
    }
    public Stage getNext(){return this.next;}

    public int brickCount(){
        int count=0;
        for(int i=0;i<elements.size();i++){
            if(elements.get(i) instanceof Brick)
                count++;
        }
        return count;
    }

    /**
     * get the stage door
     * @return the stage door or null if not found
     */
    public Door getDoor(){
        for(Element e : elements){
            if(e instanceof Door)
                return (Door) e;
        }
        return null;
    }

    /**
     * Add a element to stage
     * @param element
     */
    public void addElement(Element element){
        if(element instanceof Monster){
            Random rand = new Random();
            int x,y;
            //check if there is something in its position
            ArrayList<Position> notHere = new ArrayList<>();

            //dont place monsters close to bomberman!
            notHere.add(new Position(0,0));
            notHere.add(new Position(0,1));
            notHere.add(new Position(0,2));
            notHere.add(new Position(1,0));
            notHere.add(new Position(2  ,0));
            for (Element e :elements)
                notHere.add(e.getPosition());
            while (! isValidPosition(element.getPosition())|| notHere.contains(element.getPosition())) {
                x = rand.nextInt(Consts.RES);
                y = rand.nextInt(Consts.RES);
                element.setPosition(x,y);
            }
        }
        elements.add(element);
        numberOfElements =(elements.size());
    }
    public int getNumberOfElements(){return numberOfElements;}
    /**
     * Remove a element form this stage
     * @param element
     * @return
     */
    public boolean removeElement(Element element){
        try{
            for(int i=0;i<elements.size();i++){
                if(elements.get(i).equals(element)){
                    boolean sucess =elements.remove(element);
                    numberOfElements=(elements.size());
                    return sucess;
                }
            }
            return false;

        }catch (Exception e){
            return false;
        }
    }
    /**
     * get a specific element
     * @param index
     * @return null if not found
     */
    public Element getElement(int index){
        try{
            return this.elements.get(index);

        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public int monsterCount(){
        int count = 0;
        for(Element el: elements){
            if(el instanceof Monster)
                count++;
        }
        return count;
    }


    public static Monster createRandomMonster(){

        Random rand =new Random();
        switch (rand.nextInt(3)){
            case 0:
                return new Dino();
            case 1:
                return new Coin();
            default:
                return new Balloon();
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
