package regras;

public interface Observable {
	public void addObserver(Observer o);
	public void removeObserver(Observer o);
	public Object get(); // primeira coisa é retornar o tipo do observado(Por enquanto: arma ou ctrlRegras)
}
