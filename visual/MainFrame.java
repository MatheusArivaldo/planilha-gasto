package visual;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import models.*;
import utils.CustomHashMap;
import utils.DuplicatedException;
import visual.DialogHelper.DialogType;

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

  private BranchTreePanel branchTreePanel;

  private Color selectedColor = Color.CYAN;
  private Color unselectedColor = new JButton().getBackground();

  private JButton selectedYearButton;
  private JButton selectedCategoryButton;

  private CustomHashMap<JButton, Year> yearButtons = new CustomHashMap<JButton, Year>();
  private CustomHashMap<JButton, Category> categoryButtons = new CustomHashMap<JButton, Category>();

  // #endregion

  // #region constructor

  public MainFrame(Sheet sheet) {
    this.sheet = sheet;

    sheet.addYear(new Year(2020));
    sheet.addYear(new Year(2021));

    sheet.getYears().get(0).addCategory(new Category("alimentação"));
    sheet.getYears().get(0).addCategory(new Category("lazer"));
    sheet.getYears().get(0).addCategory(new Category("Trabalho"));

    sheet.getYears().get(1).addCategory(new Category("moradia"));
    sheet.getYears().get(1).addCategory(new Category("estudo"));

    sheet.getYears().get(0).getCategories().get(0).addBranch(new Branch("comida", 100.0));
    sheet.getYears().get(0).getCategories().get(0).addBranch(new Branch("cafe", 10.0));
    sheet.getYears().get(0).getCategories().get(0).addBranch(new Branch("pizza", 30.0));

    sheet.getYears().get(0).getCategories().get(1).addBranch(new Branch("cinema", 50.0));
    sheet.getYears().get(0).getCategories().get(1).addBranch(new Branch("livros", 20.0));

    sheet.getYears().get(1).getCategories().get(0).addBranch(new Branch("trabalho", 100.0));
    sheet.getYears().get(1).getCategories().get(0).addBranch(new Branch("casa", 50.25));

    sheet.getYears().get(1).getCategories().get(1).addBranch(new Branch("estudo", 100.0));
    sheet.getYears().get(1).getCategories().get(1).addBranch(new Branch("escola", 20.0));

    sheet.getYears().get(0).getCategories().get(0).getBranches().get(0).addBranch(new Branch("comida", 100.0));
    sheet.getYears().get(0).getCategories().get(0).getBranches().get(0).addBranch(new Branch("cafe", 10.0));

    sheet.getYears().get(0).getCategories().get(1).getBranches().get(0).addBranch(new Branch("cinema", 50.0));
    sheet.getYears().get(0).getCategories().get(1).getBranches().get(0).addBranch(new Branch("livros", 20.0));

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
    addYearButton.addActionListener(this::addYearButtonLogic);
    yearsPanel = VisualBuilder.buildPanel();
    yearsScrollPane = VisualBuilder.buildScrollPane(yearsPanel);
    yearContainer.add(addYearButton);
    yearContainer.add(yearsScrollPane);

    categoryContainer = VisualBuilder.buildContainer();
    categoryContainer.setPreferredSize(new Dimension(200, 0));
    addCategoryButton = VisualBuilder.buildButton("Adicionar categoria");
    addCategoryButton.addActionListener(this::addCategoryButtonLogic);
    categoriesPanel = VisualBuilder.buildPanel();
    categoriesScrollPane = VisualBuilder.buildScrollPane(categoriesPanel);
    categoryContainer.add(addCategoryButton);
    categoryContainer.add(categoriesScrollPane);

    branchContainer = VisualBuilder.buildContainer();
    addBranchButton = VisualBuilder.buildButton("Adicionar branch");
    addBranchButton.addActionListener(this::addBranchButtonLogic);
    branchesPanel = VisualBuilder.buildPanel();
    branchesScrollPane = VisualBuilder.buildScrollPane(branchesPanel);
    branchTreePanel = new BranchTreePanel();
    branchContainer.add(addBranchButton);
    branchContainer.add(branchesScrollPane);
    branchContainer.add(branchTreePanel);
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
      for (int i = 0; i < yearButtons.get(selectedYearButton).getCategories().size(); i++) {
        Category category = yearButtons.get(selectedYearButton).getCategories().get(i);
        buildCategoryButton(category);
      }
    }
    repaintVisual(categoriesPanel);
  }

  // #endregion

  // #region builders

  private void buildYearButton(Year year) {
    JButton yearButton = new JButton(year.getDisplayName());
    configureButtonBuilder(yearButton);
    if (selectedYearButton != null && year == yearButtons.get(selectedYearButton)) {
      yearButton.setBackground(selectedColor);
      selectedYearButton = yearButton;
    }
    yearButton.addActionListener(this::yearButtonLogic);
    yearsPanel.add(yearButton);
    yearButtons.put(yearButton, year);
  }

  private void buildCategoryButton(Category category) {
    JButton categoryButton = new JButton(category.getDisplayName());
    configureButtonBuilder(categoryButton);
    if (selectedCategoryButton != null && category == categoryButtons.get(selectedCategoryButton)) {
      categoryButton.setBackground(selectedColor);
      selectedCategoryButton = categoryButton;
    }
    categoryButton.addActionListener(this::categoryButtonLogic);
    categoriesPanel.add(categoryButton);
    categoryButtons.put(categoryButton, category);
  }

  private void configureButtonBuilder(JButton button) {
    button.setFocusable(false);
    button.setAlignmentX(0.5f);
    button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
  }

  // #endregion

  // #region elements logic

  private void yearButtonLogic(ActionEvent event) {
    var yearButton = (JButton) event.getSource();
    if (selectedYearButton == null) {
      selectedYearButton = yearButton;
      selectedYearButton.setBackground(selectedColor);
    } else {
      if (selectedYearButton.equals(yearButton)) {
        selectedYearButton.setBackground(unselectedColor);
        selectedYearButton = null;
        unselectCategory();
      } else {
        selectedYearButton.setBackground(unselectedColor);
        selectedYearButton = yearButton;
        selectedYearButton.setBackground(selectedColor);
        unselectCategory();
      }
    }

    showCategories();
    repaintVisual(yearsPanel);
  }

  private void categoryButtonLogic(ActionEvent event) {
    var categoryButton = (JButton) event.getSource();
    if (selectedCategoryButton == null) {
      selectedCategoryButton = categoryButton;
      selectedCategoryButton.setBackground(selectedColor);
      branchTreePanel.displayBranches(categoryButtons.get(categoryButton));
    } else {
      if (selectedCategoryButton.equals(categoryButton)) {
        selectedCategoryButton.setBackground(unselectedColor);
        selectedCategoryButton = null;
        branchTreePanel.displayBranches(null);
      } else {
        selectedCategoryButton.setBackground(unselectedColor);
        selectedCategoryButton = categoryButton;
        selectedCategoryButton.setBackground(selectedColor);
        branchTreePanel.displayBranches(categoryButtons.get(categoryButton));
      }
    }
    repaintVisual(categoriesPanel);
  }

  private void unselectCategory() {
    if (selectedCategoryButton != null) {
      selectedCategoryButton.setBackground(unselectedColor);
      selectedCategoryButton = null;
      branchTreePanel.displayBranches(null);
    }
  }

  // #endregion

  // #region helpers

  private void repaintVisual(JComponent component) {
    component.revalidate();
    component.repaint();
  }

  public void recolorAllSelectedButtons() {
    if (selectedYearButton != null) {
      selectedYearButton.setBackground(selectedColor);
    }
    if (selectedCategoryButton != null) {
      selectedCategoryButton.setBackground(selectedColor);
    }
  }

  // #endregion

  // #region add buttons

  private void addYearButtonLogic(ActionEvent event) {
    try {
      String input = DialogHelper.showInputDialog("Adicionar ano", "Digite o nome do ano: ");

      if (input == null) {
        return;
      }

      if (input.isEmpty()) {
        DialogHelper.showMessageDialog("Adicionar ano", "Ano não pode estar vazio!", DialogType.ERROR);
        return;
      }

      input = input.trim();

      int year = Integer.parseInt(input);
      sheet.addYear(new Year(year));
      showYears();

      DialogHelper.showMessageDialog("Adicionar ano", "Ano adicionado com sucesso!", DialogType.SUCCESS);
    } catch (Exception e) {
      if (e instanceof NumberFormatException) {
        DialogHelper.showMessageDialog("Adicionar ano", "Ano só deve conter números!", DialogType.ERROR);
      } else if (e instanceof DuplicatedException) {
        DialogHelper.showMessageDialog("Adicionar ano", "Ano já existe!", DialogType.ERROR);
      } else {
        DialogHelper.showMessageDialog("Adicionar ano", "Erro ao adicionar ano!", DialogType.ERROR);
        e.printStackTrace();
      }
    }
  }

  private void addCategoryButtonLogic(ActionEvent event) {
    try {
      if (selectedYearButton == null) {
        DialogHelper.showMessageDialog("Adicionar categoria", "Selecione um ano!", DialogType.ERROR);
        return;
      }

      String input = DialogHelper.showInputDialog("Adicionar categoria", "Digite o nome da categoria: ");

      if (input == null) {
        return;
      }

      input = input.trim().toLowerCase();

      if (input.isEmpty()) {
        DialogHelper.showMessageDialog("Adicionar categoria", "Categoria não pode estar vazia!", DialogType.ERROR);
        return;
      }

      if (!input.matches("[a-zA-Z0-9 ]+")) {
        DialogHelper.showMessageDialog("Adicionar categoria", "Categoria deve conter apenas letras, números e espaços!",
            DialogType.ERROR);
      }

      yearButtons.get(selectedYearButton).addCategory(new Category(input));
      showCategories();

      DialogHelper.showMessageDialog("Adicionar categoria", "Categoria adicionada com sucesso!", DialogType.SUCCESS);
    } catch (Exception e) {
      if (e instanceof DuplicatedException) {
        DialogHelper.showMessageDialog("Adicionar categoria", "Categoria já existe!", DialogType.ERROR);
      } else {
        DialogHelper.showMessageDialog("Adicionar categoria", "Erro ao adicionar categoria!", DialogType.ERROR);
        e.printStackTrace();
      }
    }
  }

  private void addBranchButtonLogic(ActionEvent event) {
    try {
      if (selectedCategoryButton == null) {
        DialogHelper.showMessageDialog("Adicionar branch", "Selecione uma categoria!", DialogType.ERROR);
        return;
      }

      String[] inputs = DialogHelper.showDoubleInputDialog(
          "Adicionar branch",
          "Digite o nome da branch:",
          "Digite o valor da branch:");

      if (inputs == null) {
        return;
      }

      String name = inputs[0].trim().toLowerCase();
      String valueStr = inputs[1].trim();

      if (name.isEmpty()) {
        DialogHelper.showMessageDialog("Adicionar branch", "Nome da branch não pode estar vazio!", DialogType.ERROR);
        return;
      }

      if (valueStr.isEmpty()) {
        DialogHelper.showMessageDialog("Adicionar branch", "Valor da branch não pode estar vazio!", DialogType.ERROR);
        return;
      }

      if (!name.matches("[a-zA-Z0-9 ]+")) {
        DialogHelper.showMessageDialog("Adicionar branch", "Nome deve conter apenas letras, números e espaços!",
            DialogType.ERROR);
        return;
      }

      double value;
      try {
        value = Double.parseDouble(valueStr.replace(",", "."));
      } catch (NumberFormatException e) {
        DialogHelper.showMessageDialog("Adicionar branch", "Valor deve ser um número válido!", DialogType.ERROR);
        return;
      }

      DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) branchTreePanel.getTree()
          .getLastSelectedPathComponent();
      Branch newBranch = new Branch(name, value);

      if (selectedNode == null || selectedNode.isRoot()) {
        categoryButtons.get(selectedCategoryButton).addBranch(newBranch);
      } else {
        String parentNodeText = selectedNode.getUserObject().toString();
        Category category = categoryButtons.get(selectedCategoryButton);
        Branch parentBranch = findBranch(category, parentNodeText);

        if (parentBranch != null) {
          parentBranch.addBranch(newBranch);
          newBranch.setParent(parentBranch);
        } else {
          DialogHelper.showMessageDialog("Adicionar branch", "Branch pai não encontrada!", DialogType.ERROR);
          return;
        }
      }

      branchTreePanel.displayBranches(categoryButtons.get(selectedCategoryButton));

      selectedCategoryButton.setText(categoryButtons.get(selectedCategoryButton).getDisplayName());

      DialogHelper.showMessageDialog("Adicionar branch", "Branch adicionada com sucesso!", DialogType.SUCCESS);

    } catch (Exception e) {
      if (e instanceof DuplicatedException) {
        DialogHelper.showMessageDialog("Adicionar branch", "Branch já existe!", DialogType.ERROR);
      } else {
        DialogHelper.showMessageDialog("Adicionar branch", "Erro ao adicionar branch!", DialogType.ERROR);
        e.printStackTrace();
      }
    }
  }

  private Branch findBranch(Category category, String displayName) {
    for (int i = 0; i < category.getBranches().size(); i++) {
      Branch result = findBranchRecursive(category.getBranches().get(i), displayName);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  private Branch findBranchRecursive(Branch current, String displayName) {
    if (current.getDisplayName().equals(displayName)) {
      return current;
    }

    for (int i = 0; i < current.getChildren().size(); i++) {
      Branch result = findBranchRecursive(current.getChildren().get(i), displayName);
      if (result != null) {
        return result;
      }
    }

    return null;
  }

  // #endregion

}