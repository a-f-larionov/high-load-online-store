package store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/goods/update")
    public void update(@RequestBody GoodDTO goodDTO) throws Exception {

        System.out.println(goodDTO.id);
        System.out.println(goodDTO.name);

        if (goodDTO.id == null) throw new Exception("Need a good ID");

        Good good = modelMapper.map(goodDTO, Good.class);

        goodRepository.save(good);
    }

    @PostMapping("/goods/add")
    public void add(@RequestBody GoodDTO goodDTO) throws Exception {

        System.out.println(goodDTO.id);
        System.out.println(goodDTO.name);

        if (goodDTO.id != null) throw new Exception("Good ID must be null");

        Good good = modelMapper.map(goodDTO, Good.class);

        goodRepository.save(good);
    }

    @PostMapping("/goods/delete")
    public void delete(Long id) {

        goodRepository.deleteById(id);
    }
}