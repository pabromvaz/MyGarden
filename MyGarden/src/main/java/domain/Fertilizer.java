
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Fertilizer extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Fertilizer() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	name;
	private String	picture;
	private String	description;
	private Double	ph;
	private Double	nitrogen;
	private Double	phosphorus;
	private Double	potassium;


	//	private String	type;

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
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


	//	@NotBlank
	//	@Pattern(regexp = "(Humic acid||Chemical||Slow release||Liquid||Amino acid)")
	//	public String getType() {
	//		return this.type;
	//	}
	//
	//	public void setType(final String type) {
	//		this.type = type;
	//	}

	// Relationships ----------------------------------------------------------
	private Collection<Plant>	plants;


	@Valid
	@NotNull
	@ManyToMany()
	public Collection<Plant> getPlants() {
		return this.plants;
	}

	public void setPlants(final Collection<Plant> plants) {
		this.plants = plants;
	}

	public void addPlant(final Plant plant) {
		this.plants.add(plant);
	}
}
