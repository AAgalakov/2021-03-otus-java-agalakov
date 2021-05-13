package homework;


import java.util.*;

public class CustomerReverseOrder {

    private final Deque<Customer> customerDeque = new LinkedList<>();

    public void add(Customer customer) {
        customerDeque.add(customer);
    }

    public Customer take() {
        return customerDeque.removeLast();
    }
}
