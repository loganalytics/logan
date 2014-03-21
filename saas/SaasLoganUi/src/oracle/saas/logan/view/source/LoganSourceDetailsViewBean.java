package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.jbo.domain.Number;

import oracle.saas.logan.util.EMExecutionContext;
import oracle.saas.logan.util.InterPageMessageBean;
import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.Logger;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.BaseLoganSourceViewBean;
import oracle.saas.logan.view.ILoganSourceDataBean;
import oracle.saas.logan.view.LaunchContextModeBean;
import oracle.saas.logan.view.TargetPropertyFilterBean;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;


public class LoganSourceDetailsViewBean extends BaseLoganSourceViewBean
{
    private LoganSourceDetailsBean detailsBean;
    private LoganSourceDetailsBean detailsRefBean;
    private LoganSourceExtFieldsBean extFieldsBean;
    private LoganSourceExtFieldsBean extFieldsBeanRef;
    private LoganSourceFieldRefsDataBean fieldsRefDataBean;
    private LoganSourceFieldRefsDataBean fieldsRefDataBeanRef;
    
    private LaunchContextModeBean modeBean;
    
    private String launchMode;
    private String createLikeSourceName;
    private String author;
    private Number sourceId;
    private boolean isInclExtendedFields = false;
    private boolean isInclFieldReferences = false;

    LoganSourceFilePattern currSelInclRow;
    LoganSourceFilePattern currSelExclRow;
    LoganSourceDetailsParameter currSelParamRow;

    private List<SelectItem> detailsValidationErrorMessages;
    private List<SelectItem> srcTypesList;
    private List<SelectItem> srcTgtTypesList;
    private List<SelectItem> parsersList;
    private List<LoganMetaAvailParameters> availParams;

    private static final String SOURCE_NAME_UI_ID = "emT:sbv1:it2";
    private static final String SOURCE_TYPE_UI_ID = "emT:sbv1:soc5";
    private static final String SOURCE_TTYPE_UI_ID = "emT:sbv1:soc3";
    private static final String SOURCE_ERRM_PANEL_UI_ID = "emT:sbv1:pgl8";
    private static final String PARAMS_TABLE_ID = "emT:sbv1:t5";
    private static final String PATT_TABLE_ID = "emT:sbv1:pc11:t11";
    
    private static final String SOURCE_ID = "sourceId";
    private static final String SOURCE_POJO = "sourcePojo";
    private static final String CREATE_LIKE_SOURCE_NAME_CONST =
        "create_like_logan_source_name";
    
    private final int MAX_PARAMLENGTH = 128;

    private String sourceNameOutlineColor = "";
    private String sourceTypeOutlineColor = "";
    private String sourceTTypeOutlineColor = "";
    
    private static final Logger s_log =
        Logger.getLogger(LoganSourceDetailsViewBean.class);
    private RichPopup errmpop;
    private RichPopup contpop;

    
    public LoganSourceDetailsViewBean()
    {
        super();
        Map ipmParams =
            InterPageMessageBean.getCurrentInstance().getParams();
        launchMode = LoganLibUiUtil.getModeForTrain();
        modeBean = new LaunchContextModeBean(launchMode);
        String srId = (String) ipmParams.get(SOURCE_ID);
        if (srId != null)// create_like, Edit or Show Details mode
        {
            sourceId = new Number(Integer.parseInt(srId));
            /*
            * For that we are now using the JPA-Pojo model without ADF databindings , 
            * so we can't call VO current way now
            Row contextRow = LoganLibUiUtil.getCurrentLogSourceRow(sourceId);
            */
            LoganLibSourcePojo sourcePojo = (LoganLibSourcePojo) ipmParams.get(SOURCE_POJO);
            try
            {
                detailsBean = new LoganSourceDetailsBean(sourcePojo);
                
                if(modeBean.isCreateLikeMode()){
                    extFieldsBean = instantiateExtFiledsBean();
                    extFieldsBeanRef =  (LoganSourceExtFieldsBean)extFieldsBean.clone();
                    extFieldsBean.initExtFields(sourceId, modeBean);
                    extFieldsBean.initModeSpecificData(modeBean);
                    
                    fieldsRefDataBean = instantiateFieldRefsDataBean();
                    fieldsRefDataBeanRef =  (LoganSourceFieldRefsDataBean)fieldsRefDataBean.clone(); 
                    fieldsRefDataBean.initFieldRefs(sourceId, modeBean);
                    fieldsRefDataBean.initModeSpecificData(modeBean);
                }
            }
            catch(IllegalArgumentException ew)
            {
                if(s_log.isFineEnabled())
                    s_log.fine("Error init:: LoganSourceDetailsBean :: Failed with Error: ", ew);
            }
            detailsRefBean = (LoganSourceDetailsBean)detailsBean.clone();
            if(detailsBean.isIsSystemDefinedSource() && !modeBean.isCreateLikeMode())
                modeBean.setModeToReadOnly();
        }
        else // create mode
        {
            detailsBean = instantiateLoganSourceDetailsBean();;
            detailsRefBean = detailsBean; 
        }
        if(modeBean.isCreateLikeMode())
        {
            createLikeSourceName = (String) ipmParams.get(CREATE_LIKE_SOURCE_NAME_CONST);
            detailsBean.setName(createLikeSourceName);
        }
        author = EMExecutionContext.getExecutionContext().getEMUser();
        if(!modeBean.isReadEditMode() && detailsBean != null)
        {
            detailsBean.setAuthor(author);
        }
        initLoganMetaAvailParams();
        if(s_log.isFinestEnabled())
            s_log.finest("inited LoganSourceDetailsViewBean");
        
    }

