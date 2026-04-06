package io.github.icesoft.catalog.persistence.localidad.entities;

import io.github.icesoft.catalog.persistence.provincia.entities.ProvinciaEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LOCALIDAD", uniqueConstraints = @UniqueConstraint(columnNames = {"PROVINCIA_ID",
		"DENOMINACION"}, name = "UK_LOCALIDAD_PROVINCIA_DENOMINACION"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"provincia"})
public class LocalidadEntity {

	@Id
	@Column(name = "ID", length = 4, nullable = false)
	private String id;

	@Column(name = "DENOMINACION", length = 50, nullable = false)
	private String denominacion;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROVINCIA_ID", referencedColumnName = "ID", nullable = false)
	private ProvinciaEntity provincia;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof LocalidadEntity that))
			return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

}