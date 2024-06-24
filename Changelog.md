# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com),
and this project follows the [Ragnarök Versioning Convention](https://github.com/Red-Studio-Ragnarok/Commons/blob/main/Ragnar%C3%B6k%20Versioning%20Convention.md).

## Mystic Staffs 1.3 - 2024-01-29

### Internal

- Updated [gradle-buildconfig-plugin](https://github.com/gmazzo/gradle-buildconfig-plugin) to version 5.3.5
- Updated [foojay-resolver](https://github.com/gradle/foojay-toolchains) to version 0.8.0
- Updated [org.jetbrains.gradle.plugin.idea-ext](https://plugins.gradle.org/plugin/org.jetbrains.gradle.plugin.idea-ext) to version 1.1.8
- Updated [RetroFuturaGradle](https://github.com/GTNewHorizons/RetroFuturaGradle) to version 1.4.0
- Updated [io.freefair.lombok](https://plugins.gradle.org/plugin/io.freefair.lombok) to version 8.6
- Updated to [Gradle](https://gradle.org) 8.9-rc-1

## Mystic Staffs 1.2 - 2024-01-16

### Changed

- Changed GroupId from `io.redstudioragnarok` to `dev.redstudio`

### Internal

- General Cleanup
- Switched to [CurseUpdate](https://forge.curseupdate.com/) for update checking
- Switched from [RetroFuturaGradle](https://github.com/GTNewHorizons/RetroFuturaGradle) tags to [gmazzo](https://github.com/gmazzo) [gradle-buildconfig-plugin](https://github.com/gmazzo/gradle-buildconfig-plugin)
- Switched to [Gradle](https://gradle.org) Kotlin DSL
- Updated to [Gradle](https://gradle.org) 8.5

## Mystic Staffs 1.1 - 2023-7-16

### Added

- Added logo to `mcmod.info`
- Added Forge version checker for update notification

### Changed

- Updated the description and credits in `mcmod.info`

### Fixed

- Fixed crash on startup on dedicated server

### Internal

- Changed constant names to follow Java conventions
- Switched to standard RSR buildscript
- Removed embedded [Jafama](https://github.com/jeffhain/jafama)
