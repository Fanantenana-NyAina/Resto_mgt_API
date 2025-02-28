package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class StockMovement {
    private final int IdMovement;
    private final int IdIngredient;
    private final String ingredientName;
    private final Movement movement;
    private final double quantity;
    private final Unit unit;
    private final LocalDateTime movementDateTime;


    public StockMovement(int idMovement, int idIngredient, String ingredientName, double quantity, Unit unit, Movement movement, LocalDateTime movementDateTime) {
        IdMovement = idMovement;
        IdIngredient = idIngredient;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
        this.movement = movement;
        this.movementDateTime = movementDateTime;
    }

    public int getIdIngredient() {
        return IdIngredient;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public int getIdMovement() {
        return IdMovement;
    }

    public Movement getMovement() {
        return movement;
    }

    public double getQuantity() {
        return quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public LocalDateTime getMovementDateTime() {
        return movementDateTime;
    }



    @Override
    public String toString() {
        return "StockMovement{" +
                "IdMovement=" + IdMovement +
                ", ingredientName='" + ingredientName + '\'' +
                ", movement=" + movement +
                ", quantity=" + quantity +
                ", unit=" + unit +
                ", movementDateTime=" + movementDateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StockMovement that = (StockMovement) o;
        return IdMovement == that.IdMovement && Double.compare(quantity, that.quantity) == 0 && Objects.equals(ingredientName, that.ingredientName) && movement == that.movement && unit == that.unit && Objects.equals(movementDateTime, that.movementDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdMovement, ingredientName, movement, quantity, unit, movementDateTime);
    }
}
