public class Constants {

        private Constants() { }

        public static final char EMPTY = 'e';
        public static final char YELLOW = 'y';
        public static final char RED = 'r';

        public static final int PVP = 0;
        public static final int PVRandom = 1;
        public static final int PVMinimax = 2;
        public static final int PVPrunedMinimax = 3;
        public static final int RandomVMinimax = 4;
        public static final int RandomVPrunedMinimax = 5;
        public static final int MinimaxVPrunedMinimax = 6;

        public static final int HumanPlayer=0;
        public static final int RandomPlayer = 1;
        public static final int MinimaxPlayer = 2;
        public static final int PrunedMinimaxPlayer = 3;

        public static final int ERROR = -1;
        public static final char WIN = 'w';

        public static final int TIME_CONSTANT = 1000000000;

        public static final int NEG_INFINITY = -100000000;
        public static final int POS_INFINITY = 100000000;

}
