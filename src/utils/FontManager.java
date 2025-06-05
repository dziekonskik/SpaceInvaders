package utils;
import java.awt.*;
import java.io.InputStream;

public class FontManager {
    private static Font vt323;

    public static Font getVT323(float size) {
        if (vt323 == null) {
            try {
                InputStream is = FontManager.class.getResourceAsStream("/resources/VT323-Regular.ttf");
                assert is != null;
                vt323 = Font.createFont(Font.TRUETYPE_FONT, is);
            } catch (Exception e) {
                vt323 = new Font("Monospaced", Font.BOLD, (int) size);
            }
        }
        return vt323.deriveFont(size);
    }
}