    public LoganSourceFieldRefsDataBean instantiateFieldRefsDataBean()
    {
        return new LoganSourceFieldRefsDataBean();
    }
    
    public LoganSourceExtFieldsBean instantiateExtFiledsBean()
    {
        return new LoganSourceExtFieldsBean();
    }
    
    public LoganSourceDetailsBean instantiateLoganSourceDetailsBean()
    {
        return new LoganSourceDetailsBean();
    }

    public void setDetailsBean(LoganSourceDetailsBean detailsBean)
    {
        this.detailsBean = detailsBean;
    }

    public LoganSourceDetailsBean getDetailsBean()
    {
        return detailsBean;
    }

    public void setParameters(List<LoganSourceDetailsParameter> p)
    {
        this.detailsBean.setParameters(p);
    }
    
    // only display the active parameters
    public List<LoganSourceDetailsParameter> getActiveParameters()
    {
        List<LoganSourceDetailsParameter> activeparams = new ArrayList<LoganSourceDetailsParameter>();
        for (LoganSourceDetailsParameter p : this.detailsBean.getParameters())
        {
            if (p.isParamActive())
            {
                activeparams.add(p);
            }
        }
        return activeparams;
    }

    public List<LoganSourceDetailsParameter> getParameters()
    {
        return this.detailsBean.getParameters();
    }

    public boolean isNoParameters()
    {
        return this.detailsBean.isNoParameters();
    }

    public void setInclFilePatterns(List<LoganSourceFilePattern> p)
    {
        this.detailsBean.setInclFilePatterns(p);
    }

    public List<LoganSourceFilePattern> getInclFilePatterns()
    {
        return this.detailsBean.getInclFilePatterns();
    }

    public void setExclFilePatterns(List<LoganSourceFilePattern> p)
    {
        this.detailsBean.setExclFilePatterns(p);
    }

    public List<LoganSourceFilePattern> getExclFilePatterns()
    {
        return this.detailsBean.getExclFilePatterns();
    }

    public boolean isNoExclPatterns()
    {
        return this.detailsBean.isNoExclPatterns();
    }

    public boolean isNoInclPatterns()
    {
        return this.detailsBean.isNoInclPatterns();
    }

    public void setTargetPropertyFilterBean(TargetPropertyFilterBean b)
    {
        this.detailsBean.setTargetPropertyFilterBean(b);
    }

    public TargetPropertyFilterBean getTargetPropertyFilterBean()
    {
        return this.detailsBean.getTargetPropertyFilterBean();
    }

    public String getTPFilterOs()
    {
        return getTargetPropertyFilterBean().getOs();
    }

    public String getTPFilterLcs()
    {
        return getTargetPropertyFilterBean().getLifecycleState();
    }

    public String getTPFilterVer()
    {
        return getTargetPropertyFilterBean().getVersion();
    }

    public String getTPFilterPlat()
    {
        return getTargetPropertyFilterBean().getPlatform();
    }

    public void setSourceId(Number sourceId)
    {
        this.detailsBean.setSourceId(sourceId);
    }

    public Number getSourceId()
    {
        return this.detailsBean.getSourceId();
    }

    public LoganSourceDetailsMain getMainDetails()
    {
        return this.detailsBean.getMainDetails();
    }

    public void setName(String name)
    {
        this.detailsBean.setName(name);
    }

    public String getName()
    {
        return this.detailsBean.getName();
    }

    public void setAuthor(String author)
    {
        this.detailsBean.setAuthor(author);
    }

    public boolean isIsSystemDefinedSource()
    {
        return this.detailsBean.isIsSystemDefinedSource();
    }

    public String getAuthor()
    {
        return this.detailsBean.getAuthor();
    }

    public void setSourceTypeDisplay(String type)
    {
        this.detailsBean.setSourceTypeDisplay(type);
    }

