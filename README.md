# Interface Game

A simple turn-based, console strategy game built in Java.

## Overview

This project simulates a two-player battle on an **8x8 board**.  
Each player controls six units with different abilities:

- **2 Bombers**
- **2 Healers**
- **2 Hybrids**

Units are placed randomly in each player’s starting rows at the beginning of the match.

## Gameplay Summary

- Players take turns selecting one of their living units by tag (for example, `P1-B1`).
- On each turn, the selected unit can perform one valid action:
  - `move`
  - `bomb`
  - `heal`
- The board is printed after each turn with current health values.
- A unit is removed from the board when its health reaches `0` or below.

## Unit Abilities

### Bomber
- **Move:** up to 2 tiles in any direction (within bounds, to empty cells)
- **Bomb:** deals **40 damage** in a 3x3 area centered on target cell

### Healer
- **Move:** one row forward (`x + 1`) into an empty cell
- **Heal:** restores **20 health** to all units in a 3x3 area around itself (max 100)

### Hybrid
- **Move:** diagonally to an empty cell
- **Bomb:** deals **20 damage** in a 3x3 area centered on target cell
- **Heal:** self-heals **10 health** (max 100)

## Win Condition

The game ends when a player has no living **Bombers** or **Hybrids** left.

## Project Structure

- `/home/runner/work/interface_game/interface_game/Main.java` – entry point
- `/home/runner/work/interface_game/interface_game/StrategyGame.java` – game loop, board setup, turn handling, win checks
- `/home/runner/work/interface_game/interface_game/Character.java` – shared unit fields and movement validation
- `/home/runner/work/interface_game/interface_game/Bomber.java` – bomber behavior
- `/home/runner/work/interface_game/interface_game/Healer.java` – healer behavior
- `/home/runner/work/interface_game/interface_game/Hybrid.java` – hybrid behavior
- `/home/runner/work/interface_game/interface_game/Moving.java`, `/home/runner/work/interface_game/interface_game/Bombing.java`, `/home/runner/work/interface_game/interface_game/Healing.java` – ability interfaces

## How to Run

From `/home/runner/work/interface_game/interface_game`:

```bash
javac *.java
java Main
```

## Notes

- Input positions are entered as `row col` (0-based indices from `0` to `7`).
- Invalid actions or input are rejected and re-prompted.
