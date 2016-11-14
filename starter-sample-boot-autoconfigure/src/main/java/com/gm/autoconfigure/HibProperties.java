package com.gm.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by gmurias on 12.11.16.
 */
@ConfigurationProperties(prefix = "hibernate")
public class HibProperties {
    private String dialect = "org.hibernate.dialect.PostgreSQL9Dialect";

    private boolean showSql = true;

    private String hbm2ddlAuto = "validate";

    private boolean formatSql = true;


    public String getHbm2ddlAuto() {
        return hbm2ddlAuto;
    }

    public void setHbm2ddlAuto(String hbm2ddlAuto) {
        this.hbm2ddlAuto = hbm2ddlAuto;
    }

    public boolean isFormatSql() {
        return formatSql;
    }

    public void setFormatSql(boolean formatSql) {
        this.formatSql = formatSql;
    }

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

}
