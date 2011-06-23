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

public class CoupleFiles {
    private File source;
    private File dest;
    
    /********************************************************************************************/

    /**
     * @param source
     * @param dest
     */

    public CoupleFiles(File source, File dest) {
        this.source = source;
        this.dest = dest;
    }
    
    /********************************************************************************************/

    /**
     * @return
     */

    public File getSource() {
        return source;
    }
    
    /********************************************************************************************/

    /**
     * @return
     */

    public File getDest() {
        return dest;
    }
    
    /********************************************************************************************/

    /**
     * @param source
     */

    public void setSource(File source) {
        this.source = source;
    }
    
    /********************************************************************************************/

    /**
     * @param dest
     */

    public void setDest(File dest) {
        this.dest = dest;
    }
}
