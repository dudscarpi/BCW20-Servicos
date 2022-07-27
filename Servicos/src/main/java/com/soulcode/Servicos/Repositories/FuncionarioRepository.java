package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Optional<Funcionario> findByEmail(String email);
    // Optional<Funcionario> findByNome

    //Optional<Funcionario> findByNomeAndEmailAndFoto(String nome, String email, String foto);
    List<Funcionario> findByCargo(Optional<Cargo> cargo);

    @Query(value="SELECT COUNT(id_funcionario), cargo.nome FROM funcionario, cargo WHERE funcionario.id_cargo =:idCargo AND cargo.id_cargo =:idCargo GROUP BY cargo.nome", nativeQuery=true)
    List<?> quantidadeDeFuncionariosPorCargo(Integer idCargo);

    @Query(value="SELECT * FROM funcionario WHERE foto IS NULL;", nativeQuery = true)
    List<Funcionario> funcionariosComFotoNull();
}
