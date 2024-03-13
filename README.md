# Hexagon Game Of Life

**Tasks**

- make custom configurations by mouse clicking (1 pt.)
- make configuration savable (1 pt.)
- create database of interesting configurations (1.5 pts.)
- Color the cell based on its age (1 pt.)
- Make Hexagonal automaton (game of life with cells of hexagon shape) (2 pts.)

**RULES**
- Each cell with one or no neighbour dies from isolation
- Each cell with three or more neighbours dies from overpopulation
- Only the cells with 2 neighbours survives
- Each cell with two neighbour revives

**Launching**
Just pull this repo to your favorite java IDE. Run `pom.xml` if IDE won't launch it automatically. `pom.xml` will 
download all necessary dependencies to the project thanks to maven.

All the cells are colored according to their age. The more older the cell is, the more blue the cell looks like.

**App controlling**
- After app launching an empty map of hexagons is displayed
- user can config the map by themselves by clicking on the hexagon cell. If cell is dead (cell is white), it will be colored to the black color (cell will revive).
  If cell is alive (black or another color than white), cell will be colored to white (cell will be killed)
- on the topleft of the app layout, there is situated one button, which on mouse click or on mouse hover will expand and show `Save configuration` and `Load configuration`
- `Save configuration` button will show you a dialog, which requests you to type a file name, where the configuration will be stored
- `Load configuation` button will show dialog of your filesystem with initial directory id `database/` folder, where all the saved configurations are saved and just choose the file you want to load

**Game Of Life process control**
- Process of the game can be paused, resumed or each its iteration can be traversed step-by-step as debugger
- Just remember the step-by-step mode is only available, when the process is paused
- **PAUSE** - process can be stopped by pressing `SPACE` key
- **START** and **RESUME** - process can be resumed by pressing the `ENTER` key
- **STEP-BY-STEP MODE** - you can enter this mode only in the situation, when the process is paused. If so, you can traverse process step-by-step by pressing right arrow key

**REFERENCES**
Hexagon map generation implementation was hugely inspired by this link:

```https://www.redblobgames.com/grids/hexagons/```

Rules of hexagonal game of life were taken from:

```https://arunarjunakani.github.io/HexagonalGameOfLife/```
