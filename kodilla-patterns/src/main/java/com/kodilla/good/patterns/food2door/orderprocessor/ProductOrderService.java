package com.kodilla.good.patterns.food2door.orderprocessor;

import com.kodilla.good.patterns.food2door.informationservice.InformationService;
import com.kodilla.good.patterns.food2door.purchase.PurchaseDto;
import com.kodilla.good.patterns.food2door.purchase.repository.PurchaseRepository;
import com.kodilla.good.patterns.food2door.purchase.PurchaseRequest;

public class ProductOrderService {

    private InformationService informationService;
    private OrderProcessor orderProcessor;
    private PurchaseRepository purchaseRepository;

    public ProductOrderService(final InformationService informationService,
                               final OrderProcessor orderProcessor,
                               final PurchaseRepository purchaseRepository) {
        this.informationService = informationService;
        this.orderProcessor = orderProcessor;
        this.purchaseRepository = purchaseRepository;
    }

    public PurchaseDto process(final PurchaseRequest purchaseRequest) {
        boolean isPurchased = orderProcessor.process(purchaseRequest.getSupplier(), purchaseRequest.getPurchaseTime(),
                purchaseRequest.getProducts(), purchaseRequest.getUser());

        if (isPurchased) {
            informationService.inform(purchaseRequest.getUser());
            purchaseRepository.createPurchaseDatabase(purchaseRequest.getSupplier(), purchaseRequest.getPurchaseTime(),
                    purchaseRequest.getProducts(), purchaseRequest.getUser());
            return new PurchaseDto(purchaseRequest.getUser(), true);
        } else {
            return new PurchaseDto(purchaseRequest.getUser(), false);
        }
    }
}

