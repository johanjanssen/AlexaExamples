package com.example;

import java.util.HashSet;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public final class MySecondSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    // Be careful this makes your code available to anyone
    static {
        System.setProperty("com.amazon.speech.speechlet.servlet.disableRequestSignatureCheck", "true");
    }

    public MySecondSpeechletRequestStreamHandler() {
        super(new MySecondSpeechlet(), new HashSet<String>());
    }
}
