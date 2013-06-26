/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim.game;

import civsim.tree.Expander;
import civsim.tree.Node;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Peter Molnar <mp@nanasoft.hu>
 */
public class CombatStateExpander implements Expander<CombatState> {

    @Override
    public Set<Node<CombatState>> expand(Node<CombatState> node) {
        Set<Node<CombatState>> results = new HashSet<>();
        
        if (node.getData().isBattleOver()) {
            return results;
        }
        
        for (final CombatState cs : node.getData().getNextStates()) {
            results.add(new Node<>(cs, node));
        }
        
        return results;
    }
}
