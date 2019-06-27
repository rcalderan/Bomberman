/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * @author Richard Calderan - 3672382
 * @author Leticia Burla - 10294950
 * @author Henrique Ruher - 9292538
 *
 * Represents a indestrutible Wall. Nothing can destroy it.
 */
public class Wall extends Element {
    
    public Wall() {
        super("wall.png");
        this.bTransposable = false;
    }
    
}
