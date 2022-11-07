package ru.akirakozov.sd.refactoring.datamodule;

public class ComponentsOfProd {

    private String name;
    private Long price;

    public ComponentsOfProd(String name, Long price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public Long getPrice() {
        return price;
    }
}