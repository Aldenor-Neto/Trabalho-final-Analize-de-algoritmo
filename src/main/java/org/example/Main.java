package org.example;

import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class Main {

    // Função para gerar os valores das barras
    public static int[] generateSortedArray(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("O tamanho do vetor deve ser maior que 0.");
        }

        int[] array = new int[n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(+1000) + 1;
        }

        Arrays.sort(array);

        return array;
    }

    // Função para calcular o lucro máximo
    public static int rodCutting(int[] prices, int n, int[] cuts) {
        int[] array = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int maxValue = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                if (maxValue < prices[j - 1] + array[i - j]) {
                    maxValue = prices[j - 1] + array[i - j];
                    cuts[i] = j;
                }
            }
            array[i] = maxValue;
        }

        return array[n];
    }

    // Função para calcular o lucro máximo recursivamente com memorização
    public static int rodCuttingRecursive(int[] prices, int n, int[] cuts) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return rodCuttingRecursiveAux(prices, n, memo, cuts);
    }

    public static int rodCuttingRecursiveAux(int[] prices, int n, int[] memo, int[] cuts) {
        if (n == 0) {
            return 0;
        }

        if (memo[n] != -1) {
            return memo[n];
        }

        int maxValue = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            int tempValue = prices[i - 1] + rodCuttingRecursiveAux(prices, n - i, memo, cuts);
            if (maxValue < tempValue) {
                maxValue = tempValue;
                cuts[n] = i;
            }
        }

        memo[n] = maxValue;
        return maxValue;
    }

    public static void printCuts(int[] cuts, int n) {
        System.out.print("Cortes: ");
        while (n > 0) {
            System.out.print(cuts[n] + " ");
            n -= cuts[n];
        }
        System.out.println();
    }

    public static void main(String[] args) {

        //int[] bars = {1, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000};
        //int[] prices = generateSortedArray(bars[bars.length - 1]);

        int[] bars = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] prices = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        int[] cuts = new int[bars.length + 1];

        Long[] tempIterative = new Long[bars.length];
        int[] maxValueIterative = new int[bars.length];

        Long[] tempRecursive = new Long[bars.length];
        int[] maxValueRecursive = new int[bars.length];

        System.out.println("Dados");
        for (int i = 0; i < bars.length; i++) {
            Long mediaTempIterative = (long) 0;
            Long mediaTempRecursive = (long) 0;

            for (int j = 0; j < 5; j++) {
                Long startIterative = System.nanoTime();
                maxValueIterative[i] = rodCutting(prices, bars[i], cuts);
                Long endIterative = System.nanoTime();
                mediaTempIterative += endIterative - startIterative;

                Long startRecursive = System.nanoTime();
                maxValueRecursive[i] = rodCuttingRecursive(prices, bars[i], cuts);
                Long endRecursive = System.nanoTime();
                mediaTempRecursive += endRecursive - startRecursive;
            }

            tempIterative[i] = mediaTempIterative / 5;
            tempRecursive[i] = mediaTempRecursive / 5;

            System.out.println("Tamanho da Barra: " + bars[i]);
            System.out.println("valor da barra inteira: " + prices[bars[i] - 1]);
            System.out.println("Lucro maxímo: " + maxValueIterative[i]);
            System.out.println("Tempo iterativo: " + tempIterative[i]);
            System.out.println("Tempo recursivo: " + tempRecursive[i]);

            printCuts(cuts, bars[i]);
            System.out.println();
        }

        SwingUtilities.invokeLater(() -> {
            LineChart grafico = new LineChart(bars, tempIterative, tempRecursive);
            grafico.setSize(800, 600);
            grafico.setLocationRelativeTo(null);
            grafico.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            grafico.setVisible(true);
        });
    }
}