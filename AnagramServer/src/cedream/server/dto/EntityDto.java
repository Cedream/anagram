package cedream.server.dto;

public abstract class EntityDto<T> {

    protected T id;

    public  boolean isPersistant(){
        return (id!=null);
    }

    public T getId(){
        return id;
    }

    @Override
    public boolean equals(Object dto){
        if ( dto == null || dto.getClass() != getClass()
                         || ((EntityDto)dto).isPersistant() != isPersistant()) {
            return false;
        }
        return ((EntityDto)dto).getId().equals(getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
}
