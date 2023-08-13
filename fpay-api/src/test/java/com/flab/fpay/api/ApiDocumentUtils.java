package com.flab.fpay.api;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public class ApiDocumentUtils {

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint()); // (3)
    }

}
