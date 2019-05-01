package Model;

public class StaticFire extends Element{
    protected StaticFire(String sNomeImagePNG) {
        super(sNomeImagePNG);
        bMortal=true;
    }

    @Override
    public void autoDraw() {
        super.autoDraw();
    }
}
