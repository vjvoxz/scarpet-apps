// ================================================================================
// Brushing Mobs v1.4.0 Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  Extends vanilla brush behaviour so that brushing certain mobs yields items.
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app is not a copy of any mod or data pack, it is an independent 
//  recreation of the idea of brushing mobs to get items, and is intended to be 
//  used in vanilla. This mechanic has been seen before in modded community, but 
//  most of them are discontinued or archived.
// --------------------------------------------------------------------------------
// CREDITS:
//  - Scarpet app created by vjvoxz
//  - License: MIT (see https://mit-license.org/)
//  - This script was generated with assistance from Claude, an AI model created 
//    by Anthropic.
// --------------------------------------------------------------------------------
// See CHANGELOG.md for version history and README.md for full app info.
// ================================================================================

// Define subcommands for /brushing_mobs
__config() -> {
    'scope' -> 'player',
    'commands' -> {
        '' -> 'info',
        'info' -> 'info'
    }
};

//----------------------------------------------------------------------
// Command interface: /brushing_mobs [info]
//----------------------------------------------------------------------
// Registers the /brushing_mobs info command
info() -> (
    print(format('db Brushing Mobs'));
    print(format('gi Brushing a chicken drops a single feather.'));
    print(format('gi Brushing a parrot now drops a single feather.'));
    print(format('gi Brushing a sheep now drops a single string.'));
    print(format('gi Brushing a turtle now drops a single turtle_scute.'));
    print(format('gi Brushing consumes 16 durability points from the brush.'));
    print(format('gi Created by: vjvoxz'));
    print(format('p License: MIT'));
);

// Vanilla brush durability. Update here if a future MC version changes it.
// NOTE: this wear value only applies within this app's entity-brushing
// handler below. It has no effect on, and is entirely separate from,
// vanilla suspicious sand/gravel brushing, which is handled by different
// events that this app doesn't touch.
global_brush_max_damage = 64;
global_brush_wear = 16;

// Map of entity type -> item dropped when brushed.
global_brush_drops = {
    'chicken' -> 'feather',
    'turtle'  -> 'turtle_scute',
    'sheep'   -> 'string',
    'parrot'  -> 'feather'
};

// Map of entity type -> sound played when brushed.
global_brush_sounds = {
    'chicken' -> 'entity.chicken.egg',      // "Chicken plops"
    'turtle'  -> 'entity.turtle.shamble',   // "Turtle shambles"
    'sheep'   -> 'entity.sheep.ambient',    // "Sheep baahs"
    'parrot'  -> 'entity.parrot.ambient'    // "Parrot talks"
};

// Returns true if the given item nbt marks the item as unbreakable, checking
// both the modern (1.20.5+) component tag and the legacy tag for safety.
brush_is_unbreakable(nbt) -> (
    if (nbt == null, return(false));
    if (bool(get(nbt, 'components', 'minecraft:unbreakable') != null), return(true));
    if (bool(get(nbt, 'Unbreakable')), return(true));
    false
);

__on_player_interacts_with_entity(player, entity, hand) -> (

    item = query(player, 'holds', hand);
    if (item == null, return());

    [name, count, nbt] = item;
    if (name != 'brush', return());

    entity_type = query(entity, 'type');
    drop = global_brush_drops:entity_type;
    if (drop == null, return());

    // Hand swing animation, matching whichever hand was used to brush.
    if (hand == 'offhand', modify(player, 'swing', 'offhand'), modify(player, 'swing'));

    // Audio feedback: mob-specific sound, centered on the mob being brushed.
    brush_sound = global_brush_sounds:entity_type;
    if (brush_sound != null, sound(brush_sound, pos(entity), 1.0, 1.0));

    // Spawn the dropped item with a vanilla-like random offset and pop
    // velocity instead of appearing dead-center with no motion.
    drop_offset = [rand(0.5) - 0.25, query(entity, 'height') / 2, rand(0.5) - 0.25];
    drop_pos = pos(entity) + drop_offset;
    drop_motion = [rand(0.2) - 0.1, 0.2, rand(0.2) - 0.1];

    item_entity = spawn('item', drop_pos, encode_nbt({'Item' -> {'id' -> 'minecraft:'+drop, 'Count' -> 1}}));
    if (item_entity != null, modify(item_entity, 'motion', drop_motion));

    // Figure out which inventory slot holds the brush so we can write
    // the updated (or removed) item back.
    slot = if(hand == 'offhand', -1, query(player, 'selected_slot'));

    // Unbreakable brushes never take wear, same as any vanilla tool.
    if (!brush_is_unbreakable(nbt),

        // Read current damage value (0 if the brush is undamaged / has no nbt yet).
        damage = if(nbt == null, 0, get(nbt, 'components', 'minecraft:damage'));
        if (damage == null, damage = 0);

        new_damage = damage + global_brush_wear;

        if (new_damage >= global_brush_max_damage,
            // Brush is used up - remove it from the slot.
            inventory_set(player, slot, 0);
            //print(player, format('g Your brush breaks.'));
        ,
            // Otherwise persist the extra wear on the item.
            if (nbt == null, nbt = nbt('{}'));
            put(nbt, 'components.minecraft:damage', new_damage);
            inventory_set(player, slot, count, name, nbt);
        );
    );

    'cancel' // stop vanilla brushing-a-block-adjacent interaction logic from double-firing
);