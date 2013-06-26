/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package civsim.game;

import java.util.Objects;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public class Front {

    public final Player defender;
    public final Unit defendingUnit;

    public Front(Player player, Unit unit) {
        this.defender = player;
        this.defendingUnit = unit;
    }
    
    public Front fight(Player attacker, Unit attackingUnit) throws FightException {
        if (attacker == null || attackingUnit == null) {
            throw new IllegalArgumentException();
        }
        
        if (defender.equals(attacker)) {
            throw new FightException("Player " + attacker.name() + " already has a unit on this front.");
        }
        
        if (defendingUnit.type.isTrumping(attackingUnit.type)) {
            Unit attackingUnitAfterHit = attackingUnit.hitBy(defendingUnit.strength);
            
            if (attackingUnitAfterHit.isAlive()) {
                return new Front(attacker, attackingUnitAfterHit);
            } else {
                return new Front(defender, defendingUnit);
            }
        }
        
        if (attackingUnit.type.isTrumping(defendingUnit.type)) {
            Unit defendingUnitAfterHit = defendingUnit.hitBy(attackingUnit.strength + (attackingUnit.withMetalworking ? 3 : 0));
            if (defendingUnitAfterHit.isAlive()) {
                return new Front(defender, defendingUnitAfterHit);
            } else {
                return new Front(attacker, attackingUnit);
            }
        }
        
        Unit attackingUnitAfterHit = attackingUnit.hitBy(defendingUnit.strength);
        Unit defendingUnitAfterHit = defendingUnit.hitBy(attackingUnit.strength + (attackingUnit.withMetalworking ? 3 : 0));
        
        if (attackingUnitAfterHit.isAlive() && ! defendingUnitAfterHit.isAlive()) {
            return new Front(attacker, attackingUnitAfterHit);
        }
        
        if (! attackingUnitAfterHit.isAlive() && defendingUnitAfterHit.isAlive()) {
            return new Front(defender, defendingUnitAfterHit);            
        }
        
        if (! attackingUnitAfterHit.isAlive() && ! defendingUnitAfterHit.isAlive()) {
            return null;
        }
        
        throw new FightException("Both sides are alive after fight: " +
                attackingUnitAfterHit.toString() + " - " + defendingUnitAfterHit.toString()); 
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.defender != null ? this.defender.hashCode() : 0);
        hash = 59 * hash + Objects.hashCode(this.defendingUnit);
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
        final Front other = (Front) obj;
        if (this.defender != other.defender) {
            return false;
        }
        if (!Objects.equals(this.defendingUnit, other.defendingUnit)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + defender.name() + ": " + defendingUnit.toString();
    }
    
    
}
