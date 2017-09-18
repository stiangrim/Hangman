package com.example.stiangrim.hangman;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.stiangrim.hangman.Model.StatisticsHandler;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class Statistics extends AppCompatActivity {

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        createPieChart();
    }

    private void createPieChart() {
        pieChart = (PieChart) findViewById(R.id.piegraph);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(StatisticsHandler.getWins(this), "Wins"));
        pieEntries.add(new PieEntry(StatisticsHandler.getLosses(this), "Losses"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        int[] pieColors = {Color.rgb(0, 153, 0), Color.rgb(153, 0, 0)};
        pieDataSet.setColors(pieColors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(50f);
        pieData.setValueTextColor(Color.WHITE);

        pieChart.setData(pieData);
        pieChart.animateY(2000, Easing.EasingOption.EaseOutSine);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);

        pieChart.setHoleColor(Color.rgb(228, 227, 219));
        pieChart.setHoleRadius(45);
    }
}
