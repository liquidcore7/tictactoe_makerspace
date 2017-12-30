package com.makerspace;

import java.util.*;

public class Node<T> {
    private int level;
    private Collection<Node<T>> children;
    private T value;

    public Node(T value, int level) {
        this.value = value;
        this.level = level;
        children = new HashSet<>();
    }

    public Node<T> addChild(T child) {
        Node<T> constructed = new Node<>(child, level + 1);
        children.add(constructed);
        return constructed;
    }

    public void addChild(Node<T> child) {
        children.add(child);
    }

    public void addChildren(Collection<T> children) {
        children.forEach(this::addChild);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return level == node.level &&
                Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(level, value);
    }

    public Collection<Node<T>> getChildren() {
        return children;

    }

    public int getLevel() {
        return level;
    }

    public T getValue() {
        return value;
    }
}
