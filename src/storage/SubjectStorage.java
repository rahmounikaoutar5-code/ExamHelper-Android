package storage;

import model.Subject;
import model.Chapter;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SubjectStorage {

    private static final String FILE_PATH = "data/subjects.xml";

    public void save(List<Subject> subjects) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file);
            writer.write("<subjects>\n");

            for (Subject s : subjects) {
                writer.write("  <subject>\n");
                writer.write("    <id>" + s.getId() + "</id>\n");
                writer.write("    <name>" + s.getName() + "</name>\n");
                writer.write("    <chapters>\n");

                for (Chapter c : s.getChapters()) {
                    writer.write("      <chapter>\n");
                    writer.write("        <title>" + c.getTitle() + "</title>\n");
                    writer.write("        <completed>" + c.isCompleted() + "</completed>\n");
                    writer.write("      </chapter>\n");
                }

                writer.write("    </chapters>\n");
                writer.write("  </subject>\n");
            }

            writer.write("</subjects>");
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Subject> load() {
        List<Subject> subjects = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return subjects;

            List<String> lines = Files.readAllLines(file.toPath());

            Subject currentSubject = null;
            String currentTitle = null;
            boolean completed = false;

            for (String line : lines) {
                line = line.trim();

                if (line.startsWith("<subject>")) {
                    currentSubject = null;
                }
                else if (line.startsWith("<name>")) {
                    String name = line.replace("<name>", "").replace("</name>", "");
                    currentSubject = new Subject(name);
                }
                else if (line.startsWith("<title>")) {
                    currentTitle = line.replace("<title>", "").replace("</title>", "");
                }
                else if (line.startsWith("<completed>")) {
                    completed = Boolean.parseBoolean(
                            line.replace("<completed>", "").replace("</completed>", "")
                    );
                    Chapter c = new Chapter(currentTitle);
                    if (completed) c.toggle();
                    currentSubject.getChapters().add(c);
                }
                else if (line.startsWith("</subject>")) {
                    subjects.add(currentSubject);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjects;
    }
}
