// ================================================================================
// Deep Mobs Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  Recreates the Deep Mobs datapack behaviour using an event-driven setup.
//  Mobs spawning below Y=0 have a chance to be upgraded into stronger variants.
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of the "Deep Mobs" data pack/mod 
//  by Voodoobeard.
//      Data pack/mod website
//      Developer website
//  This script doesn't copy any of the pack/mod files but provides credit to the 
//  creator, their rights and license terms still apply to the underlying idea.
// --------------------------------------------------------------------------------
// CREDITS:
//  - Scarpet port created by vjvoxz
//  - License: MIT (see https://mit-license.org/)
//  - This script was generated with assistance from Gemini, an AI model created 
//    by Google DeepMind.

// --------------------------------------------------------------------------------
// See CHANGELOG.md for version history and README.md for full app info.
// ================================================================================

// Define subcommands for /deep_mobs
__config() -> {
    'commands' -> {
        '' -> 'info',
        'info' -> 'info',
        'debug' -> 'toggle_debug'
    },
    'scope' -> 'global'
};

info() -> (
    print(format('db Deep Mobs'));
    print('gi Upgrades targeted mobs that spawn deep underground.');
    print('gi Created by: vjvoxz');
    print('p License: MIT');
);

toggle_debug() -> (
    // Flips the boolean state
    global_settings:'debug_mode' = !global_settings:'debug_mode';
    
    // Provides visual feedback in chat based on the new state
    if (global_settings:'debug_mode',
        print(format('db [DM] ', 'l Debug Mode Enabled! Upgraded mobs will now glow.'));
    ,
        print(format('db [DM] ', 'r Debug Mode Disabled.'));
    );
);

// ------------------------------------------------------------------------------
// INITIALIZATION & CONFIGURATION
// ------------------------------------------------------------------------------
__on_start() -> (
    // Centralized settings map as requested
    global_settings = {
        'deep_level' -> 0,          // y = 0
        'replacement_chance' -> 25, // Set to 100 for debugging purposes
        'skeleton_health' -> 25,    // Default health: 20
        'skeleton_attack' -> 4,     // Default attack: 2
        'skeleton_speed' -> 0.4,    // Default speed: 0.25
        'creeper_health' -> 30,     // Default health: 20
        'zombie_health' -> 40,      // Default health: 20
        'zombie_attack' -> 6,       // Default attack: 3
        'zombie_armor' -> 8,        // Default armor: 2
        'zombie_speed' -> 0.35,     // Default speed: 0.23
        'zombie_backup' -> 1.0,     // Default spawn_reinforcements: Random (0 - 0.1)
        'debug_mode' -> false       // Toggle to true to re-enable glowing visual feedback
    };

    // Dispatch map to replace multiple execute commands
    global_dispatch = {
        'zombie'   -> 'upgrade_zombie',
        'skeleton' -> 'upgrade_skeleton',
        'spider'   -> 'upgrade_spider',
        'creeper'  -> 'upgrade_creeper'
    };

    // Attach the load handler strictly to our targeted mobs to save resources
    entity_load_handler(keys(global_dispatch), '__on_mob_spawned');
);

// ------------------------------------------------------------------------------
// CALLBACKS
// ------------------------------------------------------------------------------
__on_mob_spawned(entity, is_new) -> (
    // 1. Only process freshly spawned mobs, ignore chunks loading from memory
    if (!is_new, return());
    
    // 2. Already processed? 
    if (query(entity, 'has_scoreboard_tag', 'deep_processed'), return());
    mark_processed(entity);

    // 3. Below deep level?
    if (!is_deep(entity), return());

    // 4. Replacement chance?
    if (!chance(), return());

    // 5. Dispatch to specific mob upgrade logic
    type = query(entity, 'type');
    if (has(global_dispatch, type),
        call(global_dispatch:type, entity);
    );
);

// ------------------------------------------------------------------------------
// UTILITY
// ------------------------------------------------------------------------------
is_deep(entity) -> (
    pos = query(entity, 'pos');
    return(pos:1 <= global_settings:'deep_level');
);

chance() -> (
    return(rand(100) < global_settings:'replacement_chance');
);

mark_processed(entity) -> (
    modify(entity, 'tag', 'deep_processed');
);

