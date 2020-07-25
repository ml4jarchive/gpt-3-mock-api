package org.ml4j.gpt3.prompt.format.questionanswer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionAndAnswerTemplate {

	private String defaultPremise;
	private String premiseSuffix;
	private String questionPrefix;
	private String questionSuffix;
	private String answerPrefix;
	private String answerSuffix;
	
	public String getDefaultPremise() {
		return defaultPremise;
	}

	public void setDefaultPremise(String defaultPremise) {
		this.defaultPremise = defaultPremise;
	}

	public String getPremiseSuffix() {
		return premiseSuffix;
	}

	public void setPremiseSuffix(String premiseSuffix) {
		this.premiseSuffix = premiseSuffix;
	}
	
	public String getQuestionPrefix() {
		return questionPrefix;
	}
	
	public void setQuestionPrefix(String questionPrefix) {
		this.questionPrefix = questionPrefix;
	}
	public String getAnswerPrefix() {
		return answerPrefix;
	}
	public void setAnswerPrefix(String answerPrefix) {
		this.answerPrefix = answerPrefix;
	}
	
	public String getAnswerSuffix() {
		return answerSuffix;
	}

	public void setAnswerSuffix(String answerSuffix) {
		this.answerSuffix = answerSuffix;
	}

	public String getQuestionSuffix() {
		return questionSuffix;
	}

	public void setQuestionSuffix(String questionSuffix) {
		this.questionSuffix = questionSuffix;
	}
}
