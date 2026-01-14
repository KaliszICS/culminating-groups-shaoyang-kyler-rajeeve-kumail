package systems.gacha;

/**
 * Represents the pity system in the gacha system in game.
 * This will help players to obtain rare items and characters easier.
 *
 * @author Shaoyang Chen
 * @version 1.0.3
 */
public class PitySystem {
    private int fiveStarPity;
    private int fourStarPity;
    private boolean guaranteeFlag;

    /**
     * Instantiates a new Pity system (this system does not need an arg)
     * in default, all pities are set to 0
     */
    public PitySystem() {
        fiveStarPity = 0;
        fourStarPity = 0;
        guaranteeFlag = false;
    }

    /**
     * Increase pity count in each counter
     */
    public void incrementPity() {
        fiveStarPity++;
        fourStarPity++;
    }

    /**
     * Reset pity for a specific rarity
     *
     * @param rarity the rarity of pity you want to reset
     */
    public void resetPity(int rarity) {
        switch (rarity) {
            case 5:
                fiveStarPity = 0;
                break;
            case 4:
                fourStarPity = 0;
                break;
        }
    }

    /**
     * Check guarantee if it is time for player to obtain a 4/5 star item/character
     *
     * @return the representing whether conditions for guarantee is active
     */
    public boolean checkGuarantee() {
        if (fiveStarPity >= 90) {
            guaranteeFlag = true;
            return true;
        }


        if (fourStarPity >= 10) {
            return true;
        }

        return false;
    }

    /**
     * Calculate soft pity double.
     *
     * @return the double
     */
    public double calculateSoftPity() {
        // calculate soft pity for five stars after 75
        if (fiveStarPity < 75) {
            return 0.006;
        }

        int softPityCount = fiveStarPity - 74;
        double increasedRate = 0.006 + (softPityCount * 0.06); //add 6% each pull

        // possibility cannot exceed 100%
        return Math.min(increasedRate, 1.0);
    }

    /**
     * Gets five star pity.
     *
     * @return the five star pity
     */
    public int getFiveStarPity() { return fiveStarPity; }

    /**
     * Gets four star pity.
     *
     * @return the four star pity
     */
    public int getFourStarPity() { return fourStarPity; }

    /**
     * Gets guarantee status flag.
     *
     * @return the guarantee flag
     */
    public boolean getGuaranteeFlag() { return guaranteeFlag; }
}