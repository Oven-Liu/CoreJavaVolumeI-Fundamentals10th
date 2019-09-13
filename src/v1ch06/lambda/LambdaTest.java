package v1ch06.lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;

/**
 * This program demonstrates the use of lambda expressions.
 * @version 1.0 2015-05-12
 * @author Cay Horstmann
 */
public class LambdaTest {
   public static void main(String[] args) {
      String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune" };
      System.out.println(Arrays.toString(planets));
      System.out.println("Sorted in dictionary order:");
      Arrays.sort(planets);
      System.out.println(Arrays.toString(planets));
      System.out.println("Sorted by length:");
      Arrays.sort(planets, (first, second) -> first.length() - second.length());
      System.out.println(Arrays.toString(planets));
            
      Timer t = new Timer(1000, event ->
         System.out.println("The time is " + new Date()));
      t.start();

      // keep program running until user selects "Ok"
      JOptionPane.showMessageDialog(null, "Quit program?");
      System.exit(0);         
   }
}

class Greeter {
   // public void greet() {
   //    System.out.println("Hello, world!");
   // }

   public void greet(ActionEvent actionEvent) {
      System.out.println("Hello, world!");
   }
}
class TimeGreeter extends Greeter {
   // @Override
   // public void greet() {
   //    Timer t = new Timer(1000, super::greet); // Can't resolve method 'greet'
   //    t.start();
   // }

   @Override
   public void greet(ActionEvent actionEvent) {
      Timer t = new Timer(1000, super::greet);
      t.start();
   }

   // public static void countDown(int start, int delay) {
   //    ActionListener listener = event -> {
   //       start --; // Error: Can't mutate captured variable
   //       System.out.println(start);
   //    };
   // }

   // public static void repeat(String text, int count) {
   //    for (int i = 1; i <= count; i++) {
   //       ActionListener listener = event -> {
   //          System.out.println(i + ": " + text);
   //          // Error: Can't refer to changing i
   //       };
   //       new Timer(1000, listener).start();
   //    }
   // }
}

class Application {
   public void init() {
      ActionListener listener = event -> System.out.println(this.toString());
      new Timer(1000, listener).start();
   }
   // public void test() {
   //    Path first = Paths.get("/usr/bin");
   //    Comparator<String> comp = (first, second) -> first.length() - second.length();
   //    // Error: Variable first already defined
   // }
   // public void testComparator() {
   //    Arrays.sort(people, Comparator.comparing(Person::getName));
   //    Arrays.sort(people, Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName));
   //    Arrays.sort(people, Comparator.comparing(Person::getName, (s, t) -> Integer.compare(s.length() - t.length())));
   //    Arrays.sort(people, Comparator.comparingInt(p -> p.getName().length()));
   //    Arrays.sort(people, Comparator.comparing(Person::getMiddleName, Comparator.nullsFirst(Comparator.naturalOrder())));
   //    Comparator.naturalOrder().reversed();
   //    Comparator.reverseOrder();
   // }
}
