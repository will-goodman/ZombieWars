# Zombie Wars
Worms-like turn-based game based around zombies. Written in Java.

Created as a team university project.

![Visual Representation](https://github.com/minhalkhan/ZombieWars/blob/master/Screenshot%202019-10-05%20at%2015.52.01.png)

## To run

- Import as a gradle project into you chosen IDE (tested on IntelliJ)
- Run com.elite.desktop.DesktopLauncher in the desktop module

## Gameplay

The game plays similarly to the old Worms games, where two teams take turns to fire weapons at the other.
Weapons and health can be replenished from pickups on the map. The game continues until all zombies on one team are killed.

Each player's turn lasts either 60 seconds or until they run out of energy, whichever is shorter.
A player's energy is replenished at the start of each turn to 100.
Firing weapons (explained below) and walking cost energy. Walking costs 0.05 energy.

There are two game modes:
- Single player or PvE
- Multi-player or PvP

In both game modes each player has three zombies and main gameplay mechanics remain the same.

### Weapons

#### Bone
Carried by every zombie with unlimited ammunition. <br>
A bone is thrown in a straight line until it hits and object.
A bone does 30 damage and costs 20 energy.

![Bone](core/src/main/resources/bullet.png)

#### Grenade
Each zombie starts with two grenades, although more can be picked up.<br>
A grenade can be thrown and will bounce until it comes to a stop and explode, damaging zombies within a certain radius.
A grenade does 35 damage and costs 50 energy.

![Grenade](core/src/main/resources/grenade.png)

### Pickups

#### Ammo Crate
These contain two grenades and look like the image below:<br>

![Ammo Crate](core/src/main/resources/ammoCrate.png)

#### Health Crate
These contain up to 25 health. Each zombie's health is capped at 100.

![Health Crate](core/src/main/resources/healthCrate.png)

