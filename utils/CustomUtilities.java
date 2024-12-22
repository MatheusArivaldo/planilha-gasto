package utils;

public class CustomUtilities {
  public static String formatToCurrency(double value) {
    StringBuilder formattedValue = new StringBuilder();

    // Adicionar símbolo de moeda (R$ para Real Brasileiro)
    formattedValue.append("R$ ");

    // Garantir que o número tenha sempre duas casas decimais
    String formattedNumber = String.format("%.2f", value);

    // Identificar separador decimal, independentemente do local
    char decimalSeparator = formattedNumber.contains(".") ? '.' : ',';
    int decimalIndex = formattedNumber.lastIndexOf(decimalSeparator);

    if (decimalIndex == -1) {
      throw new IllegalStateException("Erro ao localizar o separador decimal.");
    }

    // Separar a parte inteira e decimal
    String wholePart = formattedNumber.substring(0, decimalIndex);
    String decimalPart = formattedNumber.substring(decimalIndex + 1);

    // Adicionar separadores de milhar
    for (int i = 0; i < wholePart.length(); i++) {
      if (i > 0 && (wholePart.length() - i) % 3 == 0) {
        formattedValue.append('.');
      }
      formattedValue.append(wholePart.charAt(i));
    }

    // Adicionar separador decimal e a parte decimal
    formattedValue.append(",").append(decimalPart);

    return formattedValue.toString();
  }
}
