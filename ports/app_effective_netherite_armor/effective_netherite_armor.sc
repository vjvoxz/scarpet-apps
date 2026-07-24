// ================================================================================
// Effective Netherite Armor v1.1.0 Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  Grants continuous Fire Resistance to players wearing a full set of
//  Netherite armor.
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of the "Effective Netherite Armour" data pack 
//  by Voodoobeard.
//      https://mc.voodoobeard.com/#effective_netherite_armour
//      https://mc.voodoobeard.com/
//  This script doesn't copy any of the pack/mod files but provides credit to the 
//  creator, their rights and license terms still apply to the underlying idea.
// --------------------------------------------------------------------------------
// CREDITS:
//  - Scarpet port created by vjvoxz
//  - License: MIT (see https://mit-license.org/)
//  - This script was generated with assistance from Gemini, an AI model created 
//    by Google DeepMind.
// --------------------------------------------------------------------------------
// See CHANGELOG.md for full version history and README.md for full app info.
// ================================================================================

// Configuration for dynamic command routing and scope definitions
__config() -> {
    'commands' -> {
        '' -> 'info',
        'info' -> 'info'
    },
    'scope' -> 'global'
};

// Registers the /effective_netherite_armor info command
info() -> (
    print(format('db Effective Netherite Armor'));
    print(format('gi Equipping a full set of Netherite armor grants continuous Fire Resistance.'));
    print(format('wi Version: 1.1.0'));
    print(format('p License: MIT'));
);

// Global tick event to evaluate players and apply/remove status effects
__on_tick() -> (
    // Performance Optimization: Restrict processing to once per second (20 ticks).
    if (tick_time() % 20 == 0,
        
        // Map over all players using list comprehension
        map(player('all'),
            p = _;
            
            // 1. Evaluate if the player is currently wearing the full set
            has_armor = (
                query(p, 'holds', 'head'):0  == 'netherite_helmet' &&
                query(p, 'holds', 'chest'):0 == 'netherite_chestplate' &&
                query(p, 'holds', 'legs'):0  == 'netherite_leggings' &&
                query(p, 'holds', 'feet'):0  == 'netherite_boots'
            );
            
            // 2. Query the player's active Fire Resistance effect. 
            // Returns null if absent, or a tuple: [amplifier, duration]
            current_effect = query(p, 'effect', 'fire_resistance');
            
            // 3. Apply the infinite effect (-1) if they have the armor but no buff
            if (has_armor && current_effect == null,
                modify(p, 'effect', 'fire_resistance', -1, 0, false, false, false);
            );
            
            // 4. Remove the effect (set to 0) if they break the set.
            // Potion Safeguard: current_effect:1 < 0 ensures we ONLY remove infinite buffs!
            if (!has_armor && current_effect != null && current_effect:1 < 0,
                modify(p, 'effect', 'fire_resistance', 0);
            );
        );
    );
);