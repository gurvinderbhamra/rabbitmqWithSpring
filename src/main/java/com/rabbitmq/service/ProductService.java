package com.rabbitmq.service;

import com.rabbitmq.constants.Constants;
import com.rabbitmq.domain.Product;
import com.rabbitmq.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rabbitmq.constants.Constants.*;

@Service
public class ProductService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${rabbitmq.sfg.message.queue}")
    private String SFG_MESSAGE_QUEUE;

    private ProductRepository productRepository;

    private RabbitTemplate rabbitTemplate;

    public ProductService(ProductRepository productRepository, RabbitTemplate rabbitTemplate) {
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void create(Product product) {
        productRepository.save(product);
    }

    public void sendMessage(Long id) {
        log.info("Sending message to " + SFG_MESSAGE_QUEUE + ", id : " + id);
        Map<String, Long> map = new HashMap<>();
        map.put(ID, id);
        rabbitTemplate.convertAndSend(SFG_MESSAGE_QUEUE, map);
        log.info("Message sent successfully");
    }
}
