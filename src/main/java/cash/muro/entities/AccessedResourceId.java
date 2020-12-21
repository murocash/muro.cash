package cash.muro.entities;

import java.io.Serializable;
import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Convert;

import cash.muro.converters.Uri2StringConverter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccessedResourceId implements Serializable {
	
	private String userId;
	
	@Column(name = "uri")
	@Convert(converter = Uri2StringConverter.class)
	private URI uri;

}
