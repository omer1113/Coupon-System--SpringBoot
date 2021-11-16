package com.jb.Enum;

public enum Category {
    FOOD(1), ELECTRICITY(2), RESTAURANT(3), VACATION(4);

    private final int categoryID;

    Category(int categoryID) {
        this.categoryID = categoryID;
    }
}
