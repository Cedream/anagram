package cedream.server.model;

import cedream.server.exception.ModelException;

public interface Facade {

    void initialize() throws ModelException;

    void start() throws ModelException;
    
    public boolean canHaveAIndice();
    
    public void useIndice();
    
    public String getIndiceOfTheCurrentWord() throws ModelException;

    String nextWord() throws ModelException;

    boolean propose(String proposal) throws ModelException;

    String pass() throws ModelException;

    boolean isOver() throws ModelException;

    boolean canAskNextWord();

    int getNbWords() throws ModelException;

    int getNbRemainingWords() throws ModelException;

    int getNbSolvedWords() throws ModelException;

    int getNbUnsolvedWords() throws ModelException;

    int getNbProposal() throws ModelException;
}
