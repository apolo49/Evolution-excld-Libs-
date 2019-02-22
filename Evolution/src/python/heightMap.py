from lib.PIL import Image
import random
import os
from lib.noise.noise import pnoise2 as perlin
import math
size = 2048
octaves = 6
persistence = 0.05
lacunarity = 2.0
def drawImage():
    testImage = Image.new("RGB", (2048,2048), (255,255,255))
    pixel = testImage.load()
    for x in range(2048):
        for y in range(2048):
            freq = 16.0 * octaves
            p=int(perlin(x / freq, y / freq, octaves,persistence,lacunarity,size,size) * 127.0 + 128.0)
            pixel[x,y]=(p,p,p)
    return testImage
def main():
    finalImage = drawImage()
    print(os.path.abspath(__file__))
    finalImage.save(os.path.abspath(__file__).strip("src\\python\\heightMap.py")+"tion\\res\\heightMap.png")
if __name__ == "__main__":
    main()
