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
    StringBuilder result = new StringBuilder("============= 订餐明细 =============\n");

    for (String s : selection) {
      String id = s.split(" x ")[0];
      int count = Integer.parseInt(s.split(" x ")[1]);
      result.append(String.format("%s x %d = %d元\n", getName(id), count, calculateEach(getPrice(id), count)));
    }
    int sum = calculateSum(selection);
    int firstSum = calculateFirstPromotion(sum);
    int secondSum = calculateSecondPromotion(selection);
    if (firstSum != sum || secondSum != sum) {
      result.append("-----------------------------------\n").append("使用优惠:\n");
      if (firstSum <= secondSum) {
        result.append(String.format("满30减6元，省%d元\n", sum - firstSum));
        sum = firstSum;
      }
      else {
        result.append(String.format("指定菜品半价(%s)，省%d元\n", getHalfPriceName(selection), sum - secondSum));
        sum = secondSum;
      }
    }
    result.append("-----------------------------------\n").append(String.format("总计：%d元\n", sum)).append("===================================");
    return result.toString();
  }

  public static String getName(String id) {
    String[] ids = getItemIds();
    String[] names = getItemNames();

    for (int i = 0; i < ids.length; i++) {
      if (ids[i].equals(id)) {
        return names[i];
      }
    }
    return "";
  }

  public static double getPrice(String id) {
    String[] ids = getItemIds();
    double[] prices = getItemPrices();

    for (int i = 0; i < ids.length; i++) {
      if (ids[i].equals(id)) {
        return prices[i];
      }
    }
    return 0;
  }

  public static int calculateEach(double price, double count) {
    return new Double(price * count).intValue();
  }

  public static int calculateSum(String[] selectedArray) {
    int sum = 0;
    for (String s : selectedArray) {
      String id = s.split(" x ")[0];
      double count = Double.parseDouble(s.split(" x ")[1]);
      sum += calculateEach(getPrice(id), count);
    }
    return sum;
  }

  public static int calculateFirstPromotion(int sum) {
    return (sum >= 30) ? sum - 6 : sum;
  }

  public static int calculateSecondPromotion(String[] selectedArray) {
    String[] newArray = selectedArray;
    List<String> halfItems = Arrays.asList(getHalfPriceIds());

    for (int i = 0; i < newArray.length; i++) {
      String id = newArray[i].split(" x ")[0];
      double count = Double.parseDouble(newArray[i].split(" x ")[1]);
      if (halfItems.contains(id)) {
        newArray[i] = id + " x " + count / 2;
      }
    }
    return calculateSum(newArray);
  }

  public static String getHalfPriceName(String[] selectedArray) {
    List<String> halfItems = Arrays.asList(getHalfPriceIds());
    StringBuilder result = new StringBuilder();

    for (String s : selectedArray) {
      String id = s.split(" x ")[0];
      if (halfItems.contains(id)) {
        result.append(getName(id)).append("，");
      }
    }
    return result.substring(0, result.length() - 1);
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
