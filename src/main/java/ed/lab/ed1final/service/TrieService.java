package ed.lab.ed1final.service;

import ed.lab.ed1final.trie.Trie;
import org.springframework.stereotype.Service;

@Service
public class TrieService {
    private final Trie trie = new Trie();

    public void insert(String word) {
        if (!isValid(word)) throw new IllegalArgumentException("Palabra inválida");
        trie.insert(word);
    }

    public int countWordsEqualTo(String word) {
        return trie.countWordsEqualTo(word);
    }

    public int countWordsStartingWith(String prefix) {
        return trie.countWordsStartingWith(prefix);
    }

    public void erase(String word) {
        if (trie.countWordsEqualTo(word) == 0)
            throw new IllegalArgumentException("La palabra no existe");
        trie.erase(word);
    }

    private boolean isValid(String word) {
        return word != null && !word.isEmpty() && word.matches("^[a-z]+$");
    }
}
