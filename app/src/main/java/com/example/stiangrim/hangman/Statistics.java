package com.example.stiangrim.hangman;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.stiangrim.hangman.Model.StatisticsHandler;
import com.example.stiangrim.hangman.Model.ToastHandler;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        createPieChart();
    }

    private void createPieChart() {
        PieChart pieChart = (PieChart) findViewById(R.id.piegraph);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(StatisticsHandler.getWins(this), getString(R.string.wins)));
        pieEntries.add(new PieEntry(StatisticsHandler.getLosses(this), getString(R.string.losses)));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        int[] pieColors = {Color.rgb(0, 153, 0), Color.rgb(153, 0, 0)};
        pieDataSet.setColors(pieColors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(30f);
        pieData.setValueTextColor(Color.WHITE);

        pieChart.setData(pieData);
        pieChart.animateY(2000, Easing.EasingOption.EaseOutSine);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);

        pieChart.setHoleColor(Color.rgb(228, 227, 219));
        pieChart.setHoleRadius(45);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void clearData(View view) {
        final Context context = this;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.delete_confirmation)
                .setCancelable(false)
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StatisticsHandler.clearAllData(context);
                        Intent intent = new Intent(context, Statistics.class);
                        startActivity(intent);

                        ToastHandler toastHandler = new ToastHandler(context);
                        toastHandler.showDataClearedToast(2500);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle(R.string.clear_data);
        alertDialog.show();
    }
}
