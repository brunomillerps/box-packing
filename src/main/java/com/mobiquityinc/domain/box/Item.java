package com.mobiquityinc.domain.box;

import java.util.Objects;

/**
 * A representation of a box item
 */
public class Item {
    private int index;
    private double weight;
    private double value;

    public Item(int index, double weight, double value) {
        this.index = index;
        this.weight = weight;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return index == item.index &&
                Double.compare(item.weight, weight) == 0 &&
                Double.compare(item.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, weight, value);
    }

    @Override
    public String toString() {
        return "\nItem{" +
                "index=" + index +
                ", weight=" + weight +
                ", value=" + value +
                '}';
    }
}
