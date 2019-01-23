yum install gcc
wget http://download.redis.io/releases/redis-2.8.3.tar.gz
tar xzf redis-2.8.3.tar.gz
cd redis-2.8.3
make
mkdir /usr/redis
cp redis.conf /usr/redis
cd src
cp redis-server /usr/redis
cp redis-benchmark /usr/redis
cp redis-cli /usr/redis
cd /usr/redis
redis-server redis.conf &