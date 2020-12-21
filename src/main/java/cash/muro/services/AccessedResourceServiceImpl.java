package cash.muro.services;

import java.net.URI;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cash.muro.entities.AccessedResource;
import cash.muro.entities.AccessedResourceId;
import cash.muro.entities.MuroSettings;
import cash.muro.repos.AccessedResourceRepository;
import cash.muro.repos.MuroSettingsRepository;

@Service
public class AccessedResourceServiceImpl implements AccessedResourceService {
	
	@Autowired
	private MuroSettings muroSettings;

	@Autowired
	private AccessedResourceRepository resourceRepository;
	
	@Autowired
	private MuroSettingsRepository settingsRepository;
	
	@Override
	public boolean hasBalance(String userId) {
		return (resourceRepository.getFreeBalance(userId) + resourceRepository.getBoughtBalance(userId)) > 0;
	}

	@Override
	public int freeBalance(String userId) {
		return resourceRepository.getFreeBalance(userId);
	}

	@Override
	public int boughtBalance(String userId) {
		return resourceRepository.getBoughtBalance(userId);
	}
	
	@Override
	public AccessedResource save(AccessedResource resource) {
		if (!resourceRepository.existsById(resource.id())) {
			resource.setAccessedTime(LocalDateTime.now());
			resource.setBought(resourceRepository.getFreeBalance(resource.getUserId()) < 1);
			return resourceRepository.save(resource);
		}
		return resource;
	}	
	
	@Override
	public boolean allowed(String userId, URI uri) {
		return resourceRepository.existsById(new AccessedResourceId(userId, uri)) || hasBalance(userId);
	}

	@PostConstruct
    public void init() {
		settingsRepository.save(muroSettings);		
    }

}
