/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 */
public class Wall extends Element {
    
    public Wall(String sImageNamePNG) {
        super(sImageNamePNG);
        this.bTransposable = false;
    }
    
}
