package Model;

import java.io.Serializable;

public class FileModel implements Serializable {
    private String filename;
    private String filepath;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public FileModel(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
    }
}
