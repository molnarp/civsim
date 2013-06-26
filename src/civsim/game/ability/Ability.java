/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim.game.ability;

import civsim.game.CombatState;
import civsim.game.Player;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public interface Ability {

    public boolean isApplicable(Player player, CombatState combatState);
    public boolean isPreCombat();
    public boolean isPostCombat();
}
