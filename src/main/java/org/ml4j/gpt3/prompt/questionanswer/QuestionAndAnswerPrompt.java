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
package org.ml4j.gpt3.prompt.questionanswer;

import java.util.List;

public class QuestionAndAnswerPrompt {

	private String premise;
	private List<QuestionAndAnswer> questionsAndAnswers;
	private String defaultPromptQuestion;
	public String getPremise() {
		return premise;
	}
	public void setPremise(String premise) {
		this.premise = premise;
	}
	public List<QuestionAndAnswer> getQuestionsAndAnswers() {
		return questionsAndAnswers;
	}
	public void setQuestionsAndAnswers(List<QuestionAndAnswer> questionsAndAnswers) {
		this.questionsAndAnswers = questionsAndAnswers;
	}
	public String getDefaultPromptQuestion() {
		return defaultPromptQuestion;
	}
	public void setDefaultPromptQuestion(String defaultPromptQuestion) {
		this.defaultPromptQuestion = defaultPromptQuestion;
	}
	
	@Override
	public String toString() {
		return "Prompt [premise=" + premise + ", questionsAndAnswers=" + questionsAndAnswers
				+ ", defaultPromptQuestion=" + defaultPromptQuestion + "]";
	}

}
