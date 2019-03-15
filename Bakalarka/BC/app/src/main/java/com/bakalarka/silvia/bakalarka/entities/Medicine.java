package com.bakalarka.silvia.bakalarka.entities;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class Medicine {
    
        private String ownerId;
        private String name;
        private java.util.Date created;
        private java.util.Date updated;
        private String objectId;
        public String getOwnerId()
        {
            return ownerId;
        }

        public String getName()
        {
            return name;
        }

        public void setName( String name )
        {
            this.name = name;
        }

        public java.util.Date getCreated()
        {
            return created;
        }

        public java.util.Date getUpdated()
        {
            return updated;
        }

        public String getObjectId()
        {
            return objectId;
        }


        public Medicine save()
        {
            return Backendless.Data.of( Medicine.class ).save( this );
        }

        public void saveAsync( AsyncCallback<Medicine> callback )
        {
            Backendless.Data.of( Medicine.class ).save( this, callback );
        }

        public Long remove()
        {
            return Backendless.Data.of( Medicine.class ).remove( this );
        }

        public void removeAsync( AsyncCallback<Long> callback )
        {
            Backendless.Data.of( Medicine.class ).remove( this, callback );
        }

        public static Medicine findById(String id )
        {
            return Backendless.Data.of( Medicine.class ).findById( id );
        }

        public static void findByIdAsync( String id, AsyncCallback<Medicine> callback )
        {
            Backendless.Data.of( Medicine.class ).findById( id, callback );
        }

        public static Medicine findFirst()
        {
            return Backendless.Data.of( Medicine.class ).findFirst();
        }

        public static void findFirstAsync( AsyncCallback<Medicine> callback )
        {
            Backendless.Data.of( Medicine.class ).findFirst( callback );
        }

        public static Medicine findLast()
        {
            return Backendless.Data.of( Medicine.class ).findLast();
        }

        public static void findLastAsync( AsyncCallback<Medicine> callback )
        {
            Backendless.Data.of( Medicine.class ).findLast( callback );
        }

        public static List<Medicine> find(DataQueryBuilder queryBuilder )
        {
            return Backendless.Data.of( Medicine.class ).find( queryBuilder );
        }

        public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Medicine>> callback )
        {
            Backendless.Data.of( Medicine.class ).find( queryBuilder, callback );
        }
    
}
