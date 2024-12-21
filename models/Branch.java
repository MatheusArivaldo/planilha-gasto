package models;

import utils.CustomArrayList;

public class Branch {
  private String name;
  private double value;
  private CustomArrayList<Branch> children;
  private Branch parent;

  public Branch(String name, double value) {
    this.name = name;
    this.value = value;
    this.children = new CustomArrayList<Branch>();
  }

  // #region Getters and Setters

  public String getName() {
    return name;
  }

  public double getValue() {
    return value;
  }

  public CustomArrayList<Branch> getChildren() {
    return children;
  }

  public Branch getParent() {
    return parent;
  }

  public double getTotalValue() {
    double totalValue = value;
    for (int i = 0; i < children.size(); i++) {
      totalValue += children.get(i).getTotalValue();
    }
    return totalValue;
  }

  public String getDisplayName() {
    return name + " (" + value + ")" + "[" + getTotalValue() + "]";
  }

  public void setParent(Branch parent) {
    this.parent = parent;
  }

  // #endregion

  // #region Methods

  public void addBranch(Branch branch) {
    children.add(branch);
  }

  public void removeBranch(Branch branch) {
    children.remove(children.indexOf(branch));
  }

  // #endregion
}
