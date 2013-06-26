/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim.game;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public enum UnitType {
    Infantry,
    Cavalry,
    Artillery,
    Aircraft;
    
    public boolean isTrumping(UnitType other) {
        switch (this) {
            case Infantry:
                if (other.equals(Cavalry)) {
                    return true;
                }
                break;
            case Cavalry:
                if (other.equals(Artillery)) {
                    return true;
                }
                break;
            case Artillery:
                if (other.equals(Infantry)) {
                    return true;
                }
                break;
        }
        
        return false;
    }
    
    public static int compare(UnitType u1, UnitType u2) {
        return u2.ordinal() - u1.ordinal();
    }
}
