
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
	private Double	moisture;
	private Double	humidity;
	private Double	temperature;
	private Double	light;
	private Double	ph;
	private Double	nitrogen;
	private Double	phosphorus;
	private Double	potassium;
	private Date	moment;


	@NotNull
	public Double getMoisture() {
		return this.moisture;
	}

	public void setMoisture(final Double moisture) {
		this.moisture = moisture;
	}

	@NotNull
	public Double getHumidity() {
		return this.humidity;
	}

	public void setHumidity(final Double humidity) {
		this.humidity = humidity;
	}

	@NotNull
	public Double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(final Double temperature) {
		this.temperature = temperature;
	}

	@NotNull
	public Double getLight() {
		return this.light;
	}

	public void setLight(final Double light) {
		this.light = light;
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
