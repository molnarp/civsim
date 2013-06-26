/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package civsim.game.ability;

import civsim.game.CombatState;
import civsim.game.Front;
import civsim.game.Player;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public class MetalworkingAbility implements Ability {

    @Override
    public boolean isApplicable(Player player, CombatState parentState) {
        if (! parentState.getHands().get(player).isEmpty()) {
            return false;
        }
        
        // Only use metalworking, if the unit can be played against an enemy unit
        for (Front f : parentState.getFronts()) {
            if (f.defender.equals(parentState.getNextPlayer().other())) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public boolean isPreCombat() {
        return true;
    }

    @Override
    public boolean isPostCombat() {
        return false;
    }
}
