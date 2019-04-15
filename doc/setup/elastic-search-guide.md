# Elasticsearch Installation Guide

## Install
### Install Elasticsearch
1. Go to [Elasticsearch download page](https://www.elastic.co/downloads/elasticsearch) and download 
[Elasticsearch **6.7.1**](https://www.elastic.co/downloads/past-releases/elasticsearch-6-7-1#) 
according to your operating system. The current version is 7.0.0, however, it has some problem with maven dependency of 
spring data.   
2. Run `bin/elasticsearch` (or `bin\elasticsearch.bat` on Windows)

You can also follow the guide in the above web page.  
After install, you may verify your install by
```bash
curl 'http://localhost:9200/?pretty'
```
Or through browser by [elasticsearch welcome page](http://localhost:9200/?pretty)
### Install Kibana (Optional)
Kibana is an open source analytics and visualization platform designed to work with Elasticsearch. You use Kibana to 
search, view, and interact with data stored in Elasticsearch indices. You can easily perform advanced data analysis and
visualize your data in a variety of charts, tables, and maps. (from official website)  
For this reason, we strongly recommend you to install kibina. The installation guide can be found 
[here](https://www.elastic.co/guide/en/kibana/current/install.html).  

## Configuration
You can change the configuration of elasticsearch by modifying `config/elasticsearch.yml` of the elasticsearch install
root. By the comment in the configuration file, you should be above to change the settings. You may want to refer to 
[official configuration tutorial](https://www.elastic.co/guide/en/elasticsearch/reference/2.3/setup-configuration.html)
for more detailed explain.    
Refer to this [sample](../../config/elasticsearch.yml), you can fast configure the elasticsearch by changing:
```yaml
...
# Use a descriptive name for your cluster:
#
cluster.name: seaso
...
# Use a descriptive name for the node:
#
node.name: node-1
...
# Set the bind address to a specific IP (IPv4 or IPv6):
#
network.host: 0.0.0.0
#
# Set a custom port for HTTP:
#
http.port: 9200
...
# Bootstrap the cluster using an initial set of master-eligible nodes:
#
cluster.initial_master_nodes: ["node-1"]
...
```
## Run
Now restart elasticsearch and re-execute the start script (`bin/elasticsearch` or `bin/elasticsearch.bat`), go to the 
specific url according to your configured network host and http port for verification.
A url would be like this:
```
http://<your network host name/ip address>:<http port>/?pretty
```
