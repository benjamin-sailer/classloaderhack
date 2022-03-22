package de.bsailer.classloaderhack.service1;


import de.bsailer.classloaderhack.api.service.Service1;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@SuppressWarnings("unused")
public class OldFrameworkUsingServiceImpl implements Service1 {

    public String doIt(final String with) {
        ObjectMapper m = new ObjectMapper() {
            @Override
            public <T> T readValue(String s, Class<T> aClass) throws IOException {
                final PrettyPrinter prettyPrinter = _defaultPrettyPrinter;
                return super.readValue(s, aClass);
            }
        };
        // not available in jackson 2.3.4
        //m.disable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        m.disable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try {
            final Data data = m.readValue("{\"i\":1,\"s\":\"foo\"}", Data.class);
            return String.format("%s: %s %d", with, data.s, data.i);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    static class Data {
        int i;
        String s;

        public void setI(int i) {
            this.i = i;
        }

        public void setS(String s) {
            this.s = s;
        }
    }
}
