package ed.lab.ed1final.controller;

import ed.lab.ed1final.service.TrieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/trie")
public class TrieController {
    private final TrieService trieService;

    public TrieController(TrieService trieService) {
        this.trieService = trieService;
    }

    @PostMapping("/{word}")
    public ResponseEntity<Void> insertWord(@PathVariable String word) {
        try {
            trieService.insert(word);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{word}/count")
    public ResponseEntity<Map<String, Object>> countWord(@PathVariable String word) {
        int count = trieService.countWordsEqualTo(word);
        return ResponseEntity.ok(Map.of(
                "word", word,
                "wordsEqualTo", count
        ));
    }

    @GetMapping("/{prefix}/prefix")
    public ResponseEntity<Map<String, Object>> countPrefix(@PathVariable String prefix) {
        int count = trieService.countWordsStartingWith(prefix);
        return ResponseEntity.ok(Map.of(
                "prefix", prefix,
                "wordsStartingWith", count
        ));
    }

    @DeleteMapping("/{word}")
    public ResponseEntity<Void> deleteWord(@PathVariable String word) {
        try {
            trieService.erase(word);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
