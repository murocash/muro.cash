package cash.muro.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MuroPayment implements Serializable{

	@Id
	@Column(name = "payment_receiver")
	private String receiver;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_receiver", insertable = false, updatable = false)
	private MuroCheckout checkout;
	
	@Column(nullable = false)
	private String txId;
	
	@Column(nullable = false)
	private LocalDateTime dateTime = LocalDateTime.now();
	
	public MuroPayment(MuroCheckout checkout, String txId) {
		this.receiver= checkout.getReceiver();
		this.checkout = checkout;
		this.txId = txId;
	}

}
