# Home Assignment: Scratch Game #

## Requirements ##

- JDK >= 1.8
- Maven or Gradle
- feel free to choose any libraries for serialize/deserialize JSON and testing
- not recommended to add any additional libraries/frameworks, like spring or other high level frameworks

## Description ##

The Scratch Game generates a matrix (e.g., 3x3) of symbols based on probabilities assigned to each individual cell. Users place a bet (referred to as the "betting amount") to play the game. The game determines whether the user wins or loses based on predefined winning combinations. The outcome of the game is evaluated according to the symbols in the generated matrix and the winning combinations.

For a full description of the game, please refer to the detailed documentation located in `docs/problem_description.md`.
## Building the Project

To build the Scratch Game project and create an executable JAR file, follow these steps:

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   
2. **Build the Project**
   ```bash
   mvn clean package
   
3. **Running the Project**

   To run the Scratch Game, use the following command:

   ```bash
   java -jar target/scratch_game.jar --config config.json --betting-amount 100

#### Command-Line Arguments
- --config <path-to-config-file>: Path to the configuration file (config.json).
- --betting-amount <amount>: The amount to bet in the game.