
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Prediction extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Prediction() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	place;
	private Date	moment;
	private Double	precipitation;


	@NotBlank
	public String getPlace() {
		return this.place;
	}

	public void setPlace(final String place) {
		this.place = place;
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

	@NotNull
	public Double getPrecipitation() {
		return this.precipitation;
	}

	public void setPrecipitation(final Double precipitation) {
		this.precipitation = precipitation;
	}

	//	// Relationships ----------------------------------------------------------	
	//
	//	private WateringArea	wateringArea;
	//
	//
	//	@Valid
	//	@NotNull
	//	@ManyToOne(optional = false)
	//	public WateringArea getWateringArea() {
	//		return this.wateringArea;
	//	}
	//
	//	public void setWateringArea(final WateringArea wateringArea) {
	//		this.wateringArea = wateringArea;
	//	}

}
