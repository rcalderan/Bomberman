/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auxiliar;

import java.io.File;

/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 * @author Henrique Ruher - 9292538
 *
 */ 
public class Consts {
    //game
    public static final int CELL_SIDE = 50;
    public static final int RES = 11;
    public static final int PERIOD = 80;
    public static final String PATH = File.separator+"imgs"+File.separator;
    public static final int TIMER = 1;

    //itens
    public static final int TIMER_BOMB = 1760; //keep multiple of const PERIOD
    public static final int NORMALBOMB_PROBABILITY = 30;// spaw probability in percent
    public static final int REMOTEBOMB_PROBABILITY = 5;// spaw probability in percent
    public static final int LIFEUP_PROBABILITY = 10;// spaw probability in percent
    public static final int POWERUP_PROBABILITY = 30;// spaw probability in percent


    public enum DIRECTION{
        UP,RIGHT,LEFT,DOWN;
    }
}

