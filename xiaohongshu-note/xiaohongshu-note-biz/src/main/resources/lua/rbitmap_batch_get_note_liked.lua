local key = KEYS[1]

-- 笔记是否被点赞结果
local results = {}

local exists = redis.call('EXISTS', key)
if exists == 0 then
    results[1] = -1
    return results
end

for i = 1, #ARGV do
    results[i] = redis.call("R.GETBIT", key, ARGV[i])
end

return results
