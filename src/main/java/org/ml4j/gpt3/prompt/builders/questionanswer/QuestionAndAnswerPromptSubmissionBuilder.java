package org.ml4j.gpt3.prompt.builders.questionanswer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.ml4j.gpt3.GPT3Request;
import org.ml4j.gpt3.prompt.PromptSubmission;
import org.ml4j.gpt3.prompt.processors.SubmissionResult;

public class QuestionAndAnswerPromptSubmissionBuilder {

	private List<SubmissionResult> submissionResults;
	private String initialPromptText;
	private String currentPromptText;
	private String promptId;
	private String templateId;
	private String answerProviderName; 
	
	public QuestionAndAnswerPromptSubmissionBuilder(QuestionAndAnswerPromptBuilder promptBuilder, String question) {
		this.submissionResults = new ArrayList<>();
		this.initialPromptText = promptBuilder.buildPromptText(question);
		this.currentPromptText = initialPromptText;
		this.promptId = promptBuilder.getPromptId();
		this.templateId = promptBuilder.getTemplateId();
	}

	public QuestionAndAnswerPromptSubmissionBuilder(String initialPromptText, String promptId, String templateId) {
		this.submissionResults = new ArrayList<>();
		this.initialPromptText = initialPromptText;
		this.currentPromptText = initialPromptText;
		this.promptId = promptId;
		this.templateId = templateId;
	}
	
	public QuestionAndAnswerPromptSubmissionBuilder withAnswerProviderName(String answerProviderName) {
		this.answerProviderName = answerProviderName;
		return this;
	}

	public QuestionAndAnswerPromptSubmissionBuilder withSubmission(String randomness, int length, 
			String providerResponseAdditionalText) {
		GPT3Request request = new GPT3Request();
		request.setPrompt(currentPromptText);
		request.setTemperature(new BigDecimal(randomness));
		request.setMaxTokens(length);
		SubmissionResult submissionResult
		 = new SubmissionResult();
		submissionResult.setRequest(request);
		submissionResult.setSubmittedTo(answerProviderName);
		submissionResult.setResponseText(providerResponseAdditionalText);
		submissionResults.add(submissionResult);
		currentPromptText = currentPromptText + providerResponseAdditionalText;
		return this;
	}
	
	public PromptSubmission build() {
		PromptSubmission promptSubmission 
		 = new PromptSubmission();
		promptSubmission.setPromptId(promptId);
		promptSubmission.setTemplateId(templateId);
		promptSubmission.setInitialPromptText(initialPromptText);
		promptSubmission.setSubmissionResults(submissionResults);
		return promptSubmission;
	}
	
	public String getInitialPromptText() {
		return initialPromptText;
	}

	public String getCurrentPromptText() {
		return currentPromptText;
	}

	public String getPromptId() {
		return promptId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getAnswerProviderName() {
		return answerProviderName;
	}
}
