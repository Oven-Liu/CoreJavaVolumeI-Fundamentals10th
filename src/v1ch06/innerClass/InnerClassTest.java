package v1ch06.innerClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * This program demonstrates the use of inner classes.
 * @version 1.11 2015-05-12
 * @author Cay Horstmann
 */
public class InnerClassTest {
   public static void main(String[] args) {
      TalkingClock clock = new TalkingClock(1000, true);
      // TalkingClock.TimePrinter listener = clock.new TimePrinter();
      // clock.start();

      // keep program running until user selects "Ok"
      JOptionPane.showMessageDialog(null, "Quit program?");
      System.exit(0);
   }
}

/**
 * A clock that prints the time in regular intervals.
 */
class TalkingClock {
   private int interval;
   private boolean beep;

   /**
    * Constructs a talking clock
    * @param interval the interval between messages (in milliseconds)
    * @param beep true if the clock should beep
    */
   public TalkingClock(int interval, boolean beep) {
      this.interval = interval;
      this.beep = beep;
   }

   /**
    * Starts the clock.
    */
   public void start() {
      // ActionListener listener = new TimePrinter(this); // parameter automatically added
      // ActionListener listener = this.new TimePrinter();
      ActionListener listener = new TimePrinter();
      Timer t = new Timer(interval, listener);
      t.start();
   }

   // public void start(int interval, final boolean beep) {
   //    class TimePrinter implements ActionListener {
   //       @Override
   //       public void actionPerformed(ActionEvent event) {
   //          System.out.println("At the tone, the time is " + new Date());
   //          if (beep) {
   //             Toolkit.getDefaultToolkit().beep();
   //          }
   //       }
   //    }
   //
   //    ActionListener listener = new TimePrinter();
   //    Timer t = new Timer(interval, listener);
   //    t.start();
   // }

   public class TimePrinter implements ActionListener {
      // public TimePrinter(TalkingClock clock) { // automatically generated code
      //    outer = clock;
      // }
      @Override
      public void actionPerformed(ActionEvent event) {
         System.out.println("At the tone, the time is " + new Date());
         if (beep) {
         // if (TalkingClock.this.beep) {
            Toolkit.getDefaultToolkit().beep();
         }
      }
   }
}


