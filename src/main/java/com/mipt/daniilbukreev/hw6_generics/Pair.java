package com.mipt.daniilbukreev.hw6_generics;

public class Pair<K, V> {
    K key;
    V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }
    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }
    public void setValue(V value) {
        this.value = value;
    }

    public Pair<V, K> swap(Pair<K, V> pair) {
        return new Pair<>(value, key);
    }

    @Override
    public String toString() {
        if (key == null && value == null) {
            return "Pair{key=null, value=null}";
        }
        if (key == null) {
            return "Pair{key=null, value=" + value.toString() + "}";
        }
        return "Pair{key=" + key.toString() +
                ", value=" + value.toString() +
                "}";
    }
}