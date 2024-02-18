package org.nlpcn.commons.lang.finger.pojo;

public class MyFingerprint implements Comparable<MyFingerprint> {
    private String name;
    private double score  = 0;
    private double weight = 0;

    /**
     * 
     * @param name
     * @param category
     * @param userCategory
     */
    public MyFingerprint(String name, double freq, double factory) {
        this.name = name;
        this.weight = -Math.log(10000 / (freq + 1));
        this.score = weight * factory;

    }

    public void updateScore(double factor) {
        this.score += factor * weight;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int compareTo(MyFingerprint myTag) {
        // TODO Auto-generated method stub
        if (this.score < myTag.score) {
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return this.name + ":" + ":" + score;
    }

    public static void main(String[] args) {
        System.out.println("aaa".length() == "aaa".getBytes().length);
    }
}
