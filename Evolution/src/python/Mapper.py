from PIL import Image
import random
import os
pixelColour = [[0,0,0],[255,0,0],[0,255,0],[0,0,255]]
def drawImage():
    testImage = Image.new("RGB", (2048,2048), (255,255,255))
    pixel = testImage.load()
    for x in range(2048):
        for y in range(2048):
            RGB = pixelColour[random.randint(0,3)]
            pixel[x,y]=(RGB[0],RGB[1],RGB[2])
    return testImage
def main():
    finalImage = drawImage()
    finalImage.save(os.path.abspath(__file__).strip("src\\pyhton\\Mapper.py")+"on\\res\\world\\worldMap.png")
if __name__ == "__main__":
    main()
