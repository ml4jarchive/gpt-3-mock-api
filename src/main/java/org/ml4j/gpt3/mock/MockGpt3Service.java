/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.ml4j.gpt3.mock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ml4j.gpt3.GPT3Choice;
import org.ml4j.gpt3.GPT3Request;
import org.ml4j.gpt3.GPT3Response;
import org.ml4j.gpt3.prompt.processors.DefaultPromptDirectoryProcessor;
import org.ml4j.gpt3.prompt.processors.DefaultPromptJsonProcessor;
import org.ml4j.gpt3.prompt.processors.FileProcessor;
import org.springframework.stereotype.Service;

/**
 * @author Michael Lavelle
 */
@Service
public class MockGpt3Service implements MockGpt3Api {

	public final static String DEFAULT_MODULES_PATH = "src/modules/ml4j-gpt-3-absolute-zero/src/modules";
	
	public final static String LOCAL_LATEX_IO_PATH = "src/local/examples/question-answer/latex-io";
	
	public final static String LOCAL_LATEX_QA_PATH = "src/local/examples/question-answer/latex-qa";
	
	public final static String LOCAL_QA_PATH = "src/local/examples/question-answer";
	

	public Map<GPT3Request, List<String>> outputsByRequest;

	public MockGpt3Service() throws FileNotFoundException, IOException {

		this.outputsByRequest = new HashMap<>();

		// Load the mock responses from the example files.
		Path resourcesDirectory = new File(MockGpt3Service.class.getClassLoader().getResource("").getFile()).toPath();

		Path experimentsDirectory = resourcesDirectory.getParent().getParent();

		File baseModulesDir = new File(experimentsDirectory.toFile(), DEFAULT_MODULES_PATH);
		processExampleDirectories(baseModulesDir, new DefaultPromptDirectoryProcessor(512, null, null,
				null, null));
		
		processExampleDirectories(baseModulesDir, new DefaultPromptJsonProcessor(null));
		
		File baseLocalDir1 = new File(experimentsDirectory.toFile(), LOCAL_LATEX_IO_PATH);
		processExampleDirectories(baseLocalDir1, new DefaultPromptDirectoryProcessor(100, 1, 1, false,
				"\ninput:"));
		
		File baseLocalDir2 = new File(experimentsDirectory.toFile(), LOCAL_LATEX_QA_PATH);
		processExampleDirectories(baseLocalDir2, new DefaultPromptDirectoryProcessor(100, 1, 1, false,
				"\nQ:"));
		
		File baseLocalDir3 = new File(experimentsDirectory.toFile(), LOCAL_QA_PATH);
		processExampleDirectories(baseLocalDir3, new DefaultPromptJsonProcessor(
				"\nWhat does the code below do? Write one comment for each of the following numbered statements:\n\n"));
	}
	
	private void processExampleDirectories(File baseDir, FileProcessor processor) throws IOException {
		if (baseDir != null && baseDir.exists()) {
			for (File example : getExampleDirectories(baseDir, processor)) {
				if (processor.isSupported(example)) {
					outputsByRequest.putAll(processor.processExample(example));
				}
			}
		}
	}
	
	private List<File> getExampleDirectories(File baseDir, FileProcessor processor) {
		List<File> currentDirectoryList = new ArrayList<>();
		processNestedFiles(currentDirectoryList, processor, baseDir);
		return currentDirectoryList;
	}
	
	private void processNestedFiles(List<File> currentDirectoryList, FileProcessor processor,
			File directory) {
		if (processor.isSupported(directory)) {
			currentDirectoryList.add(directory);
		}
		if (directory.isDirectory()) {
			for (File subDirectory : directory.listFiles(file -> (processor.isSupported(file) || file.isDirectory()))) {
				processNestedFiles(currentDirectoryList, processor, subDirectory);
			}
		}
	}
	
	public List<GPT3Request> getAvailableRequests() {
		List<GPT3Request> prompts = new ArrayList<>();
		prompts.addAll(outputsByRequest.keySet());
		return prompts;
	}

	public GPT3Response getResponse(GPT3Request request) {
		GPT3Response response = new GPT3Response();
		List<GPT3Choice> choicesInDescendingLikelyhoodOrder = getChoicesInDescendingLikelyhoodOrder(request);
		if (request.getTemperature().floatValue() == 0) {
			response.setChoices(Arrays.asList(choicesInDescendingLikelyhoodOrder.get(0)));
		} else {
			List<GPT3Choice> cs = new ArrayList<>();
			for (int i = 0; i < 1; i++) {
				cs.add(choicesInDescendingLikelyhoodOrder.get(0));
			}
			response.setChoices(cs);
		}
		return response;
	}

	private List<GPT3Choice> getChoicesInDescendingLikelyhoodOrder(GPT3Request request) {
		return getMockTexts(request).stream().map(text -> createChoice(text))
				.collect(Collectors.toList());
	}

	private GPT3Choice createChoice(String text) {
		GPT3Choice choice = new GPT3Choice();
		choice.setText(text);
		return choice;
	}

	private List<String> getMockTexts(GPT3Request request) {
		List<String> mockResponses = outputsByRequest.get(request);
		if (mockResponses != null && !mockResponses.isEmpty()) {
			return mockResponses;
		} else {
			throw new UnsupportedOperationException("Request not supported:" + request);
		}
	}
}
