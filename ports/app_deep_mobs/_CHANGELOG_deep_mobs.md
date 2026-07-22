# Changelog

All notable changes to the Deep Mobs scarpet app will be documented in this file.

## v1.1.0
- Made mobs significantly more dangerous:
  - Zombies: Increased attack to 7, armor to 8, movement speed to 0.3, and equipped iron helmets.
  - Skeletons: Kept as regular skeletons, equipped with gold helmets and offhand Harming arrows.
  - Creepers: Cut explosion fuse time in half (30 -> 15 ticks).
- Updated `apply_attributes()` helper to support `movement_speed`.

## v1.0.4
- Added dynamic command router to toggle `debug_mode` directly in-game.

## v1.0.3
- Reverted default replacement_chance back to 20%.
- Added `debug_mode` toggle to global_settings for future testing.

## v1.0.2
- Modernized NBT attribute merging to fix a bug where stats were ignored.

## v1.0.1
- Debugging update: Changed default replacement_chance to 100%.
- Added a 20-second (400 ticks) glowing effect to upgraded mobs.

## v1.0.0
- Initial release.