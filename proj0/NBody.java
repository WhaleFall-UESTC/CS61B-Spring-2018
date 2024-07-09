import java.lang.Math;

public class NBody {
    public static double readRadius(String filename) {
        In in = new In(filename);
        int num = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int num = in.readInt();
        double radius = in.readDouble(); 

        Planet[] planets = new Planet[num];
        for (int i = 0; i < num; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m  = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xP, yP, xV, yV, m, img);
        }
        return planets;
    }

    public static final double w = 2.1e-9;

    public static void main(String[] args) {
        double T  = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = readRadius(filename);
        int size = (int) Math.round(w * radius);
        System.out.println("[info] Get canvas size: " + size);

        StdDraw.setCanvasSize(size, size);
        StdDraw.setScale(-radius, radius);
        StdDraw.enableDoubleBuffering();
        Planet[] planets = readPlanets(filename);
        
        for (int t = 0; t < T; t += dt) {
            double[] Fx = new double[planets.length];
            double[] Fy = new double[planets.length];

            for (int i = 0; i < planets.length; i++) {
                Fx[i] = planets[i].calcNetForceExertedByX(planets);
                Fy[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, Fx[i], Fy[i]);
            }

            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (int i = 0; i < planets.length; i++) {
                planets[i].draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
        }


        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
}
    }
}