package org.ml4j.gpt3.prompt.processors;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ml4j.gpt3.GPT3Request;
import org.ml4j.gpt3.prompt.PromptSubmission;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultPromptJsonProcessor implements FileProcessor {

	private ObjectMapper objectMapper;
	private String stop;
	
	public DefaultPromptJsonProcessor(String stop) {
		this.objectMapper = new ObjectMapper();
		this.stop = stop;
	}
	
	@Override
	public boolean isSupported(File file) {
		return file.isFile() && file.getName().contains("submission") && file.getName().endsWith(".json");
	}

	@Override
	public Map<GPT3Request, List<String>> processExample(File example) throws IOException {
		Map<GPT3Request, List<String>> processed = new HashMap<>();
		if (example.isFile()) {
			PromptSubmission promptSubmission = objectMapper.readValue(example, PromptSubmission.class);
			if (promptSubmission.getSubmissionResults() != null) {
				for (SubmissionResult submissionResult : promptSubmission.getSubmissionResults() ) {
					GPT3Request request = submissionResult.getRequest();
					if (stop != null) {
						request.setStop(stop);
					}
					processed.put(request, Arrays.asList(submissionResult.getResponseText()));
				}
			}
		}
		return processed;
	}
}
