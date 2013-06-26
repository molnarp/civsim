/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package civsim.game.ability;

import civsim.game.CombatState;
import civsim.game.Player;
import civsim.game.Unit;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public class AnimalHusbandryAbility implements Ability {

    @Override
    public boolean isApplicable(Player player, CombatState combatState) {
        for (Unit u : combatState.getHands().get(player)) {
            if (u.wounds > 0) {
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
        return true;
    }
}
