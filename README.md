# Minimum Vertex Cover
Group 4 competition-only branch. The program has been compiled on a mac machine. `.class` files are included in this branch.

## Compile (if needed)
At the project root directory run:
```
javac $(find . -name "*.java")
```

## Run MVC
Change directory to `/src`, then run 

`java main.java.JavaAlgo -inst <filename> -alg [BnB|LS] -time <cutoff in seconds> [-seed <random seed>]`.

An example shows below:

```bash
cd src    # now at /path/to/project/src/
java main.java.JavaAlgo -inst jazz.graph -alg LS -time 600 -seed 9
```

## Data and Output
All graphs must be under `src/data` folder. Outputs will exist under `src/output` folder.