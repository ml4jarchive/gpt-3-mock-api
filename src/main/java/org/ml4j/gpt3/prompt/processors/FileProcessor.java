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
import java.util.List;
import java.util.Map;

import org.ml4j.gpt3.GPT3Request;
/**
 * Processes a file ( may be a directory) containing information about mocked prompts
 * and outputs.
 * 
 * @author Michae Lavelle
 */
public interface FileProcessor {
	
	boolean isSupported(File file);

	Map<GPT3Request,List<String>>  processExample(File example) throws IOException;
}
