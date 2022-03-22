package de.bsailer.classloaderhack.service2;

import de.bsailer.classloaderhack.api.service.Service2;

public class Service2Factory {
    public static Service2 create() {
        return new NewFrameworkUsingServiceImpl();
    }
}
