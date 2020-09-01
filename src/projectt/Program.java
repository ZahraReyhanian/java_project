package projectt;

import java.time.LocalDate;

public class Program {

    protected String lesson;
    protected String subject;
    ;
    protected LocalDate date;
    protected double ltime;
    protected double ftime;
    protected String note;
    protected String color;
    protected int UserID;
    protected String day;

    public Program(String color, String subject, double ftime, double ltime) {
        this.color = color;
        this.subject = subject;
        this.ftime = ftime;
        this.ltime = ltime;
    }

    public Program() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getFtime() {
        return ftime;
    }

    public void setFtime(double ftime) {
        this.ftime = ftime;
    }

    public double getLtime() {
        return ltime;
    }

    public void setLtime(double ltime) {
        this.ltime = ltime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
