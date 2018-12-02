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

import static java.lang.String.format;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Validates YAML content against given JSON schema. */
@Singleton
public class DevFileSchemaValidator {

  private JsonValidator validator;
  private ObjectMapper yamlReader;
  private DevFileSchemaProvider schemaProvider;

  @Inject
  public DevFileSchemaValidator(DevFileSchemaProvider schemaProvider) throws IOException {
    this.schemaProvider = schemaProvider;
    this.validator = JsonSchemaFactory.byDefault().getValidator();
    this.yamlReader = new ObjectMapper(new YAMLFactory());
  }

  public JsonNode validateBySchema(String yamlContent, boolean verbose)
      throws DevFileFormatException {
    ProcessingReport report;
    JsonNode data;
    try {
      data = yamlReader.readTree(yamlContent);
      report = validator.validate(schemaProvider.getJsoneNode(), data);
    } catch (IOException | ProcessingException e) {
      throw new DevFileFormatException("Unable to validate Devfile. Error: " + e.getMessage());
    }
    if (!report.isSuccess()) {
      String error =
          StreamSupport.stream(report.spliterator(), false)
              .filter(m -> m.getLogLevel() == LogLevel.ERROR || m.getLogLevel() == LogLevel.FATAL)
              .map(message -> verbose ? message.asJson().toString() : message.getMessage())
              .collect(Collectors.joining(", ", "[", "]"));
      throw new DevFileFormatException(
          format("Devfile schema validation failed. Errors: %s", error));
    }
    return data;
  }
}
