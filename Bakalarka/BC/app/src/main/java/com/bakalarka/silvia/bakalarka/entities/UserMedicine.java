package com.bakalarka.silvia.bakalarka.entities;

import java.util.List;


import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;



public class UserMedicine {

        private java.util.Date updated;
        private String user_id;
        private java.util.Date created;
        private Boolean active;
        private String ownerId;
        private String medicine_id;
        private String objectId;
        public java.util.Date getUpdated()
        {
            return updated;
        }

        public String getUser_id()
        {
            return user_id;
        }

        public void setUser_id( String user_id )
        {
            this.user_id = user_id;
        }

        public java.util.Date getCreated()
        {
            return created;
        }

        public Boolean getActive()
        {
            return active;
        }

        public void setActive( Boolean active )
        {
            this.active = active;
        }

        public String getOwnerId()
        {
            return ownerId;
        }

        public String getMedicine_id()
        {
            return medicine_id;
        }

        public void setMedicine_id( String medicine_id )
        {
            this.medicine_id = medicine_id;
        }

        public String getObjectId()
        {
            return objectId;
        }


        public UserMedicine save()
        {
            return Backendless.Data.of( UserMedicine.class ).save( this );
        }

        public void saveAsync( AsyncCallback<UserMedicine> callback )
        {
            Backendless.Data.of( UserMedicine.class ).save( this, callback );
        }

        public Long remove()
        {
            return Backendless.Data.of( UserMedicine.class ).remove( this );
        }

        public void removeAsync( AsyncCallback<Long> callback )
        {
            Backendless.Data.of( UserMedicine.class ).remove( this, callback );
        }

        public static UserMedicine findById(String id )
        {
            return Backendless.Data.of( UserMedicine.class ).findById( id );
        }

        public static void findByIdAsync( String id, AsyncCallback<UserMedicine> callback )
        {
            Backendless.Data.of( UserMedicine.class ).findById( id, callback );
        }

        public static UserMedicine findFirst()
        {
            return Backendless.Data.of( UserMedicine.class ).findFirst();
        }

        public static void findFirstAsync( AsyncCallback<UserMedicine> callback )
        {
            Backendless.Data.of( UserMedicine.class ).findFirst( callback );
        }

        public static UserMedicine findLast()
        {
            return Backendless.Data.of( UserMedicine.class ).findLast();
        }

        public static void findLastAsync( AsyncCallback<UserMedicine> callback )
        {
            Backendless.Data.of( UserMedicine.class ).findLast( callback );
        }

        public static List<UserMedicine> find(DataQueryBuilder queryBuilder )
        {
            return Backendless.Data.of( UserMedicine.class ).find( queryBuilder );
        }

        public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<UserMedicine>> callback )
        {
            Backendless.Data.of( UserMedicine.class ).find( queryBuilder, callback );
        }
    }
