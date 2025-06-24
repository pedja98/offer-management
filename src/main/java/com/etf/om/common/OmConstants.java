package com.etf.om.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OmConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ErrorCodes {
        public static final String OFFER_NOT_FOUND = "offerNotFound";
        public static final String ADDON_NOT_FOUND = "addonNotFound";
        public static final String TARIFF_PLAN_NOT_FOUND = "tariffPlanNotFound";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class SuccessCodes {
        public static final String OFFER_UPDATED = "offerUpdated";
        public static  final String OFFER_CREATED = "offerCreated";
        public static  final String ADDON_CREATED = "addonCreated";
        public static final String ADD_TARIFF_PLANS = "addTariffPlans";
        public static final String ADDON_DELETED = "addonDeleted";
        public static final String TARIFF_PLANS_UPDATED = "tariffPlansUpdated";
        public static final String TARIFF_PLAN_UPDATED = "tariffPlanUpdated";
        public static final String TARIFF_PLANS_DELETED = "tariffPlansDeleted";
    }
}
