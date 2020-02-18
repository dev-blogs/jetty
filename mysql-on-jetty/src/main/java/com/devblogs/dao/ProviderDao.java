package com.devblogs.dao;

import com.devblogs.model.Provider;

public interface ProviderDao {
	int saveProvider(Provider provider);
	String findAllByNameUsingPrepareStatement(String name);
	String findAllByNameUsingStatement(String name);
	String findAllByName(String name);
	Provider findProviderByName(String name);
}