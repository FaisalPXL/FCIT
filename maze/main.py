#•	Abdullah Saad Alharbi - 2136600
#•	Mazen Abdulrahim Albalawi - 2135033
#•	Mohammed Shadi Alyamani - 2135971
#•	Faisal Hussain Al-Ragheeb - 2136580
#•	Mohammed Omar Qadi - 2136806

from collections import deque

# Define the maze as a 2D list
maze = [
    ['#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'],
    ['#', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', '#'],
    ['#', ' ', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', '#', '#', '#', '#', ' ', '#'],
    ['#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', '#'],
    ['#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#'],
    ['#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', ' ', '#'],
    ['#', ' ', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', ' ', '#'],
    ['#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'],
    ['#', ' ', '#', '#', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', '#', '#', '#', '#', '#', '#'],
    ['#', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', ' ', '.', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#'],
    ['#', '#', '#', '#', '#', ' ', '#', ' ', '#', '#', '#', '#', '#', ' ', '#', '#', '#', ' ', '#'],
    ['#', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '.', ' ', '#', ' ', ' ', ' ', '#', ' ', '#'],
    ['#', ' ', '#', ' ', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#'],
    ['#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#'],
    ['#', ' ', '#', '#', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#'],
    ['#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', '#'],
    ['#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', ' ', '#', ' ', '#'],
    ['#', 'G', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#'],
    ['#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#']
]


def find_start_goal(maze):
    # Find the start and goal positions in the maze
    for i in range(len(maze)):
        for j in range(len(maze[0])):
            if maze[i][j] == 'S':
                start = (i, j)
            elif maze[i][j] == 'G':
                goal = (i, j)
    return start, goal


def get_valid_neighbors(position, maze):
    # Get the valid neighbors of a position in the maze
    neighbors = []
    directions = [(1, 0), (-1, 0), (0, 1), (0, -1)]  # Down, Up, Right, Left
    for direction in directions:
        neighbor = (position[0] + direction[0], position[1] + direction[1])
        if (
                0 <= neighbor[0] < len(maze) and
                0 <= neighbor[1] < len(maze[0]) and
                maze[neighbor[0]][neighbor[1]] != '#'
        ):
            neighbors.append(neighbor)
    return neighbors


def dfs(maze, start, goal):
    stack = deque([(start, [])])  # Stack to store the current position and path
    visited = set()  # Set to store visited positions

    while stack:
        position, path = stack.pop()
        if position == goal:
            return path + [position]

        visited.add(position)
        neighbors = get_valid_neighbors(position, maze)
        for neighbor in neighbors:
            if neighbor not in visited:
                stack.append((neighbor, path + [position]))

    return None  # No path found


def bfs(maze, start, goal):
    queue = deque([(start, [])])  # Queue to store the current position and path
    visited = set()  # Set to store visited positions

    while queue:
        position, path = queue.popleft()
        if position == goal:
            return path + [position]

        visited.add(position)
        neighbors = get_valid_neighbors(position, maze)
        for neighbor in neighbors:
            if neighbor not in visited:
                queue.append((neighbor, path + [position]))

    return None  # No path found


# Main program

start, goal = find_start_goal(maze)

# Run DFS algorithm
dfs_path = dfs(maze, start, goal)
if dfs_path is None:
    print("DFS: No path found")
else:
    print("DFS Path:", dfs_path, '\n')

# Run BFS algorithm
bfs_path = bfs(maze, start, goal)
if bfs_path is None:
    print("BFS: No path found")
else:
    print("BFS Path:", bfs_path)