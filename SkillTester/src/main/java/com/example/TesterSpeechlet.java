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

import java.util.ArrayList;
import java.util.List;

public class TesterSpeechlet implements Speechlet {

    private final List<Check> checkList = new ArrayList();

    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        session.setAttribute("instructionNumber", 0);
        if (checkList.isEmpty()) {
            checkList.add(new Check("Alexa ask second to say hello", "hello from my second skill"));
            checkList.add(new Check("Alexa open second", "welcome to my second skill"));
            checkList.add(new Check("stop", "see you next time"));
        }
    }

    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        // Place for launch logic, such as a welcome message.
        return null;
    }


    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        // Place for cleanup logic.
    }

    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("TestIntent".equals(intentName)) {
            return getTestResponse(intent, session);
        } else if ("AnswerIntent".equals(intentName)) {
            return getAnswerResponse(intent, session);
        } else if ("AMAZON.StopIntent".equals(intentName)) {
            return getStopResponse();
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    /**
     * Triggered by telling Alexa: Alexa tell/ask [skill invocation name] start.
     */
    private SpeechletResponse getTestResponse(Intent intent, Session session) {
        Integer instructionNumber = (Integer) session.getAttribute("instructionNumber");
        return getInstructionResponse(session, instructionNumber);

    }

    /**
     * Triggered by the reply of another Alexa device
     */
    private SpeechletResponse getAnswerResponse(Intent intent, Session session) {
        Integer instructionNumber = (Integer) session.getAttribute("instructionNumber");
        Check currentCheck = checkList.get(instructionNumber);

        instructionNumber++;
        session.setAttribute("instructionNumber", instructionNumber);

        // Track the failed checks
        if (!currentCheck.getExpectedResponse().equalsIgnoreCase(intent.getSlot("Answer").getValue())) {
            currentCheck.setReceivedResponse(intent.getSlot("Answer").getValue());
            currentCheck.setPassed(false);
        } else {
            currentCheck.setPassed(true);
        }

        // Processed all checks
        if (instructionNumber.equals(checkList.size())) {
            return processResults();
        }

        return getInstructionResponse(session, instructionNumber);
    }

    /**
     * Send a request to another Alexa device and wait for the response.
     */
    private SpeechletResponse getInstructionResponse(Session session, Integer instructionNumber) {
        String text = checkList.get(instructionNumber).getRequest();

        // Card used in the Alexa interface
        SimpleCard card = new SimpleCard();
        card.setTitle("Test instruction");
        card.setContent(text);

        // The text that will be spoken by Alexa
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(text);

        // Will wait for an answer. No need to say 'Alexa' again.
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card); // Notice: Ask
    }

    /**
     * Create a card and voice response with the results of the checks
     */
    private SpeechletResponse processResults() {
        String text = "";
        for (int i = 0; i < checkList.size(); i++) {
            Check check = checkList.get(i);
            if (!check.isPassed()) {
                text+= i + "\n\t expected " + check.getExpectedResponse() +
                        "\n\t received " + check.getReceivedResponse() + "\n";
            }
        }

        if ("".equals(text)) {
            text = "Testing the skill was successful, no issues were found.";
        } else {
            text = "The following requests gave an unexpected answer\n" + text;
        }

        SimpleCard card = new SimpleCard();
        card.setTitle("Testing finished");
        card.setContent(text);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(text);
        return SpeechletResponse.newTellResponse(speech, card); // Notice: Tell
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
