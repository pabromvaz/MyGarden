
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class TimeTable extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public TimeTable() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Date	activationMoment;
	private Date	deactivationMoment;


	@Future
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getActivationMoment() {
		return this.activationMoment;
	}

	public void setActivationMoment(final Date activationMoment) {
		this.activationMoment = activationMoment;
	}

	@Future
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeactivationMoment() {
		return this.deactivationMoment;
	}

	public void setDeactivationMoment(final Date deactivationMoment) {
		this.deactivationMoment = deactivationMoment;
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
