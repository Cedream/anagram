package cedream.data.words;

import java.io.Serializable;

public class Word implements Serializable {

    private String word;
    private Integer nbPropositions;


    public Word(String word, int nbPropositions) {
        this.word = word;
        this.nbPropositions = nbPropositions;

    }

    public String getWord() {
        return word;
    }
    
    public Integer getNbPropositons() {
        return nbPropositions;
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
        return this.word.equals(obj);
    }

    void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return word;
    }

}
