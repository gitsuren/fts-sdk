package com.suru.featuretoggle.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.suru.featuretoggle.dto.FeaturesForIdResponse;

@RunWith(MockitoJUnitRunner.class)
public class FeaturesByIDRepositoryTest {

	@InjectMocks
	private FeaturesByIDRepository classToTest;

	@Mock
	private RestTemplate mockRestTemplate;

	@Before
	public void setup() {
		classToTest.systemName = "anySystemName";
		classToTest.url = "anyUrl";
	}

	@Test
	public void testetFeaturesByIdWhereReponseExists() {

		FeaturesForIdResponse response = new FeaturesForIdResponse();
		response.setId("anyId");
		response.setSystemName("anySystemName");
		when(mockRestTemplate.getForObject("anyUrl", FeaturesForIdResponse.class, new Object[] { "anySystemName", "anyId" }))
				.thenReturn(response);
		FeaturesForIdResponse result = classToTest.getFeaturesById("anyId");
		assertEquals("anyId", result.getId());
		assertEquals("anySystemName", result.getSystemName());
		verify(mockRestTemplate).getForObject("anyUrl", FeaturesForIdResponse.class, new Object[] { "anySystemName", "anyId" });
	}

	@Test
	public void testGetFeaturesByIdWhereReponseDoesNotExists() {

		when(mockRestTemplate.getForObject("anyUrl", FeaturesForIdResponse.class, new Object[] { "anySystemName", "anyId" }))
				.thenReturn(null);
		FeaturesForIdResponse result = classToTest.getFeaturesById("anyId");
		assertNull(result);
		verify(mockRestTemplate).getForObject("anyUrl", FeaturesForIdResponse.class, new Object[] { "anySystemName", "anyId" });
	}
	
	@Test
	public void testetFeaturesByIdWhereReponseWhenExceptionIsThrown() {
		
		when(mockRestTemplate.getForObject("anyUrl", FeaturesForIdResponse.class, new Object[] { "anySystemName", "anyId" })).thenThrow(new RuntimeException("hello"));
		FeaturesForIdResponse result = classToTest.getFeaturesById("anyId");
		assertNull(result);
	}
}
