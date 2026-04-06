package io.github.icesoft.catalog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 * Clase principal de la aplicación Spring Boot para el servicio de catálogo.
 * Configura el auto-escaneo de componentes y maneja el arranque de la aplicación.
 * Registra información de versión y build al inicializar el contexto.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@SpringBootApplication
@Slf4j
public class Application {

	@Autowired(required = false)
	private BuildProperties buildProperties;

	@Autowired(required = false)
	private GitProperties gitProperties;

	/**
	 * Punto de entrada principal de la aplicación.
	 * 
	 * @param args Argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Registra información de arranque cuando el contexto se ha refrescado completamente.
	 * Muestra versión del build y commit de git si están disponibles.
	 */
	@EventListener(ContextRefreshedEvent.class)
	public void logApplicationStartup() {
		if (buildProperties != null && gitProperties != null) {
			log.info("Application started {} {} ({})", buildProperties.getName(), buildProperties.getVersion(),
					gitProperties.getShortCommitId());
			log.debug("Time: {}", buildProperties.getTime());
		} else {
			log.info("Application started (build info not available)");
		}
	}

}
