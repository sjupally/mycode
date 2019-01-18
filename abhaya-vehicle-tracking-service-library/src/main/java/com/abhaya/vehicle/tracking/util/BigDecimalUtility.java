package com.abhaya.vehicle.tracking.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BigDecimalUtility {

  public static int twoDecimal = 2;
  public static int threeDecimal = 3;
  public static int sixDecimal = 6;
  public static Locale localeValue = Locale.US;

  public static String formatPriceWithSixDecimalCeiling(String value) {
    String formattedValue = "0.00";
    if (!StringUtils.isEmpty(value)) {
      BigDecimal bigDecimal = new BigDecimal(value.replaceAll(",", "")).setScale(sixDecimal,
          BigDecimal.ROUND_HALF_UP);
      /*NumberFormat format = NumberFormat.getCurrencyInstance(localeValue);
      String dAccountValue = format.format(dAccountValue);*/
      Double stripedValue = bigDecimal.stripTrailingZeros().doubleValue();
      DecimalFormat decimalFormat = new DecimalFormat();
      decimalFormat.setGroupingUsed(true);
      decimalFormat.setMinimumFractionDigits(2);
      decimalFormat.setMaximumFractionDigits(6);
      formattedValue = decimalFormat.format(stripedValue);
    }
    return formattedValue;
  }

  public static String formatPriceWithTwoDecimalCeiling(String value) {
    String formattedValue = "0.00";
    if (!StringUtils.isEmpty(value)) {
      BigDecimal bigDecimal = new BigDecimal(value.replaceAll(",", "")).setScale(twoDecimal,
          BigDecimal.ROUND_HALF_UP);
      /*NumberFormat format = NumberFormat.getCurrencyInstance(localeValue);
			double dAccountValue = bigDecimal.doubleValue();*/
      formattedValue = bigDecimal.toPlainString();
    }
    return formattedValue;
  }

  public static String formatPriceWithThreeDecimalCeiling(String value) {
    String formattedValue = "0.000";
    if (!StringUtils.isEmpty(value)) {
      BigDecimal bigDecimal = new BigDecimal(value.replaceAll(",", "")).setScale(threeDecimal, BigDecimal.ROUND_HALF_UP);
      formattedValue = bigDecimal.toPlainString();
    }
    return formattedValue;
  }


  public static Double formatPriceWithSixDecimalCeilingIntoDouble(String value) {
    Double formattedValue = 0d;
    if (!StringUtils.isEmpty(value)) {
      BigDecimal bigDecimal = new BigDecimal(value.replaceAll(",", "")).setScale(sixDecimal,
          BigDecimal.ROUND_HALF_UP);
      /*NumberFormat format = NumberFormat.getCurrencyInstance(localeValue);
			String dAccountValue = format.format(dAccountValue);*/
      formattedValue = bigDecimal.doubleValue();
    }
    return formattedValue;
  }

  public static Double formatPriceWithTwoDecimalCeilingIntoDouble(String value) {
    Double formattedValue = 0d;
    if (!StringUtils.isEmpty(value)) {
      BigDecimal bigDecimal = new BigDecimal(value.replaceAll(",", "")).setScale(twoDecimal,
          BigDecimal.ROUND_HALF_UP);
      formattedValue = bigDecimal.doubleValue();
    }
    return formattedValue;
  }

  public static String calculateExchangeRate(String amountToBeConverted, String conversionRate) {
    String returnVal = amountToBeConverted;
    if (!StringUtils.isEmpty(amountToBeConverted) && !StringUtils.isEmpty(conversionRate)) {
      BigDecimal bdAmountToBeConverted = new BigDecimal(amountToBeConverted.replaceAll(",", "")).setScale(sixDecimal, BigDecimal.ROUND_HALF_UP);
      BigDecimal bdCconversionRate = new BigDecimal(conversionRate.replaceAll(",", ""));
      BigDecimal convertedValue = bdAmountToBeConverted.divide(bdCconversionRate, BigDecimal.ROUND_HALF_UP);
      returnVal = BigDecimalUtility.formatPriceWithSixDecimalCeiling(convertedValue.stripTrailingZeros().toPlainString());
    }

    return returnVal;
  }

  public static String getNumberFormatInstance(Double value) {
    String formattedValue = "0.00";
    NumberFormat numberFormat = NumberFormat.getInstance(localeValue);
    if (null != value) {
      formattedValue = numberFormat.format(value);
    }

    return formattedValue;
  }

  public static String getNumberFormatCurrencyInstance(Double value) {
    String formattedValue = "0.00";
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeValue);
    if (null != value) {
      formattedValue = numberFormat.format(value);
    }

    return formattedValue;
  }


  public static String halfRoundUpTwoDecimal(Double value) {
    String formattedValue = "0.00";
    if (!StringUtils.isEmpty(value)) {
      BigDecimal bigDecimal = new BigDecimal(value).setScale(twoDecimal, BigDecimal.ROUND_HALF_UP);
      formattedValue = bigDecimal.stripTrailingZeros().toPlainString().trim();
    }
    return formattedValue;
  }

  public static String halfRoundUpSixDecimal(Double value) {
    String formattedValue = "0.00";
    if (!StringUtils.isEmpty(value)) {
      BigDecimal bigDecimal = new BigDecimal(value).setScale(sixDecimal, BigDecimal.ROUND_HALF_UP);
      formattedValue = bigDecimal.stripTrailingZeros().toPlainString().trim();
    }
    return formattedValue;
  }

 /* public static String roundCeilingDoubleWithoutDecimal(String value) {
    String returnVal = "0";
    if (isNotEmpty(value) && NumberUtils.isNumber(value.replaceAll(",", ""))) {
      BigDecimal bigDecimal = new BigDecimal(value.replaceAll(",", "")).setScale(0, BigDecimal.ROUND_CEILING);
      returnVal = bigDecimal.stripTrailingZeros().toPlainString();
    }
    return returnVal;
  }
*/
  public static Double formatStringToDouble(String value) {
    Double formattedValue = 0d;
    if (!StringUtils.isEmpty(value)) {
      BigDecimal bigDecimal = new BigDecimal(value.replaceAll(",", ""));
      formattedValue = bigDecimal.doubleValue();
    }
    return formattedValue;
  }

  /**
   * Method to check whether the passed object is Not empty or not.
   *
   * @param obj - to be checked.
   * @return - <code>true</code> if the object is not empty.
   */
  public static boolean isNotEmpty(final Object obj) {
    boolean returnVal = false;
    try {
      if (obj != null) {
        if (obj instanceof String) {
          String str = (String) obj;
          if (str.trim().length() > 0) {
            returnVal = true;
          }
        } else if (obj instanceof Collection) {
          Collection col = (Collection) obj;
          if (!col.isEmpty()) {
            returnVal = true;
          }
        } else if (obj instanceof Double) {
          Double dbl = (Double) obj;
          if (!dbl.equals(0d)) {
            returnVal = true;
          }
        }
      }
    } catch (Exception e) {
      log.error("=== Error from Utility.isNotEmpty() " + e.getMessage());
    }

    return returnVal;
  }

  public static String parseAndRoundCeilingDouble(Double value) {
    String returnVal = "0.00";
    if (isNotEmpty(value)) {
      BigDecimal bigDecimal = new BigDecimal(value).setScale(0, BigDecimal.ROUND_CEILING);
      returnVal = bigDecimal.stripTrailingZeros().toPlainString().trim();
    }
    return returnVal;
  }

  public static Boolean isPriceEqual(String sourcePrice, String targetprice) {

    Boolean isEqual = false;

    BigDecimal sourceBigDecimal = new BigDecimal(sourcePrice.replaceAll(",", ""));
    BigDecimal targetBigDecimal = new BigDecimal(targetprice.replaceAll(",", ""));
    if (sourceBigDecimal.compareTo(targetBigDecimal) == 0)
      isEqual = true;

    return isEqual;

  }

  public static String getInverse(String input) {
    String returnVal = "0.00";
    if (!StringUtils.isEmpty(input)) {
      BigDecimal bigDecimalInput = new BigDecimal(input);
      if (!StringUtils.isEmpty(bigDecimalInput) && bigDecimalInput.doubleValue() > BigDecimal.ZERO.doubleValue()) {
        BigDecimal bigDecimalOutput = new BigDecimal(1);
        bigDecimalOutput = bigDecimalOutput.divide(bigDecimalInput, twoDecimal, BigDecimal.ROUND_UP);
        returnVal = bigDecimalOutput.toPlainString();
      }
    }
    return returnVal;
  }

  public static String trailingZerosAfterTwoDecimalss(String value) {
    String formattedValue = "0.00";
    if (!StringUtils.isEmpty(value)) {
      DecimalFormat df = new DecimalFormat("0.00########");
      formattedValue = df.format(new BigDecimal(value).stripTrailingZeros());
    }
    return formattedValue;
  }

}
