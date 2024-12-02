import java.util.HashMap;
import java.util.Map;

public class Trie {

    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
    }

    private static class TrieNode {
        private final Map<Character, TrieNode> children = new HashMap<>();
    }
}
