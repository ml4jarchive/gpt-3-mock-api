package org.ml4j.gpt3.prompt.builders.questionanswer;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ml4j.gpt3.prompt.PromptSubmission;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class QuestionAndAnswerPromptBuilderSubmissionTest {
	
	private final String DEFAULT_Q_AND_A_PROMPT_URL = 
			"https://raw.githubusercontent.com/ml4j/gtp-3-prompt-templates/master/question-answer/"
			+ "default/examples/example1/question_answer_example_prompt_1.json";
	
	private final String DEFAULT_Q_AND_A_TEMPLATE_URL = "https://raw.githubusercontent.com/ml4j/gtp-3-prompt-templates/master/question-answer/"
			+ "default/templates/question_answer_template_1.json";
				
	@Test
	public void testDefaultQuestionAnswerStructureWithDefaultQuestion() throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		
		QuestionAndAnswerPromptBuilder builder
			= new QuestionAndAnswerPromptBuilder(new URL(DEFAULT_Q_AND_A_PROMPT_URL), 
					new URL(DEFAULT_Q_AND_A_TEMPLATE_URL));
		
		
		QuestionAndAnswerPromptSubmissionBuilder submissionBuilder
		 	= new QuestionAndAnswerPromptSubmissionBuilder(builder, null).withAnswerProviderName("testAnswerProvider"); 
		
		
		String expectedPromptText = "Q: What is human life expectancy in the United States?\n" + 
				"\n" + 
				"A: Human life expectancy in the United States is 78 years.\n" + 
				"\n" + 
				"Q: Who was president of the United States in 1955?\n" + 
				"\n" + 
				"A: Dwight D. Eisenhower was president of the United States in 1955.\n" + 
				"\n" + 
				"Q: What party did he belong to?\n" + 
				"\n" + 
				"A: He belonged to the Republican Party.\n" + 
				"\n" + 
				"Q: Who was president of the United States before George W. Bush?\n" + 
				"\n" + 
				"A: Bill Clinton was president of the United States before George W. Bush.\n" + 
				"\n" + 
				"Q: Who won the World Series in 1995?\n" + 
				"\n" + 
				"A: ";
		
		
		PromptSubmission promptSubmission = submissionBuilder.build();
						
		Assertions.assertEquals(expectedPromptText, promptSubmission.getInitialPromptText());
		Assertions.assertEquals(DEFAULT_Q_AND_A_PROMPT_URL, promptSubmission.getPromptId());
		Assertions.assertEquals(DEFAULT_Q_AND_A_TEMPLATE_URL, promptSubmission.getTemplateId());
		Assertions.assertEquals(expectedPromptText, submissionBuilder.getCurrentPromptText());

		Assertions.assertTrue(promptSubmission.getSubmissionResults().isEmpty());
		
		submissionBuilder.withSubmission("0.1", 100, "The Atlanta Braves won the World Series in 1995.\n\nQ:");
		
		Assertions.assertEquals(expectedPromptText, promptSubmission.getInitialPromptText());
		Assertions.assertEquals(DEFAULT_Q_AND_A_PROMPT_URL, promptSubmission.getPromptId());
		Assertions.assertEquals(DEFAULT_Q_AND_A_TEMPLATE_URL, promptSubmission.getTemplateId());
		Assertions.assertEquals(1, promptSubmission.getSubmissionResults().size());
		Assertions.assertEquals("testAnswerProvider", promptSubmission.getSubmissionResults().get(0).getSubmittedTo());
		Assertions.assertEquals("The Atlanta Braves won the World Series in 1995.\n\nQ:", 
				promptSubmission.getSubmissionResults().get(0).getResponseText());
		Assertions.assertEquals(100, 
				promptSubmission.getSubmissionResults().get(0).getRequest().getMaxTokens());
		Assertions.assertEquals(new BigDecimal("0.1"), 
				promptSubmission.getSubmissionResults().get(0).getRequest().getTemperature());
		
		String expectedSubmissionText = "Q: What is human life expectancy in the United States?\n" + 
				"\n" + 
				"A: Human life expectancy in the United States is 78 years.\n" + 
				"\n" + 
				"Q: Who was president of the United States in 1955?\n" + 
				"\n" + 
				"A: Dwight D. Eisenhower was president of the United States in 1955.\n" + 
				"\n" + 
				"Q: What party did he belong to?\n" + 
				"\n" + 
				"A: He belonged to the Republican Party.\n" + 
				"\n" + 
				"Q: Who was president of the United States before George W. Bush?\n" + 
				"\n" + 
				"A: Bill Clinton was president of the United States before George W. Bush.\n" + 
				"\n" + 
				"Q: Who won the World Series in 1995?\n" + 
				"\n" + 
				"A: The Atlanta Braves won the World Series in 1995.\n" + 
				"\n" + 
				"Q:";
		
		Assertions.assertEquals(expectedSubmissionText, submissionBuilder.getCurrentPromptText());

	}
}
