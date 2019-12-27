package com.company.spmu.service.integration.parser;

import com.company.spmu.service.integration.parser.IParser;

public interface RegistryParserService extends IParser {
    String NAME = "spmu_RegistryParserService";

    public void createRegistry();
}