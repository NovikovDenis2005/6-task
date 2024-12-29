import java.util.*;

public class HashMapExample<K, V> implements Map<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private List<Entry<K, V>>[] table;
    private int size;

    public HashMapExample() {
        table = new List[INITIAL_CAPACITY];
        size = 0;
    }

    private int hash(Object key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % table.length);
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = hash(key);
        List<Entry<K, V>> bucket = table[index];
        if (bucket == null) return false;

        for (Entry<K, V> entry : bucket) {
            if (Objects.equals(entry.getKey(), key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (List<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    if (Objects.equals(entry.getValue(), value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int index = hash(key);
        List<Entry<K, V>> bucket = table[index];
        if (bucket == null) return null;

        for (Entry<K, V> entry : bucket) {
            if (Objects.equals(entry.getKey(), key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        for (Entry<K, V> entry : table[index]) {
            if (Objects.equals(entry.getKey(), key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }

        table[index].add(new AbstractMap.SimpleEntry<>(key, value));
        size++;
        if (size > table.length * LOAD_FACTOR) {
            resize();
        }
        return null;
    }

    private void resize() {
        List<Entry<K, V>>[] oldTable = table;
        table = new List[oldTable.length * 2];
        size = 0;

        for (List<Entry<K, V>> bucket : oldTable) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    @Override
    public V remove(Object key) {
        int index = hash(key);
        List<Entry<K, V>> bucket = table[index];
        if (bucket == null) return null;

        Iterator<Entry<K, V>> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (Objects.equals(entry.getKey(), key)) {
                V oldValue = entry.getValue();
                iterator.remove();
                size--;
                return oldValue;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (List<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    keys.add(entry.getKey());
                }
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (List<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    values.add(entry.getValue());
                }
            }
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet<>();
        for (List<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                entries.addAll(bucket);
            }
        }
        return entries;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }
}


