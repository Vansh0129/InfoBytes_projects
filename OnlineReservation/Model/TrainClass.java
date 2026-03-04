package OnlineReservation.Model;

    public enum TrainClass {
        FIRST_AC("1A"),
        AC_2_TIER("2A"),
        AC_3_TIER("3A"),
        AC_3_TIER_ECONOMY("3E"),
        SLEEPER_CLASS("SL"),
        EXECUTIVE_CHAIR_CAR("EC"),
        AC_CHAIR_CAR("CC"),
        FIRST_CLASS("FC"),
        SECOND_SITTING("2S"),
        ANUBHUTI_CLASS("EA"),
        VISTADOME_AC("EV");

        private final String code;

        TrainClass(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
        public static TrainClass fromCode(String inputCode) {
            for (TrainClass tc : TrainClass.values()) {
                if (tc.code.equalsIgnoreCase(inputCode)) {
                    return tc;
                }
            }
            return null; // Or throw an error if the code is invalid
        }
    }

