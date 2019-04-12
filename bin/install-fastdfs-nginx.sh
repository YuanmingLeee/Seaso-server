#!/usr/bin/env bash
LAUNCH_DIR=${PWD}
mkdir -p /opt/fastdfs
cd /opt/fastdfs

### install fastDFS nginx module
wget https://github.com/happyfish100/fastdfs-nginx-module/archive/V1.20.tar.gz
tar -zxf V1.20.tar.gz
# copy fastdfs-nginx-module configuration file
cp ${LAUNCH_DIR}/bin/config/mod_fastdfs.conf /etc/fdfs

### reinstall ngnix
service nginx stop
killall nginx
wget http://nginx.org/download/nginx-1.15.9.tar.gz
tar -zxf nginx-1.15.9.tar.gz
cd nginx-1.15.9
# fix bug in config
cp ${LAUNCH_DIR}/bin/config/nginx-module-conf ../fastdfs-nginx-module-1.20/src/config

# uninstall nginx
apt-get purge nginx nginx-common

./configure \
--prefix=/usr/local/nginx \
--pid-path=/var/local/nginx/nginx.pid \
--lock-path=/var/lock/nginx/nginx.lock \
--error-log-path=/var/log/nginx/error.log \
--http-log-path=/var/log/nginx/access.log \
--with-http_gzip_static_module \
--http-client-body-temp-path=/var/temp/nginx/client \
--http-proxy-temp-path=/var/temp/nginx/proxy \
--http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
--http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
--http-scgi-temp-path=/var/temp/nginx/scgi \
--add-module=/opt/fastdfs/fastdfs-nginx-module-1.20/src

make && make install

# make soft link
ln -s /usr/local/nginx/sbin/nginx /usr/sbin/

# fix directory not found
mkdir -p /var/temp/nginx/client

# clean
cd /opt/fastdfs
rm nginx-1.15.9.tar.gz V1.20.tar.gz
