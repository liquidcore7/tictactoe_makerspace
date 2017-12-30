package com.makerspace;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;



public class Graph<T> {

    private HashMap<T, Node<T>> storage;
    private int depth;

    public int getDepth() {
        return depth;
    }

    public Graph(T startNode) {
        storage = new HashMap<>();
        storage.put(startNode, new Node<>(startNode, 0));
        depth = 1;
    }

    public Node<T> getNode(T value) {
        return storage.get(value);
    }

    public Collection<T> getChildren(T node) {
        return storage.get(node)
                .getChildren()
                .stream()
                .map(nd -> nd.getValue())
                .collect(Collectors.toList());
    }

    public void addNode(T parent, T node) {

        Node<T> prnt = storage.get(parent);
        Node<T> curr = prnt.addChild(node);

        storage.putIfAbsent(node, curr);

        if (prnt.getLevel() == (depth - 1)) {
            depth++;
        }
    }

    public void addNodes(T parent, Collection<T> nodes) {
        nodes.forEach((nd) -> this.addNode(parent, nd));
    }


    public boolean hasNode(T node) {
        return storage.containsKey(node);
    }

    public Collection<T> getLevel(int lvl) {
        return  storage.values().stream()
                .filter(node -> (node.getLevel() == lvl))
                .flatMap(entry -> entry.getChildren().stream())
                .distinct()
                .map(node -> node.getValue())
                .collect(Collectors.toList());
    }

    public void walkBreadthFirst(Consumer<? super T> applyToEach) {
        for (int i = 0; i < depth; i++) {
            getLevel(i).forEach(applyToEach);
        }
    }

    public Collection<T> getLastLevel() {
        return getLevel(depth - 2);
    }
}
