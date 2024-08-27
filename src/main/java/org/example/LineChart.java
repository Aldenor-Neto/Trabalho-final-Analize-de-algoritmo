package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import javax.swing.*;
import java.awt.*;

public class LineChart extends JFrame {

    public LineChart(int[] x, Long[] yIterative, Long[] yRecursive) {
        XYSeries seriesIterative = new XYSeries("Iterativo");
        XYSeries seriesRecursive = new XYSeries("Recursivo");

        for (int i = 0; i < x.length; i++) {
            seriesIterative.add(x[i], yIterative[i]);
            seriesRecursive.add(x[i], yRecursive[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesIterative);
        dataset.addSeries(seriesRecursive);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Gráfico Cortes de barras",
                "Barras",
                "Tempo em Nanossegundos / 2000",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // Definir cores das linhas
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);

        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }
}
