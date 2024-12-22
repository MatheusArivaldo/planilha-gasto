package models;

import utils.CustomArrayList;
import utils.DuplicatedException;

public class Sheet {
  private CustomArrayList<Year> years = new CustomArrayList<Year>();

  // #region Getters and Setters

  public CustomArrayList<Year> getYears() {
    return years;
  }

  public Year getYearByNumber(int year) {
    for (int i = 0; i < years.size(); i++) {
      if (years.get(i).getYear() == year)
        return years.get(i);
    }
    return null;
  }

  // #endregion

  // #region Methods

  public boolean addYear(Year year) {
    for (int i = 0; i < years.size(); i++) {
      if (years.get(i).getYear() == year.getYear()) {
        throw new DuplicatedException("Ano " + year.getYear() + " já existe.");
      }
    }

    years.add(year);
    return true;
  }

  // #endregion
}
