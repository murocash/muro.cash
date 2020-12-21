package cash.muro.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class DeletedEntity<E extends Object> implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne (optional = false)
	private E entity;
	
	@Column(nullable = false)
	private final Instant when = Instant.now();

	@Column(nullable = false)
	private boolean isDeleted = true;
	
	/**
	 * Creates a DeletedEntity marked as Deleted.
	 * @param entity the entity to be marked as deleted
	 */
	public DeletedEntity(E entity) {
		this.entity = entity;
	}
	
	/**
	 * Creates a DeletedEntity and allows to establish its state as not deleted (restored)
	 * @param entity
	 * @param isDeleted
	 */
	public DeletedEntity(E entity, boolean isDeleted) {
		this.entity = entity;
		this.isDeleted = isDeleted;
	}
}
