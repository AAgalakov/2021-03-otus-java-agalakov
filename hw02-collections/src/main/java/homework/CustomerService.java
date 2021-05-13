package homework;

import java.util.*;

public class CustomerService {

    private final TreeMap<Customer, String> customerStringMap = new TreeMap<>((o1, o2) -> (int) (o1.getScores() - o2.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> customerStringEntry =  customerStringMap.firstEntry();
        return new AbstractMap.SimpleEntry<> (customerStringEntry.getKey().toClone(), customerStringEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Optional<Map.Entry<Customer, String>> optionalCustomerStringEntry = Optional.ofNullable(customerStringMap.higherEntry(customer));
        return optionalCustomerStringEntry
                .map(customerStringEntry -> new AbstractMap.SimpleEntry<> (customerStringEntry.getKey().toClone(), customerStringEntry.getValue()))
                .orElse(null);
    }

    public void add(Customer customer, String data) {
        customerStringMap.put(customer, data);
    }
}
