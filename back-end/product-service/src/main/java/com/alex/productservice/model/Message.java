package com.alex.productservice.model;

public enum Message {

    PRODUCT_DELETED("Product was DELETED SUCCESSFULLY"),
    ALL_PRODUCTS_WERE_DELETED("All products was DELETED SUCCESSFULLY"),
    PRODUCT_NOT_FOUND("Product not found to update"),
    PRODUCT_ALREADY_EXISTS("Product already exists in DB"),

    HEX_EMPTY_LIST("There are no hex in DB"),
    HEX_NOT_FOUND("Hex with this idHex not exists"),
    HEX_NOT_CREATED("Something wrong, Hexes weren't created"),
    HEX_WAS_DELETED("Hex was deleted succefully"),
    HEX_CLEAR_TABLE("All haxes were deleted from DB"),

    ITEM_NOT_FOUND("Item  with this productNumber not found"),
    ITEM_WAS_DELETED("Item was delted from DB"),
    ITEM_CLEAR_ALL("All items were deleted from table")
    ;

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