apply_debug_glow(entity) -> (
    // Only applies if debug_mode is set to true in global_settings
    if (global_settings:'debug_mode',
        modify(entity, 'effect', 'glowing', 400, 0, false, false);
    );
);

// ------------------------------------------------------------------------------
// ATTRIBUTE SYSTEM
// ------------------------------------------------------------------------------
apply_attributes(entity, attributes) -> (
    nbt_list = [];
    
    // Updated to modern NBT structure: lowercase 'id', 'base', and namespaced identifiers
    if (has(attributes, 'health'), 
        nbt_list += '{id:"minecraft:max_health",base:' + attributes:'health' + 'd}';
    );
    if (has(attributes, 'armor'), 
        nbt_list += '{id:"minecraft:armor",base:' + attributes:'armor' + 'd}';
    );
    if (has(attributes, 'attack'), 
        nbt_list += '{id:"minecraft:attack_damage",base:' + attributes:'attack' + 'd}';
    );
    if (has(attributes, 'follow_range'), 
        nbt_list += '{id:"minecraft:follow_range",base:' + attributes:'follow_range' + 'd}';
    );
    if (has(attributes, 'movement_speed'), 
        nbt_list += '{id:"minecraft:movement_speed",base:' + attributes:'movement_speed' + 'd}';
    );    
    if (has(attributes, 'spawn_reinforcements'), 
        nbt_list += '{id:"minecraft:spawn_reinforcements",base:' + attributes:'spawn_reinforcements' + 'd}';
    );    
    
    // Modern master key is lowercase 'attributes'
    if (length(nbt_list) > 0,
        nbt_string = '{attributes:[' + join(',', nbt_list) + ']}';
        modify(entity, 'nbt_merge', nbt_string);
        
        // Immediately heal the entity to match its new max health capacity
        if (has(attributes, 'health'), 
            modify(entity, 'health', attributes:'health');
        );
    );
);

// ------------------------------------------------------------------------------
// REPLACEMENT SYSTEM
// ------------------------------------------------------------------------------
replace_entity(original, new_type) -> (
    pos = query(original, 'pos');
    yaw = query(original, 'yaw');
    pitch = query(original, 'pitch');
    
    new_entity = spawn(new_type, pos);
    if (!new_entity, return(null));
    
    modify(new_entity, 'location', pos:0, pos:1, pos:2, yaw, pitch);
    mark_processed(new_entity);
    modify(original, 'remove');
    
    return(new_entity);
);

// ------------------------------------------------------------------------------
// MOB DEFINITIONS
// ------------------------------------------------------------------------------
upgrade_zombie(entity) -> (
    apply_attributes(entity, {'health' -> global_settings:'zombie_health'});
    apply_attributes(entity, {'attack_damage' -> global_settings:'zombie_attack'});
    apply_attributes(entity, {'armor' -> global_settings:'zombie_armor'});
    apply_attributes(entity, {'movement_speed' -> global_settings:'zombie_speed'});
    apply_attributes(entity, {'spawn_reinforcements' -> global_settings:'zombie_backup'});
    // Equip Iron Helmet
    modify(entity, 'nbt_merge', '{equipment:{mainhand:{id:iron_axe,count:1},head:{id:iron_helmet,count:1}},drop_chances:{mainhand:0f,head:0f}}');
    apply_debug_glow(entity);
);

upgrade_skeleton(entity) -> (
    new_skel = replace_entity(entity, 'wither_skeleton');
    if (new_skel != null, 
        apply_attributes(new_skel, {'health' -> global_settings:'skeleton_health'});
        apply_attributes(new_skel, {'attack_damage' -> global_settings:'skeleton_attack'});
        apply_attributes(new_skel, {'movement_speed' -> global_settings:'skeleton_speed'});
        // Equip Gold Helmet
        modify(new_skel, 'nbt_merge', '{equipment:{head:{id:golden_helmet,count:1}},drop_chances:{head:0f}}');
        apply_debug_glow(new_skel);
    );
);

upgrade_spider(entity) -> (
    new_spider = replace_entity(entity, 'cave_spider');
    if (new_spider != null, apply_debug_glow(new_spider));
);

upgrade_creeper(entity) -> (
    apply_attributes(entity, {'health' -> global_settings:'creeper_health'});
    // Set fuse to 15 ticks (Vanilla is 30)
    modify(entity, 'nbt_merge', '{Fuse:15s}');
    apply_debug_glow(entity);
);