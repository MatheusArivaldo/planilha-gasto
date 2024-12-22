package visual;

import java.awt.*;
import javax.swing.*;

import models.*;
import utils.*;

public class MainFrame {
  // #region fields

  private Sheet sheet;
  private JFrame root;

  private JPanel bodyPanel;
  // private JPanel footerPanel;

  private JPanel yearContainer;
  private JButton addYearButton;
  private JPanel yearsPanel;
  private JScrollPane yearsScrollPane;

  private JPanel categoryContainer;
  private JButton addCategoryButton;
  private JPanel categoriesPanel;
  private JScrollPane categoriesScrollPane;

  private JPanel branchContainer;
  private JButton addBranchButton;
  private JPanel branchesPanel;
  private JScrollPane branchesScrollPane;

  private Year selectedYear;
  private JButton selectedYearButton;

  private Category selectedCategory;
  private JButton selectedCategoryButton;

  private Color selectedColor = Color.CYAN;
  private Color unselectedColor = new JButton().getBackground();

  // #endregion

  // #region constructor

  public MainFrame(Sheet sheet) {
    this.sheet = sheet;

    sheet.addYear(new Year(2020));
    sheet.addYear(new Year(2021));

    sheet.getYears().get(0).addCategory(new Category("Alimentação"));
    sheet.getYears().get(0).addCategory(new Category("Lazer"));
    sheet.getYears().get(0).addCategory(new Category("Trabalho"));

    sheet.getYears().get(1).addCategory(new Category("Moradia"));
    sheet.getYears().get(1).addCategory(new Category("Estudo"));

    show();
  }

  public void show() {
    root = new JFrame();
    root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    generateFrame();

    bodyPanel = new JPanel();
    bodyPanel.setLayout(new BorderLayout());
    bodyPanel.add(categoryContainer, BorderLayout.WEST);
    bodyPanel.add(branchContainer, BorderLayout.CENTER);

    root.add(yearContainer, BorderLayout.WEST);
    root.add(bodyPanel, BorderLayout.CENTER);

    root.setSize(new Dimension(1024, 720));
    root.setTitle("Planilha Financeira");
    root.setVisible(true);
  }

  public void generateFrame() {
    yearContainer = VisualBuilder.buildContainer();
    yearContainer.setPreferredSize(new Dimension(200, 0));
    addYearButton = VisualBuilder.buildButton("Adicionar ano");
    yearsPanel = new JPanel();
    yearsScrollPane = VisualBuilder.buildScrollPane(yearsPanel);
    yearContainer.add(addYearButton);
    yearContainer.add(yearsScrollPane);

    categoryContainer = VisualBuilder.buildContainer();
    categoryContainer.setPreferredSize(new Dimension(200, 0));
    addCategoryButton = VisualBuilder.buildButton("Adicionar categoria");
    categoriesPanel = VisualBuilder.buildPanel();
    categoriesScrollPane = VisualBuilder.buildScrollPane(categoriesPanel);
    categoryContainer.add(addCategoryButton);
    categoryContainer.add(categoriesScrollPane);

    branchContainer = VisualBuilder.buildContainer();
    addBranchButton = VisualBuilder.buildButton("Adicionar branch");
    branchesPanel = VisualBuilder.buildPanel();
    branchesScrollPane = VisualBuilder.buildScrollPane(branchesPanel);
    branchContainer.add(addBranchButton);
    branchContainer.add(branchesScrollPane);
  }

  // #endregion

}
