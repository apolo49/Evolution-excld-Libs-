from lib.PIL import Image
import random
import Locator
from lib.noise.noise import pnoise2 as perlin
import math
size = 2048**2
octaves = 6
persistence = 0.0005
lacunarity = 0.5
def drawImage():
    testImage = Image.new("RGB", (2048,2048), (255,255,255))
    pixel = testImage.load()
    for x in range(2048):
        for y in range(2048):
            freq = 64.0 * octaves
            p=int(perlin(x / freq, y / freq, octaves,persistence,lacunarity,size,size,0) * 127.0 + 128.0)
            pixel[x,y]=(p,p,p)
    return testImage
def main():
    finalImage = drawImage()
    finalImage.save(Locator.main("res\\textures\\world\\heightMap.png"))
if __name__ == "__main__":
    main()
