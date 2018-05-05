
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class WateringArea extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public WateringArea() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	name;
	private String	place;
	private String	description;
	private String	picture;
	private Boolean	visible;
	private Boolean	valveActivated;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getPlace() {
		return this.place;
	}

	public void setPlace(final String place) {
		this.place = place;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(final Boolean visible) {
		this.visible = visible;
	}

	public Boolean getValveActivated() {
		return this.valveActivated;
	}

	public void setValveActivated(final Boolean valveActivated) {
		this.valveActivated = valveActivated;
	}


	// Relationships ----------------------------------------------------------
	private Gardener				gardener;
	private Collection<TimeTable>	timeTables;
	private Collection<Measurement>	measurements;
	private Collection<Event>		events;
	private Plant					plant;
	private Collection<Taste>		tastes;
	private Collection<Comment>		comments;
	private Collection<Prediction>	predictions;


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
	@OneToMany(mappedBy = "wateringArea")
	public Collection<TimeTable> getTimeTables() {
		return this.timeTables;
	}

	public void setTimeTables(final Collection<TimeTable> timeTables) {
		this.timeTables = timeTables;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "wateringArea")
	public Collection<Measurement> getMeasurements() {
		return this.measurements;
	}

	public void setMeasurements(final Collection<Measurement> measurements) {
		this.measurements = measurements;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "wateringArea")
	public Collection<Event> getEvents() {
		return this.events;
	}

	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Plant getPlant() {
		return this.plant;
	}

	public void setPlant(final Plant plant) {
		this.plant = plant;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "wateringArea")
	public Collection<Taste> getTastes() {
		return this.tastes;
	}

	public void setTastes(final Collection<Taste> tastes) {
		this.tastes = tastes;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "wateringArea")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "wateringArea")
	public Collection<Prediction> getPredictions() {
		return this.predictions;
	}

	public void setPredictions(final Collection<Prediction> predictions) {
		this.predictions = predictions;
	}
}
