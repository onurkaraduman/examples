package com.onrkrdmn;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Measurement(name = "memory")
public class MemoryPoint {

    @Column(name = "time")
    private Instant time;

    @Column(name = "name")
    private String name;

    @Column(name = "free")
    private Long free;

    @Column(name = "used")
    private Long used;

    @Column(name = "buffer")
    private Long buffer;

    @Override
    public String toString() {
        return "MemoryPoint{" +
                "time=" + time +
                ", name='" + name + '\'' +
                ", free=" + free +
                ", used=" + used +
                ", buffer=" + buffer +
                '}';
    }
}
