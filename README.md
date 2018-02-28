# TheSea

This is a java applet that simulates the schooling behavior of fish in the sea. It is based on Daniel Schiffman's implementation of the classic boids Algorithm (Craig Reynolds, 1986), ported to Java.

There are a few variables that can be altered to influence the behavior of the school:

-popSize: initial population of the school\n
-pullDist: maximum distance (in pixels) that individual fish will be aware of each other\n
-desiredSep: distance (in pixels) that fish want to have between each other
-scareDist: distance (in pixels) that fish will notice a predator and steer away
-aC: alignment coefficient, the degree to which fish want to align their vectors
-cC: cohesion coefficient, the degree to which fish want to move toward the center of mass
-sC: separation coefficient, the degree to which fish maintain their personal space
-fC: flight coefficient, the degree to which fish will want flee a predator