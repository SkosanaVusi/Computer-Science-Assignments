# NAME: VUSI PRINCE SKOSANA
# STUDENT NUMBER: SKSPVX001
import sys
from random import randint

def FIFO(size, pages):
    memory = []                                         # memory pages
    page_fault = 0                                      # counter for if page not in memory
    lst_mmry_indx = 0                                   # index of page from pages after memory is full
    
    # taking the first pages into memory until it is full
    for m in range(0, len(pages)):
        if size == len(memory):                         # if the memory full
            lst_mmry_indx = m                    
            break                                       # break out of loop after memory is filled at first
        current = pages[m]                              # current disk page in the loop
        if current in memory:                           # if there is a hit, skip page
            continue
        page_fault += 1                                 
        memory.append(current)                          # move to memory if there is a page fault
        # print(memory)
    
    first_index = 0                                     # tracks index to be swapped in main memory, starting from the first in, at index 0.
    # Loop - starting from index after the last index of page in main memory.
    for pages_index in range(lst_mmry_indx, len(pages)):
        if pages[pages_index] in memory:                # if there is a hit, skip
            # print(memory)
            continue
        else:                                           # if there is a miss
            page_fault += 1                             
            memory[first_index] = pages[pages_index]    # replace memory page at 'first_index' with that page
            first_index =(first_index + 1) % size       # move position to next page
            # print(memory)
    return page_fault


def LRU(size, pages):
    memory = []                                         # memory pages
    page_fault = 0                                      # counter for if page not in memory
    lst_mmry_indx = 0                                   # index of page from pages after memory is full
    
    # taking the first pages into memory until it is full
    for m in range(0, len(pages)):
        if size == len(memory):                         # if the memory full
            lst_mmry_indx = m                    
            break                                       # break out of loop after memory is filled for the first time
        current = pages[m]                              # current disk page in the loop
        if current in memory:                           # if there is a hit,
            memory.remove(current)                      # remove current disk page from wherever position in memory
            memory.insert(0, current)                   # reposition it to the start(first index) as it is most recent
            continue
        page_fault += 1
        memory.insert(0, current)                       # insert at first position as it is most recent
        # print(memory)

    # Loop - starting from index after the last index of page in main memory.    
    for pages_index in range(lst_mmry_indx, len(pages)):
        if pages[pages_index] in memory:                # if there is a hit in memory,
            memory.remove(pages[pages_index])           # remove current disk page from wherever position in memory
            memory.insert(0, pages[pages_index])        # reposition it to the start(first index) as it is most recent
            # print(memory)
        else:                                           # if there is a miss,
            page_fault += 1
            memory.pop()                                # remove the last(least) page in memory
            memory.insert(0, pages[pages_index])        # insert the disk page in first position(as most recent)
            # print(memory)
    return page_fault
    
def OPT(size, pages):
    memory = []                                         # memory pages
    page_fault = 0                                      # counter for if disk page NOT in memory
    lst_mmry_indx = 0                                   # index of page from disk pages after memory is full
  
    # taking first disk pages into memory until it is full
    for m in range(0, len(pages)):
        if size == len(memory):                         # if the memory full.
            lst_mmry_indx = m                    
            break                                       # break out of loop after memory is filled at first
        current = pages[m]                              # current disk page in the loop
        if current in memory:                           # if there is a hit, skip page
            continue
        page_fault += 1                                 
        memory.append(current)                          # move disk page to memory if there is a page fault
        # print(memory)

    # Loop - starting from index after the last index of value in main memory.    
    for pages_index in range(lst_mmry_indx, len(pages)):
        if pages[pages_index] in memory:                # if there is a hit, skip
            # print(memory)
            continue
        else:                                           # if there is a miss
            if pages_index < len(pages)-1:              # if disk pages index is not the last disk pages index
                value = 0
                last_index_pos = -1                     # stores furthest index of page from memory in disk pages
                page_fault += 1
                for m_index in range(0, len(memory)):   # looping through memory pages 
                    if memory[m_index] not in pages[pages_index+1:]: # if memory page not in the rest of disk pages from the current disk page
                        memory[m_index] = pages[pages_index]         # replace current memory page with current disk page not in memory.
                        # print(memory)
                        break                                        # break out of memory loop
                    else:                                            # if memory page in rest of disk pages
                        a = len(pages) - 1 - pages[::-1].index(memory[m_index]) # stores furthest index of memory page from disk pages
                        if a > last_index_pos:                                  # compare if index of 'this' memory page is furthest than the one already tested
                            last_index_pos = a                                  # store the furthest index
                            value = memory[m_index]                             # the memory page of the furthest of them all

                        if m_index == len(memory)-1:                            # if the last page in memory 
                            memory[memory.index(value)] = pages[pages_index]    # replace the furthest memory page
                            last_index_pos = -1                                 # reset
                            value = 0                                           # reset
                            # print(memory)
            else:                                    # the last page index
                if pages[pages_index] not in memory: # if disk page NOT in the memory
                    page_fault += 1
                    memory[0] = pages[pages_index]  # replace first page in memory with disk page
                    # print(memory)
                else:                               # if disk page IN memory, do nothing
                    # print(memory)
                    continue
    return page_fault


def main():
    #...TODO...
    size = int(sys.argv[1])
    pages = []

    # Randomising reference pages
    for e in range(8):
        pages.append(randint(0, 9))

    print("FIFO", FIFO(size,pages), "page faults.")
    print ("LRU", LRU(size,pages), "page faults.")
    print ("OPT", OPT(size,pages), "page faults.")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python paging.py [number of pages]")
    else:
        main()
