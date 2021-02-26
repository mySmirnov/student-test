package net.mysmirnov.quiz.service.ui;

public class OutputUIServiceImpl implements OutputUIService {
    @Override
    public void write(Object s) {
        System.out.println(s);
    }
}
