package au.lupine.wander.object.number;

public abstract class VariableNumber<N extends Number> {

    public final N lower, upper;

    public VariableNumber(N lower, N upper) {
        this.lower = lower;
        this.upper = upper;
    }

    /**
     * @return Generate a random number between lower and upper (inclusive)
     */
    public abstract N generate();
}
