from lib.PIL import Image
import random
import os
from lib.noise.noise import pnoise2 as perlin
pixelColour = [[0,0,0],[255,0,0],[0,255,0],[0,0,255]]
size = 2048
octaves = 6
persistence = 0.05
lacunarity = 2.0
def drawImage():
    testImage = Image.new("RGB", (2048,2048), (255,255,255))
    pixel = testImage.load()
    for x in range(2048):
        for y in range(2048):
            scale=random.randint(100,100)
            gen= perlin(size/scale,size/scale,octaves=octaves,persistence=persistence,lacunarity=lacunarity,repeatx=size,repeaty=size,base=0)
            if gen >0.4:
                RGB = pixelColour[3]
            elif gen <-0.4:
                RGB = pixelColour[1]
            elif gen == 0:
                RGB = pixelColour[2]
            else:
                RGB = pixelColour[0]
            pixel[x,y]=(RGB[0],RGB[1],RGB[2])
    return testImage
def main():
    finalImage = drawImage()
    finalImage.save(os.path.abspath(__file__).strip("src\\pyhton\\Mapper.py")+"on\\res\\world\\worldMap.png")
if __name__ == "__main__":
    main()
