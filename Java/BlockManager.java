/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 */

import java.util.*;

/**
 * This class simplifies outputs to the console by keeping text into blocks.
 */
public class BlockManager {
    // initialize variables
    ArrayList<Block> layout;     // array of blocks

    // constructor
    public BlockManager(ArrayList<Block> blocks) {
        this.layout = blocks;
    }

    // constructor overload - 1 input (Block[])
    public BlockManager(Block[] blocks) {
        this(new ArrayList<>(List.of(blocks)));
    }

    // constructor overload - 1 input (Block)
    public BlockManager(Block block) {
        this(new ArrayList<>(List.of(block)));
    }

    // constructor overload - 0 inputs
    public BlockManager() {
        this(new ArrayList<>(List.of(new Block())));
    }

    // toString override
    @Override
    public String toString() {
        return draw(true);
    }

    /**
     * This method adds a block to the current block layout.
     *
     * @param block the block to add to the manager
     * @param position   int detailing what order in the layout to add the block to
     */
    public void add(Block block, int position) {
        // check if block of the same type is in array
        for (int i = 0; i < layout.size(); i++) {
            if (Objects.equals(layout.get(i).type, block.type)) {
                layout.set(i, block);
                return;
            }
        }
        // add a free spot to the block manager

        // add the block to the layout based on keyword

    }

    /**
     * This method updates all blocks in the current manager.
     */
    public void update() {
        for (Block block : this.layout) {

        }
    }

    /**
     * This method draws a border around the block and returns the resultant string.
     *
     * @param quiet if false, does not print the block to the command window.
     */
    public String draw(boolean quiet) {
        // find max width of all blocks
        int maxWidth = 0;
        for (Block block : this.layout) {
            if (block.width > maxWidth) {
                maxWidth = block.width;
            }
        }
        // initialize variables
        StringBuilder output = new StringBuilder();
        String border = "\u2551";
        String topBorder = "\u2554" + "\u2550".repeat(maxWidth + 1) + "\u2557";
        String midBorder = "\u2560" + "\u2550".repeat(maxWidth + 1) + "\u2563";
        String botBorder = "\u255A" + "\u2550".repeat(maxWidth + 1) + "\u255D";
        // draw each block in the layout
        output.append(topBorder).append('\n');
        for (int i = 0; i < this.layout.length; i++) {
            // print out the lines of the block with borders
            for (String line : this.layout[i].contents.split("\n")) {
                output.append(border).append(line).append(border).append('\n');
            }
            // print a dividing border if there are more blocks in the layout
            if (i != layout.length - 1) {
                output.append(midBorder).append('\n');
            }
        }
        output.append(botBorder);
        // print out blocks
        if (!quiet) {
            // "clear screen"
            System.out.println("\n".repeat(80));
            System.out.println(output);
        }
        return output.toString();
    }

    // overload - 0 inputs
    public void draw() {
        draw(false);
    }
}
