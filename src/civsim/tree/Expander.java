/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim.tree;

import java.util.Set;

/**
 *
 * @author Peter Molnar <mp@nanasoft.hu>
 */
public interface Expander<T> {
    public Set<Node<T>> expand(Node<T> node);
}
