package Model;
/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 */

import Auxiliar.Consts;

public class BombFire extends Element{
    private int iCountTempo;
    private boolean burning;

    public BombFire(String sNomeImagePNG) {
        super(sNomeImagePNG);
        bMortal=true;
        setBurning(true);
        iCountTempo=0;
    }

    @Override
    public void autoDraw() {
        super.autoDraw();
        iCountTempo++;
        if(iCountTempo >= Consts.TIMER){
            burning=false;
        }

    }

    public boolean isBurning() {
        return burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }
}
