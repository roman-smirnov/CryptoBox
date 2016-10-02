package roman.com.cryptobox.notes;

public class Note {
    private String title, lastModified;

    public Note() {
    }

    public Note(String title, String lastModified) {
        this.title = title;
        this.lastModified = lastModified;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}