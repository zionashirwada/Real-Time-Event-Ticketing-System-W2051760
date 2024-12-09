package lk.W2051760.ticketing_system_backend.model;

public abstract class User {
    private final int id;
    private final String name;

    protected User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    
    /** 
     * @return int
     */
    public int getId() {
        return id;
    };

    public String getName() {
        return name;
    };
}