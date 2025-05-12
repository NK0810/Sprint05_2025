package fragments;

public enum SortOptions {
    RECOMMENDED("ua_products"),
    LOWEST_PRICE("ua_products_price_default_asc"),
    HIGHEST_PRICE("ua_products_price_default_desc"),
    NEWEST("ua_products_czas_wejscia_na_magazyn_desc"),
    MAX_DISCOUNT("ua_products_discount_percent_desc");

    private String dataValue;

    SortOptions(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getDataValue() {
        return dataValue;
    }
}
