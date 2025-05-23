public class SecantAnswer {
    private int n;
    private double x0;
    private double x1;
    private double fx0;
    private double fx1;
    private double x2;
    private double ea;

    public SecantAnswer(int n, double x0, double x1, double fx0, double fx1, double x2, double ea) {
        this.n = n;
        this.x0 = x0;
        this.x1 = x1;
        this.fx0 = fx0;
        this.fx1 = fx1;
        this.x2 = x2;
        this.ea = ea;
    }

    // Getters
    public int getN() { return n; }
    public double getX0() { return x0; }
    public double getX1() { return x1; }
    public double getFx0() { return fx0; }
    public double getFx1() { return fx1; }
    public double getX2() { return x2; }
    public double getEa() { return ea; }

    // Setters
    public void setN(int n) { this.n = n; }
    public void setX0(double x0) { this.x0 = x0; }
    public void setX1(double x1) { this.x1 = x1; }
    public void setFx0(double fx0) { this.fx0 = fx0; }
    public void setFx1(double fx1) { this.fx1 = fx1; }
    public void setX2(double x2) { this.x2 = x2; }
    public void setEa(double ea) { this.ea = ea; }
}