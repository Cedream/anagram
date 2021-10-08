package cedream.server.business;

import cedream.server.dto.WordDto;
import cedream.server.exception.BusinessException;
import cedream.server.exception.FileException;
import cedream.server.file.WordsFileReader;
import static cedream.server.file.WordsFileReader.DEFAULT_READ_URL_FILE;
import java.io.File;

import java.net.URL;
import java.util.List;

public final class AdminFacade {

    public static List<WordDto> getAllWords() throws BusinessException {
        try {
            return WordsFileReader.readFile(DEFAULT_READ_URL_FILE);
        } catch (FileException eData) {
            String msg = eData.getMessage();
            try {
                msg = eData.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Liste des mots inaccessible! \n" + msg);
            }
        }

    }
    
    private AdminFacade() {

    }
}
