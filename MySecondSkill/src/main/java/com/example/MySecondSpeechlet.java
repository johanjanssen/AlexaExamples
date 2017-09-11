package com.example;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import java.util.Random;

public class MySecondSpeechlet implements Speechlet {

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        // Place for initialization logic.
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        // Place for launch logic, such as a welcome message.
        return getWelcomeResponse();
    }

    /**
     * Triggered by telling Alexa: Alexa start [skill invocation name].
     * @return
     */
    private SpeechletResponse getWelcomeResponse() {
        String speechText = "Welcome to my second skill";

        // Card used in the Alexa interface
        SimpleCard card = new SimpleCard();
        card.setTitle("Welcome reponse");
        card.setContent(speechText);

        // The text that will be spoken by Alexa
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Will wait for an answer. No need to say 'Alexa' again.
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        // Place for cleanup logic.
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("MyFirstIntent".equals(intentName)) {
            return getMyFirstAppIntentResponse();
        } else if ("OtherNameIntent".equals(intentName)) {
            return getRandomResponse();
        }else if ("AMAZON.StopIntent".equals(intentName)) {
            return getStopResponse();
        }else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }


    /**
     * Triggered by telling Alexa: Alexa tell/ask [skill invocation name] hello.
     * @return
     */
    private SpeechletResponse getMyFirstAppIntentResponse() {
        String text = "Hello from My Second Skill";

        // Card used in the Alexa interface
        SimpleCard card = new SimpleCard();
        card.setTitle("Normal response");
        card.setContent(text);

        // The text that will be spoken by Alexa
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(text);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    /**
     * Triggered by telling Alexa: Alexa tell/ask [skill invocation name] random number.
     * @return
     */
    private SpeechletResponse getRandomResponse() {
        Random rand = new Random();
        int  number = rand.nextInt(50) + 1;
        String text = String.valueOf(number);

        // Card used in the Alexa interface
        SimpleCard card = new SimpleCard();
        card.setTitle("Random response");
        card.setContent(text);

        // The text that will be spoken by Alexa
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(text);

        // Will wait for an answer. No need to say 'Alexa' again.
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    /**
     * Triggered by telling Alexa: Alexa tell/ask [skill invocation name] help.
     * @return
     */
    private SpeechletResponse getHelpResponse() {
        String text = "Hello how can I help you? You can say hello to me!";

        // Card used in the Alexa interface
        SimpleCard card = new SimpleCard();
        card.setTitle("Help response");
        card.setContent(text);

        // The text that will be spoken by Alexa
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(text);

        // Will wait for an answer. No need to say 'Alexa' again.
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }


    /**
     * Triggered by telling Alexa: stop.
     * @return
     */
    private SpeechletResponse getStopResponse() {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("See you next time");
        return SpeechletResponse.newTellResponse(speech);
    }
}
