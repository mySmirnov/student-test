package net.mysmirnov.quiz.ui;

import java.util.Scanner;

public class InputUIServiceImpl implements InputUIService {
    private Scanner scanner;

    public void init() {
        scanner = new Scanner(System.in);
    }

    public void destroy() {
        scanner.close();
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
