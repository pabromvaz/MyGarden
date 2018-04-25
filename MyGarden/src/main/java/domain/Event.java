
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Event extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Event() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	name;
	private String	description;
	private Date	moment;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
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

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	// Relationships ----------------------------------------------------------
	private WateringArea	wateringArea;


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
