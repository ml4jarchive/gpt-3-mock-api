# gpt-3-mock-api

A simple prototype of a mock gpt-3 api server,  configured to return real GPT-3 responses to a small number of selectable prompts.

I don't currently have access to the gpt3 API, however I was interested in exploring the request/response structure and wanted to run gpt3-experiments,
so I thought this mock api server may be useful.

This mock server has been written by reverse engineering the minimal request/response protocol required to run the zero-temperature experiments for the default config within the following repository :   https://github.com/minimaxir/gpt-3-experiments

( Remove non-zero temporatures from the config.yaml file within the gpt-3-experiments,  and update the open_api.py file replacing 'https' with 'http' and 'api.openai.com' with 'localhost:8080' to run against this mock server)

The mock server does not require headers to be set, and I'm assuming that responses only contain a subset of the actual response structure returned from the API.

I'd love an invite to be able to run these experiments for real.

# Running the mock server

Requires Java 8 or later.

The mock server can be clone and run via the following commands:

```
$ git clone https://github.com/ml4j/gpt-3-mock-api.git
$ cd gpt-3-mock-api
$ git submodule update --init
$ cd src/modules/ml4j-gpt-3-absolute-zero
$ git submodule update --init
$ cd ../../..
$ mvn spring-boot:run

```

## Maintainer/Creator

Michael Lavelle

## License

http://www.apache.org/licenses/LICENSE-2.0

## Disclaimer

This repo has no affiliation with OpenAI.
