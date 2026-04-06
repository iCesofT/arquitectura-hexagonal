package io.github.icesoft.catalog.persistence.comunidadautonoma.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COMUNIDAD_AUTONOMA")
@Getter
@Setter
@ToString(exclude = {"pais"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComunidadAutonomaEntity {

	@Id
	@Column(name = "ID", length = 2, nullable = false)
	private String id;

	@Column(name = "DENOMINACION", length = 50, nullable = false)
	private String denominacion;

	@Column(name = "RCP", length = 2)
	private String rcp;

	@Column(name = "DIR2", length = 2)
	private String dir2;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ComunidadAutonomaEntity that))
			return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

}