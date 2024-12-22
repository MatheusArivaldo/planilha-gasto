package visual;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogHelper {
  public static enum DialogType {
    ERROR,
    WARNING,
    INFORMATION,
    SUCCESS
  }

  public static void showMessageDialog(String title, String message, DialogType type) {
    int messageType;
    switch (type) {
      case DialogType.ERROR:
        messageType = JOptionPane.ERROR_MESSAGE;
        break;
      case DialogType.WARNING:
        messageType = JOptionPane.WARNING_MESSAGE;
        break;
      case DialogType.INFORMATION:
        messageType = JOptionPane.INFORMATION_MESSAGE;
        break;
      case DialogType.SUCCESS:
        messageType = JOptionPane.PLAIN_MESSAGE;
        break;
      default:
        messageType = JOptionPane.NO_OPTION;
    }

    JOptionPane.showConfirmDialog(
        null,
        message,
        title,
        JOptionPane.DEFAULT_OPTION,
        messageType);
  }

  public static String showInputDialog(String title, String message) {
    JTextField input = new JTextField();

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JLabel label = new JLabel(message);
    label.setAlignmentX(0.5f);
    panel.add(label);
    panel.add(input);

    int result = JOptionPane.showConfirmDialog(null, panel, title,
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
      return input.getText().trim();
    }
    return null;
  }

  public static String[] showDoubleInputDialog(String title, String message1, String message2) {
    JTextField input1 = new JTextField();
    JTextField input2 = new JTextField();

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    panel.add(new JLabel(message1));
    panel.add(input1);

    panel.add(new JLabel(message2));
    panel.add(input2);

    int result = JOptionPane.showConfirmDialog(null, panel, title,
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION && !input1.getText().trim().isEmpty() && !input2.getText().trim().isEmpty()) {
      return new String[] { input1.getText().trim(), input2.getText().trim() };
    }
    return null;
  }
}
