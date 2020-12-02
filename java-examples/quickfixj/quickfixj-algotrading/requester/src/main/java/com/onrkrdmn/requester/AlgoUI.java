package com.onrkrdmn.requester;

import edu.emory.mathcs.backport.java.util.Collections;
import org.atdl4j.config.Atdl4jConfig;
import org.atdl4j.config.Atdl4jConfiguration;
import org.atdl4j.config.Atdl4jOptions;
import org.atdl4j.data.exception.FIXatdlFormatException;
import org.atdl4j.fixatdl.core.ParameterT;
import org.atdl4j.fixatdl.core.StrategyT;
import org.atdl4j.ui.swing.app.impl.SwingAtdl4jCompositePanel;
import org.atdl4j.ui.swing.config.SwingAtdl4jConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoUI {

    private SwingAtdl4jCompositePanel atdlPanel;
    private List<SendListener> sendListeners = Collections.synchronizedList(new ArrayList());

    public void init() throws FIXatdlFormatException {
        // Embed the strategy panel in your own JFrame/JDialog
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("atdl4j - The Open-Source Java Solution for FIXatdl (Swing)");

        Atdl4jConfiguration config = new SwingAtdl4jConfiguration();
        Atdl4jConfig.setConfig(config);

        // Set the required options
        Atdl4jOptions options = new Atdl4jOptions();

        atdlPanel = new SwingAtdl4jCompositePanel();
        JPanel strategyPanel = (JPanel) atdlPanel.buildAtdl4jCompositePanel(frame, options);


        String file = "requester/src/main/resources/tazer.xml";
        atdlPanel.parseFixatdlFile(file);
        atdlPanel.loadScreenWithFilteredStrategies();

        JPanel jPanel2 = new JPanel();
        JButton jButton = new JButton();
        jButton.setSize(10, 10);
        jButton.setVisible(true);
        jButton.setText("Send");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Map<BigInteger, String> parameterValues = getParameterValues();
                sendListeners.forEach(l -> l.onClick(parameterValues));
            }
        });

        jPanel2.add(jButton);

        frame.setLayout(new GridLayout(2, 1));
        frame.add(strategyPanel);
        frame.add(jPanel2);
        frame.setSize(475, 500);
        frame.setVisible(true);
    }

    public Map<BigInteger, String> getParameterValues() {
        Map<BigInteger, String> parameters = new HashMap<>();
        StrategyT strategyT = atdlPanel.getStrategies().getStrategy().get(0);
        List<ParameterT> parameter = strategyT.getParameter();
        for (ParameterT parameterT : parameter) {
            BigInteger fixTag = parameterT.getFixTag();
            String value = parameterT.getUse().value();
            parameters.put(fixTag, value);
        }
        return parameters;
    }

    public void addSendListener(SendListener sendListener) {
        sendListeners.add(sendListener);
    }

    public static interface SendListener {
        public void onClick(Map<BigInteger, String> parameters);
    }
}
