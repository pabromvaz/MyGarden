
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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Measurement extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Measurement() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Integer	moisture;
	private Integer	humidity;
	private Integer	temperature;
	private Date	moment;


	@NotNull
	public Integer getMoisture() {
		return this.moisture;
	}

	public void setMoisture(final Integer moisture) {
		this.moisture = moisture;
	}

	@NotNull
	public Integer getHumidity() {
		return this.humidity;
	}

	public void setHumidity(final Integer humidity) {
		this.humidity = humidity;
	}

	@NotNull
	public Integer getTemperature() {
		return this.temperature;
	}

	public void setTemperature(final Integer temperature) {
		this.temperature = temperature;
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
