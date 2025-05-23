
public class GaussSeidelAnswer {
    private final int iteration;
    private final double[] x;
    private final double[] ea;

    public GaussSeidelAnswer(int iteration, double[] x, double[] ea) {
        this.iteration = iteration;
        this.x = x;
        this.ea = ea;
    }

    public int getIteration() {
        return iteration;
    }

    public double[] getX() {
        return x;
    }

    public double[] getEa() {
        return ea;
    }
}
