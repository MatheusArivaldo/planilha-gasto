package visual;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import models.*;
import utils.*;

public class MainFrame {
  // #region fields

  private Sheet sheet;
  private JFrame root;

  private JPanel bodyPanel;

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

  private Color selectedColor = Color.CYAN;
  private Color unselectedColor = new JButton().getBackground();

  private YearButton selectedYearButton;
  private CategoryButton selectedCategoryButton;

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

  private void show() {
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

    showYears();
  }

  private void generateFrame() {
    yearContainer = VisualBuilder.buildContainer();
    yearContainer.setPreferredSize(new Dimension(200, 0));
    addYearButton = VisualBuilder.buildButton("Adicionar ano");
    yearsPanel = VisualBuilder.buildPanel();
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

  // #region methods to show

  private void showYears() {
    yearsPanel.removeAll();
    for (int i = 0; i < sheet.getYears().size(); i++) {
      Year year = sheet.getYears().get(i);
      buildYearButton(year);
    }
    repaintVisual(yearsPanel);
  }

  private void showCategories() {
    categoriesPanel.removeAll();
    if (selectedYearButton != null) {
      for (int i = 0; i < selectedYearButton.year.getCategories().size(); i++) {
        Category category = selectedYearButton.year.getCategories().get(i);
        buildCategoryButton(category);
      }
    }
    repaintVisual(categoriesPanel);
  }

  // #endregion

  // #region builders

  private void buildYearButton(Year year) {
    YearButton yearButton = new YearButton(year);
    configureButtonBuilder(yearButton);
    yearButton.addActionListener(this::yearButtonLogic);
    yearsPanel.add(yearButton);
  }

  private void buildCategoryButton(Category category) {
    CategoryButton categoryButton = new CategoryButton(category);
    configureButtonBuilder(categoryButton);
    categoryButton.addActionListener(this::categoryButtonLogic);
    categoriesPanel.add(categoryButton);
  }

  private void configureButtonBuilder(JButton button) {
    button.setFocusable(false);
    button.setAlignmentX(0.5f);
    button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
  }

  // #endregion

  // #region elements logic

  public void yearButtonLogic(ActionEvent event) {
    var yearButton = (YearButton) event.getSource();
    if (selectedYearButton == null) {
      selectedYearButton = yearButton;
      selectedYearButton.setBackground(selectedColor);
    } else {
      if (selectedYearButton.equals(yearButton)) {
        selectedYearButton.setBackground(unselectedColor);
        selectedYearButton = null;
      } else {
        selectedYearButton.setBackground(unselectedColor);
        selectedYearButton = yearButton;
        selectedYearButton.setBackground(selectedColor);
      }
    }

    showCategories();
    repaintVisual(yearsPanel);
  }

  public void categoryButtonLogic(ActionEvent event) {
    var categoryButton = (CategoryButton) event.getSource();
    if (selectedCategoryButton == null) {
      selectedCategoryButton = categoryButton;
      selectedCategoryButton.setBackground(selectedColor);
    } else {
      if (selectedCategoryButton.equals(categoryButton)) {
        selectedCategoryButton.setBackground(unselectedColor);
        selectedCategoryButton = null;
      } else {
        selectedCategoryButton.setBackground(unselectedColor);
        selectedCategoryButton = categoryButton;
        selectedCategoryButton.setBackground(selectedColor);
      }
    }
    repaintVisual(categoriesPanel);
  }

  // #endregion

  // #region helpers

  private void repaintVisual(JComponent component) {
    component.revalidate();
    component.repaint();
  }

  private void recolorAllSelectedButtons() {
    if (selectedYearButton != null) {
      selectedYearButton.setBackground(selectedColor);
    }
    if (selectedCategoryButton != null) {
      selectedCategoryButton.setBackground(selectedColor);
    }
  }

  // #endregion

  // TODO: mostrar ramos criados
  // TODO: logica para adicionar ano, categoria e ramo
}

class YearButton extends JButton {
  public Year year;

  public YearButton(Year year) {
    super(year.getDisplayName());
    this.year = year;
  }
}

class CategoryButton extends JButton {
  public Category category;

  public CategoryButton(Category category) {
    super(category.getDisplayName());
    this.category = category;
  }
}