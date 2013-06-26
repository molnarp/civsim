/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package civsim.game;

import civsim.Sets;
import civsim.UnitParser;
import civsim.tree.Node;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public class CombatState {

    private final Map<Player, Set<Unit>> hands;
    private final Player nextPlayer;
    private final Set<Front> fronts;
    private final Map<Player, Set<Abilities>> abilities;

    public CombatState(Map<Player, Set<Unit>> hands, Player nextPlayer, Set<Front> fronts, Map<Player, Set<Abilities>> abilities) {
        this.hands = hands;
        this.nextPlayer = nextPlayer;
        this.fronts = fronts;
        this.abilities = abilities;               
    }

//    public CombatState(Map<Player, Set<Unit>> hands, Player nextPlayer, Set<Front> fronts) {        
//        this(hands, nextPlayer, fronts, new EnumMap<Player, Set<Abilities>>(Player.class));
//        
//        for (Player p : Player.values()) {
//            abilities.put(p, Collections.EMPTY_SET);
//        }
//    }
    
    public Set<CombatState> getNextStates() {
        Set<CombatState> nextStates = 
                new HashSet<>(hands.get(nextPlayer).size() * (fronts.size() + 1));
        
        
        // Choose a card
        for (final Unit playedUnit : hands.get(nextPlayer)) {
            
            // Create next hand
            final Map<Player, Set<Unit>> nextHands = new EnumMap<>(Player.class);
            for (Player player : Player.values()) {
                Set<Unit> hand = Sets.copyOf(hands.get(player));
                nextHands.put(player, hand);
            }
            // Remove played unit from the hand
            nextHands.get(nextPlayer).remove(playedUnit);
            
            // Choose a front
            for (final Front f : fronts) {
                if (f.defender.equals(nextPlayer)) {
                    continue;
                }
                
                // Create next fronts set
                Set<Front> nextFronts = Sets.copyOf(fronts);                
                // Remove current front from the next fronts set, because it's going to change.
                nextFronts.remove(f);
                
                // Play the unit
                Front result = f.fight(nextPlayer, playedUnit);                
                if (result != null) {
                    nextFronts.add(result);
                }
                
                nextStates.add(new CombatState(nextHands, nextPlayer.other(), nextFronts, abilities));
                
                // Play same card with metalworking
                if (! abilities.get(nextPlayer).isEmpty() && abilities.get(nextPlayer).contains(Abilities.Metalworking)) {
                    // Create next fronts set
                    Set<Front> nextFrontsMw = Sets.copyOf(fronts);                
                    // Remove current front from the next fronts set, because it's going to change.
                    nextFrontsMw.remove(f);
                    
                    Front resultMw = f.fight(nextPlayer, new Unit(playedUnit, true));
                    if (resultMw != null) {
                        nextFrontsMw.add(resultMw);
                    }
                    
                    // Copy current abilities
                    EnumMap<Player,Set<Abilities>> nextAbilities = new EnumMap<>(Player.class);
                    for (Player player : Player.values()) {
                        nextAbilities.put(player, Sets.copyOf(abilities.get(player)));
                    }
                    // Remove Metalworking from available abilities
                    nextAbilities.get(nextPlayer).remove(Abilities.Metalworking);
                    
                    nextStates.add(new CombatState(nextHands, nextPlayer.other(), nextFrontsMw, nextAbilities));
                }
            }
            
            // Add option: play a new front
            
            // Create next fronts set
            Set<Front> nextFronts = new HashSet<>(fronts.size());
            nextFronts.addAll(fronts);
            
            // Add new front
            Front f = new Front(nextPlayer, playedUnit);
            nextFronts.add(f);
            
            nextStates.add(new CombatState(nextHands, nextPlayer.other(), nextFronts, abilities));
        }
        
        return nextStates;        
    }
    
    public boolean isBattleOver() {
        if (hands.get(Player.A).isEmpty() && hands.get(Player.B).isEmpty()) {
            return true;
        }
        
        return false;
    }
    
    public Map<Player,Integer> getScore() {
        EnumMap<Player, Integer> scores = new EnumMap<>(Player.class);
        for (Player player : Player.values()) {
            scores.put(player, 0);
        }
        
        for (Front f : fronts) {
            scores.put(f.defender, scores.get(f.defender) + f.defendingUnit.strength);
        }
        
        return scores;
    }
    
    public int getSimpleScore() {
        int score = 0;
        for (Front f : fronts) {
            score += (f.defender.ordinal() == 0 ? 1 : -1) * f.defendingUnit.strength;
        }
        
        return score;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.hands);
        hash = 41 * hash + (this.nextPlayer != null ? this.nextPlayer.hashCode() : 0);
        hash = 41 * hash + Objects.hashCode(this.fronts);
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
        final CombatState other = (CombatState) obj;
        if (!Objects.equals(this.hands, other.hands)) {
            return false;
        }
        if (this.nextPlayer != other.nextPlayer) {
            return false;
        }
        if (!Objects.equals(this.fronts, other.fronts)) {
            return false;
        }
        return true;
    }

    public Map<Player, Set<Unit>> getHands() {
        return hands;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public Set<Front> getFronts() {
        return fronts;
    }

    public Map<Player, Set<Abilities>> getAbilities() {
        return abilities;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();        
        sb.append(UnitParser.getString(hands));
        
        // Add fronts
        sb.append("Fronts { ");
        for (Front f : fronts) {
            sb.append(f.defender.toString());
            sb.append(":");
            sb.append(f.defendingUnit.toString());
            sb.append(" ");
        }
        sb.append("} ");
        
        return sb.toString();
    }
    
    public static final Comparator<Node<CombatState>> SCORE_COMPARATOR = 
            new Comparator<Node<CombatState>>() {
        @Override
        public int compare(Node<CombatState> o1, Node<CombatState> o2) {
            final CombatState c1 = o1.getData(), c2 = o2.getData();

            if (c1.getSimpleScore() != c2.getSimpleScore()) {
                return c2.getSimpleScore() - c1.getSimpleScore();
            }

            return c1.getFronts().size() - c2.getFronts().size();
        }            
    };
}
