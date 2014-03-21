package oracle.saas.logan.view.source;

import java.util.Date;
import java.util.List;

import oracle.saas.logan.model.persistance.EmLoganSourcePattern;

/**
 * Logan source pojo to interact with table row data
 * @author minglei
 * */
public class LoganLibSourcePojo {
    public LoganLibSourcePojo() {
        super();
    }

    private Integer SourceIsSystem;
    private Integer SourceId;
    private String SourceDname;
    private String SourceIname;
    private String SrctypeDname;
    private String SrctypeIname;
    private Integer RulesUsing;
    private String SourceDescription;
    private String SrcTargetTypeDisplayNls;
    private String SrcTargetType;
    private Integer SourceIsSecureContent;
    private String SourceAuthor;
    private Date SourceLastUpdatedDate;
    private Integer sourceCriticalEditVersion;
    private Integer sourceEditVersion;


    
    private List<EmLoganSourcePattern> inclPatts;


    public void setSourceId(Integer SourceId) {
        this.SourceId = SourceId;
    }

    public Integer getSourceId() {
        return SourceId;
    }

    public void setSourceIsSystem(Integer SourceIsSystem) {
        this.SourceIsSystem = SourceIsSystem;
    }

    public Integer getSourceIsSystem() {
        return SourceIsSystem;
    }

    public void setSourceDname(String SourceDname) {
        this.SourceDname = SourceDname;
    }

    public String getSourceDname() {
        return SourceDname;
    }

    public void setSrctypeDname(String SrctypeDname) {
        this.SrctypeDname = SrctypeDname;
    }

    public String getSrctypeDname() {
        return SrctypeDname;
    }

    public void setRulesUsing(Integer RulesUsing) {
        this.RulesUsing = RulesUsing;
    }

    public Integer getRulesUsing() {
        return RulesUsing;
    }

    public void setSourceDescription(String SourceDescription) {
        this.SourceDescription = SourceDescription;
    }

    public String getSourceDescription() {
        return SourceDescription;
    }

    public void setSrcTargetTypeDisplayNls(String SrcTargetTypeDisplayNls) {
        this.SrcTargetTypeDisplayNls = SrcTargetTypeDisplayNls;
    }

    public String getSrcTargetTypeDisplayNls() {
        return SrcTargetTypeDisplayNls;
    }

    public void setSourceAuthor(String SourceAuthor) {
        this.SourceAuthor = SourceAuthor;
    }

    public String getSourceAuthor() {
        return SourceAuthor;
    }

    public void setSourceLastUpdatedDate(Date SourceLastUpdatedDate) {
        this.SourceLastUpdatedDate = SourceLastUpdatedDate;
    }

    public Date getSourceLastUpdatedDate() {
        return SourceLastUpdatedDate;
    }
    
    public int getInclFilePatternsCount(){
        return this.inclPatts == null? 0: this.inclPatts.size();
    } 
    
    public void setSrcTargetType(String SrcTargetType) {
        this.SrcTargetType = SrcTargetType;
    }

    public String getSrcTargetType() {
        return SrcTargetType;
    }
    
    public void setSourceIname(String SourceIname) {
        this.SourceIname = SourceIname;
    }

    public String getSourceIname() {
        return SourceIname;
    }

    public void setSrctypeIname(String SrctypeIname) {
        this.SrctypeIname = SrctypeIname;
    }

    public String getSrctypeIname() {
        return SrctypeIname;
    }

    public void setSourceIsSecureContent(Integer SourceIsSecureContent) {
        this.SourceIsSecureContent = SourceIsSecureContent;
    }

    public Integer getSourceIsSecureContent() {
        return SourceIsSecureContent;
    }

    public void setSourceCriticalEditVersion(Integer sourceCriticalEditVersion) {
        this.sourceCriticalEditVersion = sourceCriticalEditVersion;
    }

    public Integer getSourceCriticalEditVersion() {
        return sourceCriticalEditVersion;
    }

    public void setSourceEditVersion(Integer sourceEditVersion) {
        this.sourceEditVersion = sourceEditVersion;
    }

    public Integer getSourceEditVersion() {
        return sourceEditVersion;
    }


}
