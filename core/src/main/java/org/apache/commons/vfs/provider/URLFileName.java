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

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileType;

/**
 * @author <a href="http://commons.apache.org/vfs/team-list.html">Commons VFS team</a>
 */
public class URLFileName extends GenericFileName
{
    private static final int BUFFSZ = 250;

    private final String queryString;

    public URLFileName(final String scheme,
                       final String hostName,
                       final int port,
                       final int defaultPort,
                       final String userName,
                       final String password,
                       final String path,
                       final FileType type,
                       final String queryString)
    {
        super(scheme, hostName, port, defaultPort, userName, password, path, type);
        this.queryString = queryString;
    }

    /**
     * Get the query string.
     *
     * @return the query string part of the filename
     */
    public String getQueryString()
    {
        return queryString;
    }

    /**
     * Get the path and query string e.g. /path/servlet?param1=true.
     *
     * @return the path and its query string
     */
    public String getPathQuery()
    {
        StringBuffer sb = new StringBuffer(BUFFSZ);
        sb.append(getPath());
        sb.append("?");
        sb.append(getQueryString());

        return sb.toString();
    }

    /**
     * Get the path encoded suitable for url like filesystem e.g. (http, webdav).
     *
     * @param charset the charset used for the path encoding
     * @return The encoded path.
     * @throws URIException If an error occurs encoding the URI.
     * @throws FileSystemException If some other error occurs.
     */
    public String getPathQueryEncoded(String charset) throws URIException, FileSystemException
    {
        if (getQueryString() == null)
        {
            if (charset != null)
            {
                return URIUtil.encodePath(getPathDecoded(), charset);
            }
            else
            {
                return URIUtil.encodePath(getPathDecoded());
            }
        }

        StringBuffer sb = new StringBuffer(BUFFSZ);
        if (charset != null)
        {
            sb.append(URIUtil.encodePath(getPathDecoded(), charset));
        }
        else
        {
            sb.append(URIUtil.encodePath(getPathDecoded()));
        }
        sb.append("?");
        sb.append(getQueryString());
        return sb.toString();
    }

    /**
     * Create a FileName.
     * @param absPath The absolute path.
     * @param type The FileType.
     * @return The FileName
     */
    public FileName createName(final String absPath, FileType type)
    {
        return new URLFileName(getScheme(),
            getHostName(),
            getPort(),
            getDefaultPort(),
            getUserName(),
            getPassword(),
            absPath,
            type,
            getQueryString());
    }

    /**
     * Append query string to the uri.
     *
     * @return the uri
     */
    protected String createURI()
    {
        if (getQueryString() != null)
        {
            StringBuffer sb = new StringBuffer(BUFFSZ);
            sb.append(super.createURI());
            sb.append("?");
            sb.append(getQueryString());

            return sb.toString();
        }

        return super.createURI();
    }

    /**
     * Encode a URI.
     * @param charset The character set.
     * @return The encoded URI
     * @throws FileSystemException if some other exception occurs.
     * @throws URIException if an exception occurs encoding the URI.
     */
    public String getURIEncoded(String charset) throws FileSystemException, URIException
    {
        StringBuffer sb = new StringBuffer(BUFFSZ);
        appendRootUri(sb, true);
        sb.append(getPathQueryEncoded(charset));
        return sb.toString();
    }
}
