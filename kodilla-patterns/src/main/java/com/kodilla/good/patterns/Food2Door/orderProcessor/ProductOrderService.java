package com.kodilla.good.patterns.Food2Door.orderProcessor;

import com.kodilla.good.patterns.Food2Door.informationService.InformationService;
import com.kodilla.good.patterns.Food2Door.purchase.PurchaseDto;
import com.kodilla.good.patterns.Food2Door.purchase.repository.PurchaseRepository;
import com.kodilla.good.patterns.Food2Door.purchase.PurchaseRequest;

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
            purchaseRepository.createPurchase(purchaseRequest.getSupplier(), purchaseRequest.getPurchaseTime(),
                    purchaseRequest.getProducts(), purchaseRequest.getUser());
            return new PurchaseDto(purchaseRequest.getUser(), true);
        } else {
            return new PurchaseDto(purchaseRequest.getUser(), false);
        }
    }
}
