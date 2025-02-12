import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

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

    public static void fontStyle(JTextArea textArea) {
        //to fetch all the font family names
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        Object[] options = fonts;
        //using Object class because the showInputDialog returns only an Object and not an String
        Object selectionObject = JOptionPane.showInputDialog(null, "Select a font", "Font style", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        String selectionString = selectionObject.toString();
        textArea.setFont(new Font(selectionString, Font.PLAIN, textArea.getFont().getSize()));
    }

    public static void fontSize(JTextArea textArea) {
        Float[] fontSizes = {8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 14.0f, 16.0f, 18.0f, 20.0f, 22.0f, 24.0f, 26.0f, 28.0f, 32.0f, 36.0f, 40.0f, 48.0f, 56.0f, 64.0f, 72.0f};
        Object[] options = fontSizes;
        Object selection = JOptionPane.showInputDialog(null, "Select font size", "Font size", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        textArea.setFont(textArea.getFont().deriveFont((Float) selection));
    }

    public static boolean wordWrapping(JMenuItem wordWrapItem, JTextArea textArea, boolean turned_on) {
        if (turned_on) {
            turned_on = false;
            textArea.setLineWrap(false);
            wordWrapItem.setText("Wordwrap:off");
            return turned_on;

        }
        else {
            textArea.setLineWrap(true);
            wordWrapItem.setText("Wordwrap:on");
            turned_on = true;
            return turned_on;
        }
    }
}