package cash.muro.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class MuroProduct {
	
	@Id
	String code;
	
	@Column(nullable = false, unique = true, precision = 25, scale = 8)
	BigDecimal price;
	
	@Column(nullable = false, unique = true)
	int amount; //amount of resources to access
	
	public MuroProduct(String code, BigDecimal price, int amount) {
		this.code = code;
		this.price = price;
		this.amount = amount;
	}

}
