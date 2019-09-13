package v1ch09.map;

import java.util.*;

/**
 * This program demonstrates the use of a map with key type String and value type Employee.
 *
 * @author Cay Horstmann
 * @version 1.12 2015-06-21
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String, Employee> staff = new HashMap<>();
        staff.put("144-25-5464", new Employee("Amy Lee"));
        staff.put("567-24-2546", new Employee("Harry Hacker"));
        staff.put("157-62-7935", new Employee("Gary Cooper"));
        staff.put("456-62-5527", new Employee("Francesca Cruz"));

        // print all entries

        System.out.println(staff); // 567..., 157..., 456...

        // remove an entry

        staff.remove("567-24-2546");

        // replace an entry

        staff.put("789-62-5531", new Employee("Francesca Miller"));

        // look up a value

        System.out.println(staff.get("567-24-2546"));

        // iterate through all entries

        System.out.println(staff); // 456..., 567..., 789...
    }
}

