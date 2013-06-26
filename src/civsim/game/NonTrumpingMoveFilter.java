/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim.game;

import civsim.tree.Filter;
import civsim.tree.Node;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Peter Molnar <mp@nanasoft.hu>
 */
public class NonTrumpingMoveFilter implements Filter<CombatState> {

    @Override
    public boolean filterNode(Node<CombatState> node) {
        CombatState current = node.getData();
        CombatState parent = node.getParent().getData();
        
        // Check, if a new front was played, if not, return now.
        if (current.getFronts().size() <= parent.getFronts().size()) {
            return false;
        }
        
        // Determine played unit by substracting the current set of units from the parent set.
        Set<Unit> playedSet = new HashSet<>(parent.getHands().get(parent.getNextPlayer()).size());
        playedSet.addAll(parent.getHands().get(parent.getNextPlayer()));
        
        // Subtract current hand from parent hand to get played unit
        for (Unit u : current.getHands().get(parent.getNextPlayer())) {
            for (Unit pu : playedSet) {
                if (u.equals(pu)) {
                    playedSet.remove(pu);
                    break;
                }
            }
        }
        
        if (playedSet.isEmpty()) {
            throw new RuntimeException("Played card could not be determined.");
        }
        Unit playedUnit = playedSet.toArray(new Unit[1])[0];
        
        // Find out, if another front could have been played without taking a wound
        for (Front f : current.getFronts()) {
            // Skip the current players fronts.
            if (f.defender.equals(parent.getNextPlayer())) {
                continue;
            }
            
            // Check if the front could have been played without own loss and eliminating the opponent.
            if (playedUnit.type.isTrumping(f.defendingUnit.type) && 
                    playedUnit.strength >= (f.defendingUnit.strength - f.defendingUnit.wounds)) {
                return true;
            }
        }
        
        return false;
    }
    
}
