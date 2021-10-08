package cedream.data.data;

import cedream.data.users.Members;
import cedream.data.users.User;

public class DataMembers implements Data {

    private final Members members;

    public DataMembers(Members members) {
        this.members = members;
    }

    @Override
    public User getAuthor() {
        return User.ADMIN;
    }

    @Override
    public Type getType() {
        return Type.MEMBERS;
    }

    @Override
    public Object getContent() {
        return members;
    }

}
