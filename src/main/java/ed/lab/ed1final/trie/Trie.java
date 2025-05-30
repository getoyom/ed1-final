package ed.lab.ed1final.trie;

import org.springframework.stereotype.Component;

@Component
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    int wordCount = 0;
    int prefixCount = 0;
}


public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            if (node.children[idx] == null)
                node.children[idx] = new TrieNode();
            node = node.children[idx];
            node.prefixCount++;
        }
        node.wordCount++;
    }

    public int countWordsEqualTo(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            if (node.children[idx] == null)
                return 0;
            node = node.children[idx];
        }
        return node.wordCount;
    }

    public int countWordsStartingWith(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            int idx = ch - 'a';
            if (node.children[idx] == null)
                return 0;
            node = node.children[idx];
        }
        return node.prefixCount;
    }

    public void erase(String word) {
        if (countWordsEqualTo(word) == 0) return;

        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            node = node.children[idx];
            node.prefixCount--;
        }
        node.wordCount--;
    }
}


