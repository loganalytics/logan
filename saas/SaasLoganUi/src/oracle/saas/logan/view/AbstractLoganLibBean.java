package oracle.saas.logan.view;

import java.io.InputStream;

import java.util.List;

import java.util.ResourceBundle;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputFile;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.saas.logan.util.LoganLibUiUtil;
import org.apache.myfaces.trinidad.model.UploadedFile;

public abstract class AbstractLoganLibBean {

    protected static final String match_ALL = "ALL";
    protected static final String match_ANY = "ANY";

    protected String matchRadioSelectValue = match_ALL;
    protected String selectedtargetType;

    protected List<SelectItem> targetTypesList;

    protected InputStream importFileStream = null;
    protected UploadedFile importFile = null; // import
    protected RichInputFile inputFile;
    protected boolean importOverwrite = false;
    protected String hideScriptName = null;


    protected ResourceBundle rb;

    public void setMatchRadioSelectValue(String matchRadioSelectValue) {
        this.matchRadioSelectValue = matchRadioSelectValue;
    }

    public String getMatchRadioSelectValue() {
        return matchRadioSelectValue;
    }
    
    public String getSelectedtargetType() {
        return selectedtargetType;
    }

    public void setSelectedtargetType(String t) {
        selectedtargetType = t;
    }


    public List<SelectItem> getTargetTypesList() {
        //cache the select list
        if (targetTypesList == null) {
            targetTypesList = LoganLibUiUtil.getTargetTypesList();
        }
        return targetTypesList;
    }

    public void matchRadioValueChanged(ValueChangeEvent valueChangeEvent) {
        String mrnv = (String) valueChangeEvent.getNewValue();
        if (mrnv != null && mrnv.trim().length() > 0) {
            matchRadioSelectValue = mrnv;
        }
    }

    public void targetTypeValueChanged(ValueChangeEvent valueChangeEvent) {
        this.selectedtargetType = (String) valueChangeEvent.getNewValue();
    }

    /**
     * Handle the Search Button click on the respective search panel
     * @param ae
     */
    public abstract void handleSearch(ActionEvent ae);

    /**
     * Get the handle to the Main data table on the tab
     * @return
     */
    public abstract RichTable getTabDataTableHandle();

    /**
     * Get the handle to the Search panel Input Text field for the Name. eg. Source / Rule / Parser
     * @return
     */
    public abstract RichInputText getSearchNameInputText();

    /**
     * Get the handle to the Search panel Input Text field for the Description.
     * @return
     */
    public abstract RichInputText getSearchDescInputText();

    /**
     * @return
     */

    public boolean isCreateLikeDisabled() {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedMultipleOrZeroRows(getTabDataTableHandle());
    }

    /**
     * @return
     */
    public boolean isShowDetailsDisabled() {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedMultipleOrZeroRows(getTabDataTableHandle());
    }

    /**
     * @return
     */
    public boolean isEditDisabled() {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedMultipleOrZeroRows(getTabDataTableHandle());
    }

    /**
     * @return
     */
    public boolean isDeleteDisabled() {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedZeroRows(getTabDataTableHandle());
    }

    public void navToTrainWiz(ActionEvent actionEvent) {
        // TODO: enable later
        //TrainNavigationUtil.storeCurrentURL();
    }

    public void setImportFile(UploadedFile importFile) {
        this.importFile = importFile;
    }

    public UploadedFile getImportFile() {
        return importFile;
    }

    public void setImportOverwrite(boolean importOverwrite) {
        this.importOverwrite = importOverwrite;
    }

    public boolean isImportOverwrite() {
        return importOverwrite;
    }

    public void setInputFile(RichInputFile inputFile) {
        this.inputFile = inputFile;
    }

    public RichInputFile getInputFile() {
        return inputFile;
    }

    public void setImportFileStream(InputStream importFileStream) {
        this.importFileStream = importFileStream;
    }

    public InputStream getImportFileStream() {
        return importFileStream;
    }

    public void resetImportDLG(PopupFetchEvent popupFetchEvent) {
        if (inputFile != null) {
            inputFile.resetValue();
            importFile = null;
            importFileStream = null;
        }
    }


}
