package io.github.icesoft.catalog.persistence.tipovia.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "TIPO_VIA")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoViaEntity {

	@Id
	@Column(name = "ID", length = 2, nullable = false)
	private String id;

	@Column(name = "CLAVE", length = 50, nullable = false)
	private String clave;

	@Column(name = "DENOMINACION", length = 50)
	private String denominacion;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof TipoViaEntity that))
			return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

}