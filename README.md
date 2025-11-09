# jiho

`jiho` (時報) is a simple hourly chime application (like a cuckoo clock) that plays a sound a number of times corresponding to the hour.

## Features

- Plays a sound every hour, on the hour.
- Chimes 1-12 times based on the 12-hour clock (e.g., 3 times at 3:00, 12 times at 12:00).
- Includes a default built-in sound (`default_sound.wav`).
- Allows specifying a custom sound file and playback volume via a configuration file (`config.json`).
- A CLI application built with picocli.

## Requirements

- Java 21

## Build Instructions

This project supports a development environment using Nix Flakes.

**With Nix (Recommended)**

```sh
# Enter the development environment
nix develop

# Build the FAT JAR (Shadow JAR)
gradle shadowJar
```

**Without Nix**

(Requires a local installation of JDK 21 and Gradle.)

```sh
gradle shadowJar
```

After a successful build, the executable JAR will be located at `./build/libs/` (e.g., `jiho-0.1.0.jar`).

## Usage

Run the application using the built JAR file:

```sh
java -jar ./build/libs/jiho-0.1.0.jar
```

### Command Line Options

- `--config-file <PATH>`: Specify the path to the configuration file (`config.json`).
- `-h`, `--help`: Show the help message.
- `-v`, `--version`: Show version information.

## Configuration

Jiho is configured via a `config.json` file.

If the `--config-file` option is not provided, the application will look for the file in the standard OS configuration directory (e.g., `~/.config/jiho/config.json`) based on XDG Base Directory standards.

On the first run, if the config file does not exist, an empty file (`{}`) will be created automatically.

### Configuration Example

`config.json`

```json
{
  "soundSource": "/path/to/your/custom_sound.wav",
  "volume": 0.8
}
```

### Configuration Fields

- `soundSource`: (Optional) The absolute path to a custom sound file (e.g., WAV) to play.
  - If this is omitted, or the file is not found, the application will fall back to using the built-in `default_sound.wav`.
- `volume`: (Optional) The playback volume, specified as a value from `0.0` (silent) to `1.0` (maximum).
  - If this is omitted, the system's default volume will be used.

## License

This project is licensed under the MIT License.
