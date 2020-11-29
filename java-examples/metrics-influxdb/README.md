
## Install and run Grafana, Influxdb and Coronograf via Docker Compose
set the following env variables first
```
export INFLUXDB_USERNAME=
export INFLUXDB_PASSWORD=

export GRAFANA_USERNAME=
export GRAFANA_PASSWORD=
```

Then run the docker compose
```
cd config/
./run-influxdb-grafana-coronograf.sh
```

## References
https://www.baeldung.com/java-influxdb