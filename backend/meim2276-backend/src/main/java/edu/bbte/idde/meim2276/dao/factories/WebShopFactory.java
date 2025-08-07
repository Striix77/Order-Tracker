package edu.bbte.idde.meim2276.dao.factories;


import edu.bbte.idde.meim2276.config.ConfigLoader;

public class WebShopFactory {

    public static DaoFactory getFactory() {

        if ("InMem".equals(ConfigLoader.loadConfig().getDaoType())) {
            return new OrderInMemFactory();
        } else {
            return new OrderDBFactory();
        }

    }

}
