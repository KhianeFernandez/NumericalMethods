public class GaussSeidelAnswer {
    private int iteration;
    private double[] values;
    private double error;

    public GaussSeidelAnswer(int iteration, double[] values, double error) {
        this.iteration = iteration;
        this.values = values.clone();
        this.error = error;
    }

    public int getIteration() { return iteration; }
    public double[] getValues() { return values; }
    public double getError() { return error; }
}
