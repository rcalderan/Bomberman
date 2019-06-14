package Model;
/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 *
 * An item is a special element
 * It's somethis Bomberman can 'get'
 * (LiveUp, BombUp, PowerUp)
 */
public abstract class Item extends Element {
    public Item(String sNomeImagePNG) {
        super(sNomeImagePNG);
    }
}
