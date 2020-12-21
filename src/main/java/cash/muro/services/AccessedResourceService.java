package cash.muro.services;

import java.net.URI;

import cash.muro.entities.AccessedResource;

public interface AccessedResourceService {
	
	boolean hasBalance(String userId);

	int freeBalance(String userId);
	
	int boughtBalance(String userId);

	AccessedResource save(AccessedResource resource);

	boolean allowed(String userId, URI resource);
}
