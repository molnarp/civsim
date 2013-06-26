/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Peter Molnar <mp@nanasoft.hu>
 */
public class Tree<T> {

    private final Node<T> root;
    private final Set<Node<T>> leafs;

    private Tree(Node<T> root, Set<Node<T>> leafs) {
        this.root = root;
        this.leafs = leafs;
    }

    public Node<T> getRoot() {
        return root;
    }

    public Set<Node<T>> getLeafs() {
        return leafs;
    }
    
    public static <T> Tree<T> buildBfs(Node<T> root, Expander<T> expander, List<Filter<T>> filters) {
        final Set<Node<T>> leafs = new HashSet<>();
        final List<Node<T>> nodes = new ArrayList<>();
        nodes.add(root);

        for (int i = 0; i < nodes.size(); ++i) {
            Node<T> currentNode = nodes.get(i);

            Set<Node<T>> children = expander.expand(currentNode);

            // Skip processing, if no children were produced
            if (children == null || children.isEmpty()) {
                leafs.add(currentNode);
                continue;
            }

            // Filter nodes if filters were defined
            if (filters != null && ! filters.isEmpty()) {
                Set<Node<T>> unfilteredChildren = new HashSet<>(children.size());
                for (final Node<T> child : children) {
                    boolean filtered = false;

                    for (final Filter<T> f : filters) {
                        if (f.filterNode(child)) {
                            filtered = true;
                            break;
                        }
                    }

                    if (! filtered) {
                        unfilteredChildren.add(child);
                    }
                }

                children = unfilteredChildren;
            }

            currentNode.setChildren(children);
            nodes.addAll(children);
        }

        return new Tree<>(root, leafs);
    }
}
