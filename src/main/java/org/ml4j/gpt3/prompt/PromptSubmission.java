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
package org.ml4j.gpt3.prompt;

import java.util.List;

import org.ml4j.gpt3.prompt.processors.SubmissionResult;

/**
 * Represents the submission of a prompt to a completion service such as the GPT-3 API or AIDungeon.
 * 
 * Encapsulates the initial (seed) prompt text, an optional templateId/promptId if the prompt text was generated
 * from a identifiable prompt or template, and a list of SubmissionResults.
 * This SubmissionResults list includes the result of the initial submission of this prompt to the service,
 * plus any subsequent submissions.

 * 
 * @author Michael Lavelle
 */
public class PromptSubmission {

	private String templateId;
	private String promptId;
	private String initialPromptText;
	private List<SubmissionResult> submissionResults;
	
	public String getTemplateId() {
		return templateId;
	}
	public String getPromptId() {
		return promptId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public void setPromptId(String promptId) {
		this.promptId = promptId;
	}
	
	public List<SubmissionResult> getSubmissionResults() {
		return submissionResults;
	}
	public void setSubmissionResults(List<SubmissionResult> submissionResults) {
		this.submissionResults = submissionResults;
	}
	public String getInitialPromptText() {
		return initialPromptText;
	}
	public void setInitialPromptText(String initialPromptText) {
		this.initialPromptText = initialPromptText;
	}
}
