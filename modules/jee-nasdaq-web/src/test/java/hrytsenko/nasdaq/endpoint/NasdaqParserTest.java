package hrytsenko.nasdaq.endpoint;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.io.Resources;

import hrytsenko.nasdaq.domain.Company;

public class NasdaqParserTest {

    @Test
    public void testParseCompanies() throws Exception {
        String data = Resources.toString(Resources.getResource("companies/NASDAQ.csv"), StandardCharsets.UTF_8);
        List<Company> companies = NasdaqParser.parseCompanies("TEST", data);

        Assert.assertEquals(2, companies.size());
    }

}
