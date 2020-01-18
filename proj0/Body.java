	import java.lang.Math;

	/**Simulating a system of humen on the earth. */
	public class Body {
		/* means x&y of position and velocity. */
		public double xxPos;
		public double yyPos;
		public double xxVel;
		public double yyVel;
		public double mass;
		public String imgFileName;
		public final double Grav = 6.67e-11;

		/** The normal constructor that needs some parameters. */
		public Body(double xP, double yP, double xV,
					double yV, double m, String img) {
						xxPos = xP;
						yyPos = yP;
						xxVel = xV;
						yyVel = yV;
						mass = m;
						imgFileName = img;
					}

		/** copies a instance of Body. */
		public Body(Body b){
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

		/** Calculates the distance of two planet */
		public double calcDistance(Body b) {
			double Distance;
			double xxDistance;
			double yyDistance;
			xxDistance = square(this.xxPos - b.xxPos);
			yyDistance = square(this.yyPos - b.yyPos);
			Distance = xxDistance + yyDistance;
			return Math.sqrt(Distance);
		}

		/** Calculates the force in a double form. */
		public double calcForceExertedBy(Body b) {
			double Force;
			double Distance;
			Distance = calcDistance(b);
			Force = (Grav * this.mass * b.mass) / square(Distance);
			return Force;
		}
	}
