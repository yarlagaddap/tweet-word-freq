package com.sweetgum.tweets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweetUtils {
private static Pattern REMOVE_HASHTAGS = Pattern.compile("#\\w+");
private static Pattern REMOVE_TWEETERS = Pattern.compile("@\\w+");
private static Pattern REMOVE_LINKS_HTTP = Pattern.compile("(http|https)://\\S+");
private static Pattern REMOVE_LINKS_WWW = Pattern.compile("www\\.\\S+");
private static Pattern REMOVE_LINKS_COM = Pattern.compile("\\S+\\.com");
private static Pattern REMOVE_NON_ALPHA_NUMERIC = Pattern.compile("[^a-zA-Z0-9\\s]");

public static String removeHashTags(String tweet) {
  Matcher matcher = REMOVE_HASHTAGS.matcher(tweet);
  return matcher.replaceAll("");
}

public static String removeTweeters(String tweet) {
  Matcher matcher = REMOVE_TWEETERS.matcher(tweet);
  return matcher.replaceAll("");
}

public static String removeLinks(String tweet) {
  Matcher matcher = REMOVE_LINKS_HTTP.matcher(tweet);
  matcher = REMOVE_LINKS_WWW.matcher(matcher.replaceAll(""));
  matcher = REMOVE_LINKS_COM.matcher(matcher.replaceAll(""));
  return matcher.replaceAll("");
}

public static String removeNonAlphaNumeric(String tweet) {
  Matcher matcher = REMOVE_NON_ALPHA_NUMERIC.matcher(tweet);
  return matcher.replaceAll("");
}

public static String cleanUp(String tweet) {
  if (tweet == null) {
    return "";
  }
  return removeNonAlphaNumeric(removeLinks(removeTweeters(removeHashTags(tweet)))).toLowerCase().trim();
}

}
