package com.usuario_service.servicio;

import com.usuario_service.entidades.Usuario;
import com.usuario_service.feignclients.CarroFeignClient;
import com.usuario_service.feignclients.MotoFeignClient;
import com.usuario_service.modelos.Carro;
import com.usuario_service.modelos.Moto;
import com.usuario_service.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UsuarioService  {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    public List<Carro> getCarros(int usuarioId){
        List<Carro> carros = restTemplate.getForObject("http://localhost:8088/carro/usuario/" + usuarioId,List.class);
        return carros;
    }
    public List<Moto> getMotos(int usuarioId){
        List<Moto> motos = restTemplate.getForObject("http://localhost:8089/moto/usuario/" + usuarioId,List.class);
        return motos;
    }

    //Feing Clients
    public Carro saveCarro(int usuarioId, Carro carro){
        carro.setUsuarioId(usuarioId);
        Carro nuevoCarro = carroFeignClient.save(carro);
        return nuevoCarro;
    }
    public List<Usuario> getAll(){

        return usuarioRepository.findAll();
    }
    public Usuario getUsuarioById(int id){

        return usuarioRepository.findById(id).orElse(null);
    }
    public Usuario save(Usuario usuario){
        Usuario nuevoUsuario =  usuarioRepository.save(usuario);
        return nuevoUsuario;
    }

    public Moto saveMoto(int usuarioId, Moto moto){
        moto.setUsuarioId(usuarioId);
        Moto nuevoMoto = motoFeignClient.save(moto);
        return nuevoMoto;
    }

    public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){
        Map<String, Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if(usuario == null){
            resultado.put("Mensaje","Usuario no encontrado");
            return resultado;
        }

        resultado.put("Usuario", usuario);
        List<Carro> carros = carroFeignClient.getCarros(usuarioId);
        if(carros.isEmpty()){
            resultado.put("Carros", "el usuario no tiene carros");
        } else  {
            resultado.put("Carros", carros);
        }

        List<Moto> motos = motoFeignClient.getMotos(usuarioId);
        if(motos.isEmpty()){
            resultado.put("Motos", "el usuario no tiene motos");
        } else   {
            resultado.put("Motos", motos);
        }
        return resultado;
    }
}