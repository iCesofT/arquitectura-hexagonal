package io.github.icesoft.catalog.persistence.provincia.entities;

import io.github.icesoft.catalog.persistence.comunidadautonoma.entities.ComunidadAutonomaEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PROVINCIA")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"comunidadAutonoma"})
public class ProvinciaEntity {

	@Id
	@Column(name = "ID", length = 2, nullable = false)
	private String id;

	@Column(name = "DENOMINACION", length = 50, nullable = false)
	private String denominacion;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CA_ID", referencedColumnName = "ID", nullable = false)
	private ComunidadAutonomaEntity comunidadAutonoma;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ProvinciaEntity that))
			return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

}