// ================================================================================
// Assembled Slabs v1.3.0 Scarpet App
// --------------------------------------------------------------------------------
// ABOUT THE APP:
//  Breaking a double slab while crouching drops a full block rather than 2 slabs.
// --------------------------------------------------------------------------------
// ATTRIBUTION: 
//  This app recreates the behaviour of the "Assembled Slabs" data pack/mod 
//  by Voodoobeard.
//      https://mc.voodoobeard.com/#assembled_slabs
//      https://mc.voodoobeard.com/
//  This script doesn't copy any of the pack/mod files but provides credit to the 
//  creator, their rights and license terms still apply to the underlying idea.
// --------------------------------------------------------------------------------
// CREDITS:
//  - Scarpet port created by vjvoxz
//  - License: MIT (see https://mit-license.org/)
//  - This script was generated with assistance from Claude, an AI model created 
//    by Anthropic.
// --------------------------------------------------------------------------------
// See CHANGELOG.md for full version history and README.md for full app info.
// ================================================================================

// Define subcommands for /assembled_slabs
__config() -> {
    'scope' -> 'global',
    'commands' -> {
        '' -> 'info',
        'info' -> 'info'
    }
};

//----------------------------------------------------------------------
// Command interface: /assembled_slabs [info]
//----------------------------------------------------------------------
// Registers the /assembled_slabs info command
info() -> (
    print(format('db Assembled Slabs')); print(format('gi Breaking a double slab while crouching drops a full block rather than 2 slabs.')); print(format('gi Created by: vjvoxz (Ported from Voodoobeard Data Pack)')); print(format('p License: MIT'));
);

