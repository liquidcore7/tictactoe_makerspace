package com.makerspace;

import java.util.*;
import java.util.stream.Collectors;



public class Graph<T> {

    private HashMap<T, KeyValuePair<List<T>, Integer >> storage;
    private int depth;

    public int getDepth() {
        return depth;
    }

    public Graph(T startNode) {
        storage = new HashMap<>();
        storage.put(startNode, new KeyValuePair<>(new LinkedList<>(), 0));
        depth = 1;
    }

    List<T> getChildren(T node) {
        return storage.get(node).getKey();
    }

    public void addNode(T parent, T node) {
        KeyValuePair<List<T>, Integer> current = storage.get(parent);
        current.getKey().add(node);
        if (!storage.containsKey(node)) {
            storage.put(node, new KeyValuePair<>(new LinkedList<>(), current.getValue() + 1));
        }
        if (current.getValue() == (depth - 1)) {
            depth++;
        }
    }

    public void addNodes(T parent, Collection<T> nodes) {
        nodes.forEach((nd) -> this.addNode(parent, nd));
    }


    public boolean hasNode(T node) {
        return storage.containsKey(node);
    }

    List<T> getLastLevel() {
        return  storage.values().stream()
                .filter(node -> (node.getValue() == (depth - 2)))
                .flatMap(entry -> entry.getKey().stream())
                .collect(Collectors.toList());
    }
}