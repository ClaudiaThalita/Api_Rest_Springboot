package br.com.claudiathalita.springbootcommysql.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudiathalita.springbootcommysql.controller.dto.PessoaRq;
import br.com.claudiathalita.springbootcommysql.controller.dto.PessoaRs;
import br.com.claudiathalita.springbootcommysql.model.Pessoa;
import br.com.claudiathalita.springbootcommysql.repository.PessoaRepository;

@RestController
@RequestMapping(value="/pessoa")
public class PessoaController {
	
	@Autowired
	private final PessoaRepository pessoaRepository;
	
	
	public PessoaController(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}


	@RequestMapping(value="/", method=RequestMethod.GET)
	public List<PessoaRs> findAll(){
		var pessoas = pessoaRepository.findAll();
		return pessoas
				.stream()
				.map((p)-> PessoaRs.converter(p))
				.collect(Collectors.toList());
		
	}
	
	@RequestMapping(value= "/{id}",method=RequestMethod.GET)
    public PessoaRs findById(@PathVariable("id") Integer id) {
		var pessoa = pessoaRepository.getReferenceById(id);
        return PessoaRs.converter(pessoa);
    }
    
	@RequestMapping(value="/", method=RequestMethod.POST)
    public void savePerson(@RequestBody PessoaRq pessoa) {
        var p = new Pessoa();
        p.setNome(pessoa.getNome());
        p.setSobrenome(pessoa.getSobrenome());
        pessoaRepository.save(p);
    }
}
