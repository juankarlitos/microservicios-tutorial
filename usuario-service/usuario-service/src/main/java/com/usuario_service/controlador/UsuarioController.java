package com.usuario_service.controlador;

import com.usuario_service.entidades.Usuario;
import com.usuario_service.modelos.Carro;
import com.usuario_service.modelos.Moto;
import com.usuario_service.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.getAll();
        if(usuarios.isEmpty()){

            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(usuarios);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario>obtenerUsuario(@PathVariable ("id") int id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        if(usuario == null){
            return  ResponseEntity.notFound().build();
        }
         return  ResponseEntity.ok(usuario);
    }
    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return  ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }
    @GetMapping("/carros/{usuarioId}")
    public ResponseEntity<List<Carro>> listarCarros(@PathVariable("usuarioId")int Id){
        Usuario usuario = usuarioService.getUsuarioById(Id);
        if(usuario == null){
            return  ResponseEntity.notFound().build();
        }
        List<Carro> carros = usuarioService.getCarros(Id);
        return  ResponseEntity.ok(carros);
    }
    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId")int Id){
        Usuario usuario = usuarioService.getUsuarioById(Id);
        if(usuario == null){
            return  ResponseEntity.notFound().build();
        }
        List<Moto> motos = usuarioService.getMotos(Id);
        return  ResponseEntity.ok(motos);
    }

    @PostMapping("/carro/{usuarioId}")
    public  ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId")int usuarioId, @RequestBody Carro carro){

        Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
        return ResponseEntity.ok(nuevoCarro);
    }

    @PostMapping("/moto/{usuarioId}")
    public  ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId")int usuarioId, @RequestBody Moto moto){

        Moto nuevoMoto = usuarioService.saveMoto(usuarioId, moto);
        return ResponseEntity.ok(nuevoMoto);
    }

    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId")int usuarioId){
        Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
        return  ResponseEntity.ok(resultado);
    }
}