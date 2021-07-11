package com.moevm.practice;

import com.moevm.practice.cli.CLI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

import com.moevm.practice.gui.GuiLoader;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        GuiLoader.run(stage);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вы хотите использовать CLI(1) или GUI(2) ?");
        //int res = scanner.nextInt();
        int res = 1;
        switch (res) {
            case 1:
                CLI cli = new CLI();
                cli.mainCLI();
                break;
            case 2:
                Application.launch();
                break;
            default:
                System.out.println("Нет такого варианта!");
        }
    }

}