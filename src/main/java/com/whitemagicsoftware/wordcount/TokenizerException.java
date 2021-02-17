/* Copyright 2020-2021 White Magic Software, Ltd. -- All rights reserved. */
package com.whitemagicsoftware.wordcount;

/**
 * Responsible for indicating there was a problem during tokenization.
 */
public class TokenizerException extends RuntimeException {
  public TokenizerException( final Throwable cause ) {
    super( cause );
  }
}
