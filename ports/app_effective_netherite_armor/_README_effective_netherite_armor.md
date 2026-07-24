# Effective Netherite Armor Scarpet App

A [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that grants continuous Fire Resistance to players wearing a full set of Netherite armor.

---

## Overview
This app ensures that players who equip a complete Netherite armor set gain permanent Fire Resistance, enhancing survivability in lava and fire environments. It automatically applies the effect when the armor is worn and removes it when the set is broken.

## How it works
- Monitors all players once per second (20 ticks).
- Evaluates each player's equipped armor slots (`slot.armor.head`, `slot.armor.chest`, `slot.armor.legs`, `slot.armor.feet`).
- Applies infinite Fire Resistance (-1 duration) if the set is complete and no effect is present.
- Removes the infinite effect if the set is broken, ensuring potion-based Fire Resistance remains unaffected.

### Features
- Continuous Fire Resistance for full Netherite armor.
- Automatic removal of effect when armor is removed.
- Safeguard to prevent stripping normal Fire Resistance potions.
- Optimized tick handling for performance.

## Installation
1. Place `effective_netherite_armor.sc` in your scripts folder inside the Carpet Mod directory.
2. Run `/script load effective_netherite_armor.sc` in-game.
3. Verify installation with `/effective_netherite_armor info`.

## Usage Examples
Equip a full Netherite armor set to gain Fire Resistance.
Remove any piece of the set to lose the effect.

## Configuration
No user-facing configuration options are required. The app runs automatically once loaded.

## Compatibility
- Minecraft version: 1.21.X+, 26.X+
- Carpet Mod version: v1.4.112+

## Metadata & Credits
* **App Name:** Effective Netherite Armor
* **Author:** Scarpet port created by vjvoxz
* **Original Concept:** Voodoobeard

## Attribution
This app recreates the behaviour of the "[Project Name]" data pack/mod by [Voodoobeard](https://mc.voodoobeard.com/).

This project is an independent Scarpet implementation and does **not** contain or copy any files from of the original pack/mod files, but provides credit to the creator, their rights and license terms still apply to the underlying idea.

## AI Assistance
This script was generated with assistance from **Gemini**, an AI model created by [Google DeepMind](https://deepmind.google/).

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License
Released under the [MIT License](https://mit-license.org/).

## Version History
See _CHANGELOG_effective_netherite_armor.md for detailed version history.