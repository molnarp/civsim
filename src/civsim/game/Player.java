/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim.game;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public enum Player {
    A, B;
    
    public Player other() {
        switch (this) {
            case A:
                return B;
            case B:
                return A;
            default:
                return null;
        }
    }
}
