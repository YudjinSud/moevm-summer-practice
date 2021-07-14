package com.moevm.practice.gui;

import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GuiUtils {

    public static void setBorderCanvas(Canvas canvas) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.BLACK);
        context.strokeRect(
                0,
                0,
                canvas.getWidth(),
                canvas.getHeight());
    }


    public static String appendTextToLog(String logText,
                                         String newText,
                                         TextArea log) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String time = formatter.format(date);
        logText += time + " " + newText + "\n";
        log.setText(logText);
        log.setScrollTop(Double.MAX_VALUE);
        return logText;
    }

    public static void setTextOnCanvas(String text, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
                text,
                Math.round(canvas.getWidth() / 2),
                Math.round(canvas.getHeight() / 2)
        );
        Stage stage = (Stage) canvas.getScene().getWindow();
        stage.show();
    }

    public static void clearCanvas(Canvas canvas) {
        canvas.getGraphicsContext2D().clearRect(1, 1, canvas.getWidth() - 1, canvas.getHeight() - 1);
    }

    static long stringToSeed(String s) {
        if (s == null) {
            return 0;
        }
        long hash = 0;
        for (char c : s.toCharArray()) {
            hash = 31L*hash + c;
        }
        return hash;
    }

    //https://stackoverflow.com/questions/4246351/creating-random-colour-in-java/21700660
    public static Color makeRandomColor(String seed) {
        Random rand = new Random(stringToSeed(seed));
        // Will produce only bright / light colours:
        double r = rand.nextFloat() / 2f + 0.5;
        double g = rand.nextFloat() / 2f + 0.5;
        double b = rand.nextFloat() / 2f + 0.5;
        return Color.color(r,b,g);
    }

}
