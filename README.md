# QuikMod

QuikMod is the fastest way to get started with a ForgeGradle Minecraft Mod. All that is required is a vaild Gradle install, or a Gradle wrapper.

## Features

- Boilerplate Generators
  - Basic Mod Generation
  - Basic Travis-CI buildscript generation
  - Basic .gitignore generation
- Simple NetBeans setup task.
- Unified mod information file.
- Enhanced sourcecode variable expander, with ability to default, so that build does not break if expansion fails.
- Small, hassel-free buildscripts that can be modified as your mod expands.
- Unified developer login, so that you only have enter your Mincraft info once, to be logged in for all QuikMods
- Login details stored outside of the mod directory, so that you don't have to worry about commiting your passwords to sourcecontrol.

## Setup

Place the following in the the `build.gradle`:
````
// QuikMod Buildscript
buildscript {
    repositories {
        jcenter()
        maven {
            name = "Forge Gradle"
            url = "http://files.minecraftforge.net/maven"
        }
        maven {
            name = "Gradle Plugins"
            url "https://plugins.gradle.org/m2/"
        }
    }
    dependencies {
        classpath 'gradle.plugin.com.rlonryan:QuikMod:1.0.17'
    }
}

// Apply QuikMod
apply plugin: 'com.rlonryan.quikmod'
````

## Usage

- To generate a basic mod, add this to the directory of choice and run: `gradle generate runClient`

- To add NetBeans support, run: `gradle netbeans` and use the Netbeans gradle plugin for both launching and debugging.

- To generate a basic Travis mod buildscript, run: `gradle travis`

- To enable automatic login, add the following to `%userprofile%/.gradle/gradle.properties`
  - `minecraft_username=<username>`
  - `minecraft_password=<password>`

## Notes
  - Generation tasks, as a safeguard, will refuse to overwrite pre-existing files.

