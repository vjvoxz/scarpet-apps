# Changelog

All notable changes to the Brushing Mobs scarpet app will be documented in this file.

## [v1.4.0]  (current)
- Added sheep (drops one string) and parrot (drops one feather) to the brushable mob list.
- Replaced the generic armadillo-borrowed sounds with a distinct, per-mob sound:
    chicken -> entity.chicken.egg      ("Chicken plops")
    turtle  -> entity.turtle.shamble   ("Turtle shambles")
    sheep   -> entity.sheep.ambient    ("​Sheep baahs")
    parrot  -> entity.parrot.ambient   ("Parrot talks")

## [v1.3.0]
- Added missing hand swing animation when brushing a mob.
- Added missing brushing/drop sounds when brushing a mob.
- Durability wear now respects the "unbreakable" item flag (checks both the modern component tag and the legacy NBT tag), matching vanilla tools/items which never lose durability while unbreakable.
- Dropped item now spawns with a small random position offset and pop velocity, like vanilla item drops, instead of appearing dead-center with zero motion.

## [v1.2.0]
- *Clarified/confirmed scope:* the 16-point wear and drop logic below are scoped entirely to __on_player_interacts_with_entity, i.e. brushing mobs on this list. Suspicious sand/gravel brushing goes through separate vanilla block-interaction events that this app never hooks, so that behaviour (including its own vanilla durability loss) is completely unaffected.

## [v1.1.0]
- Brushing a turtle now drops a single turtle_scute.
- Brushing consumes 16 durability points from the brush (was: no wear).
- Brush breaks (item removed) once accumulated damage reaches max durability (64).

## [v1.0.0]
- Initial version: brushing a chicken drops a single feather.
