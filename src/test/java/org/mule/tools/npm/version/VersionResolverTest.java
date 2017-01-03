/**
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.npm.version;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;
import org.mule.tools.npm.NPMModule;
import org.mule.tools.npm.NPMMojo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class VersionResolverTest {

    String npmUrl = NPMMojo.DEFAULT_NPM_URL;

    @Test
    public void testRegex() throws Exception {
        Pattern pattern = Pattern.compile(VersionResolver.VERSION_REGEX);
        assertTrue(pattern.matcher("1.2.3").matches());
        assertTrue(pattern.matcher("1.2.x").matches());
        assertTrue(pattern.matcher("1.x.x").matches());
        assertTrue(pattern.matcher("1").matches());
        assertTrue(pattern.matcher("*").matches());
        assertTrue(pattern.matcher("~1.3.3").matches());
        assertTrue(pattern.matcher(">= 2.2.1").matches());
        assertTrue(pattern.matcher(">=0.0.1 <0.1.0").matches());
        assertTrue(pattern.matcher("~3.0.0rc4").matches());
        Matcher matcher = pattern.matcher(">=0.0.1 <0.1.0");
        matcher.matches();
    }

    @Test
    public void testGetNextVersion() throws Exception {
        VersionResolver versionResolver = new VersionResolver();

        String debugVersion = versionResolver.getNextVersion(mock(Log.class), npmUrl, "debug", "*");
        assertNotNull(debugVersion);
        assertFalse("*".equals(debugVersion));

        String abbrevVersion = versionResolver.getNextVersion(mock(Log.class), npmUrl, "abbrev", "1");
        assertNotNull(abbrevVersion);
        assertFalse("1".equals(abbrevVersion));

        String coffeeScriptVersion = versionResolver.getNextVersion(mock(Log.class), npmUrl, "coffee-script", "~1.3.3");
        assertNotNull(coffeeScriptVersion);
        assertEquals("1.3.3", coffeeScriptVersion);

        String sequenceVersion = versionResolver.getNextVersion(mock(Log.class), npmUrl, "sequence", ">= 2.2.1");
        assertNotNull(sequenceVersion);
        assertEquals("2.2.1", sequenceVersion);

        String wordwrapVersion = versionResolver.getNextVersion(mock(Log.class), npmUrl, "wordwrap", ">=0.0.1 <0.1.0");
        assertNotNull(wordwrapVersion);
        assertEquals("0.0.1", wordwrapVersion);

    }

}
