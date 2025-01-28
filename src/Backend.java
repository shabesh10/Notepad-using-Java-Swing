import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Backend {
    private static boolean saved = false;
    private static File savedFile = null;

        public static void newFile(JTextArea textArea) {
            textArea.setText("");
        }
        
        public static void openFile(JTextArea textArea) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    textArea.read(reader, null);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public static void saveFile(JTextArea textArea) {
            if (saved && savedFile != null) {
                // Update the already saved file
                try (FileWriter writer = new FileWriter(savedFile)) {
                    writer.write(textArea.getText());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                saveAsFile(textArea);
            }
        }
    
        public static void saveAsFile(JTextArea textArea) {

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
            savedFile = fileChooser.getSelectedFile();

            // Add default extension if missing
            if (!savedFile.getName().contains(".")) {
                savedFile = new File(savedFile.getAbsolutePath() + ".txt");
            }

            try (FileWriter writer = new FileWriter(savedFile)) {
                writer.write(textArea.getText());
                saved = true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    public static void exitApplication() {
        System.exit(0);
    }
}