global_slab_to_block = {
    'minecraft:acacia_slab' -> 'minecraft:acacia_planks', // Java Edition 1.7.2: "The Update that Changed the World"
    'minecraft:andesite_slab' -> 'minecraft:andesite', // Java Edition 1.8: "The Bountiful Update"
    'minecraft:bamboo_mosaic_slab' -> 'minecraft:bamboo_mosaic', // Java Edition 1.20: "Trails & Tales"
    'minecraft:bamboo_slab' -> 'minecraft:bamboo_planks', // Java Edition 1.20: "Trails & Tales"
    'minecraft:birch_slab' -> 'minecraft:birch_planks', // Java Edition 1.13: "Update Aquatic"
    'minecraft:black_wool_slab' -> 'minecraft:black_wool', // Java Edition 26.3: "?"
    'minecraft:blackstone_slab' -> 'minecraft:blackstone', // Java Edition 1.16: "Nether Update"
    'minecraft:blue_wool_slab' -> 'minecraft:blue_wool', // Java Edition 26.3: "?" 
    'minecraft:brick_slab' -> 'minecraft:bricks', // Java Edition 0.26: SURVIVAL TEST
    'minecraft:brown_wool_slab' -> 'minecraft:brown_wool', // Java Edition 26.3: "?" 
    'minecraft:cherry_slab' -> 'minecraft:cherry_planks', // Java Edition 1.20: "Trails & Tales"
    'minecraft:cinnabar_slab' -> 'minecraft:cinnabar', // Java Edition 26.2: "Chaos Cubed" 
    'minecraft:cobbled_deepslate_slab' -> 'minecraft:cobbled_deepslate', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:cobblestone_slab' -> 'minecraft:cobblestone', // Java Edition Classic 0.0.14a: "Pre-Classic"
    'minecraft:crimson_slab' -> 'minecraft:crimson_planks', // Java Edition 1.16: "Nether Update"
    'minecraft:cut_copper_slab' -> 'minecraft:cut_copper', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:cut_red_sandstone_slab' -> 'minecraft:cut_red_sandstone', // Java Edition 1.8: "The Bountiful Update"
    'minecraft:cut_sandstone_slab' -> 'minecraft:cut_sandstone', // Java Edition 1.2.1: "Redstone Update"
    'minecraft:cyan_wool_slab' -> 'minecraft:cyan_wool', // Java Edition 26.3: "?"
    'minecraft:dark_oak_slab' -> 'minecraft:dark_oak_planks', // Java Edition 1.7.2: "The Update that Changed the World"
    'minecraft:dark_prismarine_slab' -> 'minecraft:dark_prismarine', // Java Edition 1.8: "The Bountiful Update"
    'minecraft:deepslate_brick_slab' -> 'minecraft:deepslate_bricks', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:deepslate_tile_slab' -> 'minecraft:deepslate_tiles', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:diorite_slab' -> 'minecraft:diorite', // Java Edition 1.8: "The Bountiful Update"
    'minecraft:end_stone_brick_slab' -> 'minecraft:end_stone_bricks', // Java Edition 1.9: "Combat Update"
    'minecraft:exposed_cut_copper_slab' -> 'minecraft:exposed_cut_copper', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:granite_slab' -> 'minecraft:granite', // Java Edition 1.8: "The Bountiful Update"
    'minecraft:gray_wool_slab' -> 'minecraft:gray_wool', // Java Edition 26.3: "?"
    'minecraft:green_wool_slab' -> 'minecraft:green_wool', // Java Edition 26.3: "?"
    'minecraft:jungle_slab' -> 'minecraft:jungle_planks', // Java Edition 1.2.1: "The Jungle Update"
    'minecraft:light_blue_wool_slab' -> 'minecraft:light_blue_wool', // Java Edition 26.3: "?"
    'minecraft:light_gray_wool_slab' -> 'minecraft:light_gray_wool', // Java Edition 26.3: "?"
    'minecraft:lime_wool_slab' -> 'minecraft:lime_wool', // Java Edition 26.3: "?"
    'minecraft:magenta_wool_slab' -> 'minecraft:magenta_wool', // Java Edition 26.3: "?"
    'minecraft:mangrove_slab' -> 'minecraft:mangrove_planks', // Java Edition 1.19: "The Wild Update"
    'minecraft:mossy_cobblestone_slab' -> 'minecraft:mossy_cobblestone', // Java Edition Indev 0.31: "Indev Era"
    'minecraft:mossy_stone_brick_slab' -> 'minecraft:mossy_stone_bricks', // Java Edition Beta 1.8 Pre-release: "Adventure Update"
    'minecraft:mud_brick_slab' -> 'minecraft:mud_bricks', // Java Edition 1.19: "The Wild Update"
    'minecraft:nether_brick_slab' -> 'minecraft:nether_bricks', // Java Edition 1.5: "Redstone Update"
    'minecraft:oak_slab' -> 'minecraft:oak_planks',  // Java Edition Classic 0.0.14a: "Pre-Classic"
    'minecraft:orange_wool_slab' -> 'minecraft:orange_wool', // Java Edition 26.3: "?"
    'minecraft:oxidized_cut_copper_slab' -> 'minecraft:oxidized_cut_copper', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:pale_oak_slab' -> 'minecraft:pale_oak_planks', // Java Edition 1.21.4: "The Garden Awakens"
    'minecraft:pink_wool_slab' -> 'minecraft:pink_wool', // Java Edition 26.3: "?"
    'minecraft:polished_andesite_slab' -> 'minecraft:polished_andesite', // Java Edition 1.8: "The Bountiful Update"
    'minecraft:polished_blackstone_brick_slab' -> 'minecraft:polished_blackstone_bricks', // Java Edition 1.16: "Nether Update"
    'minecraft:polished_blackstone_slab' -> 'minecraft:polished_blackstone', // Java Edition 1.16: "Nether Update"
    'minecraft:polished_cinnabar_slab' -> 'minecraft:polished_cinnabar', // Java Edition 26.2: "Chaos Cubed" 
    'minecraft:polished_deepslate_slab' -> 'minecraft:polished_deepslate', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:polished_diorite_slab' -> 'minecraft:polished_diorite',  // Java Edition 1.8: "The Bountiful Update"
    'minecraft:polished_granite_slab' -> 'minecraft:polished_granite', // Java Edition 1.8: "The Bountiful Update"
    'minecraft:polished_sulfur_slab' -> 'minecraft:polished_sulfur', // Java Edition 26.2: "Chaos Cubed" 
    'minecraft:polished_tuff_slab' -> 'minecraft:polished_tuff', // Java Edition 1.21: "Tricky Trials"
    'minecraft:prismarine_brick_slab' -> 'minecraft:prismarine_bricks', // Java Edition 1.8: "The Bountiful Update"
    'minecraft:prismarine_slab' -> 'minecraft:prismarine', // Java Edition 1.8: "The Bountiful Update"
    'minecraft:poplar_slab' -> 'minecraft:poplar_planks', // Java Edition 26.3: "?"  
    'minecraft:purple_wool_slab' -> 'minecraft:purple_wool', // Java Edition 26.3: "?" 
    'minecraft:purpur_slab' -> 'minecraft:purpur_block', // Java Edition 1.9: "Combat Update"
    'minecraft:quartz_slab' -> 'minecraft:quartz_block',  // Java Edition 1.5: "Redstone Update"
    'minecraft:red_nether_brick_slab' -> 'minecraft:red_nether_bricks',  // Java Edition 1.10: "Frostburn Update"
    'minecraft:red_sandstone_slab' -> 'minecraft:red_sandstone', // Java Edition 1.8: "The Bountiful Update"
    'minecraft:red_wool_slab' -> 'minecraft:red_wool', // Java Edition 26.3: "?" 
    'minecraft:resin_brick_slab' -> 'minecraft:resin_bricks', // Java Edition 1.21.4: "The Garden Awakens"
    'minecraft:sandstone_slab' -> 'minecraft:sandstone', // Java Edition Beta 1.2: "Adventure Update Precursor"
    'minecraft:smooth_quartz_slab' -> 'minecraft:smooth_quartz', // Java Edition 1.13: "Update Aquatic"
    'minecraft:smooth_red_sandstone_slab' -> 'minecraft:smooth_red_sandstone', // Java Edition 1.8: "The Bountiful Update"
    'minecraft:smooth_sandstone_slab' -> 'minecraft:smooth_sandstone', // Java Edition 1.14: "Village & Pillage"
    'minecraft:smooth_stone_slab' -> 'minecraft:smooth_stone', // Java Edition 1.14: "Village & Pillage"
    'minecraft:spruce_slab' -> 'minecraft:spruce_planks', // Java Edition 1.2.1: "The Jungle Update"
    'minecraft:stone_brick_slab' -> 'minecraft:stone_bricks', // Java Edition Beta 1.8 Pre-release: "Adventure Update"
    'minecraft:stone_slab' -> 'minecraft:stone', // Java Edition Classic 0.0.14a: "Pre-Classic"
    'minecraft:sulfur_slab' -> 'minecraft:sulfur', // Java Edition 26.2: "Chaos Cubed"
    'minecraft:tuff_brick_slab' -> 'minecraft:tuff_bricks', // Java Edition 1.21: "Tricky Trials"
    'minecraft:tuff_slab' -> 'minecraft:tuff', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:warped_slab' -> 'minecraft:warped_planks', // Java Edition 1.16: "Nether Update"
    'minecraft:waxed_cut_copper_slab' -> 'minecraft:waxed_cut_copper', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:waxed_exposed_cut_copper_slab' -> 'minecraft:waxed_exposed_cut_copper', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:waxed_oxidized_cut_copper_slab' -> 'minecraft:waxed_oxidized_cut_copper', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:waxed_weathered_cut_copper_slab' -> 'minecraft:waxed_weathered_cut_copper', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:weathered_cut_copper_slab' -> 'minecraft:weathered_cut_copper', // Java Edition 1.17: Caves & Cliffs: Part I
    'minecraft:white_wool_slab' -> 'minecraft:white_wool', // Java Edition 26.3: "?"
    'minecraft:yellow_wool_slab' -> 'minecraft:yellow_wool', // Java Edition 26.3: "?"
    // Additional slabs from the "Cinch's Missing Blocks by Cinchtail v4.0.0" mod
    'cinchsmissingblocks:andesite_brick_slab' -> 'cinchsmissingblocks:andesite_bricks',
    'cinchsmissingblocks:black_concrete_slab' -> 'minecraft:black_concrete',
    'cinchsmissingblocks:black_terracotta_slab' -> 'minecraft:black_terracotta',
    'cinchsmissingblocks:blackstone_tile_slab' -> 'minecraft:blackstone',
    'cinchsmissingblocks:blue_concrete_slab' -> 'minecraft:blue_concrete',
    'cinchsmissingblocks:blue_nether_brick_slab' -> 'cinchsmissingblocks:blue_nether_bricks',
    'cinchsmissingblocks:blue_terracotta_slab' -> 'minecraft:blue_terracotta',
    'cinchsmissingblocks:brown_concrete_slab' -> 'minecraft:brown_concrete',
    'cinchsmissingblocks:brown_terracotta_slab' -> 'minecraft:brown_terracotta',
    'cinchsmissingblocks:calcite_brick_slab' -> 'cinchsmissingblocks:calcite_bricks',
    'cinchsmissingblocks:calcite_slab' -> 'minecraft:calcite',
    'cinchsmissingblocks:cracked_andesite_brick_slab' -> 'cinchsmissingblocks:cracked_andesite_bricks',
    'cinchsmissingblocks:cracked_blackstone_tile_slab' -> 'cinchsmissingblocks:cracked_blackstone_tiles',
    'cinchsmissingblocks:cracked_blue_nether_brick_slab' -> 'cinchsmissingblocks:cracked_blue_nether_bricks',
    'cinchsmissingblocks:cracked_brick_slab' -> 'cinchsmissingblocks:cracked_bricks',
    'cinchsmissingblocks:cracked_calcite_brick_slab' -> 'cinchsmissingblocks:cracked_calcite_bricks',
    'cinchsmissingblocks:cracked_deepslate_brick_slab' -> 'minecraft:cracked_deepslate_bricks',
    'cinchsmissingblocks:cracked_deepslate_tile_slab' -> 'minecraft:cracked_deepslate_tiles',
    'cinchsmissingblocks:cracked_diorite_brick_slab' -> 'cinchsmissingblocks:cracked_diorite_bricks',
    'cinchsmissingblocks:cracked_dripstone_brick_slab' -> 'cinchsmissingblocks:cracked_dripstone_bricks',
    'cinchsmissingblocks:cracked_end_stone_brick_slab' -> 'cinchsmissingblocks:cracked_end_stone_bricks',
    'cinchsmissingblocks:cracked_granite_brick_slab' -> 'cinchsmissingblocks:cracked_granite_bricks',
    'cinchsmissingblocks:cracked_mud_brick_slab' -> 'cinchsmissingblocks:cracked_mud_bricks',
    'cinchsmissingblocks:cracked_nether_brick_slab' -> 'minecraft:cracked_nether_bricks',
    'cinchsmissingblocks:cracked_polished_blackstone_brick_slab' -> 'minecraft:cracked_polished_blackstone_bricks',
    'cinchsmissingblocks:cracked_prismarine_brick_slab' -> 'cinchsmissingblocks:cracked_prismarine_bricks',
    'cinchsmissingblocks:cracked_quartz_brick_slab' -> 'cinchsmissingblocks:cracked_quartz_bricks',
    'cinchsmissingblocks:cracked_red_nether_brick_slab' -> 'cinchsmissingblocks:cracked_red_nether_bricks',
    'cinchsmissingblocks:cracked_red_sandstone_brick_slab' -> 'cinchsmissingblocks:cracked_red_sandstone_bricks',
    'cinchsmissingblocks:cracked_resin_brick_slab' -> 'cinchsmissingblocks:cracked_resin_bricks',
    'cinchsmissingblocks:cracked_sandstone_brick_slab' -> 'cinchsmissingblocks:cracked_sandstone_bricks',
    'cinchsmissingblocks:cracked_stone_brick_slab' -> 'minecraft:cracked_stone_bricks',
    'cinchsmissingblocks:cracked_stone_tile_slab' -> 'cinchsmissingblocks:cracked_stone_tiles',
    'cinchsmissingblocks:cracked_tuff_brick_slab' -> 'cinchsmissingblocks:cracked_tuff_bricks',
    'cinchsmissingblocks:cyan_concrete_slab' -> 'minecraft:cyan_concrete',
    'cinchsmissingblocks:cyan_terracotta_slab' -> 'minecraft:cyan_terracotta',
    'cinchsmissingblocks:deepslate_slab' -> 'minecraft:deepslate',
    'cinchsmissingblocks:diorite_brick_slab' -> 'cinchsmissingblocks:diorite_bricks',
    'cinchsmissingblocks:dripstone_brick_slab' -> 'cinchsmissingblocks:dripstone_bricks',
    'cinchsmissingblocks:dripstone_slab' -> 'minecraft:dripstone_block',
    'cinchsmissingblocks:end_stone_slab' -> 'minecraft:end_stone',
    'cinchsmissingblocks:granite_brick_slab' -> 'cinchsmissingblocks:granite_bricks',
    'cinchsmissingblocks:gray_concrete_slab' -> 'minecraft:gray_concrete',
    'cinchsmissingblocks:gray_terracotta_slab' -> 'minecraft:gray_terracotta',
    'cinchsmissingblocks:green_concrete_slab' -> 'minecraft:green_concrete',
    'cinchsmissingblocks:green_terracotta_slab' -> 'minecraft:green_terracotta',
    'cinchsmissingblocks:light_blue_concrete_slab' -> 'minecraft:light_blue_concrete',
    'cinchsmissingblocks:light_blue_terracotta_slab' -> 'minecraft:light_blue_terracotta',
    'cinchsmissingblocks:light_gray_concrete_slab' -> 'minecraft:light_gray_concrete',
    'cinchsmissingblocks:light_gray_terracotta_slab' -> 'minecraft:light_gray_terracotta',
    'cinchsmissingblocks:lime_concrete_slab' -> 'minecraft:lime_concrete',
    'cinchsmissingblocks:lime_terracotta_slab' -> 'minecraft:lime_terracotta',
    'cinchsmissingblocks:magenta_concrete_slab' -> 'minecraft:magenta_concrete',
    'cinchsmissingblocks:magenta_terracotta_slab' -> 'minecraft:magenta_terracotta',
    'cinchsmissingblocks:mossy_andesite_brick_slab' -> 'cinchsmissingblocks:mossy_andesite_bricks',
    'cinchsmissingblocks:mossy_brick_slab' -> 'cinchsmissingblocks:mossy_bricks',
    'cinchsmissingblocks:mossy_calcite_brick_slab' -> 'cinchsmissingblocks:mossy_calcite_bricks',
    'cinchsmissingblocks:mossy_cobbled_deepslate_slab' -> 'cinchsmissingblocks:mossy_cobbled_deepslate',
    'cinchsmissingblocks:mossy_deepslate_brick_slab' -> 'cinchsmissingblocks:mossy_deepslate_bricks',
    'cinchsmissingblocks:mossy_deepslate_tile_slab' -> 'cinchsmissingblocks:mossy_deepslate_tiles',
    'cinchsmissingblocks:mossy_diorite_brick_slab' -> 'cinchsmissingblocks:mossy_diorite_bricks',
    'cinchsmissingblocks:mossy_dripstone_brick_slab' -> 'cinchsmissingblocks:mossy_dripstone_bricks',
    'cinchsmissingblocks:mossy_end_stone_brick_slab' -> 'cinchsmissingblocks:mossy_end_stone_bricks',
    'cinchsmissingblocks:mossy_granite_brick_slab' -> 'cinchsmissingblocks:mossy_granite_bricks',
    'cinchsmissingblocks:mossy_mud_brick_slab' -> 'cinchsmissingblocks:mossy_mud_bricks',
    'cinchsmissingblocks:mossy_prismarine_brick_slab' -> 'cinchsmissingblocks:mossy_prismarine_bricks',
    'cinchsmissingblocks:mossy_quartz_brick_slab' -> 'cinchsmissingblocks:mossy_quartz_bricks',
    'cinchsmissingblocks:mossy_red_sandstone_brick_slab' -> 'cinchsmissingblocks:mossy_red_sandstone_bricks',
    'cinchsmissingblocks:mossy_resin_brick_slab' -> 'cinchsmissingblocks:mossy_resin_bricks',
    'cinchsmissingblocks:mossy_sandstone_brick_slab' -> 'cinchsmissingblocks:mossy_sandstone_bricks',
    'cinchsmissingblocks:mossy_stone_tile_slab' -> 'cinchsmissingblocks:mossy_stone_tiles',
    'cinchsmissingblocks:mossy_tuff_brick_slab' -> 'cinchsmissingblocks:mossy_tuff_bricks',
    'cinchsmissingblocks:netherrack_slab' -> 'minecraft:netherrack',
    'cinchsmissingblocks:orange_concrete_slab' -> 'minecraft:orange_concrete',
    'cinchsmissingblocks:orange_terracotta_slab' -> 'minecraft:orange_terracotta',
    'cinchsmissingblocks:packed_mud_slab' -> 'minecraft:packed_mud',
    'cinchsmissingblocks:pink_concrete_slab' -> 'minecraft:pink_concrete',
    'cinchsmissingblocks:pink_terracotta_slab' -> 'minecraft:pink_terracotta',
    'cinchsmissingblocks:polished_calcite_slab' -> 'cinchsmissingblocks:polished_calcite',
    'cinchsmissingblocks:polished_dripstone_slab' -> 'cinchsmissingblocks:polished_dripstone',
    'cinchsmissingblocks:polished_end_stone_slab' -> 'minecraft:end_stone',
    'cinchsmissingblocks:purple_concrete_slab' -> 'minecraft:purple_concrete',
    'cinchsmissingblocks:purple_terracotta_slab' -> 'minecraft:purple_terracotta',
    'cinchsmissingblocks:quartz_brick_slab' -> 'minecraft:quartz_bricks',
    'cinchsmissingblocks:red_concrete_slab' -> 'minecraft:red_concrete',
    'cinchsmissingblocks:red_sandstone_brick_slab' -> 'cinchsmissingblocks:red_sandstone_bricks',
    'cinchsmissingblocks:red_terracotta_slab' -> 'minecraft:red_terracotta',
    'cinchsmissingblocks:sandstone_brick_slab' -> 'cinchsmissingblocks:sandstone_bricks',
    'cinchsmissingblocks:smooth_basalt_slab' -> 'minecraft:smooth_basalt',
    'cinchsmissingblocks:snow_brick_slab' -> 'cinchsmissingblocks:snow_bricks',
    'cinchsmissingblocks:stone_tile_slab' -> 'cinchsmissingblocks:stone_tiles',
    'cinchsmissingblocks:terracotta_slab' -> 'minecraft:terracotta',
    'cinchsmissingblocks:white_concrete_slab' -> 'minecraft:white_concrete',
    'cinchsmissingblocks:white_terracotta_slab' -> 'minecraft:white_terracotta',
    'cinchsmissingblocks:yellow_concrete_slab' -> 'minecraft:yellow_concrete',
    'cinchsmissingblocks:yellow_terracotta_slab' -> 'minecraft:yellow_terracotta'
};

