# Minimum Vertex Cover
Group 4 competition-only branch. The program has been compiled on a mac machine. The `group4.jar` file is included in this branch as the executable.

## Usage Example
```
java -jar group4.jar -inst power.graph -alg LS -time 60 -seed 9
```

You can move the `.jar` file to any directory but please make sure graphs are in the same folder with the `.jar`.

If the `group4.jar` does not work, please see below to compile and run the algorithm yourself.

## Track(s)
LS

## Approach Description

### BnB
N/A.

### LS
Hill Combling: starting from a random initial VC, remove vertices in ascending order based on vertex degrees.

Time complexity: O(|E| + |V| log |V|)

# If group4.jar Didn't Work
## Compile (if needed)
At the project present directory `/path/to/project` run:
```
javac $(find . -name "*.java") -d bin
```

## Run MVC
Change directory to `/bin`

`cd bin`

then run

`java main.java.JavaAlgo -inst <filename> -alg [LS] -time <cutoff in seconds> [-seed <random seed>]`.

An example shows below:

```bash
cd bin    # now at /path/to/project/bin/
java main.java.JavaAlgo -inst power.graph -alg LS -time 60 -seed 100
```

## Other

See [here](https://github.com/ChujieChen/Minimum-Vertex-Cover/tree/develop) to get the latest readme.
