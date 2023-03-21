package org.akouma.stock.RestController;

import org.akouma.stock.kafkaService.kafkaproducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaRestController {
    @Autowired
    private kafkaproducerService kafkaproducerService;

    @GetMapping("/kafka")
    public void  getMessage(){

        kafkaproducerService.sendMessage("Hello JESUS @@@@ ");
    }
}