//--------------------------------------------------------------------
// Legacy / special-case slabs that are intentionally left out of
// global_slab_to_block, so they should never be flagged by
// audit_slab_map() as "unmapped" either. Kept as its own set rather
// than as null entries in the main map, since that would depend on
// has() treating a null-valued key as still "present" - safer to be
// explicit here instead.
//--------------------------------------------------------------------
global_slab_audit_ignore = {
    // Legacy block from old worlds, predates current slab crafting
    // recipes. Has no full-block equivalent to assemble into.
    'petrified_oak_slab' -> true
};

//--------------------------------------------------------------------
// Best-effort guess at the "full block" a slab was cut/crafted from,
// used only by audit_slab_map() below to suggest new table entries.
// Tries the stonecutting recipe first (always a single 1:1
// ingredient for slabs), then falls back to the shaped crafting
// recipe (III / III / III pattern - any filled slot is the block).
//--------------------------------------------------------------------
guess_full_block(slab) -> (
    guess = null;
    for (['stonecutting', 'crafting'],
        type = _;
        if (guess == null,
            recipes = recipe_data(slab, type);
            if (recipes,
                for (recipes,
                    recipe = _; ingredients = recipe: 1;
                    if (ingredients,
                        for (ingredients,
                            slot = _;
                            if (slot && slot: 0 && guess == null,
                                guess = str(slot: 0);
                            )
                        )
                    )
                )
            )
        )
    ); guess
);

