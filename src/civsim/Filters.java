/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim;

import civsim.game.CombatState;
import civsim.game.FutileMoveFilter;
import civsim.game.NonTrumpingMoveFilter;
import civsim.tree.Filter;
import civsim.tree.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Peter Molnar <mp@nanasoft.hu>
 */
public enum Filters implements Filter<CombatState> {
    
    FutileMove(new FutileMoveFilter()),
    NonTrumpingMove(new NonTrumpingMoveFilter());
    
    private final Filter<CombatState> filter;

    private Filters(Filter<CombatState> filter) {
        this.filter = filter;
    }

    @Override
    public boolean filterNode(Node<CombatState> node) {
        return filter.filterNode(node);
    }
    
    public static List<Filter<CombatState>> getFilters(String config) {
        List<Filter<CombatState>> filters = new ArrayList<>();
        String[] filterNames = config.split(",");
        
        for (String fname : filterNames) {
            if (fname == null || "".equals(fname)) {
                continue;
            }
            
            filters.add(Filters.valueOf(fname));
        }
        
        return filters;
    }
}
