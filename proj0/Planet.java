	import java.lang.Math;

	/**Simulating a system of humen on the earth. */
	public class Planet {
		/* means x&y of position and velocity. */
		public double xxPos;
		public double yyPos;
		public double xxVel;
		public double yyVel;
		public double mass;
		public String imgFileName;
		public final double Grav = 6.67e-11;

		/** The normal constructor that needs some parameters. */
		public Planet(double xP, double yP, double xV,
					double yV, double m, String img) {
						xxPos = xP;
						yyPos = yP;
						xxVel = xV;
						yyVel = yV;
						mass = m;
						imgFileName = img;
					}

		/** copies a instance of Planet. */
		public Planet(Planet b){
			xxPos = b.xxPos;
			yyPos = b.yyPos;
			xxVel = b.xxVel;
			yyVel = b.yyVel;
			mass = b.mass;
			imgFileName = b.imgFileName;
		}

		/** Calculates the squart of an integer */
		private static double square(double x) {
			return x * x;
		}

		/** Return the max digit of two digits. */
		private static double max(double x, double y) {
			if (x > y) {
				return x;
			} else {
				return y;
			}
		}

		private static double min(double x, double y) {
			if (x < y) {
				return x;
			} else {
				return y;
			}
 		}

		/** Calculates the distance of two Planet */
		public double calcDistance(Planet b) {
			double Distance;
			double xxDistance;
			double yyDistance;
			xxDistance = square(this.xxPos - b.xxPos);
			yyDistance = square(this.yyPos - b.yyPos);
			Distance = xxDistance + yyDistance;
			return Math.sqrt(Distance);
		}

		/** Calculates the force in a double form. */
		public double calcForceExertedBy(Planet b) {
			double Force;
			double Distance = calcDistance(b);
			Force = (Grav * this.mass * b.mass) / square(Distance);
			return Force;
		}

		public double calcForceExertedByX(Planet b) {
			double dx;
			double xxForce;
			double Distance = calcDistance(b);
			dx = b.xxPos - this.xxPos;
			xxForce = calcForceExertedBy(b) * (dx / Distance);
			return xxForce;
		}

		public double calcForceExertedByY(Planet b) {
			double dy;
			double yyForce;
			double Distance = calcDistance(b);
			dy = b.yyPos - this.yyPos;
			yyForce = calcForceExertedBy(b) * (dy / Distance);
			return yyForce;
		}

		public double calcNetForceExertedByX(Planet[] bs) {
			double xxForce = 0;
			for (Planet b : bs) {
				if (b.equals(this)){
					continue;
				} else {
					xxForce += this.calcForceExertedByX(b);
				}
			}
			return xxForce;
		}

		public double calcNetForceExertedByY(Planet[] bs) {
			double yyForce = 0;
			for (Planet b : bs) {
				if (b.equals(this)){
					continue;
				} else {
					yyForce += this.calcForceExertedByY(b);
				}
			}
			return yyForce;
		}

		public void update(double dt, double fX, double fY) {
			double aX = fX / mass;
			double aY = fY / mass;
			xxVel = xxVel + dt * aX;
			yyVel = yyVel + dt * aY;
			xxPos = xxPos + dt * xxVel;
			yyPos = yyPos + dt * yyVel;
		}

		/** Draw a picture on a canva using the cordinates. */
		public void draw() {
			StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
		}
	}
