package application;

import java.time.LocalDate;

public class Juego {

	private String Name, GameStatus, Comentario, StartDate, FinalDate;
	private int Score;

	public Juego(String StartDate, String FinalDate, String Name, int Score, String GameStatus,
			String Comentario) {
		super();
		this.StartDate = StartDate;
		this.FinalDate = FinalDate;
		this.Name = Name;
		this.Score = Score;
		this.GameStatus = GameStatus;
		this.Comentario = Comentario;
	}

	public Juego() {
		super();
	}

	public String getGameStatus() {
		return GameStatus;
	}

	public void setGameStatus(String GameStatus) {
		this.GameStatus = GameStatus;
	}

	public String getComentario() {
		return Comentario;
	}

	public void setComentario(String Comentario) {
		this.Comentario = Comentario;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String StartDate) {
		this.StartDate = StartDate;
	}

	public String getFinalDate() {
		return FinalDate;
	}

	public void setFinalDate(String FinalDate) {
		this.FinalDate = FinalDate;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int Score) {
		this.Score = Score;
	}

	@Override
	public String toString() {
		return "Juego [StartDate=" + StartDate + ", FinalDate=" + FinalDate + ", Name=" + Name + ", GameStatus="
				+ GameStatus + ", Comentario=" + Comentario + ", Score=" + Score + "]";
	}

}
