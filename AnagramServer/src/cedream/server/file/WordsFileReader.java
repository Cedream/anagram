package cedream.server.file;

import cedream.server.dto.WordDto;
import cedream.server.exception.FileException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class WordsFileReader {

    public static final String DEFAULT_READ_URL_FILE = "resources/data/words.txt";

    public static List<WordDto> readFile(String url) throws FileException {
        List<WordDto> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(url), "UTF-8"))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                WordDto wordDto = new WordDto(index, line);
                result.add(wordDto);
                index++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordsFileReader.class.getName()).log(Level.SEVERE, "Read file " + url, ex);
            throw new FileException(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(WordsFileReader.class.getName()).log(Level.SEVERE, "Read file " + url, ex);
            throw new FileException(ex.getMessage());
        }
        return result;
    }

    private WordsFileReader() {

    }
}
