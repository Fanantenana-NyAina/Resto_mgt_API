package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class IngredientPriceHistory {
    private int idPriceHistory;
    private int idIngredient;
    private double price;
    private LocalDateTime historyDate;

    public IngredientPriceHistory(int idPriceHistory, int idIngredient, double price, LocalDateTime historyDate) {
        this.idPriceHistory = idPriceHistory;
        this.idIngredient = idIngredient;
        this.price = price;
        this.historyDate = historyDate;
    }

    public int getIdPriceHistory() {
        return idPriceHistory;
    }

    public void setIdPriceHistory(int idPriceHistory) {
        this.idPriceHistory = idPriceHistory;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(LocalDateTime historyDate) {
        this.historyDate = historyDate;
    }

    @Override
    public String toString() {
        return "ingredientPriceHistory{" +
                "idPriceHistory=" + idPriceHistory +
                ", idIngredient=" + idIngredient +
                ", price=" + price +
                ", historyDate=" + historyDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IngredientPriceHistory that = (IngredientPriceHistory) o;
        return idPriceHistory == that.idPriceHistory && idIngredient == that.idIngredient && Double.compare(price, that.price) == 0 && Objects.equals(historyDate, that.historyDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPriceHistory, idIngredient, price, historyDate);
    }
}
