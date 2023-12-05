package pl.edu.pg.cloudlib.editor_backend;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

import java.util.List;

@Document(collectionName = "exhibits")
public class Exhibit {
    @DocumentId
    private String id;
    private String name;

    public Exhibit() {
    }

    public Exhibit(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
