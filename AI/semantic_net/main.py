#•	Abdullah Saad Alharbi - 2136600
#•	Mazen Abdulrahim Albalawi - 2135033
#•	Mohammed Shadi Alyamani - 2135971
#•	Faisal Hussain Al-Ragheeb - 2136580
#•	Mohammed Omar Qadi - 2136806

import networkx as nx
import matplotlib.pyplot as plt

# Create an empty graph
G = nx.Graph()

# Add food items as nodes:
nodes = G.add_nodes_from(["Food",
                          "Protein",
                          "Egg",
                          "Fish",
                          "Carb",
                          "Potato",
                          "Rice",
                          "Fruit",
                          "Apple",
                          "Banana"])

# Add semantic relationships as edges:
edges = G.add_edges_from([("Food", "Protein"),
                          ("Food", "Fruit"),
                          ("Food", "Carb"),
                          ("Protein", "Fish"),
                          ("Protein", "Egg"),
                          ("Carb", "Rice"),
                          ("Carb", "Potato"),
                          ("Fruit", "Apple"),
                          ("Fruit", "Banana")])

# Add attributes for each node
attributes = {
    "Food": ["Eatable", "Expiration date"],
    "Protein": ["High protein", "Low carb", "Low fat"],
    "Carb": ["Low protein", "High carb", "Medium fat"],
    "Fruit": ["Good Flavor", "High sugar"],
    "Fish": ["Aquatic Animal"],
    "Egg": ["Animal Source"],
    "Rice": ["Grains", "Starch"],
    "Potato": ["Starch"],
    "Apple": ["Red", "Roundish"],
    "Banana": ["Yellow", "Curved"]
}

# 2 For loops to add attributes for all nodes
for node, attributes_of_node in attributes.items():
    for attribute in attributes_of_node:
        G.nodes[node][attribute] = True

for edge in G.edges:
    for key, values in attributes.items():
        if key == edge[0]:
            attributes[edge[1]] += values


# Save the positions of the nodes
pos = nx.spring_layout(G, seed=40, k=1.2)


# Method to add new nodes:
def add_node(new_node, attributes, old_node, kb_att):
    global pos
    G.add_node(new_node)
    G.add_edge(old_node, new_node)

    # For loop to add attributes for new nodes
    for attribute in attributes:
        G.nodes[new_node][attribute] = True
    kb_att[new_node] = attributes
    add_attributes(old_node, new_node, kb_att)

    pos[new_node] = pos[old_node]  # Set the initial position of the new node
    pos.update(nx.spring_layout(G, pos={new_node: pos[new_node]}, seed=40, k=1.2))  # Update the position of the new node only

    print("The node has been successfully inserted.")

def add_attributes(old_node, new_node, kb_att):
    for keys, values in kb_att.items():
        if keys == old_node:
            kb_att[new_node] += values


# Method to Delete The nodes:
def delete_node(my_node):
    """
    This function deletes a node from the graph G. If the node has more than one edge,
    it also deletes its children nodes and their edges, except for those connected to 'Food'.
    """
    # Check if the node exists in the graph
    if my_node in G.nodes:
        # Ensure the node to be deleted is not 'Food'
        if my_node != "Food":
            # Get the number of edges of the node
            num_edges = len(list(G.edges(my_node)))
            # If the node has more than one edge
            if num_edges > 1:
                children = list(G.neighbors(my_node))   # Get the children of the node
                # For each child of the node
                for child in children:
                    if child != "Food":                 # Ensure the child is not 'Food'
                        edges = list(G.edges(child))    # Get the edges of the child
                        # For each edge of the child
                        for edge in edges:
                            G.remove_edge(*edge)        # Remove the edge
                        G.remove_node(child)            # Remove the child node
            G.remove_node(my_node)                      # Remove the node

            if num_edges >= 2:
                print(f"The node '{my_node}' and its children have been successfully deleted.")
            else:
                print(f"The node '{my_node}'have been successfully deleted '.")
        else:
            print("Cannot delete the 'Food' node.")
    else:
        print(f"The node '{my_node}' not found in the graph.")


# Method to Search on the specific node:
def search_node(my_node):
    if G.has_node(my_node):  # If the graph has the node return True
        for node in G.nodes:
            if node == my_node:
                print("Node '{}', found in the graph.".format(my_node))
                for key, attribute in attributes.items():
                    if key == my_node:
                        print(f"And the attributes of this node is: {attribute}")
    else:                    # Else return False
        print("Node '{}', not found un the graph.".format(my_node))


def search_by_attributes(the_attributes, kb_att):
    the_keys = []
    for key, values in kb_att.items():
        for value in values:
            if value == the_attributes:
                the_keys.append(key)
    if len(the_keys) > 0:
        print(f"This attribute '{the_attributes}', found in this nodes '{the_keys}'")
    else:
        print(f"The attribute '{the_attributes}',is not in knowledge based")


def find_path(node1, node2):
    # If both nodes exist in the graph
    if G.has_node(node1) and G.has_node(node2):
        # Check for direct connections
        if G.has_edge(node1, node2):
            print(f"{node1} and {node2} are directly connected.")
        else:
            # Check for indirect connections through shared attributes and paths
            paths = nx.all_simple_paths(G, source=node1, target=node2)
            for path in paths:
                common_attributes = set(G.nodes[path[0]].keys())
                for node in path[1:]:
                    common_attributes.update(G.nodes[node].keys())  # Update with attributes of the nodes in the path
                if common_attributes:
                    print(f"Path: {path}")
                    break
            else:
                print(f"{node1} and {node2} are not directly or indirectly connected.")
    else:
        print("One or both of the nodes do not exist in the graph.")


# Draw the semantic network:
def draw_network():
    nx.draw_networkx_nodes(G, pos, node_color="gray", node_size=2000)        # Attributes of nodes
    nx.draw_networkx_labels(G, pos)                                          # Write the label at every node
    nx.draw_networkx_edges(G, pos, edge_color="black", width=2.5)            # Attributes of edges


# While loop to ask user until exit:
while True:
    # Flag to know if user want to do operations or no:
    flag = input("Do you want to do operations on the graph? ('y' for yes / 'e' to exit): ")

    if flag == 'e':
        print("Exiting...")
        break  # Exit the loop if the user enters 'e'

    if flag != 'y':
        print("Invalid input. Please enter 'y' to continue or 'e' to exit.")
        continue  # Continue the loop if the input is not 'y'

    op = input("'i' for insert / 'd' for delete / 's' for search / 'f' for find path / 'z' for search by attribute: ")

    if op == 'i':
        new_node = input("Name of the new node: ")
        attributes_of_node = input("Write the attributes of {} separated by comma: ".format(new_node))
        old_node = input("Name of the node you want to connect with: ")
        add_node(new_node, attributes_of_node.split(","), old_node, attributes)
    elif op == 'd':
        the_node = input("Name of the node you want to delete: ")
        delete_node(the_node)
    elif op == 's':
        the_node = input("Name of the node you want to search: ")
        search_node(the_node)
    elif op == 'f':
        node1 = input("Name of the first node: ")
        node2 = input("Name of the second node: ")
        find_path(node1, node2)
    elif op == 'z':
        the_attribute = input("Name of the attribute u want to search: ")
        search_by_attributes(the_attribute, attributes)
    else:
        print("Invalid operation. Please enter 'i', 'd', or 's', a.")

# Display the semantic network
draw_network()
plt.title("Food Semantic Network")
plt.axis("off")
plt.show()


