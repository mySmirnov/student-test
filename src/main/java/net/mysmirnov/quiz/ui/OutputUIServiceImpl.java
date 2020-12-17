package net.mysmirnov.quiz.ui;

public class OutputUIServiceImpl implements OutputUIService {
    @Override
    public void write(Object s) {
        System.out.println(s);
    }
}
