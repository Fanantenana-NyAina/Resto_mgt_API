package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class StockMovement {
    private int IdMovement;
    private int IdIngredient;
    private String ingredientName;
    private Movement movement;
    private double quantity;
    private Unit unit;
    private LocalDateTime movementDateTime;

    public StockMovement(int idMovement, int idIngredient, Movement movement, double quantity, Unit unit, LocalDateTime movementDateTime) {
        IdMovement = idMovement;
        IdIngredient = idIngredient;
        this.movement = movement;
        this.quantity = quantity;
        this.unit = unit;
        this.movementDateTime = movementDateTime;
    }

    public StockMovement(int idMovement, String ingredientName, double quantity, Unit unit, Movement movement, LocalDateTime movementDateTime) {
        IdMovement = idMovement;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
        this.movement = movement;
        this.movementDateTime = movementDateTime;
    }

    public int getIdMovement() {
        return IdMovement;
    }

    public void setIdMovement(int idMovement) {
        IdMovement = idMovement;
    }

    public int getIdIngredient() {
        return IdIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        IdIngredient = idIngredient;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public LocalDateTime getMovementDateTime() {
        return movementDateTime;
    }

    public void setMovementDateTime(LocalDateTime movementDateTime) {
        this.movementDateTime = movementDateTime;
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
        return IdMovement == that.IdMovement && IdIngredient == that.IdIngredient && Double.compare(quantity, that.quantity) == 0 && Objects.equals(ingredientName, that.ingredientName) && movement == that.movement && unit == that.unit && Objects.equals(movementDateTime, that.movementDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdMovement, IdIngredient, ingredientName, movement, quantity, unit, movementDateTime);
    }
}
