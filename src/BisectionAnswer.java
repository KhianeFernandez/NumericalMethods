
public class BisectionAnswer {

    int n;
    double x0,x1,x2,fx2,ea;

    public BisectionAnswer(int n, double x0, double x1, double x2, double fx2, double ea) {
        this.n = n;
        this.x0 = x0;
        this.x1 = x1;
        this.x2 = x2;
        this.fx2 = fx2;
        this.ea = ea;
    }

    public int getN() {
        return n;
    }

    public double getX0() {
        return x0;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getFx2() {
        return fx2;
    }

    public double getEa() {
        return ea;
    }
}
