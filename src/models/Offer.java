package models;

public class Offer implements Comparable<Offer>{
    public Store store;
    public String title;
    public String description;
    public float old_price;
    public float percentage;
    public String filename;
    public String category;
    public int visits = 0;

    @Override
    public int compareTo(Offer offer) {
        return (this.visits>offer.visits)?-1:(this.visits==offer.visits)?0:1;
    }
}
