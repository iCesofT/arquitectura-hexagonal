package io.github.icesoft.catalog.application.tipovia.cucumber;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Test runner para los tests de Cucumber de los casos de uso de Tipo de Vía
 */
@Suite
@SelectClasspathResource("features/tipovia")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "io.github.icesoft.catalog.application.tipovia.cucumber")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty")
public class TipoViaUseCasesRunnerTest {
}
