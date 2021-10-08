package cedream.data.users;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Members implements Iterable<User>, Serializable {

    private final List<User> users;

    public Members() {
        users = new ArrayList<>();
    }

    public int add(int id, String name, int nbWordsFound ,InetAddress address) {
        User user = new User(id, name, nbWordsFound ,address);
        add(user);
        return user.getId();
    }

    public void add(User user) {
        users.add(user);
    }

    public void remove(int id) {
        Iterator<User> it = users.iterator();
        boolean find = false;
        User current = null;
        while (it.hasNext() && !find) {
            current = it.next();
            find = current.is(id);
        }
        if (find) {
            users.remove(current);
        }
    }

    public int size() {
        return users.size();
    }

    public void clear() {
        users.clear();
    }

    @Override
    public Iterator<User> iterator() {
        return users.iterator();
    }

    public void changeName(String name, int id) {
        User user = getUser(id);
        if (user != null) {
            user.setName(name);
        }
    }
    
    public void changeNbWordsFound(int nbWordsFound, int id) {
        User user = getUser(id);
        if (user != null) {
            user.setNbWordsFound(nbWordsFound);
        }
    }

    public User getUser(int id) {
        Iterator<User> it = users.iterator();
        boolean find = false;
        User current = null;
        while (it.hasNext() && !find) {
            current = it.next();
            find = current.is(id);
        }
        return find ? current : null;
    }
}
