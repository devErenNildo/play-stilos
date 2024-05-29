package com.playstilos.domain.product;

public enum VisibilityStatus {
    AVAILABLE("true"),
    NOTAVAILABLE("false");

    private String available;

    VisibilityStatus(String available){
        this.available = available;
    }

    public String getAvailable(){
        return available;
    }
}
