# TheSea

_This is a java applet that simulates the schooling behavior of fish. It is based on Daniel Schiffman's processing implementation of the classic boids algorithm (Craig Reynolds, 1986), ported to Java. There are two branches. The first simulates schooling as described above. The second implements a genetic algorithm to evolve the schooling behavior based on avoidance of a predator. See a description of both below._

## master-clean

This branch holds the standard schooling simulation.

A number of changes have been implemented to expand the basic flocking functionality. The Fish class has been extended into two child classes, Guppy and Predator. The guppies will school together based on three basic steering forces of alignment, cohesion, and separation. They also try to avoid any and all predator objects. The predator attempts to catch guppies.

There are a few variables that can be altered to influence the behavior of the program:

* popSize: initial population of the school
* pullDist: maximum distance (in pixels) that individual fish will be aware of each other
* desiredSep: distance (in pixels) that fish want to have between each other
* scareDist: distance (in pixels) that fish will notice a predator and steer away
* sniffDist: distance (in pixels) that a predator will notice a fish and steer towards
* aC: alignment coefficient, the degree to which fish want to align their vectors
* cC: cohesion coefficient, the degree to which fish want to move toward the center of mass
* sC: separation coefficient, the degree to which fish maintain their personal space
* fC: flight coefficient, the degree to which fish will want flee a predator

## master

This branch is the genetic algorithm implementation. The School class has been expanded into the Population class, and a DNA class has been addeed. The variables described in master-clean have been implemented as the genes that make up a fish's DNA. They begin as random, and the fish that perform better in each generation have a higher chance of passing their genes on to the next generation. 

More to come soon...
