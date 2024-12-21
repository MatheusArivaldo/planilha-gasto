import models.Sheet;
import visual.MainFrame;

public class Main {
  public static void main(String[] args) {
    Sheet sheet = new Sheet();
    new MainFrame(sheet);
  }
}
