/* Copyright 2020-2021 White Magic Software, Ltd. -- All rights reserved. */
package com.whitemagicsoftware.wordcount;

import java.util.Map;

/**
 * Defines the expected output from counting unique word tokens in a document.
 */
public interface Tokenizer {

  /**
   * Produces a list of tokens for a given document. Calling classes are
   * responsible for sorting.
   *
   * @param document The text with tokens to tally into terms.
   * @return A count of all unique words, where the returned array has a
   * single element, which represents the total occurrences for the
   * corresponding key (document term).
   * @throws TokenizerException Could not tokenize the given document.
   */
  Map<String, int[]> tokenize( String document ) throws TokenizerException;
}
