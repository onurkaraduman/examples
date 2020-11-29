package com.onrkrdmn;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class App {
    public static void main(String[] args) {
        String influxdbUsername = System.getenv("INFLUXDB_USERNAME");
        String influxdbPassword = System.getenv("INFLUXDB_PASSWORD");

        InfluxDB influxDB = createInfluxdbConnection(influxdbUsername, influxdbPassword);

        ping(influxDB);

        String databaseName = "metrics-influxdb";
        String retentionPolicy = "defaultPolicy";

        influxDB.createDatabase(databaseName);
        influxDB.createRetentionPolicy(retentionPolicy, databaseName, "30d", 1, true);

        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);

        Point point2 = Point.measurement("memory")
                .time(System.currentTimeMillis() - 100, TimeUnit.MILLISECONDS)
                .addField("name", "server1")
                .addField("free", 4743696L)
                .addField("used", 1016096L)
                .addField("buffer", 1008467L)
                .build();

        influxDB.write(databaseName, retentionPolicy, point2);

        printPoints(influxDB, "Select * from memory", databaseName);
    }

    public static InfluxDB createInfluxdbConnection(String username, String password) {
        return InfluxDBFactory.connect("http://localhost:8086", username, password);
    }

    public static void ping(InfluxDB influxDB) {
        Pong response = influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            System.out.println("Error pinging server.");
            return;
        }
    }

    private static void printPoints(InfluxDB connection, String query, String databaseName) {

        // Run the query
        Query queryObject = new Query(query, databaseName);
        QueryResult queryResult = connection.query(queryObject);

        // Map it
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<MemoryPoint> memoryPoints = resultMapper.toPOJO(queryResult, MemoryPoint.class);
        System.out.println(memoryPoints);
    }
}
