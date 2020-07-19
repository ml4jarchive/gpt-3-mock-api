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
package org.ml4j.gpt3.mock.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.ml4j.gpt3.GPT3Request;
import org.ml4j.gpt3.GPT3Response;
import org.ml4j.gpt3.mock.MockGpt3Api;
import org.ml4j.gpt3.mock.MockGpt3Service;

/**
 * @author Michael Lavelle
 */
public class MockGpt3ApiLocalClient implements MockGpt3Api {

	private MockGpt3Service mockGpt3Service;
	
	public MockGpt3ApiLocalClient() throws FileNotFoundException, IOException {
		this.mockGpt3Service = new MockGpt3Service();
	}
	
	@Override
	public GPT3Response getResponse(GPT3Request request) {
		return mockGpt3Service.getResponse(request);
	}

	@Override
	public List<String> getAvailablePrompts() {
		return mockGpt3Service.getAvailablePrompts();
	}
}
