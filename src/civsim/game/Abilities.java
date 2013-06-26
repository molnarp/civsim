/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim.game;

import civsim.game.ability.Ability;
import civsim.game.ability.AnimalHusbandryAbility;
import civsim.game.ability.BallisticsAbility;
import civsim.game.ability.MathematicsAbility;
import civsim.game.ability.MetalworkingAbility;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public enum Abilities implements Ability {
    /** Increases attack strength one time, when the unit is played. */
    Metalworking(new MetalworkingAbility()),
    /** Heals up to 3 wounds on any units in play. */
    AnimalHusbandry(new AnimalHusbandryAbility()),
    Mathematics(new MathematicsAbility()),
    Ballistics(new BallisticsAbility());
    
    private final Ability ability;
    
    public static final Map<Player, Set<Abilities>> NO_ABILITIES;
    
    static {
        NO_ABILITIES = new EnumMap<>(Player.class);
        for (Player p : Player.values()) {
            NO_ABILITIES.put(p, Collections.EMPTY_SET);
        }
    }

    private Abilities(Ability ability) {
        this.ability = ability;
    }

    @Override
    public boolean isApplicable(Player player, CombatState combatState) {
        return ability.isApplicable(player, combatState);
    }

    @Override
    public boolean isPreCombat() {
        return ability.isPreCombat();
    }

    @Override
    public boolean isPostCombat() {
        return ability.isPostCombat();
    }
    
    public static Map<Player, Set<Abilities>> getAbilities(String config) {
        Map<Player, Set<Abilities>> abilities = new EnumMap<>(Player.class);
        for (Player p : Player.values()) {
            abilities.put(p, EnumSet.noneOf(Abilities.class));
        }
        
        String[] playersAbilities = config.split(",");
        Player p = Player.A;
        
        for (String pAb : playersAbilities) {
            String[] abilityNames = pAb.split(";");

            for (String name : abilityNames) {
                if (name == null || "".equals(name)) {
                    continue;
                }

                abilities.get(p).add(Abilities.valueOf(name));
            }
            
            p = p.other();
        }
                
        return abilities;
    }
    
    public static String getString(Map<Player, Set<Abilities>> abilities) {
        StringBuilder sb = new StringBuilder();
        for (Player p : abilities.keySet()) {
            sb.append(p.toString());
            sb.append(" { ");
            for (Abilities a : abilities.get(p)) {
                sb.append(a.name());
                sb.append(" ");
            }
            
            sb.append(" } ");
        }
        
        return sb.toString();
    }
}
