package cedream.server.dto;

public class WordDto extends EntityDto<Integer> {

    private String text;

    public WordDto(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
