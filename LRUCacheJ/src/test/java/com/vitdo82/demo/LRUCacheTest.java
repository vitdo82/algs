package com.vitdo82.demo;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    @Test
    void testPutAddsNewElementToCache() {
        LRUCache<String, String> cache = new LRUCache<>(3);
        boolean result = cache.put("key1", "value1");

        assertTrue(result, "Put operation should return true");
        assertTrue(cache.containsKey("key1"), "Cache should contain the newly added key");
        assertEquals(1, cache.size(), "Cache size should increase after adding an element");
    }

    @Test
    void testPutReplacesExistingKey() {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.put("key1", "value1");
        boolean result = cache.put("key1", "newValue1");

        assertTrue(result, "Put operation should return true when replacing an existing key");
        assertTrue(cache.containsKey("key1"), "Cache should still contain the updated key");
        assertEquals(Optional.of("newValue1"), cache.get("key1"), "Value associated with key should be updated");
        assertEquals(1, cache.size(), "Cache size should remain unchanged when replacing an existing key");
    }

    @Test
    void testPutRemovesLeastRecentlyUsedWhenCacheSizeIsExceeded() {
        LRUCache<String, String> cache = new LRUCache<>(2);

        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3"); // Exceeds capacity, "key1" should be removed

        assertFalse(cache.containsKey("key1"), "Least recently used key should be removed when capacity is exceeded");
        assertTrue(cache.containsKey("key2"), "Cache should still contain key2");
        assertTrue(cache.containsKey("key3"), "Cache should still contain key3");
        assertEquals(2, cache.size(), "Cache size should not exceed its maximum capacity");
    }

    @Test
    void testPutMovesExistingKeyToMostRecentlyUsed() {
        LRUCache<String, String> cache = new LRUCache<>(3);

        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.get("key1"); // Access key1 to make it most recently used
        cache.put("key3", "value3");
        cache.put("key4", "value4"); // Exceeds capacity, key2 (least recently used) should be removed

        assertFalse(cache.containsKey("key2"), "Least recently used key should be removed when a new key is added");
        assertTrue(cache.containsKey("key1"), "Key1 should still exist as it was accessed recently");
        assertTrue(cache.containsKey("key3"), "Cache should still contain key3");
        assertTrue(cache.containsKey("key4"), "Cache should contain the newly added key4");
    }

}