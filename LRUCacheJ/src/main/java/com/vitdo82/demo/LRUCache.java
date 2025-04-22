package com.vitdo82.demo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

public class LRUCache<K, V> {

    private int size;
    private Map<K, CacheElement<K, V>> nodeMap;
    private LinkedList<CacheElement<K, V>> elementsList;

    public LRUCache(int size) {
        this.size = size;
        this.elementsList = new LinkedList<>();
        this.nodeMap = new HashMap<>();
    }

    public boolean put(K key, V value) {
        if (nodeMap.containsKey(key)) {
            elementsList.remove(nodeMap.get(key));
        } else {
            if (elementsList.size() == size) {
                final CacheElement<K, V> removeElement = elementsList.removeLast();
                nodeMap.remove(removeElement.key());
            }
        }
        final CacheElement<K, V> newElement = new CacheElement<>(key, value);
        nodeMap.put(key, newElement);
        elementsList.addFirst(newElement);
        return true;
    }

    public Optional<V> get(K key) {
        Optional<V> result = Optional.empty();
        if (this.nodeMap.containsKey(key)) {
            final CacheElement<K, V> element = nodeMap.get(key);
            result = Optional.of(element.value);

            elementsList.remove(element);
            elementsList.addFirst(element);
        }
        return result;
    }

    public int size() {
        return this.elementsList.size();
    }

    public boolean containsKey(K key) {
        return nodeMap.containsKey(key);
    }

    record CacheElement<K, V>(K key, V value) {
    }
}
