package net.mysmirnov.quiz.ui;

import java.util.Scanner;

public class InputUIServiceImpl implements InputUIService {
    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        return s;
    }
}
