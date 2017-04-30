package contentprovidersample.raju.karthi.con.contentprovidersample;

/**
 * Created by karthikeyan on 30/4/17.
 */

public class Item {
    private String name;
    private int id;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