//--------------------------------------------------------------------
// block_list() and str(block) report blocks in the default
// 'minecraft' namespace with the prefix stripped (e.g. 'oak_slab'),
// but keep the namespace for modded blocks (e.g.
// 'cinchsmissingblocks:calcite_slab'). global_slab_to_block always
// uses the fully namespaced id, for readability and consistency
// between vanilla and modded entries. Any id coming from the game
// needs to go through this before being used as a lookup key into
// that table, otherwise every vanilla entry silently fails to match.
//--------------------------------------------------------------------
full_id(id) ->
    if (id~':', id, 'minecraft:' + id);

//--------------------------------------------------------------------
// Diffs the live 'slabs' block tag (which reflects whatever version,
// datapacks or mods the server is actually running) against our
// static global_slab_to_block table, and logs any unmapped slabs
// together with an auto-detected guess, so an operator can just copy
// the suggested line into the table above rather than hunting for it
// by hand. Nothing is added to the live map automatically - guesses
// are logged only, since a wrong guess would silently change drop
// behaviour.
//--------------------------------------------------------------------
audit_slab_map() -> (
    missing = [];
    for (block_list('slabs'),
        // audit_ignore is keyed the same way block_list() reports names
        // (namespace-stripped for vanilla), so check it against the raw
        // name; global_slab_to_block always uses the full id, so that
        // check needs the normalized one.
        if (!has(global_slab_to_block, full_id(_)) && !has(global_slab_audit_ignore, _), missing += full_id(_))
    );
    if (missing,
        logger('warn', 'Assembled Slabs: unmapped slab(s) detected - add these to global_slab_to_block:');
        for (missing,
            slab = _; guess = guess_full_block(slab); guess_text =
            if (guess, guess, 'null - could not auto-detect, please fill in manually'); logger('warn', '   ' + slab + ' -> ' + guess_text + ' (auto-detected values should be verified)');
        )
    )
);

