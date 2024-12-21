package models;

import utils.CustomArrayList;

public class Year {
  private int year;
  private CustomArrayList<Category> categories;

  public Year(int year) {
    this.year = year;
    this.categories = new CustomArrayList<Category>();
  }

  // #region Getters and Setters

  public int getYear() {
    return year;
  }

  public CustomArrayList<Category> getCategories() {
    return categories;
  }

  public double getTotalValue() {
    double totalValue = 0.0;
    for (int i = 0; i < categories.size(); i++) {
      totalValue += categories.get(i).getTotalValue();
    }
    return totalValue;
  }

  // #endregion

  // #region Methods

  public void addCategory(Category category) {
    for (int i = 0; i < categories.size(); i++) {
      if (categories.get(i).getName().equals(category.getName())) {
        throw new IllegalArgumentException("Categoria " + category.getName() + " já existe.");
      }
    }

    categories.add(category);
  }

  public void removeCategory(Category category) {
    categories.remove(categories.indexOf(category));
  }

  // #endregion
}
