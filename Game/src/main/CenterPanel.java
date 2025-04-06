package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class CenterPanel extends JPanel {
    public CenterPanel() {
        setLayout(new BorderLayout()); // Use BorderLayout for centering
    }

    @Override
    public void add(java.awt.Component comp, Object constraints) {
        super.add(comp, BorderLayout.CENTER); // Always add to the CENTER
    }
}