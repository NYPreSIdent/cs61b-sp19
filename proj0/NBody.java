public class NBody {
    /** Return the Radius of a planet. */
    public static double readRadius(String FileName) {
        In in = new In(FileName);
        int Number = in.readInt();
        double Radius = in.readDouble();
        return Radius;
    }

    /** Keep looking until the file is empty. */
    public static Body[] readplanets(String FileName) {
        Body[] planets = new Body[5];
        In in = new In(FileName);
        int Number = in.readInt();
        double radius = in.readDouble();
        for (int i = 0; i < 5; i += 1) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String planet = in.readString();
            planets[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, planet);
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body[] planets = readplanets(filename);
        /*Draw the background. */
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg", 2 * radius, 2 * radius);
        /* Draws five cool planets. */
        for (Body b : planets) {
            b.draw();
        }
        StdDraw.enableDoubleBuffering();
        for (double t = 0; t <= T; t += dt) {
            double[] xForce = new double[5];
            double[] yForce = new double[5];
            for (int i = 0; i < 5; i += 1) {
                xForce[i] = planets[i].calcNetForceExertedByX(planets);
                yForce[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int j = 0; j < 5; j += 1) {
                planets[j].update(dt, xForce[j], yForce[j]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg", 2 * radius, 2 * radius);
            /* Draws five cool planets. */
            for (Body b : planets) {
                b.draw();
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
