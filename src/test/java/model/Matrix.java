package model;

import java.util.List;

public class Matrix {
    private String uuid;
    private int statusCode;
    private String cardHolder;
    private List<CardsInner> cards;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public List<CardsInner> getCards() {
        return cards;
    }

    public void setCards(List<CardsInner> cards) {
        this.cards = cards;
    }
}
