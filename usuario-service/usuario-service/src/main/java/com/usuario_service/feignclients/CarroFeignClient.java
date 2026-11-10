package com.usuario_service.feignclients;

import com.usuario_service.modelos.Carro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name= "carro-service", url = "http://localhost:8088", path = "/carro")
public interface CarroFeignClient {

        @PostMapping
        Carro save(@RequestBody Carro carro);

        @GetMapping("/usuario/{usuarioId}")
        public List<Carro> getCarros(@PathVariable("usuarioId")int  usuarioId);
}