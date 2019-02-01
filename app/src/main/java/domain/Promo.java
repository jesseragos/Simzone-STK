package domain;

/**
 * Created by Ragos Bros on 2/9/2017.
 */
public class Promo {

    private String keyword,
                  description,
                  sendTo;
    private double cost;

    public Promo(String keyword, String description, String sendTo, double cost) {
        this.keyword = keyword;
        this.description = description;
        this.sendTo = sendTo;
        this.cost = cost;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getDescription() {
        return description;
    }

    public String getSendTo() {
        return sendTo;
    }

    public double getCost() {
        return cost;
    }

}
