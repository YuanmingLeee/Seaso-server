# Seaso-server
Seaso spring server

## Install
### Install database
If you are under NTU LAN, you do not need to change the setting in `application.yml`. 
Otherwise, please install MySQL from the official [website](https://dev.mysql.com/downloads/mysql/) with your OS version.  

Then, you shall change the setting in `application.yml`:
```
datasource:
    url: jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    username: root    # this is your db username
    password: password # this is your db password
    driver-class-name: com.mysql.cj.jdbc.Driver
```
### Install FastDFS
[FastDFS](https://github.com/happyfish100/fastdfs/) is an open source high performance distributed file system (DFS). 
It's major functions include: file storing, file syncing and file accessing, and design for high capacity and 
load balance.  

FastDFS consists of two parts: **tracker** and **storage**(with nginx). You may choose to deploy tracker and storage in
different servers.  

Aside: How to remember FastDFS?   
Fast Discrete Fourier Series! XD   
#### Unix User
For FastDFS to function, you are required to install: tracker, storage, and fastdfs-nginx-module. 
[Here](https://blog.csdn.net/xcg132566/article/details/79163790) is a Chinese tutorial on how to configure FastDFS.  

##### 1. General Setup
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
##### 2. For Storage node
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

We need to reconfigure nignx:
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
##### 3. For Tracker node
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

#### Window User
Unfortunately, FastDFS does not have a installable windows version. You may want to install a Cygwin virtual 
environment or install an [Ubuntu virtual machine](https://www.microsoft.com/store/productId/9NBLGGH4MSV6) which is 
already available for Windows 10. 

### Install Elastic Search
Please see [Installation Guide](doc/elastic-search-guide.md) here.

### Install maven
#### IDE User
If you are using an IDE (e.g. IDEA) which integrates Maven, you can skip this part. Please check your IDE manual for running mvn.

#### Windows User
Go [here](https://maven.apache.org/download.cgi). Install and add it into path. Detailed installation instruction can be
found [here](https://maven.apache.org/guides/getting-started/windows-prerequisites.html).
#### Unix User
run
```
sudo apt-get install mvn
# if that does not work
sudo apt-get install maven
```

After installation, type
```
mvn -version
```
to verify.

### Install Project dependencies
#### IDE User
If you are using an IDE that supported Maven, click mvn->install for installation; Click mvn->clean for a cleanup.

#### Other User
On the very first time of running,
```
mvn install
```
This process will take a few minutes.
When there is another build/update, please remember to cleanup:
```
mvn clean
```
## Run
First, run test to see if the setting is correct:
```
mvn test
```
If all tests pass, you can pack up a jar file
```
mvn package
```
Then run the jar
```
java -jar target/seaso-0.0.1-SNAPSHOT.jar
```
If you are running/testing the project under IDEA, your IDE will automatically detect the entrance.   
On your first run, you have to init database data. Please run `data.sql` under `/db/mysql/` after you startup the server.

## Issues
Here is some issues you may meet during the installation.
### 1. Color console in Windows
With the use of log4j2, we are unable to print out a colorful log to the console in Windows platform. However, unix-like
platforms work fine. It is because DOS environment does not support colors with ANSI escape codes.  
This could be not user-friendly. You may wish to use a plugin [Grep Console](https://plugins.jetbrains.com/plugin/7125-grep-console)
for coloring if you are running this server with IDEA IDE.
