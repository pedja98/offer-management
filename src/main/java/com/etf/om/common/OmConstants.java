package com.etf.om.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OmConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ErrorCodes {
        public static final String ACTION_NOT_ALLOWED = "actionNotAllowed";
        public static final String CAN_NOT_DELETE_PLANNED_TP = "canNotDeletePlannedTp";
        public static final String INVALID_REQUEST = "invalidRequest";
        public static final String OFFER_STATUS_TRANSITION_NOT_POSSIBLE = "offerStatusTransitionNotPossible";
        public static final String OFFER_NOT_FOUND = "offerNotFound";
        public static final String ADDON_NOT_FOUND = "addonNotFound";
        public static final String DISCOUNT_NOT_FOUND = "discountNotFound";
        public static final String ADDON_DUPLICATE = "addonDuplicate";
        public static final String TARIFF_PLAN_NOT_FOUND = "tariffPlanNotFound";
        public static final String DISCOUNT_MAX = "discountMax";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class SuccessCodes {
        public static final String OFFER_UPDATED = "offerUpdated";
        public static  final String OFFER_CREATED = "offerCreated";
        public static  final String ADDON_CREATED = "addonCreated";
        public static final String ADD_TARIFF_PLANS = "addTariffPlans";
        public static final String ADDONS_DELETED = "addonsDeleted";
        public static final String TARIFF_PLANS_UPDATED = "tariffPlansUpdated";
        public static final String TARIFF_PLAN_UPDATED = "tariffPlanUpdated";
        public static final String TARIFF_PLANS_DELETED = "tariffPlansDeleted";
        public static final String DISCOUNT_UPDATED = "discountUpdated";
    }
}
