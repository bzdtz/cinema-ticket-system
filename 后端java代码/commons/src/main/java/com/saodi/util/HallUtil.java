package com.saodi.util;

import java.util.Arrays;

public class HallUtil {

    public static String twoDimensionalArrayToString(int[][] array) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int[] row : array) {
            builder.append(Arrays.toString(row));

            builder.append(",");
        }
        builder.deleteCharAt(builder.length()-1);

        builder.append(']');
        return builder.toString();
    }

//    public static int[][] parseStringToArray(String input) {
//        // 去除字符串中的空格和方括号
//        String cleanedInput = input.replaceAll("\\s+", "").replaceAll("\\[|\\]", "");
//
//        // 按逗号分割字符串，得到每个数字的字符串表示
//        String[] numberStrings = cleanedInput.split(",");
//
//        // 计算二维数组的行数和列数
//        int rows = (int) Math.sqrt(numberStrings.length);
//        int cols = rows;
//
//        // 创建二维数组
//        int[][] array = new int[rows][cols];
//
//        // 将字符串转换为整数并填充二维数组
//        int index = 0;
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                array[i][j] = Integer.parseInt(numberStrings[index]);
//                index++;
//            }
//        }
//
//        return array;
//    }
//    public static boolean isRepeated(int[][] a,int[][] b){
//
//    }

}
