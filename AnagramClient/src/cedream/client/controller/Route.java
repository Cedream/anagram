package cedream.client.controller;

/**
 * Type of routes.
 * @author Cedric Thonus
 */
public enum Route {
    
    START("S'authentifier"),
    GAME("Le jeu Anagram"),
    END("Fin de partie");
    
    private String title;
    
    private Route(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    
}
