package oracle.saas.logan.view;

import java.util.List;

public interface IPersistDataOpsHandler
{
    /**
     * Handle the Insert operation here.
     * The VO reference will be used to create
     * the <li>oracle.jbo.Row</li> represented by this object and <p>
     *   <pre>
     *    NameValuePairs nvpa = new NameValuePairs();
     *    nvpa.setAttribute("Name", m_name); // Key col 1
     *    nvpa.setAttribute("Type", m_type); // Key col 2
     *    ...
     *    nvpa.setAttribute("AttrCol", m_a1); // Attr col 1
     *    ...
     *    Row iRow = voRef.createAndInitRow(nvpa);
     *    voRef.insertRow(iRow);
     *   </pre>
     * will be called here. <br><br>
     * Parent reference attributes needed to create
     * the VO row would already have been set via a call to method <p>
     *   <pre>
     *   setParentReferenceKeys()
     *   </pre>
     * before this method is called.
     * Note: the setParentReferenceKeys() call is not needed if this bean
     * has no FKs dependencied on a parent table.
     */
    public void handleInsertOperation();

    /**
     * Handle the Update operation here.
     * The VO reference will be used to search
     * for the row represented by this object. This would typically
     * be done by creating a Key object using the values for the Key Columns
     * of the VO represented by this bean. Ex.<p>
     * <pre>
     *   Object values[] = { name, type, key3, key4 };
     *   Key key = new Key(values);
     *   Row[] uRows = voRef.findByKey(key, 1);
     * </pre>
     * and then ,
     * <pre>
     *   voRow.setAttribute()
     * </pre>
     * should be called here to update the new values from this object.
     * <br>
     * Once the new values are set for the attributes, whenever<p>
     * <pre>  DBTransaction.postChanges() and commit() </pre><br>
     * get called, the ADF-BC framework will take care of firing
     * the correct update SQLs
     */
    public void handleUpdateOperation();

    /**
     * Handle the delete operation here.
     * The VO reference will be used to search
     * for the row represented by this object and voRow.remove() will be called.
     * Ex.<p>
     * <pre>
     *   Object values[] = { name, type, key3, key4 };
     *   Key key = new Key(values);
     *   Row[] dRows = voRef.findByKey(key, 1);
     * </pre>
     * and then ,
     * <pre>
     *   dRow.remove();
     * </pre>
     * should be called here to mark this object for deletion.
     * <br>
     * After this, whenever<p>
     * <pre>  DBTransaction.postChanges() and commit() </pre><br>
     * get called, the ADF-BC framework will take care of firing
     * the correct delete SQLs and commit. 
     */
    public void handleDeleteOperation();

    /**
     * An array of Key Column Name Strings
     * @return
     */
    public String[] getParentReferenceKeyNames();

    /**
     * If this is a child object of the parent, following method
     * will be called to set the parent reference keys so that
     * handle insert has the right FK reference values.
     * @param rks
     */
    public void setParentReferenceKeys(List<KeyValuePairObj> rks);

    /**
     * return true if the Primary Key columns of this object have the same value.
     * <p>
     * This method is used to determin if the object's Key have been changed
     * which should result in a delete and an insert.<br>
     * or<br>
     * if the Keys are the same for the 
     * <i>"this"</i> object and the <i>"that"</i> object, then it means
     * only the non-key attributes might have changed, and should result in an
     * update operation.
     * 
     * @param that
     * @return true if the PKs of this and that match else false
     */
    public boolean hasMatchingPrimaryKey(IPersistDataOpsHandler that);

    /**
     * A String representation of the Key column values
     * @return primary key of this object as a string
     */
    public String getPrimaryKeyAsString();

    /**
     * If this IPersistDataOpsHandler object is a container for child
     * IPersistDataOpsHandler ojects return true otherwise false
     * 
     * @return boolean
     */
    public boolean hasDependentChildObjects();

    /**
     * If this IPersistDataOpsHandler object is a container for child
     * IPersistDataOpsHandler ojects return a list of those objects
     * 
     * @return List of IPersistDataOpsHandler objects
     */
    public List<IPersistDataOpsHandler> getDependentChildObjsList();

    /**
     * Add an IPersistDataOpsHandler object to a parent so
     * that when the parent is persisted the fwk can set the parent FK in
     * all the child objects that are added as children of that parent.
     * Only for top level parent this method would be a NO-OP
     * 
     * @return
     */
    public void addDependentChildObject(IPersistDataOpsHandler child);

    /**
     * Remove a dependent IPersistDataOpsHandler object when user
     * removes it on the wizard UI so that fwk does not unnecessarily
     * try to update parent Keys to removed objects
     * 
     * @return
     */
    public void removeDependentChildObject(IPersistDataOpsHandler child);

    /**
     * Return a List of KeyValuePairObj which can be passed
     * directly to any dependent child objects so that they can set their 
     * reference values for the parent. We will call setParentReferenceKeys
     * to set the List of KeyValuePairObj to each child bject returned in
     * getDependentChildObjsList()
     * 
     * @return List of KeyValuePairObj
     */
    public List<KeyValuePairObj> getReferenceKeysForDependents();
}
