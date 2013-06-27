/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package civsim;

import civsim.game.Abilities;
import civsim.game.CombatState;
import civsim.game.Front;
import civsim.game.TrumpMissFilter;
import civsim.game.Player;
import civsim.game.Unit;
import civsim.game.UnitType;
import civsim.tree.Node;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public class NonTrumpingMoveFilterTest {

    @Test public void testTrumpPlayAvoided() {
        // Hands
        EnumMap<Player,Set<Unit>> parentHands = new EnumMap<>(Player.class);
        parentHands.put(Player.A, Sets.asSet(new Unit(UnitType.Infantry, 2)));
        parentHands.put(Player.B, Collections.EMPTY_SET);
        
        // Fronts
        Set<Front> parentFronts = Sets.asSet(new Front(Player.B, new Unit(UnitType.Cavalry, 2)));
        
        CombatState parent = new CombatState(parentHands, Player.A, parentFronts, Abilities.NO_ABILITIES);
        Node<CombatState> parentNode = new Node<>(parent, null);
        
        // Hands
        EnumMap<Player,Set<Unit>> currentHands = new EnumMap<>(Player.class);
        currentHands.put(Player.A, Collections.EMPTY_SET);
        currentHands.put(Player.B, Collections.EMPTY_SET);
        
        // Fronts
        Set<Front> currentFronts = Sets.asSet(
                new Front(Player.B, new Unit(UnitType.Cavalry, 2)),
                new Front(Player.A, new Unit(UnitType.Infantry, 2)));
        
        CombatState current = new CombatState(currentHands, Player.B, currentFronts, Abilities.NO_ABILITIES);
        Node<CombatState> currentNode = new Node<>(current, parentNode);
        
        TrumpMissFilter filter = new TrumpMissFilter();
        
        Assert.assertTrue(filter.filterNode(currentNode));
    }

    @Test public void testNonTrumpable() {
        // Hands
        EnumMap<Player,Set<Unit>> parentHands = new EnumMap<>(Player.class);
        parentHands.put(Player.A, Sets.asSet(new Unit(UnitType.Artillery, 2)));
        parentHands.put(Player.B, Collections.EMPTY_SET);
        
        // Fronts
        Set<Front> parentFronts = Sets.asSet(new Front(Player.B, new Unit(UnitType.Cavalry, 2)));
        
        CombatState parent = new CombatState(parentHands, Player.A, parentFronts, Abilities.NO_ABILITIES);
        Node<CombatState> parentNode = new Node<>(parent, null);
        
        // Hands
        EnumMap<Player,Set<Unit>> currentHands = new EnumMap<>(Player.class);
        currentHands.put(Player.A, Collections.EMPTY_SET);
        currentHands.put(Player.B, Collections.EMPTY_SET);
        
        // Fronts
        Set<Front> currentFronts = Sets.asSet(
                new Front(Player.B, new Unit(UnitType.Cavalry, 2)),
                new Front(Player.A, new Unit(UnitType.Infantry, 2)));
        
        CombatState current = new CombatState(currentHands, Player.B, currentFronts, Abilities.NO_ABILITIES);
        Node<CombatState> currentNode = new Node<>(current, parentNode);
        
        TrumpMissFilter filter = new TrumpMissFilter();
        
        Assert.assertFalse(filter.filterNode(currentNode));
    }

}
