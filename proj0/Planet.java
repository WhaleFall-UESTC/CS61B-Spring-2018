import java.lang.Math;

public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass  = m;
        this.imgFileName = img;
    }

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass  = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = this.xxPos - p.xxPos;
        double dy = this.yyPos - p.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p) {
        if (this.equals(p)) {
            return 0;
        }
        double dx = this.xxPos - p.xxPos;
        double dy = this.yyPos - p.yyPos;
        return (G * this.mass * p.mass) / (dx * dx + dy * dy); 
    }

    public double calcForceExertedByX(Planet p) {
        double dx = p.xxPos - this.xxPos;
        double r  = calcDistance(p);
        double f  = calcForceExertedBy(p);
        return f * dx / r;
    }

    public double calcForceExertedByY(Planet p) {
        double dy = p.yyPos - this.yyPos;
        double r  = calcDistance(p);
        double f  = calcForceExertedBy(p);
        return f * dy / r;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        int len = planets.length;
        double fi = 0, res = 0;

        for (int i = 0; i < len; i++) {
            if (this.equals(planets[i])) continue;
            fi = calcForceExertedByX(planets[i]);
            res += fi;
        }

        return res;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        int len = planets.length;
        double fi = 0, res = 0;

        for (int i = 0; i < len; i++) {
            if (this.equals(planets[i])) continue;
            fi = calcForceExertedByY(planets[i]);
            res += fi;
        }

        return res;
    }

    public void update(double dt, double Fx, double Fy) {
        double xxAcc = Fx / this.mass;
        double yyAcc = Fy / this.mass;
        this.xxVel += xxAcc * dt;
        this.yyVel += yyAcc * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, 
            "images/" + this.imgFileName);
    }
}
