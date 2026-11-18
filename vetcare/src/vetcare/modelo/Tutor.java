package vetcare.modelo;
import vetcare.excecao.DadoInvalidoException;
import vetcare.excecao.OperacaoInvalidaException;

import java.util.List;
import java.util.ArrayList;

/**
 * Tutor (dono) de um ou mais animais da clínica.
 */
public class Tutor extends Pessoa{

    /** Cria o tutor com telefone. */
    public Tutor(String nome, String cpf,String telefone){
        super(nome,cpf,telefone);
    }
    /** Cria o tutor sem telefone. */
    public Tutor(String nome,String cpf){
        super(nome,cpf);
    }
    private final List<Animal> animais = new ArrayList<>();

    /** Adiciona um animal ao tutor; nulo ou repetido não é aceito. */
    public void adicionarAnimal(Animal animal){
        if(animal == null){
            throw new DadoInvalidoException("Animal invalido!");
        }
        if(animais.contains(animal)){
            throw new OperacaoInvalidaException("Animal já adicionado!");
        }
        animais.add(animal);
    }

    /** Remove o animal do tutor; devolve false se ele não estava na lista. */
    public boolean removerAnimal(Animal animal){
        if(animais.contains(animal)){
            return  animais.remove(animal);
        }else{
            return false;
        }
    }

    /** Devolve uma cópia da lista de animais, que não pode ser alterada. */
    public List<Animal> getAnimais(){
        return List.copyOf(animais);
    }


    /** Descrição do tutor com o nome e a quantidade de animais. */
    @Override
    public String getDescricao(){
        return "Tutor: " + getNome() + " ("+ animais.size() + " animais)";
    }

}
