package springclasses.aluno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor //não precisa mais de segundo parâmetro
public class AlunoRequestDto {
    private String nome;
}
