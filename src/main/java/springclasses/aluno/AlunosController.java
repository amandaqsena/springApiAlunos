package springclasses.aluno;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import springclasses.aluno.dto.AlunoRequestDto;
import springclasses.aluno.utils.AlunoId;

@RestController
@RequestMapping(path={"/alunos"})
public class AlunosController {
    private List<Aluno> alunosDB = new ArrayList<>();

    @PostMapping
    public Aluno criarAluno(
        @RequestBody AlunoRequestDto request
    ) {
        final Aluno aluno = new Aluno(AlunoId.incrementId(),
                            request.getNome());
        alunosDB.add(aluno);
        return aluno;
    }

    @GetMapping
    public List<Aluno> listarAlunos(
        @RequestParam Optional<String> prefixo
    ) {
        return prefixo.map(prf -> alunosDB.stream()
                        .filter(aluno -> aluno.getNome().startsWith(prf))
                        .collect(Collectors.toList())).orElseGet(()->alunosDB);
        
        
    }

    @GetMapping(path = {("/{id}")})
    public Aluno buscaAluno(
        @PathVariable int id
    ) {
        return alunosDB.stream()
                    .filter(aluno -> aluno.getId() == id)
                    .findFirst().orElseThrow(
                        () -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, 
                            "Não temos aluno com o ID." + id));
    }

    @PutMapping(path = {("/{id}")})
    public Aluno mudaNomeAluno(
        @PathVariable int id,
        @RequestBody AlunoRequestDto request
    ){
        Aluno alunoId = alunosDB.stream()
            .filter(aluno -> aluno.getId() == id)
            .findFirst().orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "Não temos aluno com o ID." + id));
        
        alunoId.setNome(request.getNome());

        return alunoId;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = {("/{id}")})
    public void deletaAluno(
        @PathVariable int id
    ){
        Aluno alunoRemoverAluno = alunosDB.stream()
        .filter(aluno -> aluno.getId() == id)
        .findFirst().orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Não temos aluno com o ID." + id));
        
        alunosDB.remove(alunoRemoverAluno);
    }
}
