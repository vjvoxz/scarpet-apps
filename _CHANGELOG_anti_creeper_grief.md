# Changelog

All notable changes to the Anti Creeper Grief scarpet app will be documented in this file.

## [v1.1.0]
- Added a global toggle system. Added '/anti_creeper_grief toggle' to dynamically enable/disable the grief prevention.

## [v1.0.0]
- Initial release: Recreated Vanilla Tweaks' Anti Creeper Grief datapack behavior in Scarpet.
- Optimization: Replaced the 1-second repeating command schedule with an `entity_load_handler`.
- Feature: Creepers instantly receive `ExplosionRadius:0` upon loading into the world.
- Feature: Added `info` command to display metadata.