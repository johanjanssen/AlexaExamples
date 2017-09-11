package com.example;

import java.util.HashSet;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public final class TesterSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    // Be careful this makes your code available to anyone
    static {
        System.setProperty("com.amazon.speech.speechlet.servlet.disableRequestSignatureCheck", "true");
    }

    public TesterSpeechletRequestStreamHandler() {
        super(new TesterSpeechlet(), new HashSet<String>());
    }
}
