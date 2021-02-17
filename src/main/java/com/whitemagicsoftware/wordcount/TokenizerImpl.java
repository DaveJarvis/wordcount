/* Copyright 2020-2021 White Magic Software, Ltd. -- All rights reserved. */
package com.whitemagicsoftware.wordcount;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides an implementation of {@link Tokenizer} that can split a document
 * into individual terms. Each term is tallied and returned.
 */
public class TokenizerImpl implements Tokenizer {
  private final Analyzer mAnalyzer;

  TokenizerImpl( final Analyzer analyzer ) {
    assert analyzer != null;
    mAnalyzer = analyzer;
  }

  @Override
  public Map<String, int[]> tokenize( final String document )
    throws TokenizerException {
    assert document != null;

    final var result = new HashMap<String, int[]>();

    try( final var stream = mAnalyzer.tokenStream( null, document ) ) {
      final var offset = stream.addAttribute( OffsetAttribute.class );

      stream.reset();
      while( stream.incrementToken() ) {
        final var began = offset.startOffset();
        final var ended = offset.endOffset();
        final var word = document.substring( began, ended );
        result.computeIfAbsent( word, ( k ) -> new int[]{0} )[ 0 ]++;
      }
      stream.end();

      return result;
    } catch( final IOException ex ) {
      throw new TokenizerException( ex );
    }
  }
}
