import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class SubspaceCommunicationNetwork {

    private List<SolarSystem> solarSystems;

    /**
     * Perform initializations regarding your implementation if necessary
     * @param solarSystems a list of SolarSystem objects
     */
    public SubspaceCommunicationNetwork(List<SolarSystem> solarSystems) {
        // TODO: YOUR CODE HERE
        this.solarSystems = solarSystems;
    }

    /**
     * Using the solar systems of the network, generates a list of HyperChannel objects that constitute the minimum cost communication network.
     * @return A list HyperChannel objects that constitute the minimum cost communication network.
     */
    public List<HyperChannel> getMinimumCostCommunicationNetwork() {
        List<HyperChannel> minimumCostCommunicationNetwork = new ArrayList<>();
        // TODO: YOUR CODE HERE
        List<Planet> maxOfSolarSystems = new ArrayList<>();
        for (SolarSystem ss: solarSystems){
            int max = 0;
            for (Planet p: ss.getPlanets()){
                if (max < p.getTechnologyLevel()){
                    max = p.getTechnologyLevel();
                }
            }
            for (Planet p: ss.getPlanets()){
                if (p.getTechnologyLevel() == max){
                    maxOfSolarSystems.add(p);
                    break;
                }
            }
        }
        List<HyperChannel> allWays = new ArrayList<>();
        for (int i = 0; i < maxOfSolarSystems.size()-1; i++){
            for (int j = i+1; j < maxOfSolarSystems.size(); j++){
                int maxTechLevel_i = maxOfSolarSystems.get(i).getTechnologyLevel();
                int maxTechLevel_j = maxOfSolarSystems.get(j).getTechnologyLevel();
                double cost = Constants.SUBSPACE_COMMUNICATION_CONSTANT * 2 / ((maxTechLevel_j + maxTechLevel_i));
                allWays.add(new HyperChannel(maxOfSolarSystems.get(i),maxOfSolarSystems.get(j), cost));
            }
        }
        Hashtable<Integer, String> my_dict = new Hashtable<Integer, String>();
        for (int i =0; i < maxOfSolarSystems.size(); i++){
            my_dict.put(i,maxOfSolarSystems.get(i).getId());
        }
        Hashtable<String, Integer> my_dict1 = new Hashtable<String, Integer>();
        for (int i =0; i < maxOfSolarSystems.size(); i++){
            my_dict1.put(maxOfSolarSystems.get(i).getId(),i);
        }
        Graph graph = new Graph(maxOfSolarSystems.size(),allWays.size());
        for (int i = 0; i < allWays.size(); i++){
            graph.edge[i].dest = my_dict1.get(allWays.get(i).getTo().getId());
            graph.edge[i].src = my_dict1.get(allWays.get(i).getFrom().getId());
            graph.edge[i].weight = allWays.get(i).getWeight();
        }
        graph.KruskalMST(minimumCostCommunicationNetwork,maxOfSolarSystems);

        return minimumCostCommunicationNetwork;
    }

    public void printMinimumCostCommunicationNetwork(List<HyperChannel> network) {
        double sum = 0;
        for (HyperChannel channel : network) {
            Planet[] planets = {channel.getFrom(), channel.getTo()};
            Arrays.sort(planets);
            System.out.printf("Hyperchannel between %s - %s with cost %f", planets[0], planets[1], channel.getWeight());
            System.out.println();
            sum += channel.getWeight();
        }
        System.out.printf("The total cost of the subspace communication network is %f.", sum);
        System.out.println();
    }
}
