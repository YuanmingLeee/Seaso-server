# Fast DFS Installation Guide
If some scrips/lines does not work, please try run them with `sudo`.
## 1. General Setup
For fast setup, please run the script:
```bash
sudo ./bin/install-fastdfs.sh
```
Then change settings for [tracker](bin/config/tracker.conf) and [storage](bin/config/storage.conf).
Normally, you should change the following lines:  
```
# file: tracker.conf
...
# the base path to store data and log files for tracker
base_path=home/<user>/data/fastdfs/tracker
...
# the http server port, should not be used
http.server_port=18080
...
```
```
# file: storage.conf
...
base_path=/data/fastdfs/storage
...
# store_path#, based 0, if store_path0 not exists, it's value is base_path
# the paths must be exist
store_path0=/data/fastdfs/storage
# add more paths here:
# store_path1=...
...
```
And configure the node:
```bash
sudo ./bin/config-fastdfs.sh "<track OR storage OR NOT_SET>" # track for only track, storage for only storage, not 
                                                             # provided for both, others has no effect
```
You are required to run these additional lines before start the service:
```bash
mkdir /path/to/your/tracker/base  # for tracker
mkdir /path/to/your/storage/base  # for storage
```
Now you may want to check if the install is success.  
For tracker,
```bash
service fdfs_trackered start  # start tracker
service fdfs_storaged start   # start storage
netstat -unlp| grep fdfs
```
If you see something like fdfs_trackered listening on port xxxx(the value set in `tracker.conf`), your tracker is 
working. If you see something like fdfs_storaged listening on port xxxx(the value set in `storage.conf`), your storage
is working.

Something, the following lines are not able to start the service. You may also try:
```bash
fdfs_trackered /etc/fdfs/tracker.conf start  # start tracker
fdfs_storaged /etc/fdfs/storage.conf start   # start storage
```
## 2. For Storage node
If you are configuring a storage node, the install of the nginx module is required.  
Before install, you need to modify the [configuration file](/bin/config/mod_fastdfs.conf):
```
...
# tracker server address(ip:port), the default port is 22122. You can refer to tracker.conf
tracker_server=your.tracker.server.ip:your-tracker-server-port
...
# add group name in url
url_have_group_name=true
...
# path to your storage, you can refer to storage.conf
store_path0=path/to/your/storage
# you can add more store_path if you have configured more than 1 in storage.conf
# store_path1=path/to/your/storage
```
And run the nginx install.   
**WARNING: By doing so, you are reinstall nginx if you have nginx installed previously on 
the server. Please see how to add module to nginx.**
```bash
sudo ./bin/install-fastdfs-nginx.sh
```
After we use a recompiled nginx, we are not able to start the correct nginx by `server nginx start`. You need to create 
a nginx.service at `/lib/systemed/system/nginx.service`. See this [post](https://serverfault.com/a/735262) on how to 
add `nginx.service`. A fast fix can be done by:
```bash
sudo cp ./bin/config/nginx.service /lib/systemd/system/nginx.service
systemctl daemon-reload
```

To check if nginx is working:
```bash
service nginx start  # or service nginx reload if the nginx has already been running.
netstat -unlp| grep nginx
```
You should see something like nginx is listening on port 80.  
Or you may simply go to your web browser with the url [localhost](http://localhost). You should see nginx welcome page.
If the page is not rendering, please check if your port 80 has been blocked by your firewall.

After checking, we need to reconfigure nignx:
```bash
vim /usr/local/nginx/conf/nginx.conf
```
Add listen port 8888 as configured in http.server_port of `bin/config/storage.conf`. Change the alias and root url to 
the base_path of `bin/config/storage.conf`. Example is below:
```
...
server {
    listen      8888;  
    server_name localhost;

   location / {
      root html;
      index index.html index.htm;
   }
   location ~/group([0-9])/M00 {
      alias <path/to/your/storage/base>;
      ngx_fastdfs_module;  
    }
    location /group1/M00 {
        root <path/to/your/storage/base>;
        ngx_fastdfs_module;
    }
  }
...
```
## 3. For Tracker node
If you are configuring a tracker node, the install of the nginx module is optional. However, we recommend you to 
install nginx as well, as it can be an encapsulation of storage nodes.  
If the tracker has already installed nginx, no re-installation is needed. Otherwise, please run:
```bash
sudo apt-get install nginx
```
And add configuration of `nignx.conf` depends on your installation path. An example is below:
```
...
# set group1 cluster
upstream fdfs_group1 {
     server <your storage ip>:<storage http port> weight=1 max_fails=2 fail_timeout=30s;
     # here can have more storage server
}

server {
    listen       18080;    # your tracker http port, refer to tracker.conf
    server_name  localhost;

    #charset koi8-r;

    #access_log  logs/host.access.log  main;

    location / {
        root   html;
        index  index.html index.htm;
    }

    # set group workload balance
    location /group1/M00 {
        proxy_next_upstream http_502 http_504 error timeout invalid_header;
        proxy_pass http://fdfs_group1;
        expires 30d;
    }
}
...
```
## Start Services
1. Tracker
    ```bash
    service fdfs_trackered start
    ```
    Or
    ```bash
    fdfs_trackered /etc/fdfs/tracker.conf start
    ```
2. Storage
    ```bash
    serivce fdfs_storage start
    ```
    Or
    ```bash
    fdfs_storage /etc/fdfs/storage.conf start
    ```
3. Nginx
    ```bash
    service nginx start
    ```
    Or
    ```bash
    path/to/your/nginx -s start
    ```
## Stop service
1. Tracker
    ```bash
    killall fdfs_trackered
    ```
2. Storage
    ```bash
    killall fdfs_storage
    ```
3. Nginx
    ```bash
    killall nginx
    ```
