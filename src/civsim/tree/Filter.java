/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim.tree;

/**
 *
 * @author Peter Molnar <mp@nanasoft.hu>
 */
public interface Filter<T> {    
    public boolean filterNode(Node<T> node);
}
