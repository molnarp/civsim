/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim.game;

import civsim.tree.Filter;
import civsim.tree.Node;

/**
 *
 * @author Peter Molnar <mp@nanasoft.hu>
 */
public class TrumpedFilter implements Filter<CombatState> {

    @Override
    public boolean filterNode(Node<CombatState> node) {
        final CombatState current = node.getData();
        final CombatState parent = node.getParent().getData();
        
        // Check, if the parent has the same fronts, but one unit more
        if (current.getFronts().equals(parent.getFronts()) && 
                current.getHands().get(parent.getNextPlayer()).size() < parent.getHands().get(parent.getNextPlayer()).size()) {
            return true;
        }
        
        return false;
    }
    
}
