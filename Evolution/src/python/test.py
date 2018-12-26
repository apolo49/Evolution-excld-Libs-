import random
import os
def fileChange(DNA):
    DNAFile = open(os.path.abspath(__file__).strip("test.py")+"DNA\\DNA.EvoDNA","a")
    DNAFile.write(DNA+",")
    DNAFile.close()
def genDna():
    for i in range(0,250000):
        DNA=str(random.randint(1,9))
        fileChange(DNA)
def fileMake():
    newfile = open(os.path.abspath(__file__).strip("test.py")+"DNA\\DNA.EvoDNA","w")
    newfile.close()
def main():
    if os.path.isfile(os.path.abspath(__file__).strip("test.py")+"DNA\\DNA.jar"):
        genDna()
    else:
        fileMake()
        genDna()
main()
