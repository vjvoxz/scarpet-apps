# Deep Mobs Scarpet App

Deep Mobs is a [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that recreates the Deep Mobs datapack behaviour using an event-driven setup. Mobs spawning below Y=0 have a chance to be upgraded into stronger variants.

## How it works

- **Step 1: Spawn hook**
  - On app start, it registers an entity load handler for targeted mob types.
  - It only processes entities that are **newly spawned** (`is_new`) to avoid re-processing mobs loaded from memory.

- **Step 2: Triggers/conditions**
  - The mob must be below the configured deep level (`is_deep(entity)` checks Y against `global_settings:'deep_level'`).
  - The mob must not already have the `deep_processed` scoreboard tag.
  - A probability roll must pass (`rand(100) < replacement_chance`).

- **Step 3: Outcome/effect**
  - If the mob passes all checks, it is upgraded/replaced according to its type:
    - **Zombies** receive boosted attributes and equipment (iron helmet).
    - **Skeletons** are replaced with **wither skeletons**, then upgraded with boosted attributes and equipment (gold helmet).
    - **Spiders** are replaced with **cave spiders**.
    - **Creepers** receive boosted attributes and a reduced fuse time.

## Features

- Event-driven mob upgrades triggered on spawn.
- Configurable upgrade chance via `replacement_chance`.
- Uses a `deep_processed` tag to prevent double-processing.
- Centralized `apply_attributes()` helper with modern NBT attribute merging.
- Optional `debug_mode` that makes upgraded mobs glow briefly.

## Commands

1. `/deep_mobs info`
   - Prints basic info about Deep Mobs.

2. `/deep_mobs debug`
   - Toggles `global_settings:'debug_mode'` and provides chat feedback.

## Metadata & Credits
* **App Name:** Deep Mobs
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** Voodoobeard

## Attribution
This app recreates the behaviour of the "Deep Mobs" data pack/mod by [VoodooBeard](https://mc.voodoobeard.com/).

This project is an independent Scarpet implementation and does **not** contain or copy any files from of the original pack/mod files, but provides credit to the creator, their rights and license terms still apply to the underlying idea.

## AI Assistance <Gemini>
This script was generated with assistance from **Gemini**, an AI model created by [Google DeepMind](https://deepmind.google/).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See CHANGELOG.md for detailed version history.