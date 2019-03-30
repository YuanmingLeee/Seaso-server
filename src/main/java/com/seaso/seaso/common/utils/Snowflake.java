package com.seaso.seaso.common.utils;

/**
 * Twitter Snowflake Algorithm for distributed ID generator. These IDs are 64-bit unsigned integers generated based on
 * time rather than being sequential. The full IDs is composed with a timestamp, a worker number, and a sn number.
 * When consuming by API using JSON, it is impossible to always use the id_str instead of id. This is due to the way
 * Javascript and other languages that consume JSON evaluate large integers. If you come across a scenario where it
 * does not appear that id and id_str match, it’s due to your environment having already parsed the id integer munging
 * the number in the process.
 *
 * @author beyond, Li Yuanming
 * @see <a href="https://developer.twitter.com/en/docs/basics/twitter-ids.html">Twitter IDs (snowflake)</a>
 * <p>
 * The implemention is done by Beyond using Java.
 * @see <a href="https://github.com/beyondfengyu/SnowFlake">Snowflake implemented by Beyond</a>
 * </p>
 */
public class Snowflake implements IdGeneratable {
    /**
     * Start timestamp. This value is hard coded to avoid malicious modifications.
     */
    private final static long START_STAMP = 1553832000000L;

    /**
     * Number of bits for serial number(SN)
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     * Number of bits for machine serial number(MSN)
     */
    private final static long MACHINE_BIT = 5;

    /**
     * Number of bits for data center serial number(DSN)
     */
    private final static long DATA_CENTER_BIT = 5;

    /**
     * Maximum of DSN
     */
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

    /**
     * Maximum of MSN
     */
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

    /**
     * Maximum of SN
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * Start position of MSN
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;

    /**
     * Start position of DSN
     */
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    /**
     * Start position of timestamp
     */
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    /**
     * Data center serial number
     */
    private long dsn;

    /**
     * Machine serial number
     */
    private long msn;

    /**
     * Serial number
     */
    private long sn = 0L;

    /**
     * Last timestamp
     */
    private long lastTimestamp = -1L;

    public Snowflake(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0)
            throw new IllegalArgumentException("Data center serial number can't be greater than MAX_DATA_CENTER_NUM " +
                    "or less than 0");

        if (machineId > MAX_MACHINE_NUM || machineId < 0)
            throw new IllegalArgumentException("Machine serial number can't be greater than MAX_MACHINE_NUM or less " +
                    "than 0");

        this.dsn = dataCenterId;
        this.msn = machineId;
    }

    /**
     * Generate next ID
     *
     * @return next ID
     */
    public synchronized long nextId() {
        long currentTimestamp = getNextTimestamp();
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("You clock is behind. Request of generating ID is refused by the server.");
        }

        if (currentTimestamp == lastTimestamp) {
            // self increment SN in the same millisecond
            sn = (sn + 1) & MAX_SEQUENCE;
            // sn used up, overdraft next second
            if (sn == 0L) {
                currentTimestamp = getNextMill();
            }
        } else {
            // different second, reset sn
            sn = 0L;
        }

        lastTimestamp = currentTimestamp;

        return (currentTimestamp - START_STAMP) << TIMESTAMP_LEFT // timestamp
                | dsn << DATA_CENTER_LEFT         // dsn
                | msn << MACHINE_LEFT             // msn
                | sn;                             // sn
    }

    private long getNextMill() {
        long mill = getNextTimestamp();
        while (mill <= lastTimestamp) {
            mill = getNextTimestamp();
        }
        return mill;
    }

    private long getNextTimestamp() {
        return System.currentTimeMillis();
    }
}
