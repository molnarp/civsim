/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package civsim;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
abstract public class Sets {

    
    public static <T> Set<T> asSet(T... args) {
        Set<T> set = new HashSet<>(args.length);
        set.addAll(Arrays.asList(args));
        return set;
    }
    
    public static <T> Set<T> copyOf(Collection<T> tset) {
        Set<T> copy = new HashSet<>(tset.size());
        copy.addAll(tset);
        return copy;
    }
    
}
