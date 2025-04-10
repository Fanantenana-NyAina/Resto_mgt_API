package entity;

import dao.StockMovementDAO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Ingredient {
    private int idIngredient;
    private String ingredientName;
    private double unitPrice;
    private Unit unit;
    private LocalDateTime updateDateTime;

    public Ingredient(int idIngredient, String ingredientName, double unitPrice, Unit unit, LocalDateTime updateDateTime) {
        this.idIngredient = idIngredient;
        this.ingredientName = ingredientName;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.updateDateTime = updateDateTime;
    }

    public Ingredient(int idIngredient, String ingredientName, Unit unit) {
        this.idIngredient = idIngredient;
        this.ingredientName = ingredientName;
        this.unit = unit;
    }

    public double getStockMvtStateAtDate(LocalDateTime dateChoice) {
        StockMovementDAO stockMovementDAO = new StockMovementDAO();
        if (dateChoice == null || dateChoice.toString().isEmpty()) {
            dateChoice = LocalDateTime.now();
        }

        double quantityInStock = 0;
        List<StockMovement> stockMovements = stockMovementDAO.getAllByIdBeforeDate(idIngredient, dateChoice);

        for (StockMovement stockMovement : stockMovements) {
            if (stockMovement.getMovement() == Movement.IN) {
                quantityInStock += stockMovement.getQuantity();
            } else if (stockMovement.getMovement() == Movement.OUT) {
                quantityInStock -= stockMovement.getQuantity();
            }
        }

        return quantityInStock;
    }

    public double getAvalaibleQuantityInStock() {
        LocalDateTime dateChoice = LocalDateTime.now();
        StockMovementDAO stockMovementDAO = new StockMovementDAO();
        if (dateChoice == null || dateChoice.toString().isEmpty()) {
            dateChoice = LocalDateTime.now();
        }

        double quantityInStock = 0;
        List<StockMovement> stockMovements = stockMovementDAO.getAllByIdBeforeDate(idIngredient, dateChoice);

        for (StockMovement stockMovement : stockMovements) {
            if (stockMovement.getMovement() == Movement.IN) {
                quantityInStock += stockMovement.getQuantity();
            } else if (stockMovement.getMovement() == Movement.OUT) {
                quantityInStock -= stockMovement.getQuantity();
            }
        }

        return quantityInStock;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "idIngredient=" + idIngredient +
                ", ingredientName='" + ingredientName + '\'' +
                ", unitPrice=" + unitPrice +
                ", unit=" + unit +
                ", updateDateTime=" + updateDateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return idIngredient == that.idIngredient && Double.compare(unitPrice, that.unitPrice) == 0 && Objects.equals(ingredientName, that.ingredientName) && unit == that.unit && Objects.equals(updateDateTime, that.updateDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIngredient, ingredientName, unitPrice, unit, updateDateTime);
    }
}
