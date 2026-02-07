package algorithm.adt;

public class Element<U extends Comparable<U>, V> implements Comparable<Element<U, V>> {

    private U key;
    private V value;

    public Element(U key, V value) {
        this.key = key;
        this.value = value;
    }

    public U getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(U key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public int compareTo(Element<U, V> o) {
        return this.key.compareTo(o.key);
    }

}
