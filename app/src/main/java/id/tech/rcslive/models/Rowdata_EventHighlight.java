package id.tech.rcslive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RebelCreative-A1 on 23/03/2016.
 */
public class Rowdata_EventHighlight {

    @SerializedName("id_event")
    @Expose
    private String idEvent;
    @SerializedName("id_visit")
    @Expose
    private String idVisit;
    @SerializedName("tv_tgl")
    @Expose
    private String tvTgl;
    @SerializedName("tv_judul")
    @Expose
    private String tvJudul;
    @SerializedName("tv_alamat")
    @Expose
    private String tvAlamat;
    @SerializedName("tv_kategori")
    @Expose
    private String tvKategori;
    @SerializedName("joined")
    @Expose
    private String joined;



    /**
     *
     * @return
     * The idEvent
     */
    public String getIdEvent() {
        return idEvent;
    }

    /**
     *
     * @param idEvent
     * The id_event
     */
    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    /**
     *
     * @return
     * The idVisit
     */
    public String getIdVisit() {
        return idVisit;
    }

    /**
     *
     * @param idVisit
     * The id_visit
     */
    public void setIdVisit(String idVisit) {
        this.idVisit = idVisit;
    }

    /**
     *
     * @return
     * The tvTgl
     */
    public String getTvTgl() {
        return tvTgl;
    }

    /**
     *
     * @param tvTgl
     * The tv_tgl
     */
    public void setTvTgl(String tvTgl) {
        this.tvTgl = tvTgl;
    }

    /**
     *
     * @return
     * The tvJudul
     */
    public String getTvJudul() {
        return tvJudul;
    }

    /**
     *
     * @param tvJudul
     * The tv_judul
     */
    public void setTvJudul(String tvJudul) {
        this.tvJudul = tvJudul;
    }

    /**
     *
     * @return
     * The tvAlamat
     */
    public String getTvAlamat() {
        return tvAlamat;
    }

    /**
     *
     * @param tvAlamat
     * The tv_alamat
     */
    public void setTvAlamat(String tvAlamat) {
        this.tvAlamat = tvAlamat;
    }

    /**
     *
     * @return
     * The tvKategori
     */
    public String getTvKategori() {
        return tvKategori;
    }

    /**
     *
     * @param tvKategori
     * The tv_kategori
     */
    public void setTvKategori(String tvKategori) {
        this.tvKategori = tvKategori;
    }

    /**
     *
     * @return
     * The joined
     */
    public String getJoined() {
        return joined;
    }

    /**
     *
     * @param joined
     * The joined
     */
    public void setJoined(String joined) {
        this.joined = joined;
    }


}
