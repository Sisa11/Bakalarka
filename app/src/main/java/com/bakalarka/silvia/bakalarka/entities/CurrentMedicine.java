package com.bakalarka.silvia.bakalarka.entities;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class CurrentMedicine
{
    private java.util.Date created;
    private java.util.Date updated;
    private String userID;
    private String objectId;
    private Integer numberOfPackages;
    private String medicineID;
    private Integer intervalOfIngestion;
    private Boolean active;
    private java.util.Date dateOfStart;
    private String ownerId;
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

    public Integer getIntervalOfIngestion()
    {
        return intervalOfIngestion;
    }

    public void setIntervalOfIngestion( Integer intervalOfIngestion )
    {
        this.intervalOfIngestion = intervalOfIngestion;
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