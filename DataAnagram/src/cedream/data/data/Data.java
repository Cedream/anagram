package cedream.data.data;

import cedream.data.users.User;
import java.io.Serializable;

/**
 *
 * @author Cedric Thonus
 */
public interface Data extends Serializable {
    
    Type getType();
    
    User getAuthor();
    
    Object getContent();
    
}
