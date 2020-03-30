package pl.makuch.jdbcvaadin;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Car {

    private int id;

    @NotNull
    @NotEmpty
    private String mark;

    @NotNull
    @NotEmpty
    private String model;

    @NotNull
    @NotEmpty
    private String color;

    public Car() {
    }

    public Car(String mark, String model, String color) {
        this.mark = mark;
        this.model = model;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
