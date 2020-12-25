package net.mysmirnov.quiz.ui;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
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
    public Optional<String> read() {
        String line = scanner.nextLine();
        if (StringUtils.isAllBlank(line)) {
            return Optional.empty();
        } else {
            return Optional.of(line);
        }
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }


    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }


}
