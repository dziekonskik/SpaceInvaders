package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoresModel {
    private static final String FILE_NAME = "src/highscores.txt";
    private static final int MAX_SCORES = 10;

    private final List<ScoreEntry> scores = new ArrayList<>();

    public HighScoresModel() {
        loadScores();
    }

    public void addScore(String name, int score) {
        ScoreEntry existing = null;
        for (ScoreEntry entry : scores) {
            if (entry.name.equals(name)) {
                existing = entry;
                break;
            }
        }
        if (existing != null) {
            if (score > existing.score) {
                scores.remove(existing);
                scores.add(new ScoreEntry(name, score));
            } else  return;
        } else {
            scores.add(new ScoreEntry(name, score));
        }
        Collections.sort(scores);
        if (scores.size() > MAX_SCORES) {
            scores.remove(scores.size() - 1);
        }
        saveScores();
    }


    public List<ScoreEntry> getScores() {
        return new ArrayList<>(scores);
    }

    private void loadScores() {
        scores.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scores.add(new ScoreEntry(name, score));
                }
            }
            Collections.sort(scores);
        } catch (FileNotFoundException e) {
            System.out.println("Highscores file not found — will create it when saving.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (ScoreEntry entry : scores) {
                writer.println(entry.name + ";" + entry.score);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Highscores file not found, will create new one.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getMinScore() {
        if (scores.size() < MAX_SCORES) return 0;
        return scores.get(scores.size() - 1).score;
    }

    public static class ScoreEntry implements Comparable<ScoreEntry> {
        public final String name;
        public final int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public int compareTo(ScoreEntry o) {
            return Integer.compare(o.score, this.score);
        }
    }
}
