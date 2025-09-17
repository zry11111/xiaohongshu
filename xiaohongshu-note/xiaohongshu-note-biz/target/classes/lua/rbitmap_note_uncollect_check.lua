local key = KEYS[1] -- 操作的 Redis Key
local noteId = ARGV[1] -- 笔记ID

local exists = redis.call('EXISTS', key)
if exists == 0 then
    return -1
end

local isCollected = redis.call('R.GETBIT', key, noteId)
if isCollected == 0 then
    return 0
end

-- 取消收藏笔记
return redis.call('R.SETBIT', key, noteId, 0)
