package main.java;

public class Kickstarter implements Comparable<Kickstarter> {

    private int id;
    private double pledged;
    private double goal;
    private String category;
    private String name;
    private double percentage;

    public Kickstarter(int id, double pledged, double goal, String category, String name) {
        this.id = id;
        this.pledged = pledged;
        this.goal = goal;
        this.category = category;
        this.name = name;
        this.percentage = pledged/goal*100;
    }

    @Override
    public String toString() {
        return "Name: " + name + " - Category: " + category + " - Pledged: " + pledged + " - Goal: " + goal + " - percentage: " + percentage;
    }

    public int getId() {
        return id;
    }

    public double getPledged() {
        return pledged;
    }

    public void setPledged(double pledged) {
        this.pledged = pledged;
    }

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public int compareTo(Kickstarter o) {
        return Double.compare(this.percentage, o.percentage);
    }
}
