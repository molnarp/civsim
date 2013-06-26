/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package civsim;

import civsim.game.Front;
import civsim.game.Player;
import civsim.game.Unit;
import civsim.game.UnitType;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public class FrontTest {

    @Test public void testFight1() {
        // Player A attacks with Inf/2/0
        Unit attacker = new Unit(UnitType.Infantry, 2);
        // Player B defends with Inf/1/0
        Front f = new Front(Player.B, new Unit(UnitType.Infantry, 1));
        
        Front result = f.fight(Player.A, attacker);
        
        Assert.assertEquals(Player.A, result.defender);
        Assert.assertEquals(UnitType.Infantry, result.defendingUnit.type);
        Assert.assertEquals(2, result.defendingUnit.strength);
        Assert.assertEquals(1, result.defendingUnit.wounds);
    }
    
    @Test public void testFightTie() {
        // Player A attacks with Inf/2/0
        Unit attacker = new Unit(UnitType.Infantry, 2);
        // Player B defends with Inf/1/0
        Front f = new Front(Player.B, new Unit(UnitType.Infantry, 2));
        
        Front result = f.fight(Player.A, attacker);
        Assert.assertNull(result);
    }
    
    @Test public void testFightTrumph1Win() {
        // Player A attacks with Inf/2/0
        Unit attacker = new Unit(UnitType.Infantry, 2);
        // Player B defends with Cav/2/0
        Front f = new Front(Player.B, new Unit(UnitType.Cavalry, 2));
        
        Front result = f.fight(Player.A, attacker);
        Assert.assertEquals(Player.A, result.defender);
        Assert.assertEquals(UnitType.Infantry, result.defendingUnit.type);
        Assert.assertEquals(2, result.defendingUnit.strength);
        Assert.assertEquals(0, result.defendingUnit.wounds);
    }
    
    @Test public void testFightTrumph1Lose() {
        // Player A attacks with Inf/2/0
        Unit attacker = new Unit(UnitType.Infantry, 2);
        // Player B defends with Cav/3/0
        Front f = new Front(Player.B, new Unit(UnitType.Cavalry, 3));
        
        Front result = f.fight(Player.A, attacker);
        
        Assert.assertEquals(Player.B, result.defender);        
        Assert.assertEquals(UnitType.Cavalry, result.defendingUnit.type);
        Assert.assertEquals(3, result.defendingUnit.strength);
        Assert.assertEquals(2, result.defendingUnit.wounds);
    }
    
    @Test public void testFightTrumph2Win() {
        // Player A attacks with Inf/2/1
        Unit attacker = new Unit(UnitType.Infantry, 2, 1);
        // Player B defends with Art/1/0
        Front f = new Front(Player.B, new Unit(UnitType.Artillery, 1));
        
        Front result = f.fight(Player.A, attacker);
        
        Assert.assertEquals(Player.B, result.defender);
        Assert.assertEquals(UnitType.Artillery, result.defendingUnit.type);
        Assert.assertEquals(1, result.defendingUnit.strength);
        Assert.assertEquals(0, result.defendingUnit.wounds);
    }
    
    @Test public void testFightTrumph2Lose() {
        // Player A attacks with Inf/2/0
        Unit attacker = new Unit(UnitType.Infantry, 2, 1);
        // Player B defends with Inf/1/0
        Front f = new Front(Player.B, new Unit(UnitType.Cavalry, 1));
        
        Front result = f.fight(Player.A, attacker);
        
        Assert.assertEquals(Player.A, result.defender);        
        Assert.assertEquals(UnitType.Infantry, result.defendingUnit.type);
        Assert.assertEquals(2, result.defendingUnit.strength);
        Assert.assertEquals(1, result.defendingUnit.wounds);
    }
    
    @Test public void testFightTrumph3MwWin() {
        // Player A attacks with Inf/2/1
        Unit attacker = new Unit(UnitType.Infantry, 1, 0, true);
        // Player B defends with Art/1/0
        Front f = new Front(Player.B, new Unit(UnitType.Cavalry, 2));
        
        Front result = f.fight(Player.A, attacker);
        
        Assert.assertEquals(Player.A, result.defender);
        Assert.assertEquals(UnitType.Infantry, result.defendingUnit.type);
        Assert.assertEquals(1, result.defendingUnit.strength);
        Assert.assertEquals(0, result.defendingUnit.wounds);
    }

    @Test public void testFight3MwBoth() {
        // Player A attacks with Inf/2/1
        Unit attacker = new Unit(UnitType.Infantry, 1, 0, true);
        // Player B defends with Art/1/0
        Front f = new Front(Player.B, new Unit(UnitType.Infantry, 3));
        
        Front result = f.fight(Player.A, attacker);
        
        Assert.assertNull(result);
    }
    
    @Test public void testFight3MwLose() {
        // Player A attacks with Inf/2/1
        Unit attacker = new Unit(UnitType.Infantry, 1, 0, true);
        // Player B defends with Art/1/0
        Front f = new Front(Player.B, new Unit(UnitType.Aircraft, 6));
        
        Front result = f.fight(Player.A, attacker);
        
        Assert.assertEquals(Player.B, result.defender);        
        Assert.assertEquals(UnitType.Aircraft, result.defendingUnit.type);
        Assert.assertEquals(6, result.defendingUnit.strength);
        Assert.assertEquals(4, result.defendingUnit.wounds);
    }

    @Test public void testFight3MwDefenderLose() {
        // Player A attacks with Inf/2/1
        Unit attacker = new Unit(UnitType.Infantry, 2);
        // Player B defends with Art/1/0
        Front f = new Front(Player.B, new Unit(UnitType.Infantry, 1, 0, true));
        
        Front result = f.fight(Player.A, attacker);
        
        Assert.assertEquals(Player.A, result.defender);        
        Assert.assertEquals(UnitType.Infantry, result.defendingUnit.type);
        Assert.assertEquals(2, result.defendingUnit.strength);
        Assert.assertEquals(1, result.defendingUnit.wounds);
    }
    
}
