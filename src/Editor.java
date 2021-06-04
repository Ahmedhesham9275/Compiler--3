import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class Editor extends JFrame implements ActionListener {

        // Text component
        JTextArea t;

        // Frame
        JFrame f;
    JMenuBar menuBar = new JMenuBar();

    // Create amenu for menu
    JMenu fileMenu = new JMenu("File");

    // Create menu items
    JMenuItem fileMenuItemNew = new JMenuItem("New");
    JMenuItem fileMenuItemOpen = new JMenuItem("Open");
    JMenuItem fileMenuItemSave = new JMenuItem("Save");
    JMenuItem browseMenuItem = new JMenuItem("Browse");
    JMenuItem closeMenuItem = new JMenuItem("Exit");
    JMenuItem compileAndRunMenuItem = new JMenuItem("Compile");

    JPanel panel = new JPanel();

    // Constructor
        Editor()
        {
            // Create a frame
            f = new JFrame("Scanner Editor");
            // Text component
            t = new JTextArea();

            // Create a menubar

            // Add action listener to menu items
            fileMenuItemNew.addActionListener(this);
            fileMenuItemOpen.addActionListener(this);
            fileMenuItemSave.addActionListener(this);
            closeMenuItem.addActionListener(this);
            browseMenuItem.addActionListener(this);
            compileAndRunMenuItem.addActionListener(this);

            fileMenu.add(fileMenuItemNew);
            fileMenu.add(fileMenuItemOpen);
            fileMenu.add(fileMenuItemSave);


            menuBar.add(fileMenu);
            menuBar.add(browseMenuItem);
            menuBar.add(closeMenuItem);
            menuBar.add(compileAndRunMenuItem);

            f.setJMenuBar(menuBar);
            f.add(t);
            f.setSize(1000, 1000);
            f.setVisible(true);
        }

        // If a button is pressed
        public void actionPerformed(ActionEvent e)
        {
            String action = e.getActionCommand();

             if (action.equals("Save")) {
                 // Create an object of JFileChooser class
                 JFileChooser j = new JFileChooser("f:");

                 // Invoke the showsSaveDialog function to show the save dialog
                 int dialog = j.showSaveDialog(null);

                 if (dialog == JFileChooser.APPROVE_OPTION) {

                     // Set the label to the path of the selected directory
                     File fi = new File(j.getSelectedFile().getAbsolutePath());

                     try {
                         // Create a file writer
                         FileWriter wr = new FileWriter(fi, false);

                         // Create buffered writer to write
                         BufferedWriter w = new BufferedWriter(wr);

                         // Write
                         w.write(t.getText());

                         w.flush();
                         w.close();
                     } catch (Exception evt) {
                         JOptionPane.showMessageDialog(f, evt.getMessage());
                     }
                 }
                 // If the user cancelled the operation
                 else
                     JOptionPane.showMessageDialog(f, "the user cancelled the operation");
             }
            else if (action.equals("Open")) {
                // Create an object of JFileChooser class
                JFileChooser j = new JFileChooser("f:");

                // Invoke the showsOpenDialog function to show the save dialog
                int dialog = j.showOpenDialog(null);

                // If the user selects a file
                if (dialog == JFileChooser.APPROVE_OPTION) {
                    // Set the label to the path of the selected directory
                    File fi = new File(j.getSelectedFile().getAbsolutePath());

                    try {
                        // String
                        String s1 = "", sl = "";

                        // File reader
                        FileReader fr = new FileReader(fi);

                        // Buffered reader
                        BufferedReader br = new BufferedReader(fr);

                        // Initialize sl
                        sl = br.readLine();

                        // Take the input from the file
                        while ((s1 = br.readLine()) != null) {
                            sl = sl + "\n" + s1;
                        }
                        // Set the text

                        f.remove(panel);
                        f.add(t);
                        t.setText(sl);
                        f.repaint();
                    }
                    catch (Exception evt) {
                        JOptionPane.showMessageDialog(f, evt.getMessage());
                    }
                }
                // If the user cancelled the operation
                else
                    JOptionPane.showMessageDialog(f, "the user cancelled the operation");
            }
            else if(action.equals("Browse")) {
                 try {
                     String[][] data = Scanner.scan();
                     String[]colNams = {"nOfLine","Lexeme","Return Token","Lexeme nOfLine", "matchability"};
                     JTable table = new JTable(data, colNams);
                     f.remove(t);
                     JScrollPane sp = new JScrollPane(table);
                     JLabel numOfError = new JLabel("Num of Errors: "+ String.valueOf(Scanner.numOfErrors));
                     numOfError.setForeground(Color.red);
                     panel.setLayout( new BorderLayout());
                     panel.add(sp,"Center");
                     panel.add(numOfError,"South");
                     f.add(panel);
                     f.repaint();
                     f.setVisible(true);
                 } catch (IOException ioException) {
                     JOptionPane.showMessageDialog(f,"File couldn't be reached ! try again" ,"Error",JOptionPane.ERROR_MESSAGE);
                 }
             }
             else if(action.equals("Compile")) {
                 try {
                     String code = t.getText();
                     System.out.println(code);
                     String[][] data = LexicalAnalyzer.compile(code);
                     System.out.println(data);
                     String[] colNams = {"nOfLine", "Lexeme", "Return Token", "Lexeme nOfLine", "matchability"};
                     JTable table = new JTable(data, colNams);
                     f.remove(t);
                     JScrollPane sp = new JScrollPane(table);
                     JLabel numOfError = new JLabel("Num of Errors: " + String.valueOf(LexicalAnalyzer.numOfErrors));
                     numOfError.setForeground(Color.red);
                     panel.setLayout(new BorderLayout());
                     panel.add(sp, "Center");
                     panel.add(numOfError, "South");
                     f.add(panel);
                     f.repaint();
                     f.setVisible(true);
                 }catch (Exception e2)
                 {
                     e2.printStackTrace();
                 }
             }
            else if (action.equals("New")) {
                f.remove(panel);
                t.setText("");
                 f.add(t);
                 f.repaint();
            }
            else if (action.equals("Exit")) {
                f.setVisible(false);
                System.exit(1);
            }
        }
    public static void main(String args[])
    {
        new Editor();
    }
}
