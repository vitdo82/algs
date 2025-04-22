package com.vitdo82.demo.kt

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LRUCacheTest {

    @Test
    fun `test set and get functionality`() {
        val cache = LRUCache<Int, String>(3)
        cache.set(1, "A")
        cache.set(2, "B")
        cache.set(3, "C")

        assertEquals("A", cache.get(1))
        assertEquals("B", cache.get(2))
        assertEquals("C", cache.get(3))
    }

    @Test
    fun `test eviction when capacity is exceeded`() {
        val cache = LRUCache<Int, String>(2)
        cache.set(1, "A")
        cache.set(2, "B")
        cache.set(3, "C")

        assertNull(cache.get(1)) // "A" should have been evicted
        assertEquals("B", cache.get(2))
        assertEquals("C", cache.get(3))
    }

    @Test
    fun `test updating existing key keeps it in cache`() {
        val cache = LRUCache<Int, String>(2)
        cache.set(1, "A")
        cache.set(2, "B")
        cache.set(1, "Updated-A")

        assertEquals("Updated-A", cache.get(1))
        assertEquals("B", cache.get(2))
    }

    @Test
    fun `test most recently used element stays in cache`() {
        val cache = LRUCache<Int, String>(3)
        cache.set(1, "A")
        cache.set(2, "B")
        cache.set(3, "C")
        cache.get(1) // Accessing 1 to make it most recently used
        cache.set(4, "D")

        assertNotNull(cache.get(1)) // "A" should still be in cache
        assertNull(cache.get(2)) // "B" should have been evicted
        assertEquals("C", cache.get(3))
        assertEquals("D", cache.get(4))
    }

    @Test
    fun `test eviction of least recently used element`() {
        val cache = LRUCache<Int, String>(3)
        cache.set(1, "A")
        cache.set(2, "B")
        cache.set(3, "C")
        cache.get(1) // Access 1 to make it most recently used
        cache.get(2) // Access 2 to make it more recently used than 3
        cache.set(4, "D")

        assertEquals("A", cache.get(1))
        assertEquals("B", cache.get(2))
        assertNull(cache.get(3)) // "C" should be evicted
        assertEquals("D", cache.get(4))
    }

    @Test
    fun `test get returns null for missing keys`() {
        val cache = LRUCache<Int, String>(2)
        cache.set(1, "A")
        cache.set(2, "B")

        assertNull(cache.get(3))
        assertNull(cache.get(4))
    }

    @Test
    fun `test overwriting existing key`() {
        val cache = LRUCache<Int, String>(2)
        cache.set(1, "A")
        cache.set(2, "B")
        cache.set(1, "Updated-A")

        assertEquals("Updated-A", cache.get(1))
        assertEquals("B", cache.get(2))
    }
}