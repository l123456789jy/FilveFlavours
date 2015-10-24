package suzhou.dataup.cn.myapplication.constance;

public enum Format {
    /**
     * yyyy-MM-dd HH:mm:ss.
     */
    DATA_FORMAT_YMDHMS_EN("yyyy-MM-dd HH:mm:ss"),

    /**
     * yyyy年MM月dd日 HH时mm分ss秒.
     */
    DATA_FORMAT_YMDHMS_CH("yyyy年MM月dd日 HH时mm分ss秒"),

    /**
     * yyyy-MM-dd HH:mm.
     */
    DATA_FORMAT_YMDHM_EN("yyyy-MM-dd HH:mm"),

    /**
     * yyyy年MM月dd日 HH时mm分.
     */
    DATA_FORMAT_YMDHM_CH("yyyy年MM月dd日 HH时mm分"),

    /**
     * yyyy年MM月dd日 HH时.
     */
    DATA_FORMAT_YMDH_CH("yyyy年MM月dd日 HH时"),

    /**
     * yyyy-MM-dd.
     */
    DATA_FORMAT_YMD_EN("yyyy-MM-dd"),

    /**
     * yyyy年MM月dd日.
     */
    DATA_FORMAT_YMD_CH("yyyy年MM月dd日"),

    /**
     * HH:mm.
     */
    DATA_FORMAT_HM("HH:mm");

    private String formatStr = "";

    Format(String formatStr) {
        this.formatStr = formatStr;
    }

    @Override
    public String toString() {
        return formatStr;
    }
}
