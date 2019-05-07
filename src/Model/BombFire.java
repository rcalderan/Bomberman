package Model;

import Auxiliar.Consts;

public class BombFire extends Element{
    private int iCountTempo;
    protected BombFire(String sNomeImagePNG) {
        super(sNomeImagePNG);
        bMortal=true;
        iCountTempo=0;
    }

    @Override
    public void autoDraw() {
        super.autoDraw();
        iCountTempo++;
        if(iCountTempo >= Consts.TIMER){
            this.bKill=true;
            //Draw.getGameScreen().removeElement(this);
        }

    }
}
