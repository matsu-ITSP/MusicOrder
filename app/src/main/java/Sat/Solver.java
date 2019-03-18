package Sat;

import java.util.NoSuchElementException;

public final class Solver {

    public static String main(final String arg) {
        final Formula formula = new Formula(arg);
        try {
            while (!formula.validSolution()) {
                if (formula.getCachedClauseSizeZeroResult()) {
                    formula.backTrack();
                } else {
                    formula.forwardTrack();
                }
            }
        } catch (NoSuchElementException e) {
            // Empty Stack print No Solution & Exit
            return "Unsolvable Solution";
        }

        //System.out.println("Solvable Solution");
        return formula.printSolution();
    }
}
