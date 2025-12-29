package model;

public class Chapter {

    private String title;
    private boolean completed;

    public Chapter(String title) {
        this.title = title;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void toggle() {
        completed = !completed;
    }
}
