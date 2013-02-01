package com.sweetgum.tweets;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import twitter4j.TwitterException;

import java.util.List;

public class TweetWordFreqMain {
private static final Log LOG = LogFactory.getLog(TweetWordFreqMain.class);
private static Options ALL_OPTIONS = new Options();

private CommandLineParser commandLineParser;
private TweetsProcessor tweetsProcessor;

static {
  ALL_OPTIONS.addOption(Constants.OPT_ACTION_HELP);
  ALL_OPTIONS.addOption(Constants.OPT_ACTION_TWEET_WORD_FREQ);
  ALL_OPTIONS.addOption(Constants.OPT_U);
}

public TweetWordFreqMain(TweetsProcessor processor) {
  this.tweetsProcessor = processor;
  commandLineParser = new GnuParser();
}

private void handleTweetWordFreq(String userName) {
  List<WordFreq> wordFreqList;
  try {
    wordFreqList = tweetsProcessor.getWordFreqs(userName);
    if (wordFreqList.isEmpty()) {
      LOG.info(String.format("No tweets from the user %s", userName));
      return;
    }
    for (WordFreq wordFreq : wordFreqList) {
      LOG.info(wordFreq);
    }
  } catch (TwitterException ex) {
    if (ex.getStatusCode() == 404) {
      LOG.error(String.format("User %s NOT found!", userName));
    }
  } catch (Exception ex) {
    LOG.error("Failed to get the tweets!", ex);
  }
}

private static void handleHelp() {
  LOG.info(Constants.HELP_FORMAT_LINE);
  HelpFormatter formatter = new HelpFormatter();
  formatter.printHelp(Constants.USAGE, Constants.HEADER, ALL_OPTIONS, "");
}

private void handleCommand(String args[]) {
  try {
    CommandLine commandLine = commandLineParser.parse(ALL_OPTIONS, args);
    if (commandLine.hasOption(Constants.ACTION_TWEET_WORD_FREQ)) {
      String userName = commandLine.getOptionValue(Constants.PARAM_U);
      handleTweetWordFreq(userName);
    } else if (commandLine.hasOption(Constants.ACTION_HELP)) {
      handleHelp();
    } else {
      LOG.error("invalid command!");
    }
  } catch (Exception ex) {
    LOG.error("invalid command!", ex);
  }
}


public static void main(String args[]) {
  TweetsProcessor tweetsProcessor = TweetsProcessor.getInstance();
  TweetWordFreqMain tweetWordFreqMain = new TweetWordFreqMain(tweetsProcessor);
  tweetWordFreqMain.handleCommand(args);
}
}
