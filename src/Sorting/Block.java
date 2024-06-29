package Sorting;
/*
public class Block <T> {
        T[] items;
        int count;
        Block next;
        @SuppressWarnings("unchecked")
       Block(int blockSize)
        {
            this.count = 0;
            this.items = (T[]) new Object[blockSize];
        }
        //shift right all elements, from the end of the block to the index
        private void shiftRight(int index)
        {
            for(int i = this.count; i > index; i--)
            {
                this.items[i] = this.items[i-1];
            }
            this.items[index] = null;
        }
        //shift left all elements, from the the index until the end of the block
        private void shiftLeft(int index)
        {
            for(int i = index; i < this.count; i++)
            {
                this.items[i-1] = this.items[i];
            }
            this.items[this.count-1] = null;
        }

        void add(T item)
        {
            this.items[this.count++] = item;
        }
        void add(int index, T item)
        {
            shiftRight(index);
            this.items[index] = item;
            this.count++;
        }

    T remove()
    {
        T result;
        if(this.count > 0)
        {
            this.count--;
            result = this.items[this.count];
            this.items[this.count] = null;
            return result;
        }
        return null;
    }
    T remove(int index)
    {
        T result = null;
        if(index < this.count)
        {
            result = this.items[index];
            shiftLeft(index+1);
            this.count--;
        }
        return result;
    }

    void moveHalfItems(Block nextBlock)
    {
        int halfIndex = this.count / 2;
        for(int i = halfIndex; i < this.count; i++)
        {
            nextBlock.add(this.items[i]);
//put the array position to null, to avoid loitering
            this.items[i] = null;
        }
//update the count to half the number of elements
        this.count = halfIndex;
    }
    boolean isFull()
    {
        return this.count == items.length;
    }

    private Block getBlockFromIndex(int globalIndex)
    {
        Block currentBlock = this.initialBlock;
        int relativeIndex = globalIndex;
        while(relativeIndex >= 0 && currentBlock != null)
        {
            if(relativeIndex < currentBlock.count)
            {
//the item we're searching for is in the current block
                return currentBlock;
            }
            relativeIndex -= currentBlock.count;
            currentBlock = currentBlock.next;
        }
        return null;
    }

    private int getLocalIndexFromIndex(int globalIndex)
    {
        Block currentBlock = this.initialBlock;
        int relativeIndex = globalIndex;
        while(relativeIndex >= 0 && currentBlock != null)
        {
            if(relativeIndex < currentBlock.count)
            {
//the item we're searching for is in the current block
                return relativeIndex;
            }
            relativeIndex -= currentBlock.count;
            currentBlock = currentBlock.next;
        }
        return -1;
    }
}*/
