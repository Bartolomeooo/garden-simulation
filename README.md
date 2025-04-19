![Garden Simulation](https://github.com/user-attachments/assets/dd438f51-755a-45d7-a6b8-3ce37f602f6d)
# Flower Garden Simulation

The Flower Garden Simulation is a Java program that simulates the behavior of a flower garden. It allows you to manipulate flowers, insects, and weeds within the garden, and observe their interactions and growth over time.

<div align="center">
  <img src="https://github.com/user-attachments/assets/e38d0062-4b70-4d9f-a5c7-82b09983bd04" width="400"/>
</div>

## Overview

The simulation is implemented using object-oriented programming principles. It utilizes a two-dimensional array to store instances of classes that implement the Flower interface. Each element in the array represents a flower in the garden, and its state is updated based on various factors such as hydration level, health points, presence of insects, and weeds.

The simulation provides the following functionalities:

- **Flower Management**: You can create and manipulate different types of flowers, such as red, yellow, and blue flowers. Each flower has specific attributes and behaviors.

- **Insect Infestation**: Insects can appear in the garden and affect the health of flowers.

- **Weed Growth**: Weeds can grow in the garden and compete with flowers for resources. They can spread and occupy neighboring flower spaces if not controlled.

- **Simulation Updates**: The garden's state is updated periodically, simulating the passage of time. Flowers' conditions are checked and updated based on their needs, and the presence of insects and weeds is monitored.

- **Statistics Tracking**: The simulation keeps track of various statistics, such as the number of flowers and the number of insects or weeds. The statistics are saved to a file for analysis and monitoring.

## Usage

To run the Flower Garden Simulation, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in your Java development environment.
3. Compile and run the main program.
4. Modify the simulation parameters, such as the initial number of flowers, the movement pattern of the gardener, and the simulation duration, as needed.
5. Monitor the simulation as it progresses and observe the changes in the garden's state.
6. Analyze the statistics file generated after each simulation run.
