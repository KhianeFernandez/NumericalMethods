public class SimpsonsAnswer {

    String xn;
    double x, fx, fxi;

    int mod2, rule;

    public SimpsonsAnswer(String xn, double x, double fx, int mod2, int rule, double fxi) {
        this.xn = xn;
        this.x = x;
        this.fx = fx;
        this.mod2 = mod2;
        this.rule = rule;
        this.fxi = fxi;
    }

    public String getXn(){
        return xn;
    }

    public double getX() {
        return x;
    }

    public double getFx() {
        return fx;
    }

    public int getMod2() {
        return mod2;
    }

    public int getRule() {
        return rule;
    }

    public double getFxi() {
        return fxi;
    }
}
