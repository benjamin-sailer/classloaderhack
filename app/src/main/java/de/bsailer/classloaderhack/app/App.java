package de.bsailer.classloaderhack.app;

import de.bsailer.classloaderhack.factory.Service1Factory;
import de.bsailer.classloaderhack.api.service.Service1;
import de.bsailer.classloaderhack.api.service.Service2;
import de.bsailer.classloaderhack.service2.Service2Factory;

public class App {

    public static void main(final String[] argv) {
        final Service1 service1 = Service1Factory.create();
        final Service2 service2 = Service2Factory.create();
        System.out.println(service1.doIt("me"));
        System.out.println(service2.andThat());
    }
}
