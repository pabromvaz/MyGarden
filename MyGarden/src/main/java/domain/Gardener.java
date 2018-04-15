
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Gardener extends Actor {

	// Constructors ----------------------------------------------------------
	public Gardener() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	picture;
	private Boolean	animalDetectionEventActivated;
	private Boolean	useOfFertilizerEventActivated;
	private Boolean	waterTankEventActivated;


	@NotBlank
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public Boolean getAnimalDetectionEventActivated() {
		return this.animalDetectionEventActivated;
	}

	public void setAnimalDetectionEventActivated(final Boolean animalDetectionEventActivated) {
		this.animalDetectionEventActivated = animalDetectionEventActivated;
	}

	public Boolean getUseOfFertilizerEventActivated() {
		return this.useOfFertilizerEventActivated;
	}

	public void setUseOfFertilizerEventActivated(final Boolean useOfFertilizerEventActivated) {
		this.useOfFertilizerEventActivated = useOfFertilizerEventActivated;
	}

	public Boolean getWaterTankEventActivated() {
		return this.waterTankEventActivated;
	}

	public void setWaterTankEventActivated(final Boolean waterTankEventActivated) {
		this.waterTankEventActivated = waterTankEventActivated;
	}


	// Relationships ----------------------------------------------------------
	private Collection<WateringArea>	wateringAreas;
	private Collection<Taste>			tastes;
	private Collection<Comment>			comments;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "gardener")
	public Collection<WateringArea> getWateringAreas() {
		return this.wateringAreas;
	}

	public void setWateringAreas(final Collection<WateringArea> wateringAreas) {
		this.wateringAreas = wateringAreas;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "gardener")
	public Collection<Taste> getTastes() {
		return this.tastes;
	}

	public void setTastes(final Collection<Taste> tastes) {
		this.tastes = tastes;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "gardener")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}
}
