#!/usr/bin/env bash

# start tracker
fdfs_trackered /etc/fdfs/tracker.conf start
# start storage
fdfs_storaged /etc/fdfs/storage.conf start
# start nginx
service nginx start
# if the above does not work
nginx