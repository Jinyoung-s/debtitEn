package so.debateit.common.model;

import java.util.EnumSet;

public class BoardSpecs {
    public enum  BoardUserType {
        OWNER("OWNER"),
        PART("PART");

        private final String value;

        BoardUserType(String value) {
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }

    public enum NotiType {
        NOTI("NOTI");

        private final String value;

        NotiType(String value) {
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }

    public enum DebateStaus {
        CLOSE("CLOSE");

        private final String value;

        DebateStaus(String value) {
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }

    public enum RegistType {
        SOCIAL("SOCIAL");

        private final String value;

        RegistType(String value) {
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }

    public enum BoardDvsn {
        debate("debate"),
        free("free"),
        propose("propose"),
        hotdeal("hotdeal");

        private final String value;

        BoardDvsn(String value) {
            this.value = value;
        }

        public String getValue(){
            return value;
        }

        public static EnumSet<BoardDvsn> getDebate() {
            return EnumSet.of(debate, hotdeal);
        }

        public static EnumSet<BoardDvsn> getFree() {
            return EnumSet.of(free, propose);
        }
    }


    public enum BoardKind {
        D("D"), //debate
        N("N"); //nodebate

        private final String value;

        BoardKind(String value) {
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }

}
