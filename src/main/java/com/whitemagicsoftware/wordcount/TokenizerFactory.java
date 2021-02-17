/* Copyright 2020-2021 White Magic Software, Ltd. -- All rights reserved. */
package com.whitemagicsoftware.wordcount;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.util.Locale;
import java.util.Map;

import static java.util.Locale.*;
import static java.util.Map.of;

/**
 * Responsible for creating an instance of {@link Tokenizer} for a language.
 */
public class TokenizerFactory {
  private static final Map<Locale, Class<? extends Analyzer>> ANALYZERS = of(
    ENGLISH, EnglishAnalyzer.class,
    CHINESE, SmartChineseAnalyzer.class,
    JAPANESE, JapaneseAnalyzer.class,
    KOREAN, KoreanAnalyzer.class
  );

  /**
   * Clients must use the {@link #create(Locale)} method.
   */
  private TokenizerFactory() {
  }

  /**
   * Creates a new {@link Tokenizer} instance for the given language.
   *
   * @param language The locale-based language to give context for tokenizing.
   * @return The {@link Tokenizer} that can segment words for a language.
   * @throws TokenizerException Could not instantiate the third-party library
   *                            responsible for text tokenization.
   */
  public static Tokenizer create( final Locale language )
    throws TokenizerException {
    try {
      final var clazz =
        ANALYZERS.getOrDefault( language, StandardAnalyzer.class );
      return new TokenizerImpl( clazz.getDeclaredConstructor().newInstance() );
    } catch( final Exception ex ) {
      throw new TokenizerException( ex );
    }
  }
}
