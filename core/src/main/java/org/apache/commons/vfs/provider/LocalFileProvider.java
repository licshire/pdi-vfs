/*!
* Copyright 2010 - 2013 Pentaho Corporation.  All rights reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/

package org.apache.commons.vfs.provider;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;

import java.io.File;

/**
 * A file provider which handles local files.
 *
 * @author <a href="mailto:adammurdoch@apache.org">Adam Murdoch</a>
 * @version $Revision: 804548 $ $Date: 2009-08-15 22:12:32 -0400 (Sat, 15 Aug 2009) $
 */
public interface LocalFileProvider
    extends FileProvider
{
    /**
     * Determines if a name is an absolute file name.
     *
     * @param name The name to test.
     * @todo Move this to a general file name parser interface.
     * @return true if the name is absolute.
     */
    boolean isAbsoluteLocalName(final String name);

    /**
     * Finds a local file, from its local name.
     * @param name The name of the file to locate.
     * @return The FileObject for the file.
     * @throws FileSystemException if an error occurs.
     *
     */
    FileObject findLocalFile(final String name)
        throws FileSystemException;

    /**
     * Converts from java.io.File to FileObject.
     * @param file The File for the file.
     * @return The FileObject for the file.
     * @throws FileSystemException if an error occurs.
     */
    FileObject findLocalFile(final File file)
        throws FileSystemException;
}