    public String getSourceTypeDisplay()
    {
        return this.detailsBean.getSourceTypeDisplay();
    }
    
    public String getSourceTargetTypeDisplay()
    {
        
        //TODO NLS
        return getTargetType();
//        return TargetMetricUtil.getLocalizedTargetTypeLabel(getTargetType());
    }

    public void setSourceType(String type)
    {
        this.detailsBean.setSourceType(type);
    }

    public String getSourceType()
    {
        return this.detailsBean.getSourceType();
    }

    public void setTargetType(String targetType)
    {
        this.detailsBean.setTargetType(targetType);
    }

    public String getTargetType()
    {
        return this.detailsBean.getTargetType();
    }

    public void setDescription(String description)
    {
        this.detailsBean.setDescription(description);
    }

    public String getDescription()
    {
        return this.detailsBean.getDescription();
    }

    public void setIsSecureContent(boolean s)
    {
        this.detailsBean.setIsSecureContent(s);
    }

    public boolean getIsSecureContent()
    {
        return this.detailsBean.getIsSecureContent();
    }

    public void targetTypeValueChanged(ValueChangeEvent valueChangeEvent)
    {
        String newValue = (String)valueChangeEvent.getNewValue();
        this.setTargetType(newValue);
        initLoganMetaAvailParams();
    }

    public void parserChanged(ValueChangeEvent valueChangeEvent)
    {
        String selectedValue = (String) valueChangeEvent.getNewValue();
        if (currSelInclRow != null)
        {
            currSelInclRow.setFileParserIName(selectedValue);
        }
    }

    /**
     * add blank row excluded pattern 
     * @param actionEvent
     */
    public void addExcludePattern(ActionEvent actionEvent)
    {
        LoganSourceFilePattern nw = new LoganSourceFilePattern(null);
        nw.setIncludePattern(false);
        nw.setSourceTargetType(getTargetType());
        if (this.getSourceId() != null)
        {
            nw = new LoganSourceFilePattern(null, this.getSourceId(),
                        this.getAuthor(), null, null, null, null, false, getTargetType());
        }
        List<LoganSourceFilePattern> p =
            this.detailsBean.getExclFilePatterns();
        if (p == null)
        {
            p = new ArrayList<LoganSourceFilePattern>();
            this.detailsBean.setExclFilePatterns(p);
        }
        p.add(nw);
    }

    private void removeExclParams()
    {
        // set params inactive then remove pattern
        Set<String> ss = currSelExclRow.getParamsInPattern();
        for (String s : ss)
        {
            LoganSourceDetailsParameter param = this.findParam(s);
            param.handleRemove();
        }
    }

    /**
     * remove selected row excluded pattern
     * @param actionEvent
     */
    public void removeExcludePattern(ActionEvent actionEvent)
    {
        if (currSelExclRow != null)
        {
            List<LoganSourceFilePattern> ep =
                this.detailsBean.getExclFilePatterns();
            if (ep != null && ep.size() > 0)
            {
                removeExclParams();
                ep.remove(currSelExclRow);

            }
        }
    }

    /**
     * add blank row included pattern 
     * @param actionEvent
     */
    public void addIncludePattern(ActionEvent actionEvent)
    {
        LoganSourceFilePattern nw = new LoganSourceFilePattern(null);
        nw.setSourceTargetType(getTargetType());
        if (this.getSourceId() != null)
        {
            nw = new LoganSourceFilePattern(null, this.getSourceId(), 
                                            this.getAuthor(),null, null, 
                                            null, null, true, getTargetType());
        }
        List<LoganSourceFilePattern> p =
            this.detailsBean.getInclFilePatterns();
        if (p == null)
        {
            p = new ArrayList<LoganSourceFilePattern>();
            this.detailsBean.setInclFilePatterns(p);
        }
        p.add(nw);
    }


    private void removeInclParams()
    {
        // set params inactive then remove pattern
        Set<String> ss = currSelInclRow.getParamsInPattern();
        for (String s : ss)
        {
            LoganSourceDetailsParameter param = this.findParam(s);
            param.handleRemove();
        }
    }

    /**
     * remove selected row included pattern
     * @param actionEvent
     */
    public void removeIncludePattern(ActionEvent actionEvent)
    {
        if (currSelInclRow != null)
        {
            List<LoganSourceFilePattern> ip =
                this.detailsBean.getInclFilePatterns();
            if (ip != null && ip.size() > 0)
            {
                // set params inactive then remove pattern
                removeInclParams();
                ip.remove(currSelInclRow);
           }
        }
    }


