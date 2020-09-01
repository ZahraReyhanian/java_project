package projectt;

public class Lesson {

    private String vahed;
    private String professer;
    private String nameLesson;
    private String color;

    public Lesson(String nameLesson, String professer, String color, String vahed) {
        this.vahed = vahed;
        this.professer = professer;
        this.nameLesson = nameLesson;
        this.color = color;

    }

    public String getNameLesson() {
        return nameLesson;
    }

    public void setNameLesson(String nameLesson) {
        this.nameLesson = nameLesson;
    }

    public String getProfesser() {
        return professer;
    }

    public void setProfesser(String professer) {
        this.professer = professer;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVahed() {
        return vahed;
    }

    public void setVahed(String vahed) {
        this.vahed = vahed;
    }

}
