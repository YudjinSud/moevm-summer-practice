package com.moevm.practice.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
}
