package io.github.icesoft.catalog.application.pais.cucumber;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Test runner para los tests de Cucumber de los casos de uso de País
 */
@Suite
@SelectClasspathResource("features/pais")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "io.github.icesoft.catalog.application.pais.cucumber")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty")
public class PaisUseCasesRunnerTest {
}
