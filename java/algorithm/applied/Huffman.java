package algorithm.applied;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Huffman {
    
    public static class CharacterNode implements Comparable<CharacterNode> {
        public char c;
        public int freq;
        public CharacterNode left;
        public CharacterNode right;
        
        public CharacterNode(char c, int freq) {
            this.c = c;
            this.freq = freq;
        }
        
        public CharacterNode(int freq, CharacterNode left, CharacterNode right) {
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        
        @Override
        public int compareTo(CharacterNode o) {
            return freq - o.freq;
        }
    }
    
    public static CharacterNode huffman(Set<CharacterNode> C) {
        int n = C.size();
        Queue<CharacterNode> pq = new PriorityQueue<>(C);
        for (int i = 0; i < n - 1; i++) {
            CharacterNode x = pq.poll();
            CharacterNode y = pq.poll();
            CharacterNode z = new CharacterNode(x.freq + y.freq, x, y);
            pq.add(z);
        }
        return pq.poll();
    }

    public static void printHuffman(CharacterNode root) {
        printHuffman(root, "");
    }

    private static void printHuffman(CharacterNode node, String s) {
        if (node.left == null && node.right == null) {
            System.out.println(node.c + ": " + s);
            return;
        }
        printHuffman(node.left, s + "0");
        printHuffman(node.right, s + "1");
    }
    
    public static void main(String[] args) {
        Set<CharacterNode> C = Set.of(
            new CharacterNode('a', 45), 
            new CharacterNode('b', 13), 
            new CharacterNode('c', 12), 
            new CharacterNode('d', 16), 
            new CharacterNode('e', 9), 
            new CharacterNode('f', 5));
        CharacterNode root = huffman(C);
        printHuffman(root);
    }

}
