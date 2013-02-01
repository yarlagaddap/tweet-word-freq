package com.sweetgum.tweets;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

public class Constants {

public static final String HELP_FORMAT_LINE =
      "##########################################################";

public static final String USAGE = "-help\n-tweet_word_freq <-u user_id | -s screen_name>";

public static final String HEADER =
      "Computes user's tweet words frequency.";

public static final String ACTION_HELP = "help";

public static final String ACTION_HELP_DESC = "help";

public static final String ACTION_TWEET_WORD_FREQ = "tweetwordfreq";

public static final String ACTION_TWEET_WORD_FREQ_DESC = "Computes user's tweet words frequency. Specify either user ID or screen name.";

//Params
public static final String PARAM_U = "u";

public static final String PARAM_U_DESC = "User Name";

//Options
public static final Option OPT_U = OptionBuilder.withArgName(PARAM_U)
      .hasArg().withDescription(PARAM_U_DESC).create(PARAM_U);

public static final Option OPT_ACTION_HELP = OptionBuilder.withArgName(ACTION_HELP)
      .withDescription(ACTION_HELP_DESC).create(ACTION_HELP);

public static final Option OPT_ACTION_TWEET_WORD_FREQ = OptionBuilder.withArgName(ACTION_TWEET_WORD_FREQ)
      .withDescription(ACTION_TWEET_WORD_FREQ_DESC).create(ACTION_TWEET_WORD_FREQ);
}
