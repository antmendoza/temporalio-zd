# Temporal Remote Data Encoder

This example is based on the implementation provided in the [sdk-java repository](https://github.com/temporalio/sdk-java/tree/master/temporal-remote-data-encoder), 


### run:
- run Temporal Server.
- run EncryptedPayloadsActivity, to deploy a workflow which payloads are encrypted using CryptCodec
- run Main class to start the server. it starts in localhost:8888
- go to localhost:8080 (temporal UI) and add set the url for the Data Encoder to localhost:8888

The Data Encoder will decrypt the input/output of `EncryptedPayloadsActivity` workflows
