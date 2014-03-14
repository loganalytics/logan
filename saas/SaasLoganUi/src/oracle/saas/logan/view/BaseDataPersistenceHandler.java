package oracle.saas.logan.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class BaseDataPersistenceHandler
    implements IPersistDataOpsHandler
{
    protected List<IPersistDataOpsHandler> dependentObjects = 
        new ArrayList<IPersistDataOpsHandler>();

    public BaseDataPersistenceHandler()
    {
        super();
    }

    public String[] getParentReferenceKeyNames()
    {
        return new String[0];
    }

    public boolean hasMatchingPrimaryKey(IPersistDataOpsHandler that)
    {
        if (that == null)
            return false;

        return (this.getPrimaryKeyAsString().equals(that.getPrimaryKeyAsString()));
    }

    public boolean hasDependentChildObjects()
    {
        return (this.dependentObjects != null && this.dependentObjects.size() > 0);
    }

    public List<IPersistDataOpsHandler> getDependentChildObjsList()
    {
        return dependentObjects;
    }

    /**
     * Add an IPersistDataOpsHandler object to a parent so
     * that when the parent is persisted the fwk can set the parent FK in
     * all the child objects that are added as children of that parent.
     * Only for top level parent this method would be a NO-OP
     * 
     * @return
     */
    public void addDependentChildObject(IPersistDataOpsHandler child)
    {
        this.dependentObjects.add(child);
    }
    
    /**
     * Remove a dependent IPersistDataOpsHandler object when user
     * removes it on the wizard UI so that fwk does not unnecessarily
     * try to update parent Keys to removed objects
     * 
     * @return
     */
    public void removeDependentChildObject(IPersistDataOpsHandler child)
    {
        if(this.dependentObjects != null)
        {
            this.dependentObjects.remove(child);
        }
    }

    /**
     * Default implementation returns an empty list so that leaf level
     * objects in the hierarchy do not need to implement this
     * 
     * @return
     */
    public List<KeyValuePairObj> getReferenceKeysForDependents()
    {
        return Collections.emptyList();
    }

    public boolean equals(Object o)
    {
        if (o == null || !(isObjInstanceOfSameClass(o)))
            return false;
        BaseDataPersistenceHandler that = (BaseDataPersistenceHandler) o;
        String upPropsOfThat = that.getEditablePropertiesAsString();
        return this.getEditablePropertiesAsString().equals(upPropsOfThat);
    }

    public int hashCode()
    {
        return getEditablePropertiesAsString().hashCode();
    }

    /**
     * All properties of the object that are exposed on the Ui and
     * can be updated by the user should be used to construct this
     * representative string here.
     * This will be used to check if the user has edited any of the properties
     * @return object represeting the updatable properties as String
     */
    public abstract String getEditablePropertiesAsString();
    
    /**
     * Extending concrete classes expected to check instanceof and return
     * true false based on that
     * @return object represeting the updatable properties as String
     */
    public abstract boolean isObjInstanceOfSameClass(Object o);
}
