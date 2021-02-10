package net.mysmirnov.quiz.model;

import java.sql.Date;

public class Attempt {
    private int id;
    private Date date;
    private double result;


    public Attempt() {
    }

    public Attempt(int id, Date date, double result) {
        this.id = id;
        this.date = date;
        this.result = result;
    }

    public Attempt(Date date, double result) {
        this.date = date;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attempt)) return false;

        Attempt attempt = (Attempt) o;

        if (getId() != attempt.getId()) return false;
        return Double.compare(attempt.getResult(), getResult()) == 0;
    }

    @Override
    public int hashCode() {
        int result1;
        long temp;
        result1 = getId();
        temp = Double.doubleToLongBits(getResult());
        result1 = 31 * result1 + (int) (temp ^ (temp >>> 32));
        return result1;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "id=" + id +
                ", date=" + date +
                ", result=" + result +
                '}';
    }
}
