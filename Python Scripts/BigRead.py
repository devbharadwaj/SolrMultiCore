#!/usr/bin/python
import sys

def read_in_chunks(file_object, chunk_size=1024):
    while True:
        data = file_object.read(chunk_size)
        if not data:
            break
        yield data


f = open(sys.argv[-1])
for piece in read_in_chunks(f):
    print piece
    a= raw_input()
    if (a == 'q'):
        break
