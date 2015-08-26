package com.suru.featuretoggle.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.featuretoggle.dto.FeaturesForIdResponse;
import com.suru.featuretoggle.service.cache.NoCacheStrategy;

@RunWith(MockitoJUnitRunner.class)
public class FeatureServiceTest {

	private static final String ANY_ID = "anyId";
	private static final String ANY_SYSTEM_NAME = "anySystemName";
	private static final String ANY_FEATURE_NAME = "anyFeatureName";
	
	@InjectMocks
	private FeatureService classToTest;
	 	
	@Mock
	private NoCacheStrategy mockCacheStrategy;
	
	@Test
	public void testVerifyAccessThatShouldReturnFalseDueToNoFeatures() {
		
		FeaturesForIdResponse response = new FeaturesForIdResponse();
		response.setId(ANY_ID);
		response.setSystemName(ANY_SYSTEM_NAME);
		boolean result = classToTest.hasAccess(ANY_ID, ANY_FEATURE_NAME);
		assertEquals(false, result);
		assertEquals(ANY_SYSTEM_NAME, response.getSystemName());
		assertEquals(ANY_ID, response.getId());
	}
	
	@Test
	public void testVerifyAccessThatShouldReturnTrue() {

		FeaturesForIdResponse response = new FeaturesForIdResponse();
		response.setId(ANY_ID);
		response.setSystemName(ANY_SYSTEM_NAME);
		response.getFeatures().add(ANY_FEATURE_NAME);
		when(mockCacheStrategy.getResponse(ANY_ID)).thenReturn(response);
		boolean result = classToTest.hasAccess(ANY_ID, ANY_FEATURE_NAME);
		assertEquals(true, result);
		assertEquals(ANY_SYSTEM_NAME, response.getSystemName());
		assertEquals(ANY_ID, response.getId());

	}
}
