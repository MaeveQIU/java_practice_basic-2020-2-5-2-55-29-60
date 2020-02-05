package com.thoughtworks;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("请点餐（菜品Id x 数量，用逗号隔开）：");
    String selectedItems = scan.nextLine();
    String summary = bestCharge(selectedItems);
    System.out.println(summary);
  }

  /**
   * 接收用户选择的菜品和数量，返回计算后的汇总信息
   *
   * @param selectedItems 选择的菜品信息
   */
  public static String bestCharge(String selectedItems) {
    String[] selection = selectedItems.split(",");
    String result = "";

    result += "============= 订餐明细 =============\n";
    for (String s : selection) {
      String id = s.substring(0, 8);
      int count = Integer.parseInt(s.substring(11));
      result += getName(id) + " x " + count + " = " + (calculateEach(getPrice(id), count)) + "元\n";
    }
    int sum = calculateSum(selection);
    int firstSum = calculateFirstPromotion(sum);
    int secondSum = calculateSecondPromotion(selection);
    if (firstSum != sum && secondSum != sum) {
      result += "-----------------------------------\n" + "使用优惠:\n";
      String promotionType = comparePromotion(firstSum, secondSum);
      if (promotionType.equals("First Promotion")) {
        result += "满30减6元，省" + calculateDiscount(sum, firstSum) + "元\n";
        sum = firstSum;
      }
      else if (promotionType.equals("Second Promotion")) {
        result += "指定菜品半价(" + getHalfPriceName(selection);
        result += ")，省" + calculateDiscount(sum, secondSum) + "元\n";
        sum = secondSum;
      }
    }
    result += "-----------------------------------\n";
    result += "总计：" + sum + "元\n";
    result += "===================================";
    return result;
  }

  public static String getName(String id) {
    String[] ids = getItemIds();
    String[] names = getItemNames();
    String result = "";

    for (int i = 0; i < ids.length; i++) {
      if (ids[i].equals(id)) {
        result = names[i];
      }
    }
    return result;
  }

  public static double getPrice(String id) {
    String[] ids = getItemIds();
    double[] prices = getItemPrices();
    double result = 0;

    for (int i = 0; i < ids.length; i++) {
      if (ids[i].equals(id)) {
        result = prices[i];
      }
    }
    return result;
  }

  public static String getHalfPriceName(String[] selectedArray) {
    List<String> halfItems = Arrays.asList(getHalfPriceIds());
    String result = "";

    for (String s : selectedArray) {
      String id = s.substring(0, 8);
      if (halfItems.contains(id)) {
        result += getName(id) + "，";
      }
    }
    return result.substring(0, result.length() - 1);
  }

  public static int calculateEach(double price, double count) {
    int i = (new Double(price * count)).intValue();
    return i;
  }

  public static int calculateSum(String[] selectedArray) {
    int sum = 0;
    for (String s : selectedArray) {
      String id = s.substring(0, 8);
      double count = Double.parseDouble(s.substring(11));
      sum += calculateEach(getPrice(id), count);
    }
    return sum;
  }

  public static int calculateFirstPromotion(int sum) {
    if (sum > 30) {
      return sum - 6;
    }
    else {
      return sum;
    }
  }

  public static int calculateSecondPromotion(String[] selectedArray) {
    String[] newArray = selectedArray;
    List<String> halfItems = Arrays.asList(getHalfPriceIds());

    for (int i = 0; i < newArray.length; i++) {
      String id = newArray[i].substring(0, 8);
      double count = Double.parseDouble(newArray[i].substring(11));
      if (halfItems.contains(id)) {
        newArray[i] = id + " x " + count / 2;
      }
    }
    return calculateSum(newArray);
  }

  public static String comparePromotion(int promotionOne, int promotionTwo) {
    if (promotionOne <= promotionTwo) {
      return "First Promotion";
    }
    else {
      return "Second Promotion";
    }
  }

  public static int calculateDiscount(int sum, int finalSum) {
    return sum - finalSum;
  }



  /**
   * 获取每个菜品依次的编号
   */
  public static String[] getItemIds() {
    return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
  }

  /**
   * 获取每个菜品依次的名称
   */
  public static String[] getItemNames() {
    return new String[]{"黄焖鸡", "肉夹馍", "凉皮", "冰粉"};
  }

  /**
   * 获取每个菜品依次的价格
   */
  public static double[] getItemPrices() {
    return new double[]{18.00, 6.00, 8.00, 2.00};
  }

  /**
   * 获取半价菜品的编号
   */
  public static String[] getHalfPriceIds() {
    return new String[]{"ITEM0001", "ITEM0022"};
  }
}
