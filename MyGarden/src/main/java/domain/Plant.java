
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Plant extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Plant() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	name;
	private String	description;
	private Double	minTemperature;
	private Double	maxTemperature;
	private Double	moisture;
	private Double	pH;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Range(min = -20, max = 60)
	public Double getMinTemperature() {
		return this.minTemperature;
	}

	public void setMinTemperature(final Double minTemperature) {
		this.minTemperature = minTemperature;
	}

	@NotNull
	@Range(min = -20, max = 60)
	public Double getMaxTemperature() {
		return this.maxTemperature;
	}

	public void setMaxTemperature(final Double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	@NotNull
	public Double getMoisture() {
		return this.moisture;
	}

	public void setMoisture(final Double moisture) {
		this.moisture = moisture;
	}

	@NotNull
	@Range(min = 1, max = 14)
	public Double getpH() {
		return this.pH;
	}

	public void setpH(final Double pH) {
		this.pH = pH;
	}


	// Relationships ----------------------------------------------------------
	private Collection<WateringArea>	wateringAreas;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "plant")
	public Collection<WateringArea> getWateringAreas() {
		return this.wateringAreas;
	}

	public void setWateringAreas(final Collection<WateringArea> wateringAreas) {
		this.wateringAreas = wateringAreas;
	}

}
