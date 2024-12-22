package visual;

import java.awt.*;
import javax.swing.*;

public class VisualBuilder {
  public static JScrollPane buildScrollPane(JPanel panel) {
    var scrollPane = new JScrollPane(panel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setAlignmentY(16);
    scrollPane.setBorder(BorderFactory.createMatteBorder(8, 0, 0, 0, new Color(0, 0, 0, 0)));
    return scrollPane;
  }

  public static JButton buildButton(String text) {
    var button = new JButton(text);
    button.setFocusable(false);
    button.setAlignmentX(0.5f);
    return button;
  }

  public static JPanel buildContainer() {
    var panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(8, 2, 8, 2));
    return panel;
  }

  public static JPanel buildPanel() {
    var panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    return panel;
  }
}
