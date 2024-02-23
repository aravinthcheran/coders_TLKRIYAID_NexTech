import java.io.*;
import javax.swing.JOptionPane;

public class RestaurantTester {
    public static void main(String[] args){
        try {
            File inputFile = new File("menu-items");
            MainFrame guiFrame = new MainFrame(inputFile);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error! Menu File not found!", "Please reinput",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error! Program terminated", " Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}