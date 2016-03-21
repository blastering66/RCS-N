package id.tech.rcslive.models;

/**
 * Created by RebelCreative-A1 on 21/03/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pojo_ResponseLogin {
    @SerializedName("statuslogid")
    @Expose
    private String statuslogid;
    @SerializedName("namelog")
    @Expose
    private String namelog;

    /**
     *
     * @return
     * The statuslogid
     */
    public String getStatuslogid() {
        return statuslogid;
    }

    /**
     *
     * @param statuslogid
     * The statuslogid
     */
    public void setStatuslogid(String statuslogid) {
        this.statuslogid = statuslogid;
    }

    /**
     *
     * @return
     * The namelog
     */
    public String getNamelog() {
        return namelog;
    }

    /**
     *
     * @param namelog
     * The namelog
     */
    public void setNamelog(String namelog) {
        this.namelog = namelog;
    }
}
