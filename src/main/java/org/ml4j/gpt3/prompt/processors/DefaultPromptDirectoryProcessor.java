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
package org.ml4j.gpt3.prompt.processors;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ml4j.gpt3.GPT3Request;

/**
 * Processes a file ( may be a directory) containing information about mocked prompts
 * and outputs.
 * 
 * @author Michael Lavelle
 */
public class DefaultPromptDirectoryProcessor implements FileProcessor {
	
	public boolean isSupported(File directory) {
		if (directory != null && directory.isDirectory()) {
			File[] promptFiles = directory.listFiles(file -> file.getPath().endsWith("prompt.txt"));
			if (promptFiles.length == 1 && directory.listFiles(file -> file.getPath().contains("output_")).length > 0) {
				return true;
			}
		}
		return false;
	}

	public Map<GPT3Request,List<String>>  processExample(File example) throws IOException {
		
		Map<GPT3Request,List<String>> outputsByRequest = new HashMap<>();
		
		// Obtain the contents of the prompt file.
		File promptFile = example.listFiles(file -> file.getPath().endsWith("prompt.txt"))[0];
		String prompt = new String(Files.readAllBytes(promptFile.toPath()));
		// For each output file, extract the temperature from the file name, and read
		// the contents
		// of the file, splitting into multiple out strings.
		for (File file : example.listFiles(file -> file.getPath().contains("output_"))) {
			String temperatureString = file.getPath().substring(0, file.getPath().lastIndexOf("_") + 2);
			temperatureString = temperatureString
					.substring(temperatureString.length() - 3, temperatureString.length()).replace('_', '.');
			BigDecimal temperature = new BigDecimal(temperatureString);
			String entireFileContents = new String(Files.readAllBytes(file.toPath()));
			// TODO - make this more robust.
			if (file.getPath().endsWith(".md")) {
				entireFileContents = entireFileContents.replaceAll("\\*\\*", "");
			}
			String[] parts = entireFileContents.split("---");
			for (String part : parts) {
				if (part.length() > prompt.length() + 1) {
					String output = part.substring(prompt.length());
					if (output.endsWith("\n")) {
						output = output.substring(0, output.length() - 2);
					}
					GPT3Request r = new GPT3Request();
					r.setPrompt(prompt);
					r.setTemperature(temperature);
					r.setMaxTokens(512);

					List<String> outputs = outputsByRequest.get(r);
					if (outputs == null) {
						outputs = new ArrayList<>();
						GPT3Request request = new GPT3Request();
						request.setPrompt(prompt);
						request.setMaxTokens(512);
						request.setTemperature(temperature);
						outputsByRequest.put(request, outputs);
					}
					outputs.add(output);
				}
			}
		}
		
		return outputsByRequest;
	}
}
