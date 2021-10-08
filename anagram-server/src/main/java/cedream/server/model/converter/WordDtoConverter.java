package cedream.server.model.converter;

import cedream.server.dto.WordDto;
import cedream.server.model.Word;

public class WordDtoConverter implements GenericConverter<WordDto, Word> {

    @Override
    public Word apply(WordDto input) {
        Word output = new Word(input.getText());
        return output;
    }

}
