package cedream.client.model;

import cedream.data.data.Data;
import cedream.data.data.DataUser;
import cedream.data.data.Type;
import cedream.client.client.AbstractClient;
import cedream.data.users.Members;
import cedream.data.users.User;
import java.io.IOException;

/**
 * The AnagramClient contains all the methods necessary to set up a
 * Anagram client.
 * 
 * @author Cedric Thonus
 */
public class AnagramClient extends AbstractClient {
    
    private Members members;
    private User mySelf;
    
    /**
     * 
     * @param username username of client.
     * @param ip ip of anagram server.
     * @param port port of anagram server.
     * @throws IOException 
     */
    public AnagramClient(String username, String ip, int port) throws IOException {
        super(ip, port);
        openConnection();
        updateUsername(username);
        members = new Members();
    }
    
    /**
     * get object User of client.
     * @return user object.
     */
    public User getMySelf() {
        return mySelf;
    }
    
    /**
     * Get connected members.
     * @return Members object.
     */
    public Members getMembers() {
        return members;
    }
    
    /**
     * update username of client.
     * @param username username of client.
     * @throws IOException 
     */
    private void updateUsername(String username) throws IOException {
        sendToServer(new DataUser(0, username));
    }

    /**
     * notify changes to observers.
     */
    private void notifyChange() {
        setChanged();
        notifyObservers();
    }
    
    /**
     * closeConnection();
     * @throws IOException 
     */
    public void quit() throws IOException {
        closeConnection();
    }

    @Override
    protected void handleMessageFromServer(Object obj) {
        Data data = (Data) obj;
        Type type = data.getType();
        switch (type) {
            case USER:
                mySelf = data.getAuthor();
                break;
            case WORD:
                break;
            case WORDFOUND:
                break;
            case PASS:
                break;
            case REVEALWORD:
                break;
            case PROPOSITION:
                break;
            case BADWORD:
                break;
            case INDICE:
                break;
            case MEMBERS:
                Members members = (Members) data.getContent();
                updateMembers(members);
            break;
            case ENDGAME:
                break;
            default:
                throw new IllegalArgumentException("Data type unknown " + type);
        }
        setChanged();
        notifyObservers(data);
    }
    
    /**
     * update members with a new object Members.
     * @param members new Members object contains conntected members.
     */
    void updateMembers(Members members) {
        this.members.clear();
        for (User member : members) {
            this.members.add(member);
        }
        notifyChange();
    }
    
}
