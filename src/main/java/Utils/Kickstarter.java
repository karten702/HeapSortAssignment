package main.java.Utils;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Kickstarter proj = (Kickstarter) obj;
        return this.id == proj.id;
    }

    @Override
    public int compareTo(Kickstarter o) {
        if (this.percentage < o.percentage)
            return 1;           // Neither val is NaN, thisVal is smaller
        if (this.percentage > o.percentage)
            return -1;            // Neither val is NaN, thisVal is larger

        // Cannot use doubleToRawLongBits because of possibility of NaNs.
        long thisBits    = Double.doubleToLongBits(this.percentage);
        long anotherBits = Double.doubleToLongBits(o.percentage);
        if (thisBits != anotherBits)
            return thisBits > anotherBits ? -1 : 1;

        if (this.goal < o.goal)
            return 1;           // Neither val is NaN, thisVal is smaller
        if (this.goal > o.goal)
            return -1;            // Neither val is NaN, thisVal is larger

        // Cannot use doubleToRawLongBits because of possibility of NaNs.
        thisBits    = Double.doubleToLongBits(this.goal);
        anotherBits = Double.doubleToLongBits(o.goal);
        if (thisBits != anotherBits)
            return thisBits > anotherBits ? -1 : 1;

        return Integer.compare(this.id, o.id);

    }
}
