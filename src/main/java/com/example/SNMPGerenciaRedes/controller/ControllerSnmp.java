package com.example.SNMPGerenciaRedes.controller;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ControllerSnmp {

    @GetMapping("/api/dados-snmp")
    public Map<String, String> coletarDadosSnmp(){
        Map<String,String> dados = new HashMap<>();

        try {
            TransportMapping<UdpAddress>transport = new DefaultUdpTransportMapping();
            transport.listen();
            Snmp snmp = new Snmp(transport);

            Address targetAddress = new UdpAddress("127.0.0.1/161");
            CommunityTarget<Address> target = new CommunityTarget<>();
            target.setCommunity(new OctetString("public"));
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(1500);
            target.setVersion(SnmpConstants.version2c);

            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.1.0")));
            pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.3.0")));
            pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.4.0")));
            pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.5.0")));
            pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.6.0")));
            pdu.setType(PDU.GET);

            ResponseEvent<Address> responseEvent = snmp.send(pdu,target);

            if (responseEvent != null && responseEvent.getResponse() != null) {
                PDU responsePDU = responseEvent.getResponse();

                dados.put("sysDescr", responsePDU.get(0).getVariable().toString());
                dados.put("sysUpTime", responsePDU.get(1).getVariable().toString());
                dados.put("sysContact", responsePDU.get(2).getVariable().toString());
                dados.put("sysName", responsePDU.get(3).getVariable().toString());
                dados.put("sysLocation", responsePDU.get(4).getVariable().toString());
            }else {
                dados.put("erro", "O SNMP não respondeu.");
            }
            snmp.close();

        }catch (Exception e) {
            dados.put("erro","Erro na conexão" + e.getMessage());
        }
        return dados;






    }

}
