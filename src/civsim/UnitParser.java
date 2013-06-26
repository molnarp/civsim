/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim;

import civsim.game.Player;
import civsim.game.Unit;
import civsim.game.UnitType;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Peter Molnar <mp@nanasoft.hu>
 */
public class UnitParser {

    private enum State {
        Unit, CombatValue;
    }
    
    public static Map<Player, Set<Unit>> parse(String input) {
        State s = State.Unit;
        
        // Initialize hands
        EnumMap<Player, Set<Unit>> hands = new EnumMap<>(Player.class);
        for (Player p : Player.values()) {
            hands.put(p, new HashSet<Unit>());
        }
        
        // Set up state variables
        Player player = Player.A;
        UnitType unitType = null;
        int combatValue;
        
        for (int i = 0; i < input.length(); ++i) {
            
            String c = input.substring(i, i + 1).toLowerCase();
            
            switch (s) {
                case Unit:
                    switch (c) {
                        case "i":
                            unitType = UnitType.Infantry;
                            s = State.CombatValue;
                            break;
                        case "c":
                            unitType = UnitType.Cavalry;
                            s = State.CombatValue;
                            break;
                        case "a":
                            unitType = UnitType.Artillery;
                            s = State.CombatValue;
                            break;
                        case "r":
                            unitType = UnitType.Aircraft;
                            s = State.CombatValue;
                            break;
                        case ",":
                            player = player.other();
                            s = State.Unit;
                            break;
                        default:
                            unitType = null;
                            s = State.CombatValue;
                            break;
                    }
                    
                    break;
                case CombatValue:
                    try {
                        combatValue = Integer.valueOf(c);
                    } catch (NumberFormatException ex) {
                        combatValue = -1;
                    }
                    
                    if (unitType != null && combatValue > 0) {
                        hands.get(player).add(new Unit(unitType, combatValue));
                    } else {
                        throw new RuntimeException("Parser error on hands position " + i + ".");
                    }
                    
                    s = State.Unit;
                    
                    break;
            }
        }
        
        return hands;
    }
    
    public static String getString(Map<Player, Set<Unit>> hands) {
        StringBuilder sb = new StringBuilder();
        
        // Add player hands
        for (Player p : Player.values()) {
            if (! hands.get(p).isEmpty()) {
                sb.append(p.toString());
                sb.append(" { ");
                for (Unit u : hands.get(p)) {
                    sb.append(u.toString());
                    sb.append(" ");
                }
                sb.append("} ");
            }
        }
        
        return sb.toString();
    }
}
