/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api.devfile.server;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

public class DevFileSchemaValidatorTest {

  private DevFileSchemaValidator schemaValidator;

  @BeforeClass
  public void setUp() throws Exception {
    schemaValidator = new DevFileSchemaValidator();
  }

  @Test
  public void shouldValidateCorrectYamlBySchema() throws Exception {
    String devFileYamlContent =
        Files.readFile(getClass().getClassLoader().getResourceAsStream("devfile.yaml"));
    // when
    schemaValidator.validateBySchema(devFileYamlContent, false);
  }

  @Test(
      expectedExceptions = DevFileFormatException.class,
      expectedExceptionsMessageRegExp =
          "Devfile schema validation failed. Errors: \\[object has missing required properties \\(\\[\"name\"\\]\\)\\]$")
  public void shouldValidateIncorrectYamlBySchema() throws Exception {
    String devFileYamlContent =
        Files.readFile(getClass().getClassLoader().getResourceAsStream("devfile_bad.yaml"));
    // when
    schemaValidator.validateBySchema(devFileYamlContent, false);
  }
}
