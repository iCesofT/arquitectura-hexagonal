package io.github.icesoft.catalog.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "io.github.icesoft.catalog", importOptions = ImportOption.DoNotIncludeTests.class)
class HexagonalArchitectureTest {

	private static final String DOMAIN = "io.github.icesoft.catalog.domain..";
	private static final String APPLICATION = "io.github.icesoft.catalog.application..";
	private static final String PERSISTENCE = "io.github.icesoft.catalog.persistence..";
	private static final String INFRASTRUCTURE = "io.github.icesoft.catalog.infrastructure..";

	@ArchTest
	static final ArchRule dominioNoDependeDeAplicacionNiInfraestructura = noClasses()
			.that().resideInAPackage(DOMAIN)
			.should().dependOnClassesThat()
			.resideInAnyPackage(APPLICATION, PERSISTENCE, INFRASTRUCTURE)
			.because("El dominio es el núcleo y no debe conocer capas externas");

	@ArchTest
	static final ArchRule aplicacionNoDependeDeInfraestructura = noClasses()
			.that().resideInAPackage(APPLICATION)
			.should().dependOnClassesThat()
			.resideInAnyPackage(PERSISTENCE, INFRASTRUCTURE)
			.because("La capa de aplicación solo puede depender del dominio");

	@ArchTest
	static final ArchRule persistenciaNoDependeDeAplicacion = noClasses()
			.that().resideInAPackage(PERSISTENCE)
			.should().dependOnClassesThat()
			.resideInAPackage(APPLICATION)
			.because("Los adaptadores de persistencia solo implementan puertos del dominio");

	@ArchTest
	static final ArchRule arquitecturaHexagonalPorCapas = layeredArchitecture()
			.consideringOnlyDependenciesInLayers()
			.layer("Domain").definedBy(DOMAIN)
			.layer("Application").definedBy(APPLICATION)
			.layer("Persistence").definedBy(PERSISTENCE)
			.layer("Infrastructure").definedBy(INFRASTRUCTURE)
			.whereLayer("Domain").mayNotAccessAnyLayer()
			.whereLayer("Application").mayOnlyAccessLayers("Domain")
			.whereLayer("Persistence").mayOnlyAccessLayers("Domain")
			.whereLayer("Infrastructure").mayOnlyAccessLayers("Application", "Domain")
			.because("La arquitectura hexagonal exige dependencias hacia el interior");
}
