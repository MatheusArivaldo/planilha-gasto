package models;

import utils.CustomArrayList;

public class Category {
  private String name;
  private CustomArrayList<Branch> branches;

  public Category(String name) {
    this.name = name;
    this.branches = new CustomArrayList<Branch>();
  }

  // #region Getters and Setters

  public String getName() {
    return name;
  }

  public CustomArrayList<Branch> getBranches() {
    return branches;
  }

  public double getTotalValue() {
    double totalValue = 0.0;
    for (int i = 0; i < branches.size(); i++) {
      totalValue += branches.get(i).getTotalValue();
    }
    return totalValue;
  }

  public String getDisplayName() {
    return name + " [" + getTotalValue() + "]";
  }

  // #endregion

  // #region Methods

  public void addBranch(Branch branch) {
    branches.add(branch);
  }

  public void removeBranch(Branch branch) {
    branches.remove(branches.indexOf(branch));
  }

  // #endregion
}
