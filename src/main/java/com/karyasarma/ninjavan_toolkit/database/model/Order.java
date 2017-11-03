package com.karyasarma.ninjavan_toolkit.database.model;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
public class Order
{
    private int id;
    private String trackingId;
    private String status;
    private String granularStatus;

    public Order()
    {
    }

    public Order(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTrackingId()
    {
        return trackingId;
    }

    public void setTrackingId(String trackingId)
    {
        this.trackingId = trackingId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getGranularStatus()
    {
        return granularStatus;
    }

    public void setGranularStatus(String granularStatus)
    {
        this.granularStatus = granularStatus;
    }

    @Override
    public String toString()
    {
        return "Order{" +
                "id=" + id +
                ", trackingId='" + trackingId + '\'' +
                ", status='" + status + '\'' +
                ", granularStatus='" + granularStatus + '\'' +
                '}';
    }
}
