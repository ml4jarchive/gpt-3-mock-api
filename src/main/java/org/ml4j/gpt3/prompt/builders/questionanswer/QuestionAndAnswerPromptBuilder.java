package org.ml4j.gpt3.prompt.builders.questionanswer;

import java.io.IOException;
import java.net.URL;

import org.ml4j.gpt3.prompt.format.questionanswer.QuestionAndAnswerTemplate;
import org.ml4j.gpt3.prompt.style.questionanswer.QuestionAndAnswer;
import org.ml4j.gpt3.prompt.style.questionanswer.QuestionAndAnswerPrompt;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QuestionAndAnswerPromptBuilder {

	private QuestionAndAnswerPrompt questionAndAnswerPrompt;
	private QuestionAndAnswerTemplate template;
	private String templateId;
	private String promptId;

	public QuestionAndAnswerPromptBuilder(URL promptUrl, URL templateUrl)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		this.template = objectMapper.readValue(templateUrl, QuestionAndAnswerTemplate.class);
		this.questionAndAnswerPrompt = objectMapper.readValue(promptUrl, QuestionAndAnswerPrompt.class);
		this.templateId = templateUrl.toString();
		this.promptId = promptUrl.toString();
	}

	public QuestionAndAnswerPromptBuilder(QuestionAndAnswerPrompt questionAndAnswerPrompt, String promptId, QuestionAndAnswerTemplate template, String templateId) {
		this.questionAndAnswerPrompt = questionAndAnswerPrompt;
		this.template = template;
		this.promptId = promptId;
		this.templateId = templateId;
	}

	public QuestionAndAnswerPrompt getQuestionAndAnswerPrompt() {
		return questionAndAnswerPrompt;
	}

	public QuestionAndAnswerTemplate getTemplate() {
		return template;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getPromptId() {
		return promptId;
	}

	public String buildPromptText(String question) {
		String promptQuestion = question != null ? question : questionAndAnswerPrompt.getDefaultPromptQuestion();

		StringBuilder out = new StringBuilder();
		out.append(questionAndAnswerPrompt.getPremise());
		out.append(template.getPremiseSuffix());
		for (QuestionAndAnswer qa : questionAndAnswerPrompt.getQuestionsAndAnswers()) {
			String qaString = template.getQuestionPrefix() + qa.getQuestion() + template.getQuestionSuffix()
					+ template.getAnswerPrefix() + qa.getAnswer() + template.getAnswerSuffix();
			out.append(qaString);
		}
		out.append(template.getQuestionPrefix() + promptQuestion + template.getQuestionSuffix()
				+ template.getAnswerPrefix());

		return out.toString();
	}
}
