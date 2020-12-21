package cash.muro.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = { @Index(columnList = "sender") })
public class MuroCheckout {

	@Id
	private String receiver;

	private String sender;

	@OneToOne
	private MuroProduct product;

}
