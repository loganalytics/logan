package oracle.saas.logan.view.source;

import java.util.Date;


public class LoganLibSourcePojo {
    public LoganLibSourcePojo() {
        super();
    }
    
    private Integer SourceIsSystem;
    private Integer SourceId;
    private String SourceDname;
    private String SrctypeDname;
    private Integer RulesUsing;
    private String SourceDescription;
    private String SrcTargetTypeDisplayNls;
    private String SourceAuthor;
    private Date SourceLastUpdatedDate;


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


}
