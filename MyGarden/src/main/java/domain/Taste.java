
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@AttributeOverrides({
	@AttributeOverride(name = "like", column = @Column(name = "likeWateringArea"))
})
public class Taste extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Taste() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Boolean	like;


	public Boolean getLike() {
		return this.like;
	}

	public void setLike(final Boolean like) {
		this.like = like;
	}


	// Relationships ----------------------------------------------------------
	private Gardener		gardener;
	private WateringArea	wateringArea;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Gardener getGardener() {
		return this.gardener;
	}

	public void setGardener(final Gardener gardener) {
		this.gardener = gardener;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public WateringArea getWateringArea() {
		return this.wateringArea;
	}

	public void setWateringArea(final WateringArea wateringArea) {
		this.wateringArea = wateringArea;
	}

}
