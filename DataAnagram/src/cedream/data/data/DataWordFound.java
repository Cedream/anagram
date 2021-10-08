package cedream.data.data;

import cedream.data.words.Word;
import cedream.data.users.User;

public class DataWordFound implements Data {
    
    private final User user;
    private final Word word;

    public DataWordFound(User user, String word) {
        this.user = user;
        this.word = new Word(word, 0);
    }
    
    @Override
    public Type getType() {
        return Type.WORDFOUND;
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
