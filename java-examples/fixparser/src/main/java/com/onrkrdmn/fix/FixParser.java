package com.onrkrdmn.fix;

import com.onrkrdmn.fix.model.FixField;
import com.onrkrdmn.fix.model.FixMessage;
import lombok.extern.java.Log;
import quickfix.ConfigError;
import quickfix.DataDictionary;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
public class FixParser {
    private static final String DEFAULT_DICTIONARY_PATH = "fix/dictionary/fix44.xml";

    private static final int FIELD_CHECKSUM = 10;
    private static final int BEGIN_STRING = 8;
    private static final int BODY_LENGTH = 9;
    private static final int MSG_TYPE = 35;
    private static final int SENDER_COMP_ID = 49;
    private static final int TARGET_COMP_ID = 56;
    private static final String KEY_VALUE_DELIMETER = "=";
    private static final String DIRECTION_OUTGOING = "outgoing";
    private static final String DIRECTION_INCOMING = "incoming";


    private DataDictionary dataDictionary;


    public FixParser() {
        initDataDictionary(null);
    }

    public FixParser(String dictionaryFilePath) {
        initDataDictionary(dictionaryFilePath);
    }

    public List<FixMessage> parse(List<String> rawMessages) {
        List<FixMessage> fixMessagess = new ArrayList<>();
        for (String rawMessage : rawMessages) {
            fixMessagess.add(createFixMessage(rawMessage));
        }
        return fixMessagess;
    }

    private FixMessage createFixMessage(String rawMessage) {
        String extractedFixMessage = extractFixMessagePart(rawMessage);
        FixMessage fixMessage = new FixMessage();
        String delimeter = findDelimeter(extractedFixMessage);
        String[] fixMessageSplit = extractedFixMessage.split("\\" + delimeter);
        for (String messageField : fixMessageSplit) {
            String[] keyValue = messageField.split(KEY_VALUE_DELIMETER);
            int key = Integer.valueOf(keyValue[0]);
            String value = keyValue[1];
            String tagName = dataDictionary.getFieldName(key);
            String valueName = dataDictionary.getValueName(key, value);
            FixField fixField = new FixField(key, tagName, value, valueName, false);
            fixMessage.addFixField(fixField);
        }
        return fixMessage;
    }

    private String extractFixMessagePart(String text) {
        int indexOfBeginTag = text.indexOf(BEGIN_STRING + KEY_VALUE_DELIMETER);
        return text.substring(indexOfBeginTag);
    }

    private String findDelimeter(String rawMessage) {
        String withEqual = this.BODY_LENGTH + this.KEY_VALUE_DELIMETER;
        int endIndex = rawMessage.indexOf(withEqual);
        String ch = rawMessage.substring(endIndex - 1, endIndex);
        if (ch.equals("A")) {
            String ch2 = rawMessage.substring(endIndex - 2, endIndex - 1);
            if (ch2.equals("^"))
                return "\\" + ch2 + ch;
        } else {
            return rawMessage.substring(endIndex - 1, endIndex);
        }
        throw new RuntimeException("Delimeter couldn't be found");
    }

    private void initDataDictionary(String dictionaryFilePath) {
        if (dictionaryFilePath != null) {
            try {
                dataDictionary = new DataDictionary(new FileInputStream(dictionaryFilePath));
            } catch (Exception e) {
                log.log(Level.WARNING, "Custom datadictionary couldn't be created. Path:" + dictionaryFilePath, e);
            }
        }
        if (dataDictionary == null) {
            log.info("Default datadictionary is loading. Path:" + DEFAULT_DICTIONARY_PATH);
            try {
                dataDictionary = new DataDictionary(getClass().getClassLoader().getResourceAsStream(DEFAULT_DICTIONARY_PATH));
            } catch (ConfigError configError) {
                log.log(Level.SEVERE, "Default datadictionary couldn't be loaded", configError);
            }
        }
    }
}

