package utils;

public enum MonsterLevel {
    LEVEL1(1, 100, "/resources/monster-level1.png"),
    LEVEL2(2, 250, "/resources/monster-level2.png"),
    LEVEL3(3, 500, "/resources/monster-level3.png");

    private final int hitsToKill;
    private final int points;
    private final String imagePath;

    MonsterLevel(int hitsToKill, int points, String imagePath) {
        this.hitsToKill = hitsToKill;
        this.points = points;
        this.imagePath = imagePath;
    }

    public int getHitsToKill() {
        return hitsToKill;
    }

    public int getPoints() {
        return points;
    }

    public String getImagePath() {
        return imagePath;
    }
}

