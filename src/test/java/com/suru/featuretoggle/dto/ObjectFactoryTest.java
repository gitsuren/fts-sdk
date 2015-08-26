package com.suru.featuretoggle.dto;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ObjectFactoryTest {

	@InjectMocks
	private ObjectFactory classToTest;
	
	@Test
	public void testCreateFeaturesForIdResponse() {
		
		FeaturesForIdResponse response = classToTest.createFeaturesForIdResponse();
		assertNotNull(response);
	}
}
