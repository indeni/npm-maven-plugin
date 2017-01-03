/**
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.npm;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.settings.Settings;

import java.io.File;

/**
 * Goal that offers Recess support in Maven builds.
 *
 * @goal fetch-modules
 * @phase generate-sources
 */
public class NPMMojo extends AbstractJavascriptMojo {

    public static final String DEFAULT_NPM_URL = "http://registry.npmjs.org/%s/%s";
    /**
     * Where the resulting files will be downloaded.
     *
     * @parameter expression="${recess.outputDirectory}" default-value="${basedir}/src/main/resources/META-INF"
     */
    private File outputDirectory;

    /**
     * Which repository to use for NPM
     *
     * @parameter expression="${recess.npmUrl}" default-value="http://registry.npmjs.org/%s/%s"
     */
    private String npmUrl;

    /**
     * The identifiers of the packages to download. Use the following syntax: package:version
     *
     * @parameter expression="${recess.packages}
     * @required
     */
    private String[] packages;

    /**
     * The Maven Settings.
     *
     * @parameter default-value="${settings}"
     * @required
     * @readonly
     */
    private Settings settings;

    /**
     * Optional user to authenticate to NPM registry with
     *
     * @parameter expression="${recess.username}"
     */
    private String username;

    /**
     * Optional password to authenticate to NPM registry with
     *
     * @parameter expression="${recess.password}"
     */
    private String password;

    public void execute() throws MojoExecutionException {
        Log log = getLog();

        NPMModule.proxy = settings.getActiveProxy();
        NPMModule.username = this.username;
        NPMModule.password = this.password;
        for (String aPackage : packages) {
            NPMModule.fromQueryString(log, npmUrl, aPackage).saveToFileWithDependencies(outputDirectory);
        }
    }
}
