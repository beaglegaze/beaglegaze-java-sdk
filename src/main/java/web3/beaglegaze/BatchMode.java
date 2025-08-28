package web3.beaglegaze;

public enum BatchMode {
    
    OFF {
        public boolean hit() {
            return true;
        }
    },
    RANDOM {
        public boolean hit() {
            return Math.random() < 0.1;
        }
    };

    public abstract boolean hit();
}
