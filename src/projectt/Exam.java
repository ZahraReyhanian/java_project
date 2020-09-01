package projectt;

import java.time.LocalDate;
import java.time.Period;

public class Exam extends Program {

    public Exam(String subject, String lesson, LocalDate date, String note, double ftime, double ltime, String day) {
        this.date = date;
        this.subject = subject;
        this.lesson = lesson;
        this.date = date;
        this.ftime = ftime;
        this.ltime = ltime;
        this.note = note;
        this.day = day;

    }

    public Exam() {

    }

}
