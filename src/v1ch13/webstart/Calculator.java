package v1ch13.webstart;

import javax.swing.*;
import java.awt.*;

/**
 * A calculator with a calculation history that can be deployed as a Java Web Start application.
 *
 * @author Cay Horstmann
 * @version 1.04 2015-06-12
 */
public class Calculator {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CalculatorFrame frame = new CalculatorFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
