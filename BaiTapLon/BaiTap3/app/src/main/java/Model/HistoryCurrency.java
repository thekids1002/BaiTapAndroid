package Model;

public class HistoryCurrency {
    private int id;
    private String spn_from;
    private String spn_to;
    private String value_from;
    private String value_to;

    public HistoryCurrency() {
    }

    public HistoryCurrency(int id, String spn_from, String spn_to, String value_from, String value_to) {
        this.id = id;
        this.spn_from = spn_from;
        this.spn_to = spn_to;
        this.value_from = value_from;
        this.value_to = value_to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpn_from() {
        return spn_from;
    }

    public void setSpn_from(String spn_from) {
        this.spn_from = spn_from;
    }

    public String getSpn_to() {
        return spn_to;
    }

    public void setSpn_to(String spn_to) {
        this.spn_to = spn_to;
    }

    public String getValue_from() {
        return value_from;
    }

    public void setValue_from(String value_from) {
        this.value_from = value_from;
    }

    public String getValue_to() {
        return value_to;
    }

    public void setValue_to(String value_to) {
        this.value_to = value_to;
    }

    @Override
    public String toString() {
        return "HistoryCurrency{" +
                "id=" + id +
                ", spn_from='" + spn_from + '\'' +
                ", spn_to='" + spn_to + '\'' +
                ", value_from='" + value_from + '\'' +
                ", value_to='" + value_to + '\'' +
                '}';
    }
}
