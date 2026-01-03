__config() -> {
    'stay_loaded' -> true,
    'scope' -> 'global',
    'commands' -> {
        '' -> '_toggle_spreading',
        'on' -> '_enable',
        'off' -> '_disable',
        'rate <rate>' -> '_set_rate'
    }
};

global_spreading_enabled = true;
global_checks_per_tick = 3;

__on_start() -> (
    print('Lichen Spread loaded! Use /lichen_spread to toggle');
);

_toggle_spreading() -> (
    global_spreading_enabled = !global_spreading_enabled;
    print(format(
        'Lichen spreading: ' + if(global_spreading_enabled, 'g enabled', 'r disabled')
    ));
);

_enable() -> (
    global_spreading_enabled = true;
    print(format('g Lichen spreading enabled'));
);

_disable() -> (
    global_spreading_enabled = false;
    print(format('r Lichen spreading disabled'));
);

_set_rate(rate) -> (
    global_checks_per_tick = number(rate);
    print(format('y Checks per tick set to: ' + rate));
);

__on_tick() -> (
    if(!global_spreading_enabled, return());
    
    for(player('all'), 
        loop(global_checks_per_tick,
            _check_random_lichen(pos(_))
        )
    );
);

_check_random_lichen(player_pos) -> (
    [px, py, pz] = player_pos;
    
    rx = floor(rand(128) - 64);
    ry = floor(rand(64) - 32);
    rz = floor(rand(128) - 64);
    
    pos = [px + rx, py + ry, pz + rz];
    block_at_pos = block(pos);
    
    if(block_at_pos == 'glow_lichen' && rand(1) < 0.01,
        _attempt_spread(pos, block_at_pos)
    );
);

_attempt_spread(pos, lichen_block) -> (
    direction = floor(rand(3)) + 1;
    
    if(direction == 1, _spread_down(pos, lichen_block));
    if(direction == 2, _spread_up(pos, lichen_block));
    if(direction == 3, _spread_horizontal(pos, lichen_block));
);

_spread_down(pos, lichen_block) -> (
    [x, y, z] = pos;
    below = [x, y-1, z];
    below_block = block(below);
    properties = block_properties(lichen_block);
    
    if(_is_air(below_block),
        if(properties:'north' == 'true' && rand(1) < 0.5,
            set(below, 'glow_lichen', 'north', 'true')
        );
        if(properties:'south' == 'true' && rand(1) < 0.5,
            set(below, 'glow_lichen', 'south', 'true')
        );
        if(properties:'east' == 'true' && rand(1) < 0.5,
            set(below, 'glow_lichen', 'east', 'true')
        );
        if(properties:'west' == 'true' && rand(1) < 0.5,
            set(below, 'glow_lichen', 'west', 'true')
        );
    ,
        if(properties:'north' == 'true' && rand(1) < 0.5 && _can_attach([x, y-1, z-1]),
            set(below, 'glow_lichen', 'north', 'true')
        );
        if(properties:'south' == 'true' && rand(1) < 0.5 && _can_attach([x, y-1, z+1]),
            set(below, 'glow_lichen', 'south', 'true')
        );
        if(properties:'east' == 'true' && rand(1) < 0.5 && _can_attach([x+1, y-1, z]),
            set(below, 'glow_lichen', 'east', 'true')
        );
        if(properties:'west' == 'true' && rand(1) < 0.5 && _can_attach([x-1, y-1, z]),
            set(below, 'glow_lichen', 'west', 'true')
        );
    );
);

