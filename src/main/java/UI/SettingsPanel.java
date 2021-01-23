package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private JButton detectButton = new JButton("detect eye color");
    private JButton connectButton = new JButton("connect camera");
    private JLabel detectedColorBar = new JLabel("-----", SwingConstants.CENTER);
    private JRadioButton showEyesButton = new JRadioButton("show eyes");

    public SettingsPanel() {
        this.setBorder(BorderFactory.createTitledBorder("Settings"));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(Box.createRigidArea(new Dimension(0,15)));
        this.add(connectButton);
        this.add(Box.createRigidArea(new Dimension(0,15)));
        this.add(detectButton);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(prepareDetectedColorBar(detectedColorBar));
        this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(showEyesButton);
        this.add(new JPanel());
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

    public JPanel prepareDetectedColorBar(JLabel label) {
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEtchedBorder());
        label.setFont(new Font("Comic Sans", Font.BOLD,14));

        panel.setMaximumSize(new Dimension(1000, 20));

        panel.add(label);

        return panel;
    }
}
