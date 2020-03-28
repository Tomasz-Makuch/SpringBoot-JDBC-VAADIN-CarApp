package pl.makuch.jdbcvaadin;

public enum FilterType {

    ID("id"),
    MARK("mark"), 
    MODEL("model"),
    COLOR ("color");

    private String value;

    FilterType(String value) {
        this. value = value;
    }

    public String getValue() {
        return value;
    }
}
