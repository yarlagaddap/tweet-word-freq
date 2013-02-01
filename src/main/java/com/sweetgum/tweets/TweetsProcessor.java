package com.sweetgum.tweets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.PropertyConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

public class TweetsProcessor {
private static final Log LOG = LogFactory.getLog(TweetsProcessor.class);

//Ideally this config will go in a properties file
//Praveen's access codes. The related twitter app will be deleted after 10 days.
private static final String CONSUMER_KEY = "hIeWsLXX0BNqXmLE3BZhWQ";
private static final String CONSUMER_SECRET =
    "Qlp7BjsUP3LPmefFeCMIt8eA7zYwJBOMNhwV76wDeI";
private static final String ACCESS_TOKEN =
    "1138314637-XkSbnNMhTdGNjXt94kGgPekaKrOMUxpr3C6t8Xq";
private static final String ACCESS_SECRET =
    "rRCUeLM4NoRvgutjfwg8etld8r4VtrvZ1SrgH8MQQ64";

private static final Paging DEFAULT_PAGING = new Paging(1, 1000);
private static final TweetsProcessor instance = new TweetsProcessor();

private Twitter twitter;

private TweetsProcessor() {
  Properties props = new Properties();
  props.setProperty(PropertyConfiguration.OAUTH_CONSUMER_KEY, CONSUMER_KEY);
  props.setProperty(PropertyConfiguration.OAUTH_CONSUMER_SECRET,
      CONSUMER_SECRET);
  props.setProperty(PropertyConfiguration.OAUTH_ACCESS_TOKEN, ACCESS_TOKEN);
  props.setProperty(PropertyConfiguration.OAUTH_ACCESS_TOKEN_SECRET,
      ACCESS_SECRET);
  Configuration conf = new PropertyConfiguration(props);
  this.twitter = new TwitterFactory(conf).getInstance();
}

public static TweetsProcessor getInstance() {
  return instance;
}

public List<WordFreq> getWordFreqs(String screenName) throws TwitterException {
  ResponseList<Status> statuses = this.twitter.getUserTimeline(screenName, DEFAULT_PAGING);
  return getWordFreq(statuses);
}


public List<WordFreq> getWordFreq(ResponseList<Status> statuses) {
  if (statuses == null || statuses.isEmpty()) {
    return Collections.emptyList();
  }

  Map<String, WordFreq> wordFreqMap = new HashMap<String, WordFreq>();

  for (Status status : statuses) {
    if (LOG.isDebugEnabled()) {
      LOG.debug(status.getText());
    }

    String tweet = TweetUtils.cleanUp(status.getText());
    if (tweet == null || tweet.length() == 0) {
      continue;
    }

    StringTokenizer tokenizer = new StringTokenizer(tweet);
    while (tokenizer.hasMoreTokens()) {
      String token = tokenizer.nextToken();
      WordFreq wordFreq = wordFreqMap.get(token);
      if (wordFreq == null) {
        wordFreq = new WordFreq(token);
        wordFreqMap.put(wordFreq.getWord(), wordFreq);
      }
      wordFreq.incFreq();
    }
  }
  List<WordFreq> wordFreqList = new ArrayList<WordFreq>(wordFreqMap.values());
  Collections.sort(wordFreqList, new WordFreqListRevComparator());
  return wordFreqList;
}

private static class WordFreqListRevComparator implements Comparator<WordFreq> {

  @Override
  public int compare(WordFreq wordFreq1, WordFreq wordFreq2) {
    if (wordFreq1.getFreq() < wordFreq2.getFreq()) {
      return 1;
    } else if (wordFreq1.getFreq() > wordFreq2.getFreq()) {
      return -1;
    }
    return 0;
  }
}

}
