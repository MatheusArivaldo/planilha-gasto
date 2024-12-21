package visual;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

import models.*;
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
      } else if (e instanceof IllegalArgumentException) {
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

      categoriesPanel.revalidate();
      categoriesPanel.repaint();

      DialogHelper.showMessageDialog(
          "Adicionar Categoria",
          "Categoria " + input + " adicionada com sucesso!",
          DialogType.SUCCESS);
    } catch (Exception e) {
      if (e instanceof IllegalArgumentException) {
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
    System.err.println("Add Branch");
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
    return categoryButton;
  }

  // #endregion

  // #region Helpers

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

    updateCategories();
  }

  // #endregion

}
