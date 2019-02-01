package domain;

/**
 * Created by Ragos Bros on 2/14/2017.
 */
public class StandardInfo {

    private final String header;
    private final String textContent;

    public StandardInfo(String header, String textContent) {
        this.header = header;
        this.textContent = textContent;
    }

    public String getHeader() {
        return header;
    }

    public String getTextContent() {
        return textContent;
    }

}