    /**
     * lookup param to see if it exists in params table
     * @param paramName
     * @return
     */
    private LoganSourceDetailsParameter findParam(String paramName)
    {       
        for (LoganSourceDetailsParameter p : this.detailsBean.getParameters())
        {
            // dont add the param if it exist or is one of the built in params
            if (p != null && (p.getParamName().equals(paramName)))
            {
                return p;
            }
        }
        return (null);
    }

    /**
     * add new active param
     * @param paramName
     * @return
     */
    private boolean addParam(String paramName, String pattern)
    {
        boolean padded = false;
        if(builtInParam(paramName))
            return padded;
        LoganSourceDetailsParameter p = findParam(paramName);
        // if not found add it
        if (p == null)
        {
            LoganSourceDetailsParameter nw =
               new LoganSourceDetailsParameter(null);
            if (this.getSourceId() != null)
            {
               nw =
                   new LoganSourceDetailsParameter(this.getSourceId(), paramName, null, null,
                                                   this.getAuthor(), true);
            }
            List<LoganSourceDetailsParameter> params =
               this.detailsBean.getParameters();
            if (params == null)
            {
               params = new ArrayList<LoganSourceDetailsParameter>();
               this.detailsBean.setParameters(params);
            }
            nw.setParamName(paramName);
            nw.setParamIsActive(new Number(1));
            nw.setParamActive(true);
            params.add(nw);
            padded = true;
        }
        
        // reactivate - is in use
        if (p != null && !p.isParamActive())
        {
            p.setParamIsActive(new Number(1));
            p.setParamActive(true);
        }
        
        return padded;
    }


    public void logParamRowSelected(SelectionEvent selectionEvent)
    {
        RichTable iT = (RichTable) selectionEvent.getComponent();
        this.currSelParamRow =
                (LoganSourceDetailsParameter) LoganLibUiUtil.getSelectedIPRowForTable(iT);
    }

    public void selectedInclPattRow(SelectionEvent selectionEvent)
    {
        this.currSelInclRow = getCurrPatternRowFromUiTable(selectionEvent);
    }

    public void selectedExclPattRow(SelectionEvent selectionEvent)
    {
        this.currSelExclRow = getCurrPatternRowFromUiTable(selectionEvent);
    }

    private LoganSourceFilePattern getCurrPatternRowFromUiTable(SelectionEvent selectionEvent)
    {
        RichTable iT = (RichTable) selectionEvent.getSource();
        return (LoganSourceFilePattern) LoganLibUiUtil.getSelectedIPRowForTable(iT);
    }

    public void sourceTypeValueChanged(ValueChangeEvent valueChangeEvent)
    {
        // Add event code here...
    }

