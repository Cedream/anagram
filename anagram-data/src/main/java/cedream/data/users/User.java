package cedream.data.users;

import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {

    public static final User ADMIN = new User(0, "ADMIN");
    private final int id;
    private Integer nbWordsFound;
    private String name;
    private InetAddress address;

    public User(int id, String name, int nbWordsFound, InetAddress address) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.nbWordsFound = nbWordsFound;
    }
    
    public User(int id, String name, int nbWordsFound) {
        this(id, name, nbWordsFound, null);
    }

    public User(int id, String name) {
        this(id, name, 0, null);
    }

    public String getName() {
        return name;
    }
    
    public int getNbWordsFound() {
        return nbWordsFound;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
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
        final User other = (User) obj;
        return this.id == other.id;
    }

    boolean is(int id) {
        return this.id == id;
    }

    public int getId() {
        return id;
    }

    void setName(String name) {
        this.name = name;
    }
    
    void setNbWordsFound(int nbWordsFound) {
        this.nbWordsFound = nbWordsFound;
    }

    public String getAddress() {
        return address.getHostAddress();
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

}
