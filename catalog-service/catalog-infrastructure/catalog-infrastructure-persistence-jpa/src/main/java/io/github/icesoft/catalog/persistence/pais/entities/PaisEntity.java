package io.github.icesoft.catalog.persistence.pais.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad JPA que representa la tabla PAIS en la base de datos. Mapea los datos
 * de países con sus códigos ISO estándar para integración con sistemas externos
 * y normalización de datos geográficos.
 * 
 * <p>
 * Incluye índices únicos en los códigos ALFA2 y ALFA3 para garantizar
 * integridad referencial y optimizar búsquedas por código ISO.
 * </p>
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Entity
@Table(name = "PAIS", indexes = {@Index(name = "UK_PAIS_ALFA3", columnList = "ALFA3", unique = true),
		@Index(name = "UK_PAIS_ALFA2", columnList = "ALFA2", unique = true)})
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaisEntity {

	@Id
	@Column(name = "ID", length = 3, nullable = false)
	private String id;

	@Column(name = "ALFA3", length = 3, nullable = false, unique = true)
	private String alfa3;

	@Column(name = "ALFA2", length = 2, nullable = false, unique = true)
	private String alfa2;

	@Column(name = "DENOMINACION", length = 50, nullable = false)
	private String denominacion;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof PaisEntity that))
			return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

}