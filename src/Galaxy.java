import java.util.*;

public class Galaxy {

    private final List<Planet> planets;
    private List<SolarSystem> solarSystems;

    public Galaxy(List<Planet> planets) {
        this.planets = planets;
    }

    /**
     * Using the galaxy's list of Planet objects, explores all the solar systems in the galaxy.
     * Saves the result to the solarSystems instance variable and returns a shallow copy of it.
     *
     * @return List of SolarSystem objects.
     */
    public List<SolarSystem> exploreSolarSystems() {
        solarSystems = new ArrayList<>();
        // TODO: YOUR CODE HERE
        complete();
        Hashtable<Integer, String> my_dict = new Hashtable<Integer, String>();
        for (int i =0; i < planets.size(); i++){
            my_dict.put(i,planets.get(i).getId());
        }
        boolean[] visited = new boolean[planets.size()];
        for (int v = 0; v < planets.size(); ++v){
            if (!visited[v]) {
                SolarSystem ss = new SolarSystem();
                DFSUtil(v, visited,ss, my_dict);
                solarSystems.add(ss);
            }
        }

        return new ArrayList<>(solarSystems);
    }
    public void DFSUtil(int v, boolean[] visited, SolarSystem ss,Hashtable<Integer, String> my_dict){
        visited[v] = true;
        ss.addPlanet(planets.get(v));
        for (String s : planets.get(v).getNeighbors()){
            int myKey = 0;
            for (int i = 0; i < my_dict.size(); i++){
                if (Objects.equals(my_dict.get(i), s)){
                    myKey = i;
                }
            }
            if (!visited[myKey]){
                DFSUtil(myKey,visited,ss,my_dict);
            }
        }

    }
    public void complete(){
        for (int i = 0; i < planets.size(); i++){
            for (String s : planets.get(i).getNeighbors()){
                int myKey = 0;
                for (int j = 0; j < planets.size(); j++){
                    if (Objects.equals(s, planets.get(j).getId())){
                        myKey = j;
                    }
                }
                if (!planets.get(myKey).getNeighbors().contains(planets.get(i).getId())){
                    planets.get(myKey).getNeighbors().add(planets.get(i).getId());
                }
            }
        }
    }

    public List<SolarSystem> getSolarSystems() {
        return solarSystems;
    }

    // FOR TESTING
    public List<Planet> getPlanets() {
        return planets;
    }

    // FOR TESTING
    public int getSolarSystemIndexByPlanetID(String planetId) {
        for (int i = 0; i < solarSystems.size(); i++) {
            SolarSystem solarSystem = solarSystems.get(i);
            if (solarSystem.hasPlanet(planetId)) {
                return i;
            }
        }
        return -1;
    }

    public void printSolarSystems(List<SolarSystem> solarSystems) {
        System.out.printf("%d solar systems have been discovered.%n", solarSystems.size());
        for (int i = 0; i < solarSystems.size(); i++) {
            SolarSystem solarSystem = solarSystems.get(i);
            List<Planet> planets = new ArrayList<>(solarSystem.getPlanets());
            Collections.sort(planets);
            System.out.printf("Planets in Solar System %d: %s", i + 1, planets);
            System.out.println();
        }
    }
}
