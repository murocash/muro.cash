package cash.muro.converters;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class InetAddress2BytesConverter implements AttributeConverter<InetAddress, byte[]> {

	    @Override
	    public byte[] convertToDatabaseColumn(InetAddress entityValue) {
	        return (entityValue == null) ? null : entityValue.getAddress();
	    }

	    @Override
	    public InetAddress convertToEntityAttribute(byte[] databaseValue) {
	        try {
				return (databaseValue.length > 0 ? InetAddress.getByAddress(databaseValue) : null);
			} catch (UnknownHostException e) {
				return null;
			}
	    }
}
