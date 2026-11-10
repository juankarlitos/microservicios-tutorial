package com.motoservice.controlador;

import com.motoservice.entidades.Moto;
import com.motoservice.servicio.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moto")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> listaMotos(){
        List<Moto> moto = motoService.getAll();
        if(moto.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(moto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Moto> obtenerMoto(@PathVariable("id") int id){
        Moto moto = motoService.getMotoById(id);
        if(moto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moto);
    }
    @PostMapping
    public ResponseEntity<Moto> guardarCarro(@RequestBody Moto moto){
        Moto nuevaMoto = motoService.save(moto);
        return ResponseEntity.ok(nuevaMoto);
    }
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotosPorUsuarioId(@PathVariable("usuarioId") int usuarioId){
        List<Moto> motos = motoService.byUsuarioId(usuarioId);
        if(motos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(motos);
    }
}