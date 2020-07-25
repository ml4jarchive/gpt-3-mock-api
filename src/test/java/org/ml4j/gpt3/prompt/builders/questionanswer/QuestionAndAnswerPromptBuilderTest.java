package org.ml4j.gpt3.prompt.builders.questionanswer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class QuestionAndAnswerPromptBuilderTest {
	
	private final String DEFAULT_Q_AND_A_PROMPT_URL = 
			"https://raw.githubusercontent.com/ml4j/gtp-3-prompt-templates/master/question-answer/"
			+ "default/examples/example1/question_answer_example_prompt_1.json";
	
	private final String DEFAULT_Q_AND_A_TEMPLATE_URL = "https://raw.githubusercontent.com/ml4j/gtp-3-prompt-templates/master/question-answer/"
			+ "default/templates/question_answer_template_1.json";
	
	private final String CODE_EXPLANATION_Q_AND_A_PROMPT_URL = 
			"https://raw.githubusercontent.com/ml4j/gtp-3-prompt-templates/master/question-answer/code-explanation/examples/java-example-1/"
			+ "code_explanation_example_prompt_1.json";
	
	private final String CODE_EXPLANATION_Q_AND_A_TEMPLATE_URL = "https://raw.githubusercontent.com/ml4j/gtp-3-prompt-templates/master/question-answer"
			+ "/code-explanation/templates/code_explanation_template_1.json";
			
	@Test
	public void testDefaultQuestionAnswerStructureWithDefaultQuestion() throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		
		QuestionAndAnswerPromptBuilder builder
			= new QuestionAndAnswerPromptBuilder(new URL(DEFAULT_Q_AND_A_PROMPT_URL), 
					new URL(DEFAULT_Q_AND_A_TEMPLATE_URL));
		
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
		
		String promptText = builder.buildPromptText(null);
				
		Assertions.assertEquals(expectedPromptText, promptText);
		
	}
	
	@Test
	public void testDefaultQuestionAnswerStructureWithCustomQuestion() throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		
		QuestionAndAnswerPromptBuilder builder
		= new QuestionAndAnswerPromptBuilder(new URL(DEFAULT_Q_AND_A_PROMPT_URL), 
				new URL(DEFAULT_Q_AND_A_TEMPLATE_URL));
		
		String customQuestion = "Is this a good question?";
		
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
				"Q: " + customQuestion + "\n" + 
				"\n" + 
				"A: ";
		
		String promptText = builder.buildPromptText(customQuestion);
				
		Assertions.assertEquals(expectedPromptText, promptText);
		
	}
	
	@Test
	public void testCodeExplanationQuestionAnswerStructureWithDefaultQuestion() throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		
		QuestionAndAnswerPromptBuilder builder
			= new QuestionAndAnswerPromptBuilder(new URL(CODE_EXPLANATION_Q_AND_A_PROMPT_URL), 
					new URL(CODE_EXPLANATION_Q_AND_A_TEMPLATE_URL));
		
		String expectedPromptText = "What does the code below do? Write one comment for each of the following numbered statements:\n" + 
				"\n" + 
				"Statement 1) stream().map(Dimension::getName).collect(Collectors.toList()).toString();\n" + 
				"Statement 2) stream().flatMap(c -> c.decompose().stream()).collect(Collectors.toList());\n" + 
				"Statement 3) allDecomposedAliases.add(decompose().stream().collect(Collectors.toList()));\n" + 
				"Statement 4) box.getScaledCorners(originalImage.getWidth(), originalImage.getHeight()));\n" + 
				"Statement 5) List<Image> subImages = channelConcatImages.subList(channelRangeStart, channelRangeEnd);\n" + 
				"\n" + 
				"Comments:\n" + 
				"\n" + 
				"Statement 1) // Obtain the names of the dimensions, collect them into a list, and return the list as a string.\n" + 
				"Statement 2) // Call decompose recursively on each of the elements of the stream and collect the results into a list\n" + 
				"Statement 3) // Collect the aliases from the stream returned by the decompose() method into a list, and add them to allDecomposedAliases\n" + 
				"Statement 4) // Get the scaled corners of a bounding box using its width and height of the original image\n" + 
				"Statement 5) // Obtain a list of the elements of subImages with indexes been channelRangeStart (inclusive) and channelRangeEnd (exclusive) and assign the list to a local variable called subImages\n" + 
				"\n" + 
				"What does the code below do? Write one comment for each of the following numbered statements:\n" + 
				"\n" + 
				"Statement 1) List<User> friendsList = me.getFriends();\n" + 
				"Statement 2) List<User> genXFriends = friendsList.stream().filter(user.getAge() > 40 user.getAge() <= 55).collect(Collectiors.toList());\n" + 
				"Statement 3) genXFriends.forEach(user -> user.sendMessage(\"Hi \" + user.getScreenName() + \"you are the best!\");\n" + 
				"\n" + 
				"Comments:\n" + 
				"\n" + 
				"";
		
		String promptText = builder.buildPromptText(null);
				
		Assertions.assertEquals(expectedPromptText, promptText);
		
	}
	
	@Test
	public void testCodeExplanationQuestionAnswerStructureWithCustomQuestion() throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		
		QuestionAndAnswerPromptBuilder builder
		= new QuestionAndAnswerPromptBuilder(new URL(CODE_EXPLANATION_Q_AND_A_PROMPT_URL), 
				new URL(CODE_EXPLANATION_Q_AND_A_TEMPLATE_URL));
		
		String customQuestion = "Statement 1) users.forEach(user -> System.out.println(user.getGender()));";
		
		String expectedPromptText = "What does the code below do? Write one comment for each of the following numbered statements:\n" + 
				"\n" + 
				"Statement 1) stream().map(Dimension::getName).collect(Collectors.toList()).toString();\n" + 
				"Statement 2) stream().flatMap(c -> c.decompose().stream()).collect(Collectors.toList());\n" + 
				"Statement 3) allDecomposedAliases.add(decompose().stream().collect(Collectors.toList()));\n" + 
				"Statement 4) box.getScaledCorners(originalImage.getWidth(), originalImage.getHeight()));\n" + 
				"Statement 5) List<Image> subImages = channelConcatImages.subList(channelRangeStart, channelRangeEnd);\n" + 
				"\n" + 
				"Comments:\n" + 
				"\n" + 
				"Statement 1) // Obtain the names of the dimensions, collect them into a list, and return the list as a string.\n" + 
				"Statement 2) // Call decompose recursively on each of the elements of the stream and collect the results into a list\n" + 
				"Statement 3) // Collect the aliases from the stream returned by the decompose() method into a list, and add them to allDecomposedAliases\n" + 
				"Statement 4) // Get the scaled corners of a bounding box using its width and height of the original image\n" + 
				"Statement 5) // Obtain a list of the elements of subImages with indexes been channelRangeStart (inclusive) and channelRangeEnd (exclusive) and assign the list to a local variable called subImages\n" + 
				"\n" + 
				"What does the code below do? Write one comment for each of the following numbered statements:\n" + 
				"\n" + 
				"Statement 1) users.forEach(user -> System.out.println(user.getGender()));\n" + 
				"\n" + 
				"Comments:\n" + 
				"\n" + 
				"";
		
		String promptText = builder.buildPromptText(customQuestion);
				
		Assertions.assertEquals(expectedPromptText, promptText);
		
	}
}
