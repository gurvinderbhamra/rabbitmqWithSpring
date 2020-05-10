package com.rabbitmq.listener;

import com.rabbitmq.domain.Product;
import com.rabbitmq.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static com.rabbitmq.constants.Constants.ID;

@Component
public class ProductMessageListener {

    private ProductRepository productRepository;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public ProductMessageListener(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Whenever any message is received in the queue, this method will be called.
     * @param message contains the message body
     */
    public void messageReceived(Map message) {
        log.info("Message received : " + message);
        long id = (long) message.get(ID);
        Product product = productRepository.findById(id).orElse(null);
        if (Objects.nonNull(product)) {
            log.info("Product sent : " + product.getName());
        } else {
            log.error("Product not found by id : " + id);
        }
    }
}
