package com.etf.om.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OmConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ErrorCodes {
        public static final String OFFER_NOT_FOUND = "offerNotFound";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class SuccessCodes {
        public static final String OFFER_UPDATED = "offerUpdated";
    }
}
