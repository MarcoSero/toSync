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

import java.io.*;
import javax.swing.*;
import java.util.*;

public class Code {

    /**
     *
     * @param srcDir
     * @param dstDir
     * @param log
     * @throws IOException
     *
     * Analyze the directory and its children. When a File isFile(), call copyFile().
     */

    public static void copyDirectory(File srcDir, File dstDir, JTextArea log) throws IOException {
        if (srcDir.isDirectory()) {
            if (!dstDir.exists()) {
                dstDir.mkdir();
                log.append("CREATED " + dstDir.getAbsolutePath() + "\n");
            }

            String[] children = srcDir.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(srcDir, children[i]), new File(dstDir, children[i]), log);
            }
        } else {
            // if isFile() -->copy
            copyFile(srcDir, dstDir, log);
        }
    }
    
    /********************************************************************************************/

    /**
     *
     * @param src
     * @param dst
     * @param log
     * @throws IOException
     *
     */

    public static void copyFile(File src, File dst, JTextArea log) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        byte[] buf = new byte[1024]; // array of the stream of bytes
        int len;
        while ((len = in.read(buf)) > 0) { //read the File src
            out.write(buf, 0, len);        //write the File dst
        }
        in.close();
        out.close();

        log.append("COPIED " + src.getAbsolutePath() + "\n    TO " + dst.getAbsolutePath() + "\n");
    }

    /********************************************************************************************/

    /**
     *
     * @param srcDir
     * @param dstDir
     * @param toAnalyze
     * @param dirToCreate
     * @param log
     * @throws IOException
     */

    public static void analiyzeOnlyRecent(File srcDir, File dstDir, Vector<CoupleFiles> toAnalyze, Vector<String> dirToCreate, JTextArea log) throws IOException {
        if (srcDir.isDirectory()) {
            if(mostRecent(srcDir, dstDir) != 0) { // if the directory source have lastModified == directory dest, finish
                if (!dstDir.exists()) { //if directory dst doesn't exists, save it to create after
                    dirToCreate.add(dstDir.getAbsolutePath());
                }

                String[] children = srcDir.list();
                for (int i=0; i<children.length; i++) { //analyze all children of directory
                    analiyzeOnlyRecent(new File(srcDir, children[i]), new File(dstDir, children[i]), toAnalyze, dirToCreate, log);
                }
            }
        } else {
            if(mostRecent(srcDir, dstDir) == 1) //if file is most recent will be copied
                toAnalyze.add(new CoupleFiles(srcDir, dstDir)); //add new couple of files to copy
        }
    }

    // VECCHIA VERSIONE
     /*public static void analiyzeOnlyRecent(File srcDir, File dstDir, Vector<CoupleFiles> toAnalyze, JTextArea log) throws IOException {
        if (srcDir.isDirectory()) {
            if (!dstDir.exists()) { //if directory dst doesn't exists, hreate it
                dstDir.mkdir();     //because then only method coptFile() will be called
                log.append("CREATED " + dstDir.getAbsolutePath() + "\n");
            }

            String[] children = srcDir.list();
            for (int i=0; i<children.length; i++) { //analyze all children of directory
                analiyzeOnlyRecent(new File(srcDir, children[i]), new File(dstDir, children[i]), toAnalyze, log);
            }
        } else {
            if(mostRecent(srcDir, dstDir) == 1) //if file is most recent will be copied
                toAnalyze.add(new CoupleFiles(srcDir, dstDir)); //add new couple of files to copy
        }
    }*/

    /********************************************************************************************/

    public static void createDir(Vector<String> dirToCreate, JTextArea log) throws IOException {
        File temp;
        for(int i = 0; i < dirToCreate.size(); i++) {
            temp = new File(dirToCreate.get(i));
            temp.mkdir();
            log.append("CREATED " + temp.getAbsolutePath() + "\n");
        }

    }

    /********************************************************************************************/

    /**
     *
     * @param toDelete
     * @param log
     */

    public static void deleteFolder(File toDelete, JTextArea log) {
        if(toDelete.isDirectory()) {
	    	String[] childrenToDelete = toDelete.list();
	        for(int i = 0; i < childrenToDelete.length; i++) {
	            File deletable = new File(toDelete.getAbsolutePath() + File.separator + childrenToDelete[i]);
	            //System.out.println(deletable.getAbsolutePath());
	            deleteFolder(deletable, log);
	        }
            toDelete.delete();
            log.append("DELETED " + toDelete.getAbsolutePath() + "\n");

        }
        else {
            toDelete.delete();
            log.append("DELETED " + toDelete.getAbsolutePath() + "\n");
        }
    }

    /********************************************************************************************/

    /**
     *
     * @param source
     * @param dest
     * @return
     */

    public static int mostRecent(File source, File dest) {
        if(source.lastModified() == dest.lastModified())
            return 0;
        else if(source.lastModified() < dest.lastModified())
            return -1;
        else
            return 1;
    }

    /********************************************************************************************/

    /**
     *
     * @param source
     * @param dest
     * @param toCompare
     * @param log
     */

    public static void cleanCompare(File source, File dest, String[] toCompare, JTextArea log) {
        // non controllo che source sia una dir, poichè se il metodo è stato
        // chiamato, lo è di sicuro

        if(dest.isDirectory()) {
            String[] childrenDest = dest.list();
            boolean found = false;

            for(int i = 0; i < childrenDest.length; i++) {
                found = false;

                for(int j = 0; j < toCompare.length; j++) {
                    if(childrenDest[i].compareTo(toCompare[j]) == 0)
                        found = true;
                }

                if(found == false) {
                    File deletable = new File(dest.getAbsolutePath() + File.separator + childrenDest[i]);
                    deleteFolder(deletable, log);
                }
            }
        } else if(dest.isFile()) {
                dest.delete(); //perchè source è una dir
        }

    }

    /********************************************************************************************/

    /**
     *
     * @param source
     * @param dest
     * @param log
     * @throws IOException
     */

    public static void backup(File source, File dest, JTextArea log) throws IOException {

        if (source.isDirectory()) { //se è una dir ci vado di ricorsione

            String[] childrenSource = source.list();

            for (int i=0; i<childrenSource.length; i++) {

                File s = new File(source.getAbsolutePath() + File.separator + childrenSource[i]);
                File d = new File(dest.getAbsolutePath() + File.separator + childrenSource[i]);

                if(s.exists() && !d.exists()) { //if s exists but dest doesn't exist
                                                //creates directory dest and fills with source
                    copyDirectory(s, d, log);
                } else {      //if source and dest exist, copies source
                              //only if it is most recent
                    if(mostRecent(s, d) == 1)
                        copyDirectory(s, d, log);
                }

                cleanCompare(source, dest, childrenSource, log);
                backup(s, d, log);
            }
        } else if(mostRecent(source, dest) == 1)
                copyFile(source, dest, log);
    }

    /********************************************************************************************/

    /**
     *
     * @param source
     * @param dest
     * @param log
     * @throws IOException
     */

    public static void reciprocal(File source, File dest, JTextArea log) throws IOException {
        Vector<CoupleFiles> str2dst = new Vector<CoupleFiles>();
        Vector<CoupleFiles> dst2src = new Vector<CoupleFiles>();

        //analiyzeOnlyRecent(source, dest, str2dst, log);
        //analiyzeOnlyRecent(dest, source, dst2src, log);

        Vector<String> str2dstDir = new Vector<String>();
        Vector<String> dst2srcDir = new Vector<String>();

        analiyzeOnlyRecent(source, dest, str2dst, str2dstDir, log);
        analiyzeOnlyRecent(dest, source, dst2src, dst2srcDir, log);

        createDir(str2dstDir, log);
        createDir(dst2srcDir, log);

        CoupleFiles iterator;
        for(int i = 0; i < str2dst.size(); i++) {
            iterator = str2dst.get(i);
            copyFile(iterator.getSource(), iterator.getDest(), log);
        }

        for(int i = 0; i < dst2src.size(); i++) {
            iterator = dst2src.get(i);
            copyFile(iterator.getSource(), iterator.getDest(), log);
        }
    }

    /********************************************************************************************/

    /**
     *
     * @param source
     * @param dest
     * @param log
     * @throws IOException
     */

    public static void oldBackup(File source, File dest, JTextArea log) throws IOException {

        if (source.isDirectory()) { //se è una dir ci vado di ricorsione

            String[] childrenSource = source.list();

            for (int i=0; i<childrenSource.length; i++) {

                File s = new File(source.getAbsolutePath() + File.separator + childrenSource[i]);
                File d = new File(dest.getAbsolutePath() + File.separator + childrenSource[i]);

                if(s.exists() && !d.exists()) { //if s exists but dest doesn't exist
                                                //creates directory dest and fills with source
                    copyDirectory(s, d, log);
                } else {      //if source and dest exist, copies source
                              //only if it is most recent
                    if(mostRecent(s, d) == 1)
                        copyDirectory(s, d, log);
                }

                oldBackup(s, d, log);
            }
        } else if(mostRecent(source, dest) == 1)
                copyFile(source, dest, log);
    }

} //end class