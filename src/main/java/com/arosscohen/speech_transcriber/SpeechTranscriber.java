package com.arosscohen.speech_transcriber;

import java.io.BufferedInputStream;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

class SpeechTranscriber {
  
  public static void main(String[] args) throws Throwable {
    
    Configuration configuration = new Configuration();
    configuration.setAcousticModelPath("resource:/com/arosscohen/speech_transcriber/cmusphinx-en-us-ptm-5.2");
    configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
    configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
    
    final StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
    
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        try {
          recognizer.stopRecognition();
        } catch (IllegalStateException e) {
          System.out.println(e.toString());
        }
      }
    });
    
    recognizer.startRecognition(new BufferedInputStream(System.in));
    
    SpeechResult result;
    while ((result = recognizer.getResult()) != null) {
      for (String s : result.getNbest(1)) {
        System.out.println(s);
      }
    }
    
  }
  
}
