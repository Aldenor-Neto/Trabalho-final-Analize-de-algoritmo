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
    public static int rodCutting(int[] prices, int n) {
        // Array para armazenar os lucros máximos para cada comprimento
        int[] array = new int[n + 1];

        // Loop para calcular o lucro máximo para cada comprimento
        for (int i = 1; i <= n; i++) {
            int maxValue = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                maxValue = Math.max(maxValue, prices[j - 1] + array[i - j]);
            }
            array[i] = maxValue;
        }

        return array[n];
    }

    // Função para calcular o lucro máximo recursivamente com memorização
    public static int rodCuttingRecursive(int[] prices, int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return rodCuttingRecursiveAux(prices, n, memo);
    }

    public static int rodCuttingRecursiveAux(int[] prices, int n, int[] memo) {
        if (n == 0) {
            return 0;
        }

        if (memo[n] != -1) {
            return memo[n];
        }

        int maxValue = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            maxValue = Math.max(maxValue, prices[i - 1] + rodCuttingRecursiveAux(prices, n - i, memo));
        }

        memo[n] = maxValue;
        return maxValue;
    }

    public static void main(String[] args) {

        // Exemplo de uso
        int[] bars = {1, 50, 250, 1000, 15000};
        int[] prices = generateSortedArray(bars[bars.length - 1]);

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
                maxValueIterative[i] = rodCutting(prices, bars[i]);
                Long endIterative = System.nanoTime();
                mediaTempIterative += endIterative - startIterative;

                Long startRecursive = System.nanoTime();
                maxValueRecursive[i] = rodCuttingRecursive(prices, bars[i]);
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