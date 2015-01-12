README

This application simulates a combat according to Civilization: The Board Game (core set) rules, and
writes output to CSV files. Combat is between players A and B, where A is the attacker 
(ie. B plays his unit first).

1. PREREQUISITES:
 - Java Runtime Environment 7

2. DOWNLOADS

Pre-built binaries: https://www.dropbox.com/sh/76ksihrfyhvwn20/2uI3GjBGGx 

3. RUNNING

To run the application (use civsim.bat on Windows):

``$ civsim [options]``

Add `--help` to see available command line switches.

Run example: 
``$ civsim -u i1c1a1,i2c2a2 -f Trumped,TrumpMiss  -a Metalworking -s 111222.csv -o 111222o.csv -c 111222c.csv``

This will simulate a combat between Player A having Infantry(1),Cavalry(1),Artillery(1) + Metalworking ability against Player B having Infantry(2),Cavalry(2),Artillery(2) and no abilities. The combat summary is written into 111222.csv, individual outcomes are written into 111222o.csv and full combat history for each combat is written into 111222c.csv.

Units:
 - Each unit is defined as a letter prefix from [icar] followed by it's strength. Strength is arbitary but must be a single digit. Multiple units are defined as a single word of unit definitions, such as `i1c2a3`. The comma character separates units of player A and B, for example `a1,i2c2`.

Letter codes:
   - i: infantry
   - c: cavalry
   - a: artillery
   - r: aircraft

Thus, `i2` means Infantry(2), `a1` means Artillery(1). 

Filters:
 - Trumped: Filters combats where a new unit was played in a way that it is trumped and removed without causing a wound.
 - TrumpMiss: Filters combats where a unit is played on a new Front, but could have trumped and defeated an enemy unit without getting a wound.

Abilities:
 - Metalworking: The player can play a unit with Metalworking during combat.

Example: `-a Metalworking,Metalworking`

With this parameter, both player A and B will have this ability.

4. BUILDING FROM SOURCE

To build from source, you need JDK 7, and Apache Ant, and then run:

``$ ant clean jar``

Binary standalone distribution is built in the dist/ folder.

