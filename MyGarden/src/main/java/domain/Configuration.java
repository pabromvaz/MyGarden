
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Configuration() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Boolean	manualWatering;
	private Boolean	automaticWatering;
	private Boolean	intrusionWarningActivated;
	private Boolean	fertilizerWarningActivated;
	private Boolean	tankWarningActivated;


	public Boolean getManualWatering() {
		return this.manualWatering;
	}

	public void setManualWatering(final Boolean manualWatering) {
		this.manualWatering = manualWatering;
	}

	public Boolean getAutomaticWatering() {
		return this.automaticWatering;
	}

	public void setAutomaticWatering(final Boolean automaticWatering) {
		this.automaticWatering = automaticWatering;
	}

	public Boolean getIntrusionWarningActivated() {
		return this.intrusionWarningActivated;
	}

	public void setIntrusionWarningActivated(final Boolean intrusionWarningActivated) {
		this.intrusionWarningActivated = intrusionWarningActivated;
	}

	public Boolean getFertilizerWarningActivated() {
		return this.fertilizerWarningActivated;
	}

	public void setFertilizerWarningActivated(final Boolean fertilizerWarningActivated) {
		this.fertilizerWarningActivated = fertilizerWarningActivated;
	}

	public Boolean getTankWarningActivated() {
		return this.tankWarningActivated;
	}

	public void setTankWarningActivated(final Boolean tankWarningActivated) {
		this.tankWarningActivated = tankWarningActivated;
	}


	// Relationships ----------------------------------------------------------

	private Collection<Event>	events;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "configuration")
	public Collection<Event> getEvents() {
		return this.events;
	}

	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

}
