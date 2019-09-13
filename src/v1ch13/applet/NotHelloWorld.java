package v1ch13.applet;

import javax.swing.*;
import java.awt.*;

/**
 * @author Cay Horstmann
 * @version 1.24 2015-06-12
 */
public class NotHelloWorld extends JApplet {
    public void init() {
        EventQueue.invokeLater(() -> {
            JLabel label = new JLabel("Not a Hello, World applet",
                    SwingConstants.CENTER);
            add(label);
        });
    }
}
