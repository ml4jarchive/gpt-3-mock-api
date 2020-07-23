package org.ml4j.gpt3.prompt.processors;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ml4j.gpt3.GPT3Request;

public class DefaultPromptFileProcessor implements FileProcessor {

	private int maxTokens;
	private Integer topP;
	private Integer n;
	private Boolean stream;
	private String stop;
	
	public DefaultPromptFileProcessor(int maxTokens, Integer topP, Integer n,  Boolean stream, String stop) {
		this.maxTokens = maxTokens;
		this.topP = topP;
		this.n = n;
		this.stream = stream;
		this.stop = stop;
	}
	
	@Override
	public boolean isSupported(File file) {
		return file.isFile();
	}
	
	private BigDecimal getTemperature(File file) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	private int getSplitIndex(File file) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Map<GPT3Request, List<String>> processExample(File example) throws IOException {
		Map<GPT3Request, List<String>> processed = new HashMap<>();
		if (example.isFile()) {
			String text = new String(Files.readAllBytes(example.toPath()));
			
			int splitIndex = getSplitIndex(example);
			
			String prompt = text.substring(0, splitIndex);
			String output = text.substring(splitIndex);
			GPT3Request request = new GPT3Request();
			request.setPrompt(prompt);
			request.setTemperature(getTemperature(example));
			request.setMaxTokens(maxTokens);
			request.setN(n);
			request.setTopP(topP);
			request.setStop(stop);
			request.setStream(stream);
			processed.put(request, Arrays.asList(output));
		}
		return processed;
	}

}
