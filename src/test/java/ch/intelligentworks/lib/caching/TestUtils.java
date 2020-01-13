/*
 *
 * This document contains trade secret data which is the property of
 * Swisscom AG. Information contained herein may not be used,
 * copied or disclosed in whole or part except as permitted by written
 * agreement from Swisscom AG.
 *
 * Copyright (C) 2020 Swisscom AG/Switzerland
 */

package ch.intelligentworks.lib.caching;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Tests need some common utility functions for convenient use
 * Created on 16.10.2019 13:26
 *
 * @author mdogan
 * @version 1.0
 */
public class TestUtils {

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void createSecurityContext(){
    SecurityContextHolder.getContext().setAuthentication(null);
  }
}
