package au.edu.jcu.braintrainermaths;

import java.util.ArrayList;

public class Person {

    public static ArrayList<Person> personArrayList = new ArrayList<>();

    private int id;
    private String name;
    private String score;

    public Person(String name, String score) {
        this.id = personArrayList.size();
        this.name = name;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
