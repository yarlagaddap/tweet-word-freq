package com.sweetgum.tweets;

public class WordFreq {
private String word;
private long freq;

public WordFreq(String word) {
  this.word = word;
}

public WordFreq(String word, long freq) {
  this(word);
  this.freq = freq;
}

public void incFreq() {
  freq++;
}

public void decFreq() {
  freq--;
}

public long getFreq() {
  return freq;
}

public String getWord() {
  return word;
}

public String toString() {
  return String.format("%s -- %d", word, freq);
}
}