    public void validateLogSourceName(FacesContext facesContext,
                                      UIComponent uIComponent,
                                      Object object)
    {
        String sourceName = (object != null? object.toString(): null);
        String errm = this.detailsBean.validateLogSourceName(sourceName);
        if (errm != null)
        {
            FacesMessage msg = new FacesMessage(errm);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    public void logFilePatternValidator(FacesContext facesContext,
                                        UIComponent uIComponent,
                                        Object object)
    {
        //TODO
//        String pattern = (object != null? object.toString(): null);
//        if (pattern == null)
//               return;
//        
//        boolean isValid = LoganUtil.validateCurlyBracesInExpr(pattern);
//        String errMsg = null;
//        if(!isValid)
//        {
//            errMsg = UiUtil.getUiString("PATTERN_INCORRECT_SYNTAX_ERR");
//            FacesMessage msg = new FacesMessage(errMsg);
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            throw new ValidatorException(msg);
//        }
    }

    public String getDetailErrorsFacetName()
    {
        if (detailsValidationErrorMessages == null ||
            detailsValidationErrorMessages.size() == 0)
            return "N";
        else
            return "E";
    }

    /**
     * Given the LoganSourceDetailsBean validate that it has 
     * all the requrired properties set.
     * @param detailsBean
     * @return List of validation Errors if any else zero sized list
     */
    public static List<SelectItem> validateLogSourceData(LoganSourceDetailsBean detailsBean)
    {
        List<SelectItem> validationErrors = new ArrayList<SelectItem>();
        String sourceName = detailsBean.getName();
        String errm = detailsBean.validateLogSourceName(sourceName);
        if (errm != null)
        {
            validationErrors.add(new SelectItem(SOURCE_NAME_UI_ID, errm));
        }
        errm = detailsBean.validateLogSourceType();
        if (errm != null)
        {
            validationErrors.add(new SelectItem(SOURCE_TYPE_UI_ID, errm));
        }
        errm = detailsBean.validateLogSourceTargetType();
        if (errm != null)
        {
            validationErrors.add(new SelectItem(SOURCE_TTYPE_UI_ID, errm));
        }
        
        validateIncludedPattern(validationErrors, detailsBean);
        return validationErrors;
    }

    public boolean validateViewBeanData()
    {
        boolean validationSuccess = true;
        if (this.isReadEditMode())
            return validationSuccess;
        
        List<SelectItem> listOfItems = validateLogSourceData(this.detailsBean);
        detailsValidationErrorMessages = null;
        
        validationSuccess = (listOfItems == null || listOfItems.size() == 0);
        if (!validationSuccess)
        {
            detailsValidationErrorMessages = listOfItems;
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            this.errmpop.show(hints);  
        }
        return validationSuccess;
    }

    public void setDetailsValidationErrorMessages(List<SelectItem> detailsValidationErrorMessages)
    {
        this.detailsValidationErrorMessages =
                detailsValidationErrorMessages;
    }

    public List<SelectItem> getDetailsValidationErrorMessages()
    {
        return detailsValidationErrorMessages;
    }

    public void setSourceNameOutlineColor(String sourceNameOutlineColor)
    {
        this.sourceNameOutlineColor = sourceNameOutlineColor;
    }

    public String getSourceNameOutlineColor()
    {
        return sourceNameOutlineColor;
    }

    public void setSourceTypeOutlineColor(String sourceTypeOutlineColor)
    {
        this.sourceTypeOutlineColor = sourceTypeOutlineColor;
    }

    public String getSourceTypeOutlineColor()
    {
        return sourceTypeOutlineColor;
    }

    public void setSourceTTypeOutlineColor(String sourceTTypeOutlineColor)
    {
        this.sourceTTypeOutlineColor = sourceTTypeOutlineColor;
    }

    public String getSourceTTypeOutlineColor()
    {
        return sourceTTypeOutlineColor;
    }

    /**
     * user clicked params tab
     * @param de
     */
    public void paramsDisclosed(DisclosureEvent de)
    {
        // only if Parametrs tab selected
        RichShowDetailItem detail = (RichShowDetailItem) de.getSource();
        String id = detail.getId();
        boolean tabExpanded = de.isExpanded();
        //System.out.println("disclose: "+id);
        if (!(id.equals(LoganLibUiUtil.TAB_PARAMS_ID) && tabExpanded))
            return;  
        initLoganMetaAvailParams();
        checkParamsTabData();
    }
    
    private boolean checkParamsTabData()
    {
        // do some pattern validation
        // check pattern length
        // check params length within pattern
        // cleanup any parameters not used in pattern
        if (false == doValidatePatterns())
        {
            // fix issues before switching
            LoganLibUiUtil.disclosePatternsTab();
            return false;
        }
        else
        {    
            doAddParams();
            removeUnusedParams();
            // refresh requery params
            LoganLibUiUtil.addPPR(PARAMS_TABLE_ID);  
            return true;
        }    
    }
    
    /**
     * check for orphaned params and reset the active flag so they
     * wont show up in the table;
     * When the facet is deleted the params will also be deleted
     * @return
     */
    private boolean removeUnusedParams() 
    {
      boolean prmv = false;
      
     List<LoganSourceFilePattern> excp = this.detailsBean.getInclFilePatterns();
     List<LoganSourceFilePattern> incp = this.detailsBean.getExclFilePatterns();
     List<LoganSourceDetailsParameter> params = this.detailsBean.getParameters();
     
     for (LoganSourceDetailsParameter p : params)
     {
        String cparam = p.getParamName();
        String pattochk = "{" + cparam + "}";
        
        boolean match = paramInPattern(incp, pattochk); // included
        if (!match) // look in excluded
          match = paramInPattern(excp, pattochk); // excluded
        
        // no match
        if (!match)
        {
          // this is an orphaned parameter
          p.setParamActive(false);
          p.setParamIsActive(new Number(0));
          prmv = true;
        }
      }
      return prmv;
    }
    
    // find param in pattern
    private boolean paramInPattern(List<LoganSourceFilePattern> patterns, String checkparam)
    {
        boolean haspatt = false;
        for (LoganSourceFilePattern p : patterns)
        {
            String spattern = p.getFileName();

            if (spattern !=null && spattern.trim().contains(checkparam))
                haspatt = true;

            if (haspatt)
                break;
        }
        return haspatt;
    }

    /**
     * validate patterns - check param length
     * @return
     */
    private boolean doValidatePatterns()
    {
        // loop through patterns and validate
        boolean piIsValid = true;
        boolean peIsValid = true;

        piIsValid = validateParamsInPatt(getInclFilePatterns());
        if (piIsValid)
            peIsValid = validateParamsInPatt(getExclFilePatterns());

        return (piIsValid && peIsValid);

    }   

    public boolean validate()
    {
        return (validateViewBeanData() && validateParameters());
    }

    public boolean validateParameters()
    {
        if(!checkParamsTabData())
            return false;
        boolean validateSuccess = true;
        if(isEmptyDefaults())
        {
            UiUtil.displayErrorMsg(UiUtil.getLoganBundle(), "ERRHDR_REQ", "ERR_SETPARAM");
            LoganLibUiUtil.discloseParamsTab();
            validateSuccess = false;
        }
        return validateSuccess;
    }
    
    public boolean validateIncludedPattern()
    {
        return validateIncludedPattern(null, this.detailsBean);
    }

    public static boolean validateIncludedPattern(List<SelectItem> errms,
                                                  LoganSourceDetailsBean detailsBean)
    {
        boolean validateSuccess = true;
        List<LoganSourceFilePattern> inclPatterns = 
            detailsBean.getInclFilePatterns();
        
        if (inclPatterns == null ||
            inclPatterns.size() == 0)
        {
            if(errms == null)
                UiUtil.displayErrorMsg(UiUtil.getLoganBundle(), 
                                       "ERRHDR_REQ", 
                                       "SOURCE_NO_INCLUDED_PATTERN_ERR");
            else
                errms.add(new SelectItem(PATT_TABLE_ID,
                                         UiUtil.getUiString("SOURCE_NO_INCLUDED_PATTERN_ERR")));
            validateSuccess = false;
        } 
        else 
        {
            if (isMissingParser(detailsBean))
            {
                if(errms == null)
                    UiUtil.displayErrorMsg(UiUtil.getLoganBundle(), 
                                       "ERRHDR_REQ", "ERR_SELECTPARSER");
                else
                    errms.add(new SelectItem(PATT_TABLE_ID,
                                             UiUtil.getUiString("ERR_SELECTPARSER")));
                validateSuccess = false;
            }
        }
        return validateSuccess;
    }
    
    private Set<LoganMetaAvailParameters> getAvailParamsSet()
    {
        Set<LoganMetaAvailParameters> ap = null;
        if(availParams != null)
        {
            ap = new HashSet<LoganMetaAvailParameters>();
            ap.addAll(availParams);
        }
        return ap;
    }
    
    private boolean validateParamsInPatt(List<LoganSourceFilePattern> patterns)
    {
        boolean isvalid = true;
        // iterate over patterns
        for (LoganSourceFilePattern patt : patterns)
        {
            patt.setBuiltInParams(getAvailParamsSet());
            String spattern = patt.getFileName();
            if (spattern != null && spattern.trim().length() > 0)
            {
                patt.setFileName(spattern);
                // remove esc {{} and {}} from the pattern
                Set<String> newRows = patt.getParamsInPattern();
   
                // validate param length within pattern for each param
                if (newRows != null)
                    // will display an error if invalid
                    isvalid = checkParam(newRows, spattern);
                if (!isvalid)
                    break;
            }              
        }
        return isvalid;
    }
    
    private boolean builtInParam(String newParam)
    {
        if(newParam == null || availParams == null || availParams.size() == 0)
            return false;
        boolean builtin = false;
        for(LoganMetaAvailParameters ap : availParams)
        {
            if(newParam.equals(ap.getParamName()))
            {
                builtin = true;
                break;
            }
        }
        return builtin;
    }
    
    /**
     * return Valid if this is not a built-in param, and does not exist, and is valid length 
     * @param params
     * @return
     */
    private boolean checkParam(Set<String> params,
                               String pattern)
    {
        boolean validParam = true;
 
        for (String newParam: (params))
        {
            // make sure it doesnt already exist and it is NOT a built-in param
            if ((newParam.trim().length() > 0) &&
                (!builtInParam(newParam)))
            {
                if (newParam.trim().length() > MAX_PARAMLENGTH)
                {
                    // display error and allow user to fix pattern
                    UiUtil.displayErrorMsg(UiUtil.getLoganBundle(), "MSGERROR", "ERRMSG_INVALID_PARAM", new Object[]{ pattern });

                    validParam = false;
                    break;
                }

            }
        }

        return validParam;
    }


    /**
     *  Add params from list of patterns user created.
     *  Patterns that contain { someValue } will be added to the
     *  Parameters table when user clicks on patterns tab or OK button.
     *  If added during OK user must enter default value.
     *
     * @return
     */
    public boolean doAddParams()
    {
        boolean piAdded = addParams(this.getInclFilePatterns()); // included
        boolean peAdded = addParams(this.getExclFilePatterns()); // excluded

        return (peAdded || piAdded);
    }
    
    /**
     * loop thru parameters and detect any missing default values
     * @return
     */
    private boolean isEmptyDefaults()
    {
        boolean isEmpty = false;         
        for (LoganSourceDetailsParameter p : this.getActiveParameters())
        {
            if (p != null)
            {
                String defVal = p.getDefaultValue();
                if (defVal == null || defVal.trim().length() == 0)
                {
                    isEmpty = true;
                    break;
                }
            }
        }
        return isEmpty;
    }
    
    /**
     * check for missing file parser selection in Patterns tab
     * @return
     */
    public static boolean isMissingParser(LoganSourceDetailsBean detailsBean)
    {
        boolean missing = false;
        for (LoganSourceFilePattern ip : detailsBean.getInclFilePatterns())
        {
            if (ip.getFileParserIName() == null)
            {
                missing = true;
                break;
            }
        }
        // file patterns do not have specified parser        
        return missing;
    }
    
    /**
     * add params associated with file patterns
     * @param patterns
     * @return
     */
    private boolean addParams(List<LoganSourceFilePattern> patterns)
    {
        int pcnt = 0;

        for (LoganSourceFilePattern patt : patterns)
        {
            String spattern = patt.getFileName();
            patt.setBuiltInParams(getAvailParamsSet());
            if (spattern != null && spattern.trim().length() > 0)
            {   
                patt.setFileName(spattern);
                // remove esc {{} and {}} from the pattern, so we dont include as param
                Set<String> newParams = patt.getParamsInPattern();
                if (newParams != null)
                {
                    for (String paramName : newParams)
                    {
                        boolean rowAdded = addParam(paramName, spattern);
                        if (rowAdded) {pcnt++;}
                    }
                }
            }
        }
        return (pcnt>0);
    }

    private LoganSourceFilePattern findPattern(String pattern)
    {
  
        List<LoganSourceFilePattern> ip = this.getInclFilePatterns();
        List<LoganSourceFilePattern> ep = this.getExclFilePatterns();
        for (LoganSourceFilePattern patt : ip)
        {
            String spattern =  patt.getFileName();
            if (spattern.equals(pattern))
            {   
                return patt;
                
            }
        }
        for (LoganSourceFilePattern patt : ep)
        {
            String spattern = patt.getFileName();
            if (spattern.equals(pattern))
            {   
                return patt;
                
            }
        }
        return null;
    }
    
    private void handlePatternNameChange(String oldPattern, String newPattern)
    {
        LoganSourceFilePattern patt = findPattern(oldPattern);
        if (patt == null)
            return;
        
        Set<String> params = patt.getParamsInPattern();       
        for (String p : params)
        {
            LoganSourceDetailsParameter param = this.findParam(p);
            
            if (param != null) {
                param.handleRemove();
            }
        }
        patt.setBuiltInParams(getAvailParamsSet());
        patt.setFileName(newPattern);
    }
    
    public void pattInclNameChange(ValueChangeEvent valueChangeEvent)
    {
        // oldname and newname
        String vold = (String) valueChangeEvent.getOldValue();
        String vnew = (String) valueChangeEvent.getNewValue();
        if (vold == null || vold.equals(vnew))
            return;
        
        // if params assoc with this pattern, then 
        // remove using pattern from param
        handlePatternNameChange(vold, vnew);
    }

    public void pattExclNameChange(ValueChangeEvent valueChangeEvent)
    {
        // oldname and newname
        String vold = (String) valueChangeEvent.getOldValue();
        String vnew = (String) valueChangeEvent.getNewValue();
       if (vold == null || vold.equals(vnew))
           return;

       // if params assoc with this pattern, then 
       // remove using pattern from param
       handlePatternNameChange(vold, vnew);

   }
    
    public boolean isCreateMode()
    {
        return modeBean.isCreateMode();
    }

    public boolean isCreateLikeMode()
    {
        return modeBean.isCreateLikeMode();
    }

    public boolean isEditMode()
    {
        return modeBean.isEditMode();
    }

    public boolean isReadOnlyMode()
    {
        return modeBean.isReadOnlyMode();
    }

    public boolean isReadEditMode()
    {
        return modeBean.isReadEditMode();
    }

    public String getMode()
    {
        return modeBean.getMode();
    }
    
    public List<SelectItem> getSourceTypesList()
    {
        if (srcTypesList == null)
        {
            srcTypesList = LoganLibUiUtil.getSourceTypesList();
        }
        return srcTypesList;
    }
    
    public List<SelectItem> getSourceTargetTypesList()
    {
        if (srcTgtTypesList == null)
        {
            srcTgtTypesList = LoganLibUiUtil.getTargetTypesList(false);
        }
        return srcTgtTypesList;
    }
    
    public List<SelectItem> getParsersList()
    {
        if (parsersList == null)
        {
            parsersList = LoganLibUiUtil.getParsersList();
        }
        return parsersList;
    }
    
    public String getFunctionHeaderDetailText()
    {
        String modeNLSID = getMode();
        if(isReadOnlyMode())
            modeNLSID = "DETAILS";
        if(isCreateLikeMode())
            modeNLSID = "CREATE";
        return UiUtil.getUiString(modeNLSID);
    }
    
    public String handleOk()
    {
        boolean valideSucess = this.validate();
        if (valideSucess)
        {
            if (this.isCreateLikeMode())
            {
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                this.contpop.show(hints);
                return null;
            }
            else 
            {
                saveSource();
                return "return-to-logan-lib-home";
            }
        }
        else
        {
            return "";
        }
        
    }
    
    public void saveSource(ActionEvent actionEvent){
        saveSource();
    }
    
    public void saveSource(){
        saveSorceDetails();
        if (this.isCreateLikeMode()){
            
            if(isInclExtendedFields == true){
                extFieldsBean.setSourceId(detailsBean.getSourceId());
                LoganSourceExtFieldsViewBean extFields = new LoganSourceExtFieldsViewBean();
                extFields.setExtFieldsBean(extFieldsBean);
                extFields.setExtFieldsBeanRef(extFieldsBeanRef);
                extFields.setSourceCurrentDataBean(detailsBean);
                extFields.setModeBean(modeBean);
                extFields.saveExtFields();
            }
            if(isInclFieldReferences == true){
                fieldsRefDataBean.setSourceId(detailsBean.getSourceId());
                LogSourceFieldRefsViewBean fieldRefs = new LogSourceFieldRefsViewBean();
                fieldRefs.setfieldsRefDataBean(fieldsRefDataBean);
                fieldRefs.setfieldsRefDataBeanRef(fieldsRefDataBeanRef);
                fieldRefs.setSourceCurrentDataBean(detailsBean);
                fieldRefs.setModeBean(modeBean);
                fieldRefs.saveFieldsRef();
            }
            
        }
        
        //TODO JPA
        // only way I got to make the table refresh dependent data
//        if (this.isCreateLikeMode() || this.isCreateMode())
//        {
//            LoganAMImpl am = LoganLibUiUtil.getAppModule();
//            EmLoganSourceVOImpl vo = am.getEmLoganSourceVO();
//            vo.executeQuery();
//            while(vo.hasNext()) {
//                Row dRow = vo.next();
//                Number sourceId = (Number) dRow.getAttribute("SourceId");
//                Number sourceIdNew = detailsBean.getSourceId();
//                if (sourceIdNew.intValue() == sourceId.intValue())
//                    break;
//            }
//        }
    }
    
    public void saveSorceDetails(){
        super.save(detailsRefBean, modeBean);  
    }

    public boolean isInclExtendedFields() {
        return isInclExtendedFields;
    }

    public boolean isInclFieldReferences() {        
        return isInclFieldReferences;
    }

    public void setInclExtendedFields(boolean isInclExtendedFields) {
        this.isInclExtendedFields = isInclExtendedFields;
    }

    public void setInclFieldReferences(boolean isInclFieldReferences) {
        this.isInclFieldReferences = isInclFieldReferences;
    }
    
    public void inclExtendedFieldsChanged(ValueChangeEvent ve){
        if (isInclExtendedFields == false){
            isInclExtendedFields = true;
        } else {
            isInclExtendedFields = false;
        }
    }
    
    public void inclFieldReferencesChanged(ValueChangeEvent ve){
        if (isInclFieldReferences == false){
            isInclFieldReferences = true;
        } else {
            isInclFieldReferences = false;
        }
    }

    public ILoganSourceDataBean getSourceDataBean() {
        return this.detailsBean;
    }

    public void setSourceDataBean(ILoganSourceDataBean tsdb) {
        this.detailsBean = (LoganSourceDetailsBean)tsdb;
    }
    
    private void initLoganMetaAvailParams()
    {
        Set<LoganMetaAvailParameters> ap = 
            LoganLibUiUtil.getLoganMetaAvailParameters(getTargetType());
        if(ap != null)
            this.availParams = new ArrayList<LoganMetaAvailParameters>(ap);
    }

    public void availParamsPopupFetchListener(PopupFetchEvent popupFetchEvent)
    {
        initLoganMetaAvailParams();
    }

    public void setAvailParams(List<LoganMetaAvailParameters> availParams)
    {
        this.availParams = availParams;
    }

    public List<LoganMetaAvailParameters> getAvailParams()
    {
        return availParams;
    }

    public void setErrmpop(RichPopup errmpop)
    {
        this.errmpop = errmpop;
    }

    public RichPopup getErrmpop()
    {
        return errmpop;
    }

    public void setContpop(RichPopup contpop)
    {
        this.contpop = contpop;
    }

    public RichPopup getContpop()
    {
        return contpop;
    }


}

