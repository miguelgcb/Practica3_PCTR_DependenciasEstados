package src.p03.c01;

public interface IParque {
	
	public abstract void entrarAlParque(String puerta) throws InterruptedException;

	public abstract void salirDelParque(String puerta) throws InterruptedException;

}
