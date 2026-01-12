package systems.battle;
import java.util.Random;

//tell me if you guys want to add or not add things because im not really sure what should/shouldnt be here
/**
 * The damage calculator
 */
public final class Damage{
    private static final Random RNG = new Random();
    private Damage() {}

    /**
     * Compute the damage from all factors
     * @param atk the base attack damage
     * @param def the defense of target
     * @param crit the crit rate
     * @param critDmg the crit damage
     * @return the damage
     */
    public static int compute(int atk, int def, double crit, double critDmg) {
        final double def_Factor = 0.5;           //TBD value idk what to put the number at rn, so it will be at 0.5
        int base = (int)Math.max(1, Math.round(atk - def * def_Factor));

        //basically gives the damage a range 
        double variance = 0.85 + RNG.nextDouble() * 0.15;  
        double dmg = base * variance;
        //checks if the attack is a crit or not
        boolean isCrit = RNG.nextDouble() <clamp01(crit);
        if (isCrit) {
            dmg *= (1.0 + Math.max(0.0, critDmg));
        }
        int out = (int)Math.floor(dmg);
        return Math.max(0, out);
    }

    /**
     * prevents out of range inputs
     * @param v 
     * @return v
     */
    private static double clamp01(double v) {
        if (v<0) {
            return 0;
        }
        if (v>1) {
            return 1;
        }
        return v;
    }
}