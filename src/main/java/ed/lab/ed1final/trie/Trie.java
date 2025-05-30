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



/*

## 🧩 Paso 3: Implementar el Trie

### 📄 TrieNode.java

```java
package com.example.trie.service;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    int wordCount = 0;
    int prefixCount = 0;
}
```

### 📄 Trie.java

```java
package com.example.trie.service;

import org.springframework.stereotype.Component;

@Component
public class Trie {
    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.computeIfAbsent(ch, c -> new TrieNode());
            node.prefixCount++;
        }
        node.wordCount++;
    }

    public int countWordsEqualTo(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) return 0;
        }
        return node.wordCount;
    }

    public int countWordsStartingWith(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) return 0;
        }
        return node.prefixCount;
    }

    public boolean erase(String word) {
        if (countWordsEqualTo(word) == 0) return false;
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            TrieNode next = node.children.get(ch);
            next.prefixCount--;
            node = next;
        }
        node.wordCount--;
        return true;
    }
}
```

---

## 🧩 Paso 4: Crear los DTOs de respuesta

### 📄 WordCountResponse.java

```java
package com.example.trie.dto;

public class WordCountResponse {
    private final String word;
    private final int wordsEqualTo;

    public WordCountResponse(String word, int wordsEqualTo) {
        this.word = word;
        this.wordsEqualTo = wordsEqualTo;
    }

    public String getWord() {
        return word;
    }

    public int getWordsEqualTo() {
        return wordsEqualTo;
    }
}
```

### 📄 PrefixCountResponse.java

```java
package com.example.trie.dto;

public class PrefixCountResponse {
    private final String prefix;
    private final int wordsStartingWith;

    public PrefixCountResponse(String prefix, int wordsStartingWith) {
        this.prefix = prefix;
        this.wordsStartingWith = wordsStartingWith;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getWordsStartingWith() {
        return wordsStartingWith;
    }
}
```

---

## 🧩 Paso 5: Implementar el controlador

### 📄 TrieController.java

```java
package com.example.trie.controller;

import com.example.trie.dto.PrefixCountResponse;
import com.example.trie.dto.WordCountResponse;
import com.example.trie.service.Trie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trie")
public class TrieController {

    private final Trie trie;

    public TrieController(Trie trie) {
        this.trie = trie;
    }

    private boolean isValid(String word) {
        return word != null && !word.isEmpty() && word.matches("[a-z]+");
    }

    @PostMapping("/{word}")
    public ResponseEntity<Void> insert(@PathVariable String word) {
        if (!isValid(word)) return ResponseEntity.badRequest().build();
        trie.insert(word);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{word}/count")
    public ResponseEntity<WordCountResponse> countWordsEqualTo(@PathVariable String word) {
        int count = trie.countWordsEqualTo(word);
        return ResponseEntity.ok(new WordCountResponse(word, count));
    }

    @GetMapping("/{prefix}/prefix")
    public ResponseEntity<PrefixCountResponse> countWordsStartingWith(@PathVariable String prefix) {
        int count = trie.countWordsStartingWith(prefix);
        return ResponseEntity.ok(new PrefixCountResponse(prefix, count));
    }

    @DeleteMapping("/{word}")
    public ResponseEntity<Void> erase(@PathVariable String word) {
        if (!trie.erase(word)) return ResponseEntity.badRequest().build();
        return ResponseEntity.noContent().build();
    }
}
```

---

## 🧩 Paso 6: Verifica que tu archivo `pom.xml` tenga las dependencias necesarias

### 📄 pom.xml (fragmento esencial)

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 🧩 Paso 7: Ejecuta el proyecto

Desde la raíz del proyecto, usa:

```bash
./mvnw spring-boot:run
```

O desde tu IDE (como IntelliJ o Eclipse), ejecuta la clase `TrieApplication`.

---

## 🧩 Paso 8: Corre los tests

Tu clase de pruebas `E02TrieControllerTest` debe ejecutarse correctamente con todos los casos pasando si seguiste los pasos.

---

¿Quieres que te envíe todo esto en un archivo `.zip` ya estructurado? ¿O deseas agregar más validaciones o funciones?

*/
