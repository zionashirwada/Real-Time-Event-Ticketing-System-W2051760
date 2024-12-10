package lk.W2051760.ticketing_system_backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Ticket {

    private final int id;

    public Ticket(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                '}';
    }
}
