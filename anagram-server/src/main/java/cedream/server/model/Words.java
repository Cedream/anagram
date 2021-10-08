package cedream.server.model;

import cedream.server.exception.ModelException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

class Words {

    private final List<Word> words;
    private Iterator<Word> iterator;
    private boolean full;

    Words() {
        words = new ArrayList<>();
        full = false;
    }

    Words(List<Word> otherList) {
        words = new ArrayList<>(otherList);
        full = true;
    }

    void add(Word word) throws ModelException {
        if (full) {
            throw new ModelException("La liste des mots est complète");
        }
        Objects.requireNonNull(word, "Aucun mot donné en paramètre");
        words.add(word);
    }

    void shuffle() throws ModelException {
        if (!full) {
            throw new ModelException("La liste des mots n'est pas complète");
        }
        Collections.shuffle(words);
        iterator = words.iterator();
    }

    public boolean isFull() {
        return full;
    }

    void setFull(boolean full) {
        this.full = full;
    }

    Word next() throws ModelException {
        if (!full) {
            throw new ModelException("La liste des mots n'est pas complète");
        }
        return iterator.next();
    }

    boolean hasNext() throws ModelException {
        if (!full) {
            throw new ModelException("La liste des mots n'est pas complète");
        }
        return iterator.hasNext();
    }

    boolean isEmpty() {
        return words.isEmpty();
    }

    int size() {
        return words.size();
    }

    int getNbRemainingWords() {
        return (int) words.stream()
                .filter(Word::isUnread)
                .count();
    }

    int getNbSolvedWords() {
        return (int) words.stream()
                .filter(Word::isSolved)
                .count();
    }

    int getNbUnsolvedWords() {
        return (int) words.stream()
                .filter(Word::isUnSolved)
                .count();
    }
}
