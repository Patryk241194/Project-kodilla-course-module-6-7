package com.kodilla.hibernate.invoice.dao;

import com.kodilla.hibernate.invoice.Invoice;
import com.kodilla.hibernate.invoice.Item;
import com.kodilla.hibernate.invoice.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class InvoiceDaoTestSuite {

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private ProductDao productDao;

    @Test
    void testInvoiceDaoSave() {
        //Given & When
        Product product1 = new Product("Product 1");
        Product product2 = new Product("Product 2");
        productDao.save(product1);
        productDao.save(product2);
        int productId1 = product1.getId();
        int productId2 = product2.getId();

        Item item1 = new Item(product1, BigDecimal.valueOf(40.0), 6);
        Item item2 = new Item(product2, BigDecimal.valueOf(20.0), 2);

        Invoice invoice = new Invoice("1");
        item1.setInvoice(invoice);
        item2.setInvoice(invoice);
        invoice.getItems().add(item1);
        invoice.getItems().add(item2);
        invoiceDao.save(invoice);
        int invoiceId = invoice.getId();

        //Then
        Assertions.assertNotEquals(0, invoiceId);
        Assertions.assertNotEquals(0, productId1);
        Assertions.assertNotEquals(0, productId2);
        Assertions.assertEquals(2, invoice.getItems().size());
        Assertions.assertEquals(BigDecimal.valueOf(240.0), invoice.getItems().get(0).getNetValue());
        Assertions.assertEquals(BigDecimal.valueOf(40.0), invoice.getItems().get(1).getNetValue());

        //CleanUp
        productDao.deleteById(productId1);
        productDao.deleteById(productId2);
        invoiceDao.deleteById(invoiceId);
    }
}
