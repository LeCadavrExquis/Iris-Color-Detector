import UI.DisplayPanel;
import UI.SettingsPanel;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PresentationMode {
    public final static Map<Integer, String> stepsDict = Map.of(
            1, "Get image",
            2, "Detect eyes",
            3, "Cut Eyes",
            4, "Filter images"
    );

    private BufferedImage originalImage;
    private BufferedImage detectedEyes;
    private List<BufferedImage> cutedEyes;
    private List<BufferedImage> filteredEyes;

    public PresentationMode(BufferedImage originalImage, BufferedImage detectedEyes, List<BufferedImage> cutEyes, List<BufferedImage> filteredEyes) {
        this.originalImage = originalImage;
        this.detectedEyes = detectedEyes;
        this.cutedEyes = cutEyes;
        this.filteredEyes = filteredEyes;
    }

    public static Map<Integer, String> getStepsDict() {
        return stepsDict;
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public List<BufferedImage> getCutedEyes() {
        return cutedEyes;
    }

    public List<BufferedImage> getFilteredEyes() {
        return filteredEyes;
    }

    public BufferedImage getDetectedEyes() {
        return detectedEyes;
    }
}
