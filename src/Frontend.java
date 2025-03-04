import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Frontend extends JFrame {

    public Frontend () {
        setTitle("Notepad--");;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH); // to make the JFrame window open on a full screen
        addGuiComponents();
        setMinimumSize(new Dimension(650, 400));
    }

    private boolean turned_on = false; //variable for wordwrapping

    public void addGuiComponents() {
    // The menubar
    JMenuBar mb = new JMenuBar();

    // The file menu
    JMenu fileItem = new JMenu("File");

    // Creating file menu items
    JMenuItem newItem = new JMenuItem("New");
    JMenuItem openItem = new JMenuItem("Open...");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem saveAsItem = new JMenuItem("Save As...");
    JMenuItem exitItem = new JMenuItem("Exit");

    // Adding items to the file menu
    fileItem.add(newItem);
    fileItem.add(openItem);
    fileItem.add(saveAsItem);
    fileItem.add(saveItem);
    fileItem.add(exitItem);
    
    //creating edit menu and its items
    JMenu editItem = new JMenu("Edit");
    JMenuItem undoItem = new JMenuItem("Undo");
    JMenuItem redoItem = new JMenuItem("Redo");
    JMenuItem wordWrapItem = new JMenuItem("Wordwrap:off");
    JMenuItem replaceItem = new JMenuItem("Replace...");
    editItem.add(undoItem);
    editItem.add(redoItem);
    editItem.add(wordWrapItem);
    editItem.add(replaceItem);

    //creating format menu and its items
    JMenu formatItem = new JMenu("Format");
    JMenuItem fontItem = new JMenuItem("Font");
    JMenuItem fontSizeItem = new JMenuItem("Font Size");
    formatItem.add(fontItem);
    formatItem.add(fontSizeItem);

    //adding help menu and its items
    JMenu helpItem = new JMenu("Help");
    JMenuItem aboutItem = new JMenuItem("About Notepad--");
    helpItem.add(aboutItem);
    
    // Adding the menus to the menubar
    mb.add(fileItem);
    mb.add(editItem);
    mb.add(formatItem);
    mb.add(helpItem);

    //setting the menu bar
    setJMenuBar(mb);

    //adding textarea
    JTextArea textArea = new JTextArea();

    //to undo and redo
    UndoManager um = new UndoManager();

    textArea.getDocument().addUndoableEditListener(
        new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                um.addEdit(e.getEdit());
            }
        }
    );

    JScrollPane scrollPane = new JScrollPane(textArea);
    add(scrollPane, BorderLayout.CENTER);

    //adding events
    newItem.addActionListener(new ActionListener() {
        @Override 
        public void actionPerformed(ActionEvent e) {
            Backend.newFile(textArea);
        }
    }); 

    aboutItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e) {
            JOptionPane.showMessageDialog(null, "This is a evolved notepad but backwards.", "Notepad--", JOptionPane.INFORMATION_MESSAGE);
        }
    });

    openItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Backend.openFile(textArea);
        }
    });

    saveItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Backend.saveFile(textArea);
        }
    });

    saveAsItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Backend.saveAsFile(textArea);
        }
    });

    exitItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Backend.exitApplication();
        }
    });

    undoItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Backend.undo(um);
        }
    });

    redoItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Backend.redo(um);
        }
    });

    replaceItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Backend.replace(textArea);
        }
    });

    fontItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Backend.fontStyle(textArea);
        }
    });

    fontSizeItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Backend.fontSize(textArea);
        }
    });


    wordWrapItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            turned_on = Backend.wordWrapping(wordWrapItem, textArea, turned_on);
        }
    });

    }        
    
}