package v1ch13.systemInfo;

import java.io.*;
import java.util.*;

/**
 * This program prints out all system properties.
 *
 * @author Cay Horstmann
 * @version 1.10 2002-07-06
 */
public class SystemInfo {
    public static void main(String args[]) {
        try {
            Properties sysprops = System.getProperties();
            sysprops.store(System.out, "System Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}