package com.davkovania.system.silvia.systemdavkovania.Database;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.DataQueryBuilder;

import java.io.Serializable;
import java.util.List;

public class CurrentMedicine implements Serializable
{
    private java.util.Date created;
    private java.util.Date updated;
    private String userID;
    private String objectId;
    private Double serialVersionUID;
    private Integer numberOfPackages;
    private String medicineID;
    private Integer addingPills;
    private Boolean active;
    private java.util.Date dateOfStart;
    private String ownerId;
  private Medicine medicine;
  private java.util.List<Times> times;
    public java.util.Date getCreated()
    {
        return created;
    }

    public java.util.Date getUpdated()
    {
        return updated;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID( String userID )
    {
        this.userID = userID;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public Double getSerialVersionUID()
    {
        return serialVersionUID;
    }

    public void setSerialVersionUID( Double serialVersionUID )
    {
        this.serialVersionUID = serialVersionUID;
    }

    public Integer getNumberOfPackages()
    {
        return numberOfPackages;
    }

    public void setNumberOfPackages( Integer numberOfPackages )
    {
        this.numberOfPackages = numberOfPackages;
    }

    public String getMedicineID()
    {
        return medicineID;
    }

    public void setMedicineID( String medicineID )
    {
        this.medicineID = medicineID;
    }

    public Integer getAddingPills()
    {
        return addingPills;
    }

    public void setAddingPills( Integer addingPills )
    {
        this.addingPills = addingPills;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive( Boolean active )
    {
        this.active = active;
    }

    public java.util.Date getDateOfStart()
    {
        return dateOfStart;
    }

    public void setDateOfStart( java.util.Date dateOfStart )
    {
        this.dateOfStart = dateOfStart;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

  public Medicine getMedicine()
  {
    return medicine;
  }

  public void setMedicine( Medicine medicine )
  {
    this.medicine = medicine;
  }

  public java.util.List<Times> getTimes()
  {
    return times;
  }

  public void setTimes( java.util.List<Times> times )
  {
    this.times = times;
  }


    public CurrentMedicine save()
    {
        return Backendless.Data.of( CurrentMedicine.class ).save( this );
    }

    public void saveAsync( AsyncCallback<CurrentMedicine> callback )
    {
        Backendless.Data.of( CurrentMedicine.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( CurrentMedicine.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( CurrentMedicine.class ).remove( this, callback );
    }

    public static CurrentMedicine findById( String id )
    {
        return Backendless.Data.of( CurrentMedicine.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<CurrentMedicine> callback )
    {
        Backendless.Data.of( CurrentMedicine.class ).findById( id, callback );
    }

    public static CurrentMedicine findFirst()
    {
        return Backendless.Data.of( CurrentMedicine.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<CurrentMedicine> callback )
    {
        Backendless.Data.of( CurrentMedicine.class ).findFirst( callback );
    }

    public static CurrentMedicine findLast()
    {
        return Backendless.Data.of( CurrentMedicine.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<CurrentMedicine> callback )
    {
        Backendless.Data.of( CurrentMedicine.class ).findLast( callback );
    }

    public static List<CurrentMedicine> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( CurrentMedicine.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<CurrentMedicine>> callback )
    {
        Backendless.Data.of( CurrentMedicine.class ).find( queryBuilder, callback );
    }
}