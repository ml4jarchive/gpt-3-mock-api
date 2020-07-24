package org.ml4j.gpt3.prompt.processors;

import org.ml4j.gpt3.GPT3Request;

public class SubmissionResult {

	private GPT3Request request;
	private String submittedTo;
	private String responseText;
	public GPT3Request getRequest() {
		return request;
	}
	public void setRequest(GPT3Request request) {
		this.request = request;
	}
	public String getSubmittedTo() {
		return submittedTo;
	}
	public void setSubmittedTo(String submittedTo) {
		this.submittedTo = submittedTo;
	}
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
}
