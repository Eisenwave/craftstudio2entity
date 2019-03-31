# craftstudio2entity
Converts CraftStudio .csjsmodel files into Minecraft Bedrock Edition entity geometry files.

## Installation
This software has been developed using [Java 8](https://www.java.com/en/download/).
You will need to download a JRE (Java Runtime Environment) 1.8+ in order to run it.

To compile it from source, you require [Apache Maven](https://maven.apache.org/).
Simply enter the repository folder and run `<maven command> clean package`.
This is usually `mvn clean package`. The executable JAR can then be found in the `target` folder.

## Usage

Assuming a Java executable is in your PATH, you can run the jar using `java -jar <jar path>`.

The full usage is `java -jar <jar path> <csjsmodel path> <entity path> [flags]`.

### Flags

`r` - Replace existing files.