__on_start() -> audit_slab_map();

//--------------------------------------------------------------------
// Intercepts a player breaking a double slab while sneaking, and
// replaces the default (2x slab) drop with a single full block.
// All other breaks (single slabs, or double slabs broken standing
// up) are left completely untouched and fall through to vanilla.
//--------------------------------------------------------------------
__on_player_breaks_block(player, block) -> (
   full_block = global_slab_to_block: full_id(str(block));
   if (full_block != null && block_state(block, 'type') == 'double' && query(player, 'sneaking'),
      pos = pos(block);
      // Captured now, before the block is removed below - 'block' is a
      // lazily-evaluated reference and would report 'air' if queried
      // again after destroy() runs.
      slab_id = full_id(str(block));
      holds = query(player, 'holds', 'mainhand');
      slot = query(player, 'selected_slot');
      // Route the actual removal through destroy() with the player's
      // real held item/nbt, instead of set(pos,'air'), so the game
      // applies its own correct Unbreaking-aware durability logic
      // (and respects the Unbreakable tag/component) exactly like a
      // normal break would - we don't hand-roll any of that math
      // ourselves, which would only drift from real vanilla behaviour.
      destroyed = true;
      if (holds,
            [item, count, tag] = holds;
            tag_back = destroy(pos, item, tag);
            if (tag_back == false, destroyed = false);
            if (tag_back == null,
               // Tool broke: vanilla removes/decrements it the same way.
               delete(tag, 'Damage');
               inventory_set(player, slot, count - 1, item, tag);
               sound('entity.item.break', pos(player), 1.0, 1.0);
               ,
               type(tag_back) == 'nbt',
               // Tool survived, carrying its updated damage value.
               inventory_set(player, slot, count, item, tag_back);
            )
            // tag_back == true means the tool has no damage to track
            // (e.g. Unbreakable, or Unbreaking rolled to skip this hit)
            // - nothing to write back in that case.
            ,
            destroyed = destroy(pos, 'air') != false; // empty hand
      );
      if (destroyed,
            // destroy() already spawned the block's normal loot (2 single
            // slabs, from its usual loot table) right where it stood -
            // clear just that, then drop our own single full-block item.
            for (entity_area('item', pos + [0.5, 0.5, 0.5], [0.6, 0.6, 0.6]),
               [drop_item, drop_count, drop_tag] = query(_, 'item');
               if (query(_, 'age') <= 1 && full_id(drop_item) == slab_id, modify(_, 'remove'))
            );
            // Vanilla scatters item drops within roughly the middle 70% of
            // the block on X/Z (offset 0.15 to 0.85) rather than dead
            // center, and gives them a small random horizontal drift plus
            // a gentle upward pop instead of spawning them with zero
            // motion.
            drop_pos = pos + [0.15 + rand(0.7), 0.1 + rand(0.15), 0.15 + rand(0.7)];
            item_entity = spawn('item', drop_pos, nbt('{Item:{id:"' + full_block + '",Count:1b}}'));
            if (item_entity, modify(item_entity, 'motion', rand(0.2) - 0.1, rand(0.2) + 0.1, rand(0.2) - 0.1));
      );
      'cancel'
   )
);
