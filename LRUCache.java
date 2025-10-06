import java.util.HashMap;
import java.util.Map;

// Generic Node class for the doubly linked list
class Node<K, V> {
    K key;
    V value;
    Node<K, V> prev;
    Node<K, V> next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

// The LRU Cache implementation
public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final Node<K, V> head; // Dummy head
    private final Node<K, V> tail; // Dummy tail

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node<>(null, null); // Dummy node
        this.tail = new Node<>(null, null); // Dummy node
        head.next = tail;
        tail.prev = head;
    }

    // Helper to remove a node from the list
    private void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Helper to add a node to the front (most recently used)
    private void addToFront(Node<K, V> node) {
        node.next = head.next;
        node.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    public V get(K key) {
        if (cache.containsKey(key)) {
            Node<K, V> node = cache.get(key);
            // Move the accessed node to the front
            remove(node);
            addToFront(node);
            return node.value;
        }
        return null; // Not found
    }

    public void put(K key, V value) {
        // If key exists, update value and move to front
        if (cache.containsKey(key)) {
            Node<K, V> node = cache.get(key);
            node.value = value;
            remove(node);
            addToFront(node);
        } else {
            // If at capacity, evict the least recently used item (the one before tail)
            if (cache.size() >= capacity) {
                Node<K, V> lruNode = tail.prev;
                remove(lruNode);
                cache.remove(lruNode.key);
            }
            // Add the new node
            Node<K, V> newNode = new Node<>(key, value);
            cache.put(key, newNode);
            addToFront(newNode);
        }
    }
}