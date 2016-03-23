package id.tech.rcslive.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pojo_EventHighlight {
    @SerializedName("json_code")
    @Expose
    private String jsonCode;
    @SerializedName("total_data")
    @Expose
    private Integer totalData;
    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();

    /**
     *
     * @return
     * The jsonCode
     */
    public String getJsonCode() {
        return jsonCode;
    }

    /**
     *
     * @param jsonCode
     * The json_code
     */
    public void setJsonCode(String jsonCode) {
        this.jsonCode = jsonCode;
    }

    /**
     *
     * @return
     * The totalData
     */
    public Integer getTotalData() {
        return totalData;
    }

    /**
     *
     * @param totalData
     * The total_data
     */
    public void setTotalData(Integer totalData) {
        this.totalData = totalData;
    }

    /**
     *
     * @return
     * The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    public static class Datum {

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
}
