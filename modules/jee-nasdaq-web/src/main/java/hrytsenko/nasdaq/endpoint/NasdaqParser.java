package hrytsenko.nasdaq.endpoint;

import java.io.IOException;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import hrytsenko.nasdaq.AppException;
import hrytsenko.nasdaq.domain.Company;

public final class NasdaqParser {

    private NasdaqParser() {
    }

    public static List<Company> parseCompanies(String exchange, String data) {
        try {
            CsvSchema schema = CsvSchema.builder().setUseHeader(true).build();
            CsvMapper mapper = new CsvMapper();
            ObjectReader reader = mapper.reader(CompanyDto.class).with(schema);

            Stream<CompanyDto> companies = StreamSupport
                    .stream(Spliterators.spliteratorUnknownSize(reader.readValues(data), Spliterator.ORDERED), false);
            return companies.map(CompanyDto::toCompany).peek(company -> company.setExchange(exchange))
                    .collect(Collectors.toList());
        } catch (IOException exception) {
            throw new AppException("Could not parse companies.", exception);
        }
    }

}
