package visual;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

import models.*;
import utils.DuplicatedException;
import visual.DialogHelper.DialogType;

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

    createYearContainer();
    createCategoryContainer();
    createBranchContainer();

    updateYears();

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

  // #endregion

  // #region Containers

  public void createYearContainer() {
    yearContainer = new JPanel();
    yearContainer.setLayout(new BoxLayout(yearContainer, BoxLayout.Y_AXIS));
    yearContainer.setPreferredSize(new Dimension(200, 0));
    yearContainer.setBorder(BorderFactory.createEmptyBorder(8, 2, 8, 2));

    addYearButton = new JButton("Adicionar Ano");
    addYearButton.setFocusable(false);
    addYearButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
    addYearButton.setAlignmentX(0.5f);
    addYearButton.addActionListener(e -> addYear(e));

    yearsPanel = new JPanel();
    yearsPanel.setLayout(new BoxLayout(yearsPanel, BoxLayout.Y_AXIS));

    yearsScrollPane = new JScrollPane(yearsPanel);
    yearsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    yearsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    yearsScrollPane.setAlignmentY(16);
    yearsScrollPane.setBorder(BorderFactory.createMatteBorder(8, 0, 0, 0, new Color(0, 0, 0, 0)));

    yearContainer.add(addYearButton);
    yearContainer.add(yearsScrollPane);
  }

  public void createCategoryContainer() {
    categoryContainer = new JPanel();
    categoryContainer.setLayout(new BoxLayout(categoryContainer, BoxLayout.Y_AXIS));
    categoryContainer.setPreferredSize(new Dimension(200, 0));
    categoryContainer.setBorder(BorderFactory.createEmptyBorder(8, 2, 8, 2));

    addCategoryButton = new JButton("Adicionar Categoria");
    addCategoryButton.setFocusable(false);
    addCategoryButton.setPreferredSize(new Dimension(0, 30));
    addCategoryButton.setAlignmentX(0.5f);
    addCategoryButton.addActionListener(e -> addCategory(e));

    categoriesPanel = new JPanel();
    categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));

    categoriesScrollPane = new JScrollPane(categoriesPanel);
    categoriesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    categoriesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    categoriesScrollPane.setAlignmentY(16);
    categoriesScrollPane.setBorder(BorderFactory.createMatteBorder(8, 0, 0, 0, new Color(0, 0, 0, 0)));

    categoryContainer.add(addCategoryButton);
    categoryContainer.add(categoriesScrollPane);
  }

  public void createBranchContainer() {
    branchContainer = new JPanel();
    branchContainer.setLayout(new BoxLayout(branchContainer, BoxLayout.Y_AXIS));
    branchContainer.setBorder(BorderFactory.createEmptyBorder(8, 2, 8, 2));

    addBranchButton = new JButton("Adicionar Categoria");
    addBranchButton.setFocusable(false);
    addBranchButton.setPreferredSize(new Dimension(0, 30));
    addBranchButton.setAlignmentX(0.5f);
    addBranchButton.addActionListener(e -> addBranch(e));

    branchesPanel = new JPanel();
    branchesPanel.setLayout(new BoxLayout(branchesPanel, BoxLayout.Y_AXIS));

    branchesScrollPane = new JScrollPane(branchesPanel);
    branchesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    branchesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    branchesScrollPane.setAlignmentY(16);
    branchesScrollPane.setBorder(BorderFactory.createMatteBorder(8, 0, 0, 0, new Color(0, 0, 0, 0)));

    branchContainer.add(addBranchButton);
    branchContainer.add(branchesScrollPane);
  }

  // #endregion

  // #region add actions

  public void addYear(ActionEvent event) {
    String input = DialogHelper.showInputDialog("Adicionar Ano", "Digite o ano: ").trim();

    try {
      if (input == null || input.isEmpty()) {
        System.err.println("Ano vazio!");
        DialogHelper.showMessageDialog(
            "Adicionar Ano",
            "Ano não pode ser vazio!",
            DialogType.ERROR);
        return;
      }

      int year = Integer.parseInt(input);
      Year yearToAdd = new Year(year);

      if (sheet.addYear(yearToAdd)) {
        DialogHelper.showMessageDialog(
            "Adicionar Ano",
            "Ano " + year + " adicionado com sucesso!",
            DialogType.SUCCESS);

        JButton yearButton = createYearButton(yearToAdd);

        yearsPanel.add(yearButton);
        yearContainer.revalidate();
        yearContainer.repaint();
      } else {
        DialogHelper.showMessageDialog(
            "Adicionar Ano",
            "Erro ao adicionar ano!",
            DialogType.ERROR);
      }
    } catch (Exception e) {
      if (e instanceof NumberFormatException) {
        DialogHelper.showMessageDialog(
            "Adicionar Ano",
            "Digite apenas o números!",
            DialogType.ERROR);
      } else if (e instanceof DuplicatedException) {
        DialogHelper.showMessageDialog(
            "Adicionar Ano",
            "Ano  " + input + " já existe!",
            DialogType.ERROR);
      } else {
        DialogHelper.showMessageDialog(
            "Adicionar Ano",
            "Erro desconhecido ao adicionar ano!",
            DialogType.ERROR);
        e.printStackTrace();
      }
    }
  }

  public void addCategory(ActionEvent event) {
    if (selectedYear == null) {
      DialogHelper.showMessageDialog(
          "Adicionar Categoria",
          "Selecione um ano primeiro!",
          DialogType.ERROR);
      return;
    }

    String input = DialogHelper.showInputDialog("Adicionar Categoria", "Digite o nome da categoria: ").trim()
        .toLowerCase();

    if (input == null || input.isEmpty()) {
      DialogHelper.showMessageDialog(
          "Adicionar Categoria",
          "Nome da categoria nao pode ser vazio!",
          DialogType.ERROR);
      return;
    }

    if (!input.matches("^[a-zA-Z0-9 ]+$")) {
      DialogHelper.showMessageDialog(
          "Adicionar Categoria",
          "Nome da categoria nao pode conter caracteres especiais!",
          DialogType.ERROR);
      return;
    }

    Category category = new Category(input);

    try {
      selectedYear.addCategory(category);

      JButton categoryButton = createCategoryButton(category);
      categoriesPanel.add(categoryButton);

      updateCategories();

      DialogHelper.showMessageDialog(
          "Adicionar Categoria",
          "Categoria " + input + " adicionada com sucesso!",
          DialogType.SUCCESS);
    } catch (Exception e) {
      if (e instanceof DuplicatedException) {
        DialogHelper.showMessageDialog(
            "Adicionar Categoria",
            "Categoria " + input + " ja existe!",
            DialogType.ERROR);
      } else {
        DialogHelper.showMessageDialog(
            "Adicionar Categoria",
            "Erro desconhecido ao adicionar categoria!",
            DialogType.ERROR);
        e.printStackTrace();
      }
    }
  }

  public void addBranch(ActionEvent event) {
    if (selectedCategory == null) {
      DialogHelper.showMessageDialog(
          "Adicionar Categoria",
          "Selecione uma categoria primeiro!",
          DialogType.ERROR);
      return;
    }

    String[] inputs = DialogHelper.showDoubleInputDialog(
        "Adicionar Branch",
        "Digite o nome da branch:",
        "Digite o valor da branch:");

    if (inputs == null || inputs[0].isEmpty() || inputs[1].isEmpty()) {
      DialogHelper.showMessageDialog(
          "Adicionar Branch",
          "Nome e valor da branch nao podem ser vazios!",
          DialogType.ERROR);
      return;
    }

    if (!inputs[0].matches("^[a-zA-Z0-9 ]+$")) {
      DialogHelper.showMessageDialog(
          "Adicionar Branch",
          "Nome da branch nao pode conter caracteres especiais!",
          DialogType.ERROR);
      return;
    }

    try {
      Branch branch = new Branch(inputs[0], Double.parseDouble(inputs[1]));
      selectedCategory.addBranch(branch);

      updateCategories();

      DialogHelper.showMessageDialog(
          "Adicionar Branch",
          "Branch " + inputs[0] + " adicionada com sucesso!",
          DialogType.SUCCESS);
    } catch (Exception e) {
      if (e instanceof CloneNotSupportedException) {
        DialogHelper.showMessageDialog(
            "Adicionar Branch",
            "Branch " + inputs[0] + " ja existe!",
            DialogType.ERROR);
      } else if (e instanceof NumberFormatException) {
        DialogHelper.showMessageDialog(
            "Adicionar Branch",
            "O valor deve ser um número!",
            DialogType.ERROR);
      } else {
        DialogHelper.showMessageDialog(
            "Adicionar Branch",
            "Erro desconhecido ao adicionar branch!",
            DialogType.ERROR);
        e.printStackTrace();
      }
    }
  }

  // #endregion

  // #region update from sheet

  public void updateYears() {
    yearsPanel.removeAll();

    for (int i = 0; i < sheet.getYears().size(); i++) {
      Year year = sheet.getYears().get(i);
      JButton yearButton = createYearButton(year);
      yearsPanel.add(yearButton);
    }

    yearsPanel.revalidate();
    yearsPanel.repaint();
  }

  public void updateCategories() {
    System.err.println("Update categories");
    categoriesPanel.removeAll();
    if (selectedYear != null) {
      for (int i = 0; i < selectedYear.getCategories().size(); i++) {
        Category category = selectedYear.getCategories().get(i);
        JButton categoryButton = createCategoryButton(category);
        categoriesPanel.add(categoryButton);
      }
    }

    categoriesPanel.revalidate();
    categoriesPanel.repaint();
  }

  public void updateBranches() {
    updateCategorySelectedVisual();
  }

  public void unselectCategory() {
    if (selectedCategory != null) {
      System.err.println("Unselect category");
      selectedCategoryButton.setBackground(unselectedColor);
      selectedCategory = null;
      selectedCategoryButton = null;
    }
  }

  public void updateCategorySelectedVisual() {
    if (selectedCategoryButton != null) {
      selectedCategoryButton.setBackground(selectedColor);
      System.err.println("Update category selected visual");
    }
  }

  // #endregion

  // #region elements constructors

  public JButton createYearButton(Year year) {
    JButton yearButton = new JButton(year.getYear() + "");
    yearButton.setFocusable(false);
    yearButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    yearButton.setAlignmentX(0.5f);
    yearButton.addActionListener(e -> selectYear(sheet.getYearByNumber(year.getYear()), (JButton) e.getSource()));
    return yearButton;
  }

  public JButton createCategoryButton(Category category) {
    JButton categoryButton = new JButton(category.getDisplayName());
    categoryButton.setFocusable(false);
    categoryButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    categoryButton.setAlignmentX(0.5f);
    categoryButton.addActionListener(e -> selectCategory(category, (JButton) e.getSource()));
    return categoryButton;
  }

  // #endregion

  // #region selection

  public void selectYear(Year year, JButton yearButton) {
    if (selectedYear == null) {
      selectedYear = year;
      selectedYearButton = yearButton;
      selectedYearButton.setBackground(selectedColor);
    } else if (selectedYear.equals(year)) {
      selectedYear = null;
      selectedYearButton.setBackground(unselectedColor);
      selectedYearButton = null;
    } else {
      selectedYear = year;
      selectedYearButton.setBackground(unselectedColor);
      selectedYearButton = yearButton;
      selectedYearButton.setBackground(selectedColor);
    }

    unselectCategory();
    updateCategories();
  }

  public void selectCategory(Category category, JButton categoryButton) {
    if (selectedCategory == null || selectedYear == null) {
      selectedCategory = category;
      selectedCategoryButton = categoryButton;
      selectedCategoryButton.setBackground(selectedColor);
    } else if (selectedCategory.equals(category)) {
      selectedCategory = null;
      selectedCategoryButton.setBackground(unselectedColor);
      selectedCategoryButton = null;
    } else {
      selectedCategory = category;
      selectedCategoryButton.setBackground(unselectedColor);
      selectedCategoryButton = categoryButton;
      selectedCategoryButton.setBackground(selectedColor);
    }

    updateBranches();
  }

  // #endregion

}
