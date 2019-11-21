package com.onrkrdmn.cache.model;

public interface Cacheable {

	boolean isExpired();

	Object getIdentifier();

	Object getObject();
}
