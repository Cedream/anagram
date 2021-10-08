package cedream.server.model;

import cedream.server.exception.ModelException;
import static cedream.server.model.Status.READ;
import static cedream.server.model.Status.SOLVED;
import static cedream.server.model.Status.UNREAD;
import static cedream.server.model.Status.UNSOLVED;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Word {

    private final String text;
    private Status status;
    private int nbProposal;

    public Word(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Aucun texte pour le mot");
        }
        this.text = text;
        this.status = UNREAD;
        this.nbProposal = 0;
    }

    String getScramble() {
        List<String> chars = Arrays.asList(text.split(""));
        Collections.shuffle(chars);
        StringBuilder sb = new StringBuilder();
        chars.forEach(c -> sb.append(c));
        return sb.toString();
    }

    boolean isAnagram(String other) throws ModelException {
        if (other == null || other.isEmpty()) {
            throw new ModelException("Aucun texte a comparer");
        }
        return other.equalsIgnoreCase(text);
    }

    String getText() {
        return text;
    }

    void setRead() throws ModelException {
        if (status != UNREAD) {
            throw new ModelException("Le status ne peut pas etre changé en READ");
        }
        status = READ;
    }

    void solved() throws ModelException {
        if (status != READ) {
            throw new ModelException("Le status ne peut pas etre changé en SOLVED");
        }
        status = SOLVED;
    }

    void unsolved() throws ModelException {
        if (status != READ) {
            throw new ModelException("Le status ne peut pas etre changé en UNSOLVED");
        }
        status = UNSOLVED;
    }

    boolean isUnread() {
        return status == UNREAD;
    }

    boolean isSolved() {
        return status == SOLVED;
    }

    boolean isUnSolved() {
        return status == UNSOLVED;
    }

    void addProposal() {
        nbProposal++;
    }

    int getNbProposal() {
        return nbProposal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.text);
        hash = 37 * hash + Objects.hashCode(this.status);
        hash = 37 * hash + this.nbProposal;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Word other = (Word) obj;
        if (this.nbProposal != other.nbProposal) {
            return false;
        }
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        return this.status == other.status;
    }

    @Override
    public String toString() {
        return "Word " + text + " , " + nbProposal + " , " + status;
    }

}
