package UI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private JButton detectButton = new JButton("detect eye color");
    private JLabel detectedColorBar = new JLabel("-");
    private JRadioButton showEyesButton = new JRadioButton("show eyes");

    public SettingsPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(detectButton);
        this.add(detectedColorBar);
        this.add(showEyesButton);
    }

    public void setColorEyeText(String color){
        detectedColorBar.setText(color);
    }

    public void setActionListener(ActionListener ac){
        detectButton.setActionCommand("detect");
        detectButton.addActionListener(ac);

        showEyesButton.setActionCommand("showEyes");
        showEyesButton.addActionListener(ac);
    }
}
