package UI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private JButton detectButton = new JButton("detect eye color");
    private JButton connectButton = new JButton("connect camera");
    private JLabel detectedColorBar = new JLabel("-");
    private JRadioButton showEyesButton = new JRadioButton("show eyes");

    public SettingsPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(connectButton);
        this.add(detectButton);
        this.add(detectedColorBar);
        this.add(showEyesButton);
    }

    public void setColorEyeText(String color){
        detectedColorBar.setText(color);
    }

    public void setActionListener(ActionListener ac){
        connectButton.setActionCommand("connectCamera");
        connectButton.addActionListener(ac);
        connectButton.addActionListener(
                l -> {
                    if ("connectCamera".equals(connectButton.getActionCommand())){
                        connectButton.setActionCommand("disconnectCamera");
                        connectButton.setText("disconnectCamera");
                    }else {
                        connectButton.setActionCommand("connectCamera");
                        connectButton.setText("connectCamera");
                    }
                }
        );

        detectButton.setActionCommand("detect");
        detectButton.addActionListener(ac);

        showEyesButton.setActionCommand("showEyes");
        showEyesButton.addActionListener(ac);
    }

    public JRadioButton getShowEyesButton() {
        return showEyesButton;
    }
}
