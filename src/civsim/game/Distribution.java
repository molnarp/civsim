package civsim.game;


import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
abstract public class Distribution {

    public static final Map<Integer,Integer> INFANTRY_DISTRIBUTION = new HashMap<>();
    public static final Map<Integer,Integer> CAVALRY_DISTRIBUTION = new HashMap<>();
    public static final Map<Integer,Integer> ARTILLERY_DISTRIBUTION = new HashMap<>();
    
    static {
        INFANTRY_DISTRIBUTION.put(1, 3);
        INFANTRY_DISTRIBUTION.put(2, 9);
        INFANTRY_DISTRIBUTION.put(3, 3);
        
        CAVALRY_DISTRIBUTION.put(1, 3);
        CAVALRY_DISTRIBUTION.put(2, 9);
        CAVALRY_DISTRIBUTION.put(3, 3);
        
        ARTILLERY_DISTRIBUTION.put(1, 3);
        ARTILLERY_DISTRIBUTION.put(2, 9);
        ARTILLERY_DISTRIBUTION.put(3, 3);
    }
}
