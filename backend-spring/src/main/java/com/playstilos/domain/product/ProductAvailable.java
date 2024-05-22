package com.playstilos.domain.product;

public enum ProductAvailable {
    AVAILABLE("true"),
    NOTAVAILABLE("false");

    private String available;

    ProductAvailable(String available){
        this.available = available;
    }

    public String getAvailable(){
        return available;
    }
}
