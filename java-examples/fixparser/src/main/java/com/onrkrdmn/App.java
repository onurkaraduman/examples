package com.onrkrdmn;


import com.onrkrdmn.fix.FixParser;
import com.onrkrdmn.fix.model.FixMessage;

import java.util.Arrays;
import java.util.List;

public class App {

    private static String fixMessage = "outgoing: 8=FIX.4.2|9=130|35=AE|49=LSEHub|56=LSETR|115=BROKERX|34=2287|43=N|52=20120330-12:14:09|370=20120330-12:14:09.816|571=00008661533TRLO1-1-1-0|150=H|10=074|";

    public static void main(String[] args) {
        FixParser fixParser = new FixParser();
        List<FixMessage> fixMessages = fixParser.parse(Arrays.asList(fixMessage));

        fixMessages.forEach(m -> m.prettyPrint());
    }
}
