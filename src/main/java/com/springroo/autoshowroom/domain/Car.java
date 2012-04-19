package com.springroo.autoshowroom.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Car {

    @NotNull
    private String modelName;

    @NotNull
    @Enumerated
    private Model model;

    @NotNull
    @Min(10L)
    private Integer price;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfProduction;

    @NotNull
    @ManyToOne
    private Customer customer;

	public String getModelName() {
        return this.modelName;
    }

	public void setModelName(String modelName) {
        this.modelName = modelName;
    }

	public Model getModel() {
        return this.model;
    }

	public void setModel(Model model) {
        this.model = model;
    }

	public Integer getPrice() {
        return this.price;
    }

	public void setPrice(Integer price) {
        this.price = price;
    }

	public Date getDateOfProduction() {
        return this.dateOfProduction;
    }

	public void setDateOfProduction(Date dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

	public Customer getCustomer() {
        return this.customer;
    }

	public void setCustomer(Customer customer) {
        this.customer = customer;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Car().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCars() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Car o", Long.class).getSingleResult();
    }

	public static List<Car> findAllCars() {
        return entityManager().createQuery("SELECT o FROM Car o", Car.class).getResultList();
    }

	public static Car findCar(Long id) {
        if (id == null) return null;
        return entityManager().find(Car.class, id);
    }

	public static List<Car> findCarEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Car o", Car.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Car attached = Car.findCar(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public Car merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Car merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }
}
