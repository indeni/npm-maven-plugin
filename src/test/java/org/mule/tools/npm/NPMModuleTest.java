/**
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.npm;


import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class NPMModuleTest {

    @Test
    public void testDownloadLess() throws Exception {
        NPMModule npmModule = NPMModule.fromNameAndVersion(mock(Log.class), NPMMojo.DEFAULT_NPM_URL, "less", "1.0.32");
        npmModule.saveToFile(new File("target/less-test"));
    }

    @Test
    public void testDownloadVigile() throws Exception {
        NPMModule npmModule2 = NPMModule.fromName(mock(Log.class), NPMMojo.DEFAULT_NPM_URL, "vigile");
        npmModule2.saveToFileWithDependencies(new File("target/vigile-test"));
    }

    @Test
    public void testDownloadJshint() throws Exception {
        NPMModule npmModule3 = NPMModule.fromQueryString(mock(Log.class), NPMMojo.DEFAULT_NPM_URL, "jshint:0.8.1");
        npmModule3.saveToFileWithDependencies(new File("target/jshint-test"));
    }
}
