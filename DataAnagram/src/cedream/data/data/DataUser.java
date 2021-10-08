package cedream.data.data;

import cedream.data.users.User;

public class DataUser implements Data {

    private final User author;
    
    public DataUser(int id, String username, int nbWordsFound) {
        author = new User(id, username, nbWordsFound);
    }
    
    public DataUser(int id, String username) {
        this(id, username, 0);
    }
    
    @Override
    public Type getType() {
        return Type.USER;
    }

    @Override
    public User getAuthor() {
        return author;
    }
    
    @Override
    public Object getContent() {
        return author;
    }
    
}
