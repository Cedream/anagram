package cedream.server;

import cedream.data.data.Data;
import cedream.data.data.DataAnagram;
import cedream.data.data.DataUser;
import cedream.data.words.Word;
import cedream.server.server.AnagramServer;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cedric Thonus
 */
public class AnagramServerConsole implements Observer {

    private final AnagramServer model;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            AnagramServer model = new AnagramServer();
            AnagramServerConsole console = new AnagramServerConsole(model);
            model.addObserver(console);
            System.out.println("Server started");
            System.out.println("");
        } catch (IOException ex) {
            Logger.getLogger(AnagramServerConsole.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    public AnagramServerConsole(AnagramServer model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            
            if (arg instanceof DataUser) {
                DataUser user = (DataUser) arg;
                System.out.println(user.getAuthor().getName() + " s'est connecté au serveur ");
            }
            
            if (arg instanceof DataAnagram) {
                DataAnagram anagram = (DataAnagram) arg;
                switch (anagram.getType()) {
                    case PROPOSITION:
                        System.out.println(anagram.getAuthor().getName() 
                                + " a fait la proposition suivante : " 
                                + anagram.getContent().toString());
                        break;
                    default:
                }
            }
            
        }

    }

}
