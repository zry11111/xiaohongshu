local key = KEYS[1]
local followUserId = ARGV[1]

local exists redis.call('EXISTS',key)
if exists == 0 then
    return -1
end

local score = redis.call('ZSCORE',key,followUserId)
if score == false or score == nil then
    return -4
end

redis.call('ZREM',key,followUserId)

return 0;