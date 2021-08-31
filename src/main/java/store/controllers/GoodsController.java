package store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import store.dto.GoodDTO;
import store.entities.Good;
import store.repositories.GoodRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GoodsController {

    private final ModelMapper modelMapper;

    private final GoodRepository goodRepository;

    @PostMapping("/goods/get-list")
    public List<GoodDTO> getList() {

        List<Good> list = goodRepository.findAll();

        return list.stream()
                .map((Good good) -> modelMapper.map(good, GoodDTO.class))
                .collect(Collectors.toList());
    }
}