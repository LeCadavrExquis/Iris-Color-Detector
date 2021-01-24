package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private JButton detectButton = new JButton("detect eye color");
    private JButton connectButton = new JButton("connect camera");
    private JLabel detectedColorBar = new JLabel("-----", SwingConstants.CENTER);
    private JRadioButton showEyesButton = new JRadioButton("show eyes");
    private JButton presentationModeButton = new JButton("presentation");
    private JButton forwardButton = new JButton("->");
    private JButton backwardButton = new JButton("<-");

    public SettingsPanel() {
        this.setBorder(BorderFactory.createTitledBorder("Settings"));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(Box.createRigidArea(new Dimension(0,15)));
        this.add(connectButton);
        this.add(Box.createRigidArea(new Dimension(1,15)));
        this.add(detectButton);
        this.add(Box.createRigidArea(new Dimension(1,10)));
        this.add(prepareDetectedColorBar(detectedColorBar));
        this.add(Box.createRigidArea(new Dimension(1,10)));
        this.add(showEyesButton);
        this.add(Box.createRigidArea(new Dimension(1,20)));
        this.add(presentationModeButton);
        this.add(Box.createRigidArea(new Dimension(1,10)));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(backwardButton);
        panel.add(Box.createRigidArea(new Dimension(10,0)));
        panel.add(forwardButton);
        this.add(panel);
        this.add(Box.createRigidArea(new Dimension(1,20)));
        this.add(new JPanel());

        detectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        detectedColorBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        showEyesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        presentationModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        forwardButton.setEnabled(false);
        backwardButton.setEnabled(false);
    }

    public void setColorEyeText(String color){
        detectedColorBar.setText(color);
    }

    public void setActionListener(ActionListener controllerListener, ActionListener viewListener){
        connectButton.setActionCommand("connectCamera");
        connectButton.addActionListener(controllerListener);
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
        detectButton.addActionListener(controllerListener);

        showEyesButton.setActionCommand("showEyes");
        showEyesButton.addActionListener(controllerListener);

        presentationModeButton.setActionCommand("presentation");
        presentationModeButton.addActionListener(controllerListener);
        presentationModeButton.addActionListener(
                l -> {
                    forwardButton.setEnabled(true);
                    backwardButton.setEnabled(true);
                }
        );

        forwardButton.setActionCommand("step_forward");
        forwardButton.addActionListener(viewListener);

        backwardButton.setActionCommand("step_backward");
        backwardButton.addActionListener(viewListener);
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
