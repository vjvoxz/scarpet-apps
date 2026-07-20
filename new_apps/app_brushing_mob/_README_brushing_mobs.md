# Brushing Mobs Scarpet App

**Brushing Mobs** is a [Carpet Mod](https://github.com/gnembon/fabric-carpet) `scarpet` app that extends the vanilla brush mechanic, allowing players to brush certain passive mobs to obtain renewable resources without harming them.

## How it works

When a player right-clicks a supported mob with a **Brush**, the app intercepts the interaction and performs a custom brushing action.

- The mob drops a renewable resource depending on its type.
- A unique sound is played for each supported mob.
- The dropped item is spawned with a small random offset and velocity, similar to vanilla item drops.
- The brush loses **16 durability** per successful brushing.
- Brushes with the **Unbreakable** component do not lose durability.

The mechanic is completely independent of vanilla archaeology, so brushing suspicious sand or gravel behaves exactly as it does in vanilla Minecraft.

## Features

- Adds renewable mob resources through brushing.
- Uses vanilla brush durability mechanics.
- Supports both normal and Unbreakable brushes.
- Plays mob-specific sound effects.
- Vanilla-like item spawning with randomized motion.
- Does not modify or interfere with archaeology mechanics.
- Lightweight implementation using Carpet Mod events.

## Supported Mobs

| Mob | Drop |
|------|------|
| Chicken   | Feather       |
| Parrot    | Feather       |
| Sheep     | String        |
| Turtle    | Turtle Scute  |

## Commands

### `/brushing_mobs`

Displays information about the app.

### `/brushing_mobs info`

Shows:

- Supported mobs and their drops
- Brush durability usage
- Author information
- License

## Metadata & Credits

- **App Name:** Brushing Mobs
- **Author:** Scarpet port created by **vjvoxz**
- **Concept:** Independent Scarpet implementation inspired by community brushing mechanics.

## Attribution

This app is **not a port or copy** of any existing mod or data pack.

It is an independent Scarpet implementation inspired by the general concept of brushing mobs for renewable resources. Similar mechanics have appeared in community mods in the past, many of which are now discontinued or archived.

## AI Assistance

This script was generated with assistance from **Claude**, an AI model created by Anthropic.

Although the script has been tested in-game by a human, you should review the source yourself before relying on it, particularly in long-term survival worlds.

## License

Released under the **MIT License**.

## Version History

See **CHANGELOG.md** for detailed version history.