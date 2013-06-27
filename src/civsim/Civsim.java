/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim;

import civsim.game.Abilities;
import civsim.game.CombatState;
import civsim.game.CombatStateExpander;
import civsim.game.Front;
import civsim.game.Player;
import civsim.game.Unit;
import civsim.game.ability.Ability;
import civsim.tree.Filter;
import civsim.tree.Node;
import civsim.tree.Tree;
import com.csvreader.CsvWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public class Civsim {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Parse command line options
            Options options = getOptions();
            Parser parser = new GnuParser();
            CommandLine cli = parser.parse(getOptions(), args);
            
            // Check for help switch and print help, if needed
            if (cli.hasOption("h")) {
                help(options);
                return;
            }

            // Check, if mandatory options are all set
            checkOptions(options, cli, new String[] { "u" });
            
            // Parse units
            Map<Player, Set<Unit>> units = UnitParser.parse(cli.getOptionValue("u"));
            System.err.println("Units: " + UnitParser.getString(units));
            
            // Parse filters
            List<Filter<CombatState>> filters = Collections.EMPTY_LIST;
            if (cli.hasOption("f")) {
                filters = Filters.getFilters(cli.getOptionValue("f"));
            }
            
            // Parse abilities
            Map<Player, Set<Abilities>> abilities = new EnumMap<>(Player.class);
            for (Player p : Player.values()) {
                abilities.put(p, Collections.EMPTY_SET);
            }
            if (cli.hasOption("f")) {
                abilities = Abilities.getAbilities(cli.getOptionValue("a"));
            }
            System.err.println("Abilities: " + Abilities.getString(abilities));
            
            // Run simulation
            CombatState startState = new CombatState(units, Player.B, new HashSet<Front>(0), abilities);
            // Create filter for futile moves - moves that result in a unit lost without effect
            Tree<CombatState> resultTree = Tree.buildBfs(new Node<>(startState, null), 
                    new CombatStateExpander(), filters);

            // Sort leafs based on score
            List<Node<CombatState>> leafs = new ArrayList<>(resultTree.getLeafs().size());
            leafs.addAll(resultTree.getLeafs());
            Collections.sort(leafs, CombatState.SCORE_COMPARATOR);        
            
            // Write summary file
            if (cli.hasOption("s")) {
                writeSummary(leafs, cli.getOptionValue("s"));
            }
            
            if (cli.hasOption("o")) {
                writeOutcomes(leafs, cli.getOptionValue("o"));
            }

            if (cli.hasOption("c")) {
                writeCombats(leafs, cli.getOptionValue("c"));
            }
            
            
        } catch (ParseException ex) {
            System.err.println(ex);
            System.exit(1);
        } catch (IllegalArgumentException ex) {
            System.exit(1);
        }
    }
    
    public static void writeSummary(List<Node<CombatState>> leafs, String summaryFile) {
        // Create result statistics
        Map<Integer,Integer> resultMap = new HashMap<>();
        for (Node<CombatState> node : leafs) {
            // Calculate score
            int score = node.getData().getSimpleScore();
            
            if (resultMap.containsKey(score)) {
                resultMap.put(score, resultMap.get(score) + 1);
            } else {
                resultMap.put(score, 1);
            }
        }
        
        // Create a sorted list of scores
        List<Integer> scoreList = new ArrayList<>(resultMap.size());
        scoreList.addAll(resultMap.keySet());
        Collections.sort(scoreList);
        Collections.reverse(scoreList);
        
        // Write summary results
        CsvWriter cw = new CsvWriter(summaryFile);
        try {
            cw.writeRecord(new String[] { "score", "frequency" });
            
            for (Integer score : scoreList) {
                cw.writeRecord(new String[] { 
                    Integer.toString(score), 
                    Integer.toString(resultMap.get(score)) 
                });
            }
        
            cw.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            cw.close();
        }        
    }
    
    public static void writeOutcomes(List<Node<CombatState>> leafs, String outcomesFile) {
        
        // Write combat results
        CsvWriter cw = new CsvWriter(outcomesFile);
        long combatNo = 0;
        try {
            cw.writeRecord(new String[] { "combat_no", "score", "fronts" });
            
            for (Node<CombatState> n : leafs) {
                cw.writeRecord(new String[] { 
                    Long.toString(combatNo), 
                    Integer.toString(n.getData().getSimpleScore()), n.getData().toString() });
            }
        
            cw.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            cw.close();
        }
    }
    
    public static void writeCombats(List<Node<CombatState>> leafs, String combatFile) {
        
        // Write combat results
        CsvWriter cw = new CsvWriter(combatFile);
        long combatNo = 0;
        try {
            // Write header
            cw.writeRecord(new String[] {"combat_no", "score", "hands", "abilities", "fronts" });
            
            // Iterate on outcomes
            for (Node<CombatState> n : leafs) {
                
                // Move up on combat history and collect into list
                List<Node> history = new ArrayList<>();
                Node<CombatState> currentNode = n;
                do {
                    history.add(currentNode);
                    currentNode = currentNode.getParent();
                } while (currentNode != null);
                
                // Reverse to get right order
                Collections.reverse(history);
                
                // Write to file
                for (Node<CombatState> hn : history) {
                    cw.writeRecord(new String[] { 
                        Long.toString(combatNo), 
                        Integer.toString(hn.getData().getSimpleScore()), 
                        UnitParser.getString(hn.getData().getHands()),
                        Abilities.getString(hn.getData().getAbilities()),
                        hn.getData().toString()
                    });                    
                }
                
                ++combatNo;
            }
        
            cw.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            cw.close();
        }        
    }
    
    public static Options getOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "Prints this help.");
        options.addOption("a", "abilities", true, "Semicolon separated list of abilities, comma for next player abilities.");
        options.addOption("f", "filters", true, "Comma separated list of filters.");
        options.addOption("u", "units", true, "The units in hands of players.");
        options.addOption("s", "summaryFile", true, "The file where the summary output is written.");
        options.addOption("o", "outcomeFile", true, "The file where the combat outcomes are written.");
        options.addOption("c", "combatFile", true, "The file where the combat proceedings are written.");
        
        return options;
    }
    
    public static void help(Options options) {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp(80, "civsim [options]", "Civilization: The Board Game combat simulation tool.\n", 
                options, "\nCopyright 2013 Peter Molnar. "
                + "Licensed under the Apache License, Version 2.0.");
    }
    
    public static void checkOptions(Options options, CommandLine cli, String[] mandatoryOptions) {
        boolean hasErrors = false;
        
        for (String mo : mandatoryOptions) {
            Option opt = options.getOption(mo);
            if (! cli.hasOption(mo)) {
                System.err.println("ERROR: Missing mandatory option --" + 
                        opt.getLongOpt());
                hasErrors = true;
            }
            
            if (opt.hasArg() && (cli.getOptionValue(mo) == null || "".equals(cli.getOptionValue(mo)))) {
                System.err.println("ERROR: Option --" + 
                        opt.getLongOpt() + " must have a value.");
                hasErrors = true;
            }
        }
        
        if (hasErrors) {
            throw new IllegalArgumentException();
        }
    }
}
