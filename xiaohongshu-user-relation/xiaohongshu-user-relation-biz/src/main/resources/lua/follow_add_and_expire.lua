local key = KEYS[1] -- 操作的 Redis Key
local followUserId = ARGV[1] -- 关注的用户ID
local timestamp = ARGV[2] -- 时间戳
local expireTime = ARGV[3] -- 过期时间，单位秒

redis.call('ZADD', key, timestamp, followUserId)
-- 设置过期时间 每次操作后要刷新过期时间，因此分为两步执行
redis.call('EXPIRE', key, expireSeconds)
return 0