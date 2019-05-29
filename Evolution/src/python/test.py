import random
import Locator
from os.path import isfile
import sys
def fileChange(DNA):
    DNAFile = open(Locator.main("src\\python\\DNA\\DNA.EvoDNA"),"a")
    DNAFile.write(DNA+",")
    DNAFile.close()
def genDna():
    for i in range(0,250000):
        DNA=str(random.randint(1,9))
        fileChange(DNA)
def fileMake():
    newfile = open(Locator.main("src\\python\\DNA\\DNA.EvoDNA"),"w")
    newfile.close()
def main():
    if isfile(Locator.main("src\\python\\DNA\\DNA.EvoDNA")) == True:
        DNA = open(Locator.main("src\\python\\DNA\\DNA.EvoDNA"),"r")
        DNARead = DNA.read()
        if DNARead == "":
            DNA.close()
            genDna()
    else:
        fileMake()
        genDna()
main()
