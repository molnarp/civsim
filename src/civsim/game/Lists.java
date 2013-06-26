/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package civsim.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
abstract public class Lists {
    public static <T> List<T> copyOf(Collection<T> ts) {
        List<T> copy = new ArrayList<>(ts.size());
        copy.addAll(ts);
        
        return copy;
    }
}
