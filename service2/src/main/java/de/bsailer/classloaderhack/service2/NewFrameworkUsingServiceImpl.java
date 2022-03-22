package de.bsailer.classloaderhack.service2;

import de.bsailer.classloaderhack.api.service.Service2;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class NewFrameworkUsingServiceImpl implements Service2 {

    @Override
    public int andThat() {
        /*
         * _defaultPrettyPrinter is no longer available in jackson 2.12.x
        ObjectMapper m2 = new ObjectMapper() {
            @Override
            public <T> T readValue(String s, Class<T> aClass) throws IOException {
                final PrettyPrinter defaultPrettyPrinter = _defaultPrettyPrinter;
                return super.readValue(s, aClass);
            }
        };
        */
        final ObjectMapper m = new ObjectMapper();
        m.disable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        m.disable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try {
            final SecondaryData data = m.readValue("{\"i\":2,\"s\":\"bar\"}", SecondaryData.class);
            return data.i;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @SuppressWarnings("unused")
    static class SecondaryData {
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
