/**
 *
 * @author Marco Sero
 * @version 1.0
 * Copyright 2010 Marco Sero
 *
 *  This file is part of toSync.

    toSync is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    toSync is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with toSync; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

import java.awt.*;
import java.beans.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;

public class toSync extends javax.swing.JFrame {
	
	// Variables declaration - do not modify
    private javax.swing.JFrame FrameLog;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JLabel copyright;
    private javax.swing.JButton destButton;
    private javax.swing.JTextField destText;
    private javax.swing.JLabel instruction;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea log;
    private javax.swing.JLabel picture;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JRadioButton radioBackup;
    private javax.swing.JRadioButton radioOldBackup;
    private javax.swing.JRadioButton radioReciprocal;
    private javax.swing.JButton sourceButton;
    private javax.swing.JTextField sourceText;
    private javax.swing.JButton syncButton;
    // End of variables declaration

    private JFileChooser fc; // To select source and destination
    private File source, dest;
    private boolean enableBackup, enableReciprocal, enableOldBackup; //boolean for radiobutton

    /**
     * Start the task which enables the progressBar during syncing. Moreover,
     * change the cursor with the WAIT_CURSOR and when the process has completed
     * plays beep().
     */
    private void startTask() {
	SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

	    @Override
	    protected Void doInBackground() throws Exception {
                try {
                    if(enableReciprocal)
                        Code.reciprocal(source, dest, log);
                    else if(enableOldBackup)
                        Code.oldBackup(source, dest, log);
                    else if(enableBackup)
                        Code.backup(source, dest, log);
                    
                } catch(IOException error) {
                    System.out.println("I/O Error");
                }
		return null;
	    }
	};
	worker.addPropertyChangeListener(new PropertyChangeListener() {

	    public void propertyChange(PropertyChangeEvent e) {
		if(e.getNewValue() == SwingWorker.StateValue.STARTED) {
		    syncButton.setEnabled(false); //disable syncButton
		    progressBar.setIndeterminate(true); //enable progressBar
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //turn on the wait cursor
		} else if(e.getNewValue() == SwingWorker.StateValue.DONE) {
                    FrameLog.setVisible(true); //enable log windows
		    syncButton.setEnabled(true); //enable syncButton
		    progressBar.setIndeterminate(false); //disable progressBar
                    setCursor(null); //turn off the wait cursor
                    Toolkit.getDefaultToolkit().beep(); //beep in the end of syncing

                    
		}
	    }
	});
	ExecutorService exec = Executors.newSingleThreadExecutor();
	exec.submit(worker);
	exec.shutdown();
    }



    /********************************************************************************************/

    /**
     * Creates new form toSync
     */
    public toSync() {

        //Create a file chooser
        fc = new JFileChooser();

        //If I leave these lines commented out,
        //then the default mode (FILES_ONLY) will be used.
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        FrameLog = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();
        sourceButton = new javax.swing.JButton();
        sourceText = new javax.swing.JTextField();
        destButton = new javax.swing.JButton();
        destText = new javax.swing.JTextField();
        radioReciprocal = new javax.swing.JRadioButton();
        radioOldBackup = new javax.swing.JRadioButton();
        radioBackup = new javax.swing.JRadioButton();
        syncButton = new javax.swing.JButton();
        picture = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        copyright = new javax.swing.JLabel();
        instruction = new javax.swing.JLabel();

        FrameLog.setTitle("Log");
        FrameLog.setMinimumSize(new java.awt.Dimension(500, 300));

        log.setColumns(20);
        log.setEditable(false);
        log.setFont(new java.awt.Font("DejaVu Sans", 0, 13));
        log.setRows(5);
        jScrollPane1.setViewportView(log);

        javax.swing.GroupLayout FrameLogLayout = new javax.swing.GroupLayout(FrameLog.getContentPane());
        FrameLog.getContentPane().setLayout(FrameLogLayout);
        FrameLogLayout.setHorizontalGroup(
            FrameLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FrameLogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
                .addContainerGap())
        );
        FrameLogLayout.setVerticalGroup(
            FrameLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FrameLogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addContainerGap())
        );

        Image icon = Toolkit.getDefaultToolkit().getImage("img" + File.separator + "toSync32.png");
        FrameLog.setIconImage(icon);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        FrameLog.setLocation(new Point((dimension.width - FrameLog.getSize().width) / 2,
            (dimension.height - FrameLog.getSize().height) / 2 ));

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("toSync");
    setName("toSync"); // NOI18N
    setResizable(false);

    sourceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/folder.png"))); // NOI18N
    sourceButton.setText("Select Source...");
    sourceButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            sourceButtonActionPerformed(evt);
        }
    });

    sourceText.setEditable(false);

    destButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/folder.png"))); // NOI18N
    destButton.setText("Select Destination...");
    destButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            destButtonActionPerformed(evt);
        }
    });

    destText.setEditable(false);

    buttonGroup.add(radioReciprocal);
    radioReciprocal.setText("Reciprocal");
    radioReciprocal.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            radioReciprocalActionPerformed(evt);
        }
    });

    buttonGroup.add(radioOldBackup);
    radioOldBackup.setText("Source completes Destination");
    radioOldBackup.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            radioOldBackupActionPerformed(evt);
        }
    });

    buttonGroup.add(radioBackup);
    radioBackup.setText("Source replaces Destination");
    radioBackup.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            radioBackupActionPerformed(evt);
        }
    });

    syncButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/accept.png"))); // NOI18N
    syncButton.setText("Start toSync!");
    syncButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            syncButtonActionPerformed(evt);
        }
    });

    picture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/toSync48.png"))); // NOI18N

    progressBar.setFocusable(false);

    copyright.setFont(new java.awt.Font("Lucida Grande", 0, 10));
    copyright.setText("<html>toSync v 1.0<br> Copyright (C) 2010  Marco Sero</html>");
    copyright.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

    instruction.setText("<HTML>Select the source, the destination and the kind of synchronization...<HTML>");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(radioBackup)
                .addComponent(radioOldBackup)
                .addComponent(radioReciprocal)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(destText, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(destButton, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(copyright, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addComponent(syncButton))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sourceButton)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(picture)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(instruction, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                        .addComponent(sourceText, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE))))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(sourceButton)
                .addComponent(sourceText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(instruction, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addComponent(picture))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(destText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(destButton))
            .addGap(18, 18, 18)
            .addComponent(radioReciprocal)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioOldBackup)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioBackup)
            .addGap(12, 12, 12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(copyright, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(syncButton)
                    .addGap(31, 31, 31))))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sourceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sourceButtonActionPerformed
        int returnVal = fc.showOpenDialog(toSync.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            source = fc.getSelectedFile(); // directory source
        }
        if(source != null)
            sourceText.setText(source.getAbsolutePath()); // copy the absolute path to textfield
    }//GEN-LAST:event_sourceButtonActionPerformed

    /********************************************************************************************/

    /**
     *
     * @param evt
     */

    private void destButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destButtonActionPerformed
        int returnVal = fc.showSaveDialog(toSync.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            dest = fc.getSelectedFile(); // directory destination
        }
        if(dest != null)
            destText.setText(dest.getAbsolutePath()); // copy the absolute path to textfield
    }//GEN-LAST:event_destButtonActionPerformed

     /********************************************************************************************/

    /**
     *
     * @param evt
     */

    private void syncButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncButtonActionPerformed
        if(source == null || dest == null)
            return;
        if((enableBackup == false) && (enableReciprocal == false) && (enableOldBackup == false))
            return;

        log = new JTextArea(); //create a new JTextArea for the next syncing
        log.setColumns(20);
        log.setEditable(false);
        log.setFont(new java.awt.Font("DejaVu Sans", 0, 13));
        log.setRows(5);
        jScrollPane1.setViewportView(log);

        startTask();
    }//GEN-LAST:event_syncButtonActionPerformed

     /********************************************************************************************/

    /**
     *
     * @param evt
     */

    private void radioReciprocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioReciprocalActionPerformed
        enableBackup = false;
        enableReciprocal = true;
        enableOldBackup = false;
        instruction.setText("<HTML>If a file is in source (or in destination) but not in destination "
                            + "(or in source), will be copied. The most recent file will "
                            + "overwrite the oldest.<HTML>");
        picture.setIcon(createImageIcon("img/reciprocal.png"));
    }//GEN-LAST:event_radioReciprocalActionPerformed

    private void radioOldBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioOldBackupActionPerformed
        enableBackup = false;
        enableReciprocal = false;
        enableOldBackup = true;
        instruction.setText("<HTML>If a file is in source but not in destination, will be copied in "
                + "destination.<HTML>");
        picture.setIcon(createImageIcon("img/oldBackup.png"));
    }//GEN-LAST:event_radioOldBackupActionPerformed

    private void radioBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBackupActionPerformed
        enableBackup = true;
        enableReciprocal = false;
        enableOldBackup = false;
        instruction.setText("<HTML>If a file is in source but not in destination, will be copied in "
                + "destination. If a file is in destination but not in source, "
                + "will be deleted in destination.<HTML>");
        picture.setIcon(createImageIcon("img/backup.png"));
    }//GEN-LAST:event_radioBackupActionPerformed

    /**
     * Creates an ImageIcon if the path is valid.
     * @param String - resource path
     * @param String - description of the file
     */
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = toSync.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new toSync();

                frame.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("img/toSync32.png")));

                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	        frame.setLocation(new Point((dimension.width - frame.getSize().width) / 2,
	    					  (dimension.height - frame.getSize().height) / 2 ));
                
                frame.setVisible(true);
            }
        });
    }

}
