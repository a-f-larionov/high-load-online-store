package store.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.dto.PurchaseDTO;
import store.entities.Good;
import store.entities.Purchase;
import store.repositories.GoodRepository;
import store.repositories.PurchaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PurchaseController {

    private final ModelMapper modelMapper;

    private final GoodRepository goodRepository;

    private final PurchaseRepository purchaseRepository;

    @PostMapping("/purchase/makeOne")
    @Transactional
    public void purchase(@RequestBody PurchaseDTO purchaseDTO) throws Exception {

        // получить товар из бд
        Optional<Good> goodOptional = goodRepository.findById(purchaseDTO.getGoodId());

        // проверить наличие товара и кол-ва
        if (!goodOptional.isPresent()) {
            throw new Exception("Good not found" + purchaseDTO.getGoodId());
        }

        Good good = goodOptional.get();

        if (good.getQuantity() < purchaseDTO.getQuantity()) {
            throw new Exception("Goods not amount enough");
        }

        // снизить кол-во на складе
        good.setQuantity(good.getQuantity() - purchaseDTO.getQuantity());

        goodRepository.save(good);

        // создать покупка
        Purchase purchase = new Purchase();

        purchase.setGoodId(good.getId());
        purchase.setQuantity(purchaseDTO.getQuantity());

        purchaseRepository.save(purchase);
    }

    @PostMapping("/purchase/get-list")
    public List<PurchaseDTO> getList() {

        List<Purchase> list = purchaseRepository.findAll();

        return list.stream()
                .map((Purchase purchase) -> modelMapper.map(purchase, PurchaseDTO.class))
                .collect(Collectors.toList());
    }
}
