/* Copyright 2020-2021 White Magic Software, Ltd. -- All rights reserved. */
package com.whitemagicsoftware.wordcount;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap.SimpleEntry;
import java.util.Locale;
import java.util.Map;

import static java.util.Locale.*;
import static java.util.Map.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Responsible for testing tokenization behaviour when counting words in a
 * document.
 */
class TokenizerTest {
  // Tokenization excludes stop-words by default.
  private static final Map<Locale, SimpleEntry<String, Integer>> PHRASES = of(
    ENGLISH,
    // your theory doesn't agree experiment wrong
    entry( "If your theory doesn't agree with experiment, it's wrong.", 6 ),
    // 世界 就是 一个 疯子 的 囚笼
    CHINESE,
    entry( "世界就是一个疯子的囚笼", 6 ),
    // 材料 麩 日本 主 菓子
    JAPANESE,
    entry( "麩菓子は、麩を主材料とした日本の菓子。", 7 ),
    // 있 사과 나 먹
    KOREAN,
    entry( "나는사과를먹고있다", 4 ),
    GERMAN,
    entry( "Ca. 90min mit newmotion geladen, weil ich mit " +
             "Maingau/EinfachStromLaden keine Verbindung über die App bekam. " +
             "Säule hat keinen RFID-Leser usw. " +
             "2. Buchse seit 2 Tagen mit Kommunalfahrzeug/EWV blockiert.", 31 )
  );

  @Test
  void test_Tokenize_MultilingualPhrases_CountCorrect() {
    for( final var language : PHRASES.keySet() ) {
      final var tokenizer = TokenizerFactory.create( language );
      final var entries = PHRASES.get( language );
      final var phrase = entries.getKey();
      final var expectedCount = entries.getValue();
      final var words = tokenizer.tokenize( phrase );

      int actualCount = 0;

      for( final var word : words.keySet() ) {
        actualCount += words.get( word )[ 0 ];
      }

      assertEquals( expectedCount, actualCount );
    }
  }

  private static <K, V> SimpleEntry<K, V> entry( final K k, final V v ) {
    return new SimpleEntry<>( k, v );
  }
}
