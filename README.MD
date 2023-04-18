# mCommands

An advanced general purpose command dispatching framework
designed using OOP concepts and Java 17.

This library utilizes [the Adventure library](https://github.com/KyoriPowered/adventure) for
messages and text styles!

## Installation

This library is hosted by Maven central so you don't need to add another repository!

This is how you can add mCommands to your gradle project:

```gradle 
repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.mqzn:mCommands-annotations:1.0.1'
}
```

## Why use mCommands when there are a LOT of other popular options?
### Built with Java 17
This project aims to support such newer java versions for a better code design and overall

provide better performance, maintainability, and to have better options to allow cleaner code.

### Development Time Friendly
Unlike a lot of others, your command structure does NOT end up with 4 different annotations for Usages, Commands, Permissions and Descriptions, it is one for usage, and one for name + aliases, permission and description combined.

You can also apply your OWN [custom senders](https://github.com/Mqzn/mCommands/wiki/Annotated-Commands#custom-command-senders) without a hassle!

## Q&A

Wiki for mCommands is available [here](https://github.com/Mqzn/mCommands/wiki)
