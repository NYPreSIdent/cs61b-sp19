public class NBody {
    /** Return the Radius of a planet. */
    public static double readRadius(String FileName) {
        In in = new In(FileName);
        int Number = in.readInt();
        double Radius = in.readDouble();
        return Radius;
    }

    /** Keep looking until the file is empty. */
    public static Planet[] readPlanets(String FileName) {
        int i = 0;
        In in = new In(FileName);
        In inall = new In(FileName);
        String[] line = inall.readAllLines();
        int leng = line.length - 2;
        Planet[] planets = new Planet[leng];
        int Number = in.readInt();
        double radius = in.readDouble();
        while(!in.isEmpty()) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String planet = in.readString();
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, planet);
            i += 1;
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        int length = planets.length;
        /*Draw the background. */
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg", 2 * radius, 2 * radius);
        /* Draws five cool planets. */
        for (Planet b : planets) {
            b.draw();
        }
        StdDraw.enableDoubleBuffering();
        for (double t = 0; t <= T; t += dt) {
            double[] xForce = new double[length];
            double[] yForce = new double[length];
            for (int j = 0; j < length; j += 1) {
                xForce[j] = planets[j].calcNetForceExertedByX(planets);
                yForce[j] = planets[j].calcNetForceExertedByY(planets);
            }
            for (int n = 0; n < length; n += 1) {
                planets[n].update(dt, xForce[n], yForce[n]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg", 2 * radius, 2 * radius);
            /* Draws five cool planets. */
            for (Planet b : planets) {
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
