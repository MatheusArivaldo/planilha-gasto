package visual;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import models.*;

public class BranchTreePanel extends JPanel {
  private JTree tree;
  private DefaultTreeModel treeModel;
  private DefaultMutableTreeNode rootNode;

  public BranchTreePanel() {
    setLayout(new BorderLayout());
    initializeTree();
  }

  private void initializeTree() {
    rootNode = new DefaultMutableTreeNode("Ramos");
    treeModel = new DefaultTreeModel(rootNode);
    tree = new JTree(treeModel);

    // Configurar aparência da árvore
    tree.setShowsRootHandles(true);
    tree.setRootVisible(false);

    // Adicionar à árvore com scroll
    JScrollPane scrollPane = new JScrollPane(tree);
    add(scrollPane, BorderLayout.CENTER);
  }

  public void displayBranches(Category category) {
    rootNode.removeAllChildren();
    if (category == null) {
      treeModel.reload();
      return;
    }

    // Adicionar ramos da categoria
    for (int i = 0; i < category.getBranches().size(); i++) {
      Branch branch = category.getBranches().get(i);
      addBranchToTree(branch, rootNode);
    }

    // Expandir todos os nós
    expandAllNodes();
    treeModel.reload();
  }

  public JTree getTree() {
    return tree;
  }

  private void addBranchToTree(Branch branch, DefaultMutableTreeNode parentNode) {
    DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode(branch.getDisplayName());
    parentNode.add(branchNode);

    // Adicionar ramos filhos recursivamente
    for (int i = 0; i < branch.getChildren().size(); i++) {
      Branch childBranch = branch.getChildren().get(i);
      addBranchToTree(childBranch, branchNode);
    }
  }

  private void expandAllNodes() {
    for (int i = 0; i < tree.getRowCount(); i++) {
      tree.expandRow(i);
    }
  }

  // Método para customizar a aparência dos nós
  public void customizeTreeRenderer() {
    tree.setCellRenderer(new DefaultTreeCellRenderer() {
      @Override
      public Component getTreeCellRendererComponent(JTree tree, Object value,
          boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        // Customizar ícones e cores se necessário
        if (value instanceof DefaultMutableTreeNode) {
          DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
          if (node.isRoot()) {
            setIcon(null);
          }
        }

        return this;
      }
    });
  }
}