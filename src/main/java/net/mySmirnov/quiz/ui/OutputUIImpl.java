package net.mySmirnov.quiz.ui;

public class OutputUIImpl implements OutputUI {
    @Override
    public void write(Object s) {
        System.out.println(s);
    }
}
