/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package civsim.game;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public class Unit implements Comparable<Unit> {

    public final UnitType type;
    public final int strength;
    public final int wounds;
    public final boolean withMetalworking;
    
    public Unit(UnitType type, int strength, int wounds, boolean withMetalworking) {
        this.type = type;
        this.strength = strength;
        this.wounds = wounds;
        this.withMetalworking = withMetalworking;
    }

    public Unit(UnitType type, int strength, int wounds) {
        this(type, strength, wounds, false);
    }

    public Unit(UnitType type, int strength) {
        this(type, strength, 0, false);
    }

    public Unit(Unit unit, boolean withMetalworking) {
        this(unit.type, unit.strength, unit.wounds, withMetalworking);
    }

    
    public boolean isAlive() {
        return (wounds < strength);
    }
    
    public Unit hitBy(int strength) {
        return hitBy(strength, false);
    }

    public Unit hitBy(int strength, boolean withMetalworking) {
        return new Unit(type, this.strength, wounds + strength + (withMetalworking ? 3 : 0));
    }
    
    @Override
    public String toString() {
        return type.name() + "(" + strength + "/" + wounds + (withMetalworking ? "/MW" : "") + ")";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 59 * hash + this.strength;
        hash = 59 * hash + this.wounds;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Unit other = (Unit) obj;
        if (this.type != other.type) {
            return false;
        }
        if (this.strength != other.strength) {
            return false;
        }
        if (this.wounds != other.wounds) {
            return false;
        }
        return true;
    }    

    @Override
    public int compareTo(Unit o) {
        if (! type.equals(o.type)) {
            return type.compareTo(o.type);
        }
        else if (strength != o.strength) {
            return strength - o.strength;
        }
        
        return wounds - o.wounds;
    }
}
