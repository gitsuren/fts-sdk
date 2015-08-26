package com.suru.featuretoggle.service.program;

import org.springframework.beans.factory.annotation.Autowired;

import com.suru.featuretoggle.service.FeatureService;

public class FeatureToggleServiceTestProgram extends BaseTestProgram {

	@Autowired
	private FeatureService service;

	@Override
	protected void execute() throws Exception {

		boolean result = service.hasAccess("du80449", "NEW_ACR_DATA");
		System.out.println("Result 1: " + result);
		result = service.hasAccess("du80449", "TESTING");
		System.out.println("Result 2: " + result);
		result = service.hasAccess("chuckie", "TESTING");
		System.out.println("Result 3: " + result);
	}

	public static void main(String[] args) throws Exception {

		FeatureToggleServiceTestProgram program = new FeatureToggleServiceTestProgram();
		program.run();
	}
}
