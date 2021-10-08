package cedream.server.server;

import cedream.data.data.Data;
import cedream.data.data.DataAnagram;
import cedream.data.data.DataMembers;
import cedream.data.data.DataUser;
import cedream.data.data.DataWordFound;
import cedream.data.data.Type;
import cedream.data.users.Members;
import cedream.data.users.User;
import cedream.server.exception.ModelException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The <code> AnagramServer </code> contains all the methods necessary to set up
 * a Anagram server.
 */
public class AnagramServer extends AbstractServer {

    private static final int PORT = 12345;
    static final String ID_MAPINFO = "ID";

    private static InetAddress getLocalAddress() {
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while (b.hasMoreElements()) {
                for (InterfaceAddress f : b.nextElement().getInterfaceAddresses()) {
                    if (f.getAddress().isSiteLocalAddress()) {
                        return f.getAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Logger.getLogger(AnagramServer.class.getName()).log(Level.SEVERE, "NetworkInterface error", e);
        }
        return null;
    }

    private int clientId;

    private final Members members;

    /**
     * Constructs the server. Build a thread to listen connection request.
     *
     * @throws IOException if an I/O error occurs when creating the server
     * socket.
     */
    public AnagramServer() throws IOException {
        super(PORT);
        members = new Members();
        clientId = 0;
        this.listen();
    }

    /**
     * Return the list of connected users.
     *
     * @return the list of connected users.
     */
    /*public Members getMembers() {
        return members;
    }*/
    /**
     * Return the server IP address.
     *
     * @return the server IP address.
     */
    public String getIP() {
        if (getLocalAddress() == null) {
            return "Unknown";
        }
        return getLocalAddress().getHostAddress();
    }

    /**
     * Return the number of connected users.
     *
     * @return the number of connected users.
     */
    public int getNbConnected() {
        return getNumberOfClients();
    }

    /**
     * Quits the server and closes all aspects of the connection to clients.
     *
     * @throws IOException
     */
    public void quit() throws IOException {
        this.stopListening();
        this.close();
    }

    /**
     * Return the next client id.
     *
     * @return the next client id.
     */
    final synchronized int getNextId() {
        clientId++;
        return clientId;
    }

    @Override
    protected void handleMessageFromClient(Object obj, ConnectionToClient client) {
        Data data = (Data) obj;
        Type type = data.getType();
        switch (type) {
            case USER:
                doUserType(data, client);
                break;
            case PROPOSITION:
                doPropositionType(data, client);
                break;
            case PASS:
                doPassType(data, client);
                break;
            case BADWORD:
                break;
            case ASKINDICE:
                doAskIndiceType(data, client);
                break;
            case MEMBERS:
                break;
            default:
                throw new IllegalArgumentException("Message type unknown " + type);
        }
        setChanged();
        notifyObservers(data);
    }
    
    private void doAskIndiceType(Data data, ConnectionToClient client) {
        try {
            String indice = client.getModel().getIndiceOfTheCurrentWord();
            client.getModel().useIndice();
            DataAnagram dataAnagram = new DataAnagram(indice, Type.INDICE, User.ADMIN);
            sendToClient(dataAnagram, client);
        } catch (ModelException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Send to client the himself, the members and the anagram and send to all
     * clients the members.
     *
     * @param data
     * @param client
     */
    private void doUserType(Data data, ConnectionToClient client) {
        try {
            if (data.getAuthor().getId() != 0) {
                return;
            }
            int memberId = (int) client.getInfo(ID_MAPINFO);
            User author = data.getAuthor();
            members.changeName(author.getName(), memberId);
            Data dataName = new DataUser(memberId, author.getName(),
                    client.getModel().getNbSolvedWords());
            sendToClient(dataName, client);
            sendToClient(new DataAnagram(client.getScrambledWord(),
                    Type.WORD, User.ADMIN), client);
            sendToAllClients(new DataMembers(members));
        } catch (ModelException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Check the proposition and send a reply to the customer whether or not the
     * proposal was good.
     *
     * @param data
     * @param client
     */
    private void doPropositionType(Data data, ConnectionToClient client) {
        try {
            boolean found = client.getModel().propose((data.getContent().toString()));
            if (!found) {
                sendToClient(
                        new DataAnagram(null, Type.BADWORD, User.ADMIN,
                                client.getModel().getNbProposal()), client);
                if (client.getModel().isOver()) {
                    sendToClient(new DataAnagram(null,
                            Type.ENDGAME, User.ADMIN), client);
                }
            } else {
                //Update user
                
                Data dataUser = new DataUser(data.getAuthor().getId(), data.getAuthor().getName(),
                        client.getModel().getNbSolvedWords());
                sendToClient(dataUser, client);
                
                //Update members
                members.changeNbWordsFound(client.getModel().getNbSolvedWords(),
                        data.getAuthor().getId());
                sendToAllClients(new DataMembers(members));
                
                //Send to all clients a word found by a user
                String withIndice = client.getModel().canHaveAIndice() ? "" : "*";
                sendToAllClients(new DataWordFound(dataUser.getAuthor(),
                        data.getContent().toString()+ withIndice));
                
                //Send new word
                if (client.getModel().isOver()) {
                    sendToClient(new DataAnagram(null,
                            Type.ENDGAME, User.ADMIN), client);
                } else {
                    client.setScrambledWord(client.getModel().nextWord());
                    sendToClient(new DataAnagram(client.getScrambledWord(),
                            Type.WORD, User.ADMIN), client);
                }

            }
        } catch (ModelException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Send the answer and send a new anagram
     *
     * @param data
     * @param client
     */
    private void doPassType(Data data, ConnectionToClient client) {
        try {
            String word = client.getModel().pass();
            sendToClient(new DataAnagram(word, Type.REVEALWORD, User.ADMIN), client);
            if (client.getModel().isOver()) {
                sendToClient(new DataAnagram(null,
                        Type.ENDGAME, User.ADMIN), client);
            } else {
                client.setScrambledWord(client.getModel().nextWord());
                sendToClient(new DataAnagram(client.getScrambledWord(),
                        Type.WORD, User.ADMIN), client);
            }
        } catch (ModelException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    protected void clientConnected(ConnectionToClient client) {
        try {
            super.clientConnected(client);
            int memberId = members.add(getNextId(), client.getName(),
                    client.getModel().getNbSolvedWords(), client.getInetAddress());
            client.setInfo(ID_MAPINFO, memberId);
            sendToClient(new DataAnagram(client.getScrambledWord(),
                    Type.WORD, User.ADMIN), client);
            setChanged();
            notifyObservers();
        } catch (ModelException ex) {
            Logger.getLogger(AnagramServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        members.remove((int) client.getInfo(ID_MAPINFO));
        sendToAllClients(new DataMembers(members));
    }

    @Override
    protected synchronized void clientException(ConnectionToClient client, Throwable exception) {
        super.clientException(client, exception);
        try {
            if (client.isConnected()) {
                client.sendToClient(new IllegalArgumentException("Message illisible " + exception.getMessage()));
            }
        } catch (IOException ex) {
            Logger.getLogger(AnagramServer.class.getName()).log(Level.SEVERE, "Impossible d envoyer erreur au client", ex);
        }
    }

    void sendToClient(Data data, ConnectionToClient client) {
        try {
            client.sendToClient(data);
        } catch (IOException ex) {
            Logger.getLogger(AnagramServer.class.getName()).log(Level.SEVERE, "Impossible d envoyer au client", ex);
        }
    }

}
