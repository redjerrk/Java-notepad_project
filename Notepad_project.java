package notepad_project;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Notepad_project extends JFrame {

    JTextArea mainarea;
    JMenuBar mbar;
    JMenu mnuFile, mnuEdit, mnuFormat, mnuHelp;
    JMenuItem itmNew, itmOpen, itmSave, itmSaveas, itmExit;
    String filename;
    JFileChooser jc;
    String fileContent;

    //constractor
    public Notepad_project() {
        initComponent();

        itmSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                save();
            }
        });
        itmSaveas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                saveAs();
            }
        });
        itmOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                open();
            }
        });

        itmNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                open_new();
            }
        });

    }

    private void initComponent() {
        jc = new JFileChooser(".");
        mainarea = new JTextArea();
        getContentPane().add(mainarea);
        getContentPane().add(new JScrollPane(mainarea), BorderLayout.CENTER);
        setTitle("Untitled Notepad");
        setSize(856, 654);

        //manu bar
        mbar = new JMenuBar();
        mnuFile = new JMenu("File");
        mnuEdit = new JMenu("Edit");
        mnuFormat = new JMenu("Format");
        mnuHelp = new JMenu("Help");


        //manu item
        itmNew = new JMenuItem("New");
        itmOpen = new JMenuItem("Open");
        itmSave = new JMenuItem("Save");
        itmSaveas = new JMenuItem("Save As");

        // adding file
        mnuFile.add(itmNew);
        mnuFile.add(itmOpen);
        mnuFile.add(itmSave);
        mnuFile.add(itmSaveas);

        // adding manu item to manu bar
        mbar.add(mnuFile);
        mbar.add(mnuEdit);
        mbar.add(mnuFormat);
        mbar.add(mnuHelp);

        //adding manu bar to frame
        setJMenuBar(mbar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        

    }

    private void save() {
        PrintWriter fout = null;
        

        try {

            if (filename == null) {
                saveAs();

            } else {
                fout = new PrintWriter(new FileWriter(filename));
                String s = mainarea.getText();
                StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
                while (st.hasMoreElements()) {
                    fout.println(st.nextToken());

                }
                JOptionPane.showMessageDialog(rootPane, "File Saved");
                fileContent = mainarea.getText();

            }

        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            if (fout != null) {
                fout.close();
            }

        }

    }

    private void saveAs() {
        PrintWriter fout = null;
        int retval = -1;

        try {
            retval = jc.showSaveDialog(this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                fout = new PrintWriter(new FileWriter(jc.getSelectedFile()));
            }

            String s = mainarea.getText();
            StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
            while (st.hasMoreElements()) {
                fout.println(st.nextToken());

            }
            JOptionPane.showMessageDialog(rootPane, "File Saved");
            fileContent = mainarea.getText();
            filename = jc.getSelectedFile().getName();
            setTitle(filename = jc.getSelectedFile().getName());

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (fout != null) {
                fout.close();
            }
        }
    }

    private void open() {
        try {
            int retval = jc.showOpenDialog(this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                mainarea.setText(null);
                Reader in = new FileReader(jc.getSelectedFile());
                char[] buff = new char[10000000];
                int nch;
                while ((nch = in.read(buff, 0, buff.length)) != -1) {
                    mainarea.append(new String(buff, 0, nch));
                }
            }
            setTitle(filename = jc.getSelectedFile().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void open_new() {
        if (!mainarea.getText().equals("") && !mainarea.getText().equals(fileContent)) {
            if (filename == null) {
                int option = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the Changes?");
                if (option == 0) {
                    saveAs();
                } else {
                    clear();
                }
            } else {
                int option = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the Changes?");
                if (option == 0) {
                    save();
                    clear();
                } else {
                    clear();
                }

            }
        } else {
            clear();
        }
    }

    private void clear() {
        mainarea.setText(null);
        setTitle("Untitled Notepad");
        filename = null;
        fileContent = null;

    }

    public static void main(String[] args) {
        Notepad_project np = new Notepad_project();

    }

}
