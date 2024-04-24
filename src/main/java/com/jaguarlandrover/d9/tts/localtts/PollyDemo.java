package com.jaguarlandrover.d9.tts.localtts;

import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

public class PollyDemo {

  //private final AmazonPollyClient polly;
  private final AmazonPolly polly;
  private final Voice voice;
  private static final String SAMPLE = "I hate this horrible car";

  public PollyDemo(Region region) {
    // create an Amazon Polly client in a specific region
    //polly = new AmazonPollyClient(new DefaultAWSCredentialsProviderChain(),
     //   new ClientConfiguration());
    polly = AmazonPollyClientBuilder.standard().withRegion(region.getName()).build();
   // AmazonPolly client = AmazonPollyClientBuilder.defaultClient();
    //polly.setRegion(region);
    // Create describe voices request.
    DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest().withLanguageCode(LanguageCode.fromValue("en-GB"));

    // Synchronously ask Amazon Polly to describe available TTS voices.
    DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
  //  DescribeVoicesResult describeVoicesResult = client.describeVoices(describeVoicesRequest);
    voice = describeVoicesResult.getVoices().get(0);
  }

  public InputStream synthesize(String text, OutputFormat format) throws IOException {
    SynthesizeSpeechRequest synthReq =
        new SynthesizeSpeechRequest().withText(text)//.withVoiceId(voice.getId())
            .withOutputFormat(format).withEngine("neural");
    SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);


    return synthRes.getAudioStream();
  }

  public static void main(String args[]) throws Exception {
    //create the test class
    PollyDemo helloWorld = new PollyDemo(Region.getRegion(Regions.EU_WEST_1));
    //get the audio stream
    InputStream speechStream = helloWorld.synthesize(SAMPLE, OutputFormat.Mp3);

    putAudioInS3(speechStream);

    //create an MP3 player
  /*  AdvancedPlayer player = new AdvancedPlayer(speechStream,
        javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

    player.setPlayBackListener(new PlaybackListener() {
      @Override
      public void playbackStarted(PlaybackEvent evt) {
        System.out.println("Playback started");
        System.out.println(SAMPLE);
      }

      @Override
      public void playbackFinished(PlaybackEvent evt) {
        System.out.println("Playback finished");
      }
    });


    // play it!
    player.play();*/

  }

  private static String putAudioInS3(InputStream speechStream) {
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();
    String bucketName = "tts-workshop2";

   // s3.putObject("tts", "example-1", speechStream, new ObjectMetadata());
    final String objectKey = "example-1.mp3";
    PutObjectRequest objectRequest = new PutObjectRequest(bucketName, objectKey, speechStream, new ObjectMetadata()).withInputStream(speechStream);//.withCannedAcl(CannedAccessControlList.PublicRead);

    s3.putObject(objectRequest);

    final Calendar instance = Calendar.getInstance();

    instance.add(Calendar.MINUTE, 1);


    GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
            .withMethod(HttpMethod.GET).withExpiration(instance.getTime());

    URL presignedUrl = s3.generatePresignedUrl(urlRequest);

    String objectUrl = s3.getUrl("tts-workshop2", objectKey).toString();
    System.out.println(objectUrl);
    System.out.println(presignedUrl);
    return objectUrl;

  }
}