_spread_up(pos, lichen_block) -> (
    if(!_check_density(pos), return());
    
    [x, y, z] = pos;
    above = [x, y+1, z];
    above_block = block(above);
    properties = block_properties(lichen_block);
    
    if(!_is_air(above_block), return());
    
    if(properties:'north' == 'true' && rand(1) < 0.5 && _can_attach([x, y+1, z-1]),
        set(above, 'glow_lichen', 'north', 'true')
    );
    if(properties:'south' == 'true' && rand(1) < 0.5 && _can_attach([x, y+1, z+1]),
        set(above, 'glow_lichen', 'south', 'true')
    );
    if(properties:'east' == 'true' && rand(1) < 0.5 && _can_attach([x+1, y+1, z]),
        set(above, 'glow_lichen', 'east', 'true')
    );
    if(properties:'west' == 'true' && rand(1) < 0.5 && _can_attach([x-1, y+1, z]),
        set(above, 'glow_lichen', 'west', 'true')
    );
);

_spread_horizontal(pos, lichen_block) -> (
    if(!_check_density(pos), return());
    
    [x, y, z] = pos;
    properties = block_properties(lichen_block);
    
    if(properties:'north' != 'true' && _is_air([x, y, z-1]),
        _spread_horizontal_direction(pos, properties, 'north', [x, y, z-1])
    );
    if(properties:'south' != 'true' && _is_air([x, y, z+1]),
        _spread_horizontal_direction(pos, properties, 'south', [x, y, z+1])
    );
    if(properties:'east' != 'true' && _is_air([x+1, y, z]),
        _spread_horizontal_direction(pos, properties, 'east', [x+1, y, z])
    );
    if(properties:'west' != 'true' && _is_air([x-1, y, z]),
        _spread_horizontal_direction(pos, properties, 'west', [x-1, y, z])
    );
);

_spread_horizontal_direction(pos, properties, direction, target) -> (
    [tx, ty, tz] = target;
    
    if(direction == 'north',
        if(properties:'east' == 'true' && _can_attach([tx+1, ty, tz]),
            set(target, 'glow_lichen', 'east', 'true')
        );
        if(properties:'west' == 'true' && _can_attach([tx-1, ty, tz]),
            set(target, 'glow_lichen', 'west', 'true')
        );
    );
    
    if(direction == 'south',
        if(properties:'east' == 'true' && _can_attach([tx+1, ty, tz]),
            set(target, 'glow_lichen', 'east', 'true')
        );
        if(properties:'west' == 'true' && _can_attach([tx-1, ty, tz]),
            set(target, 'glow_lichen', 'west', 'true')
        );
    );
    
    if(direction == 'east',
        if(properties:'north' == 'true' && _can_attach([tx, ty, tz-1]),
            set(target, 'glow_lichen', 'north', 'true')
        );
        if(properties:'south' == 'true' && _can_attach([tx, ty, tz+1]),
            set(target, 'glow_lichen', 'south', 'true')
        );
    );
    
    if(direction == 'west',
        if(properties:'north' == 'true' && _can_attach([tx, ty, tz-1]),
            set(target, 'glow_lichen', 'north', 'true')
        );
        if(properties:'south' == 'true' && _can_attach([tx, ty, tz+1]),
            set(target, 'glow_lichen', 'south', 'true')
        );
    );
    
    if(_can_attach([tx, ty+1, tz]),
        set(target, 'glow_lichen', 'down', 'true')
    );
);

_check_density(pos) -> (
    [x, y, z] = pos;
    count = 0;
    
    for([-4, 4],
        for([-4, 4],
            if(block([x+_, y+1, z+__]) == 'glow_lichen',
                count += 1
            )
        )
    );
    
    count < 4
);

_is_air(pos) -> (
    b = block(pos);
    b == 'air' || b == 'cave_air' || b == 'void_air'
);

_can_attach(pos) -> (
    b = block(pos);
    state = block_state(pos);
    
    if(b == 'air' || b == 'cave_air' || b == 'void_air', return(false));
    
    attachable = [
        'stone', 'deepslate', 'dirt', 'grass_block',
        'andesite', 'diorite', 'granite', 'tuff',
        'oak_log', 'birch_log', 'spruce_log', 'jungle_log',
        'acacia_log', 'dark_oak_log', 'mangrove_log', 'cherry_log',
        'oak_leaves', 'birch_leaves', 'spruce_leaves'
    ];
    
    b ~ attachable || solid(state)
);