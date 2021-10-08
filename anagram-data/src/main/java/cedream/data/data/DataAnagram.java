package cedream.data.data;

import cedream.data.words.Word;
import cedream.data.users.User;

/**
 *
 * @author Cedric Thonus 49739
 */
public class DataAnagram implements Data {

    private final Word word;
    private final Type type;
    private final User user;
    
    
    public DataAnagram(String word, Type type, User user, int nbPropositions) {
        this.word = new Word(word, nbPropositions);
        this.type = type;
        this.user = user;
    }
    
    public DataAnagram(String word, Type type, User user) {
        this.word = new Word(word, 0);
        this.type = type;
        this.user = user;
    }
    
    @Override
    public Type getType() {
        return type;
    }

    @Override
    public User getAuthor() {
        return user;
    }
    
    @Override
    public Object getContent() {
        return word;
    }
    
}
