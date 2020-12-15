package net.mySmirnov.quiz.ui;

import java.util.Scanner;

public class InputUIImpl implements InputUI {
    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        return s;
    }
}
