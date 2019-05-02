package Model;

import Auxiliar.Consts;

public class StaticFire extends Element{
    private int iCountTempo;
    protected StaticFire(String sNomeImagePNG) {
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
