#!/usr/bin/env bash
mkdir -p /tmp/fastdfs
cd /tmp/fastdfs

### install libfastcommon
# get
wget https://github.com/happyfish100/libfastcommon/archive/V1.0.39.tar.gz
tar -zxf V1.0.39.tar.gz

# install
cd libfastcommon-1.0.39
./make.sh && ./make.sh install
cd ../

# set soft link
ln -s /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so
ln -s /usr/lib64/libfastcommon.so /usr/lib/libfastcommon.so
ln -s /usr/lib64/libfdfsclient.so /usr/local/lib/libfdfsclient.so
ln -s /usr/lib64/libfdfsclient.so /usr/lib/libfdfsclient.so

### install fastDFS
# get
wget https://github.com/happyfish100/fastdfs/archive/V5.11.tar.gz
tar -zxf V5.11.tar.gz

# install
cd fastdfs-5.11
./make.sh && ./make.sh install

# copy configuration
cd conf
cp http.conf mime.types /etc/fdfs

# clean
rm -r /tmp/fastdfs
