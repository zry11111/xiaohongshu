local key = KEYS[1] -- 操作的 Redis Key
local noteId = ARGV[1] -- 笔记ID
local exists = redis.call('EXISTS', key)
if exists == 0 then
    return -1
end
return redis.call('R.GETBIT', key, noteId)

