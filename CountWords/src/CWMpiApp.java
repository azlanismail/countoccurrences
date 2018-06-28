import mpi.*;

public class CWMpiApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MPI.Init(args);
		int me = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();
		System.out.println("Hi from <"+me+">");
		MPI.Finalize();

	}

}
