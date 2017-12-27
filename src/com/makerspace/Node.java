package com.makerspace;

import java.util.*;

public class Node<T> {
    private T value;
    private int level;
    private Set<Node<T>> children;

    public Node(T init, int lvl) {
        value = init;
        level = lvl;
        children = new HashSet<>();
    }

    public int getLevel() {
        return level;
    }

    public void addChild(T child) {
        children.add(new Node<>(child, level + 1));
    }

    public void addChildren(Collection<T> childList) {
        childList.forEach((init) ->
                children.add(new Node<>(init, level + 1))
        );
    }

    public T getValue() {
        return value;
    }

    public Set<Node<T>> getChildren() {
        return children;
    }
}
