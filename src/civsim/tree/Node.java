/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package civsim.tree;

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Peter Molnar <mp@nanasoft.hu>
 */
public class Node<T> {
    private T data;
    private Node<T> parent;
    private Set<Node<T>> children;

    public Node(T data, Node<T> parent) {
        this.data = data;
        this.parent = parent;
    }
    
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Set<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(Set<Node<T>> children) {
        this.children = children;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node<T> other = (Node<T>) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        if (!Objects.equals(this.children, other.children)) {
            return false;
        }
        return true;
    }
    
    
}
