
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Fertilizer extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Fertilizer() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	name;
	private String	description;
	private Double	ph;
	private Double	nitrogen;
	private Double	phosphorus;
	private Double	potassium;


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
	public Double getPh() {
		return this.ph;
	}

	public void setPh(final Double ph) {
		this.ph = ph;
	}

	@NotNull
	public Double getNitrogen() {
		return this.nitrogen;
	}

	public void setNitrogen(final Double nitrogen) {
		this.nitrogen = nitrogen;
	}

	@NotNull
	public Double getPhosphorus() {
		return this.phosphorus;
	}

	public void setPhosphorus(final Double phosphorus) {
		this.phosphorus = phosphorus;
	}

	@NotNull
	public Double getPotassium() {
		return this.potassium;
	}

	public void setPotassium(final Double potassium) {
		this.potassium = potassium;
	}

	// Relationships ----------------------------------------------------------

}
