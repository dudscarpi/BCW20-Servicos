package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Pagamento;
import com.soulcode.Servicos.Models.StatusPagamento;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ChamadoRepository chamadoRepository;

    public List<Pagamento> mostrarTodosPagamentos(){
        return pagamentoRepository.findAll();
    }

    @Cacheable(value = "pagamentoCache", key = "#idPagamento")
    public Pagamento mostrarPagamentoPeloId(Integer idPagamento){
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        return pagamento.orElseThrow();
    }

    @Cacheable(value = "pagamentoCache", key = "#status")
    public List<Pagamento> mostrarPagamentosPeloStatus(String status){
        return pagamentoRepository.findByStatus(status);
    }


    @Cacheable(value = "pagamentoCache", key = "#status")
    public List<?> quantidadeDePagamentosPorStatus(){
        return pagamentoRepository.quantidadeDePagamentosPorStatus();
    }

    @CachePut(value = "pagamentoCache", key = "#idChamado")
    public Pagamento cadastrarPagamento(Pagamento pagamento, Integer idChamado){
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        if (chamado.isPresent()){
            pagamento.setIdPagamento(idChamado);
            pagamento.setStatus(StatusPagamento.LANCADO);
            pagamentoRepository.save(pagamento);

            chamado.get().setPagamento(pagamento);
            chamadoRepository.save(chamado.get());
            return pagamento;
        }else{
            throw new RuntimeException();
        }

    }

    @CachePut(value = "pagamentoCache", key = "#pagamento")
    public Pagamento editarPagamento(Pagamento pagamento){
        return pagamentoRepository.save(pagamento);
    }

    @CachePut(value = "pagamentoCache", key = "#idPagamento")
    public Pagamento modificarStatusPagamento(Integer idPagamento,String status){
        Pagamento pagamento = mostrarPagamentoPeloId(idPagamento);

        switch (status){
            case "LANCADO":
                pagamento.setStatus(StatusPagamento.LANCADO);
                break;
            case "QUITADO":
                pagamento.setStatus(StatusPagamento.QUITADO);
                break;
        }
        return pagamentoRepository.save(pagamento);
    }

    @Cacheable("pagamentoCache")
    public List<List> orcamentoComServicoCliente(){

        return pagamentoRepository.orcamentoComServicoCliente();
    }
}
