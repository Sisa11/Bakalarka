package com.davkovania.system.silvia.systemdavkovania.Database;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class Times
{
  private java.util.Date time;
    private String ownerId;
    private String objectId;
    private java.util.Date updated;
    private java.util.Date created;
    private String idCurrentMedicine;
    private String timeOfNotification;
  public java.util.Date getTime()
  {
    return time;
  }

  public void setTime( java.util.Date time )
  {
    this.time = time;
  }

    public String getOwnerId()
    {
        return ownerId;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public java.util.Date getUpdated()
    {
        return updated;
    }

    public java.util.Date getCreated()
    {
        return created;
    }

    public String getIdCurrentMedicine()
    {
        return idCurrentMedicine;
    }

    public void setIdCurrentMedicine( String idCurrentMedicine )
    {
        this.idCurrentMedicine = idCurrentMedicine;
    }

    public String getTimeOfNotification()
    {
        return timeOfNotification;
    }

    public void setTimeOfNotification( String timeOfNotification )
    {
        this.timeOfNotification = timeOfNotification;
    }


    public Times save()
    {
        return Backendless.Data.of( Times.class ).save( this );
    }

    public void saveAsync( AsyncCallback<Times> callback )
    {
        Backendless.Data.of( Times.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Times.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Times.class ).remove( this, callback );
    }

    public static Times findById( String id )
    {
        return Backendless.Data.of( Times.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<Times> callback )
    {
        Backendless.Data.of( Times.class ).findById( id, callback );
    }

    public static Times findFirst()
    {
        return Backendless.Data.of( Times.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<Times> callback )
    {
        Backendless.Data.of( Times.class ).findFirst( callback );
    }

    public static Times findLast()
    {
        return Backendless.Data.of( Times.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<Times> callback )
    {
        Backendless.Data.of( Times.class ).findLast( callback );
    }

    public static List<Times> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( Times.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Times>> callback )
    {
        Backendless.Data.of( Times.class ).find( queryBuilder, callback );
    }
}