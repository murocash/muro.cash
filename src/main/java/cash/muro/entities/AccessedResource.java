package cash.muro.entities;

import java.net.InetAddress;
import java.net.URI;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.Table;

import cash.muro.converters.InetAddress2BytesConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(AccessedResourceId.class)
@Table(indexes = { @Index(name = "idx_accessed_time", columnList = "accessed_time"), @Index(name = "idx_bought", columnList = "bought") }) 
public class AccessedResource {

	@Id
	private String userId;
	
	@Id
	private URI uri;
	
	@Column(nullable = false, length = 128)
	@Convert(converter = InetAddress2BytesConverter.class)
	private InetAddress ip;
	
	@Column(name = "accessed_time", nullable = false)
	private LocalDateTime accessedTime;
	private boolean bought = false;
	
	public AccessedResource(String userId, URI uri, InetAddress ip) {
		this.userId = userId;
		this.uri = uri;
		this.ip = ip;
	}
	
	public AccessedResourceId id() {
		return new AccessedResourceId(userId, uri);
	}

}
