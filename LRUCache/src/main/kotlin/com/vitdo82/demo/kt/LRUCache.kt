package com.vitdo82.demo.kt

class LRUCache<K, V>(private val size: Int) {

    private val cache = mutableMapOf<K, Node<K, V>>()
    private var head: Node<K, V>? = null
    private var tail: Node<K, V>? = null

    data class Node<K, V>(val key: K, var value: V, var prev: Node<K, V>? = null, var next: Node<K, V>? = null) {}

    fun set(key: K, value: V) {
        val node = cache[key]
        if (node != null) {
            node.value = value
            moveToHead(node)
        } else {
            val newNode = Node(key, value)
            if (cache.size >= size) {
                removeTail();
            }
            addToHead(newNode)
            cache[key] = newNode
        }
    }

    fun get(key: K): V? {
        val node = cache[key] ?: return null;
        moveToHead(node)
        return node.value
    }

    fun moveToHead(node: Node<K, V>) {
        if (node == head) return
        removeNode(node);
        addToHead(node)
    }

    private fun addToHead(node: Node<K, V>) {
        node.next = head
        node.prev = null;
        head?.prev = node

        head = node
        if (tail == null) {
            tail = node
        }
    }

    private fun removeNode(node: Node<K, V>) {
        node.prev?.next = node.next
        node.next?.prev = node.prev
        if (node == tail) {
            tail = node.prev
        }
        if (node == head) {
            head = node.next
        }
    }

    private fun removeTail() {
        tail?.let {
            cache.remove(it.key)
            removeNode(it)
        }
    }
}