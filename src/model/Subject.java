package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Subject {

    private String id;
    private String name;
    private List<Chapter> chapters = new ArrayList<>();

    public Subject(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public int getProgressPercent() {
        if (chapters.isEmpty()) return 0;
        int done = 0;
        for (Chapter c : chapters) {
            if (c.isCompleted()) done++;
        }
        return (int) (100.0 * done / chapters.size());
    }
}
