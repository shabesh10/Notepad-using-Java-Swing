import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

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

    public static void undo(UndoManager um) {
        if (um.canUndo()) {
            um.undo();
        }
    }
    
    public static void redo(UndoManager um) {
        if (um.canRedo()) {
            um.redo();
        }
    }
    

    public static void replace(JTextArea textArea) {
        String find = JOptionPane.showInputDialog("Find..");
        String replace = JOptionPane.showInputDialog("Replace..");
        String fullString = textArea.getText();
        fullString = fullString.replaceAll(find, replace);
        textArea.setText(fullString);
    }
